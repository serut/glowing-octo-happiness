package controle;

import individu.Element;
import individu.Item;
import individu.Combattant;
import individu.classe.Hodor;
import interfaceGraphique.VueElement;

import java.awt.Point;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import serveur.IArene;
import utilitaires.UtilitaireConsole;

/**
 * 
 * Se connecte au serveur avec un Element et sa VueElement.
 * "run" permet a l'Element/VueElement de se deplacer
 *
 */
public class Console extends UnicastRemoteObject implements IConsole {
	
	private static final long serialVersionUID = 1L;
	private static final int port=5099;	              //port par defaut pour communiquer avec le serveur RMI
	private Remote serveur = null;                    //le serveur avec lequel le controleur communique
	private VueElement ve = null;                     //la vue de l'element (pour l'interface graphique)
	private Element elem = null;                      //l'element pour lequel le controleur est cree
	private Hashtable<Integer,VueElement> voisins;    //les vues des voisins sur l'interface graphique
	private Point pointErrance;                       //le point ou aller errer
	private int refRMI;                               //la reference (entiere) attribuee par le serveur a la connexion
	
	
	/**
	 * Constructeur
	 * @param elem l'element pour lequel le controleur est cree
	 * @param dx la position initiale de l'element sur l'ordonnee (interface graphique)
	 * @param dy la position initiale de l'element sur l'abscisse (interface graphique)
	 * @throws RemoteException
	 */
	public Console(Element elem, int dx, int dy) throws RemoteException {
		 //appel au constructeur de la super-classe -> il peut etre implicite
		super();
		try{
			//initialisation de l'element pour lequel le controleur est cree
			this.elem=elem;
			
			//position initiale aleatoire
			//Random r=new Random();
			//Point pos = new Point(r.nextInt(100),r.nextInt(100));
			
			//Creation de la position initiale de la vue de l'element sur l'interface graphique
			Point pos = new Point(dx,dy);
			
			//initialisation des voisins (vide avant la connexion)
			voisins = new Hashtable<Integer,VueElement>();
			
			//preparation connexion au serveur
			//handshake/enregistrement RMI
			serveur=Naming.lookup("rmi://localhost:"+port+"/Arene");
			serveur.toString();
			
			//initialisation de la reference du controleur sur le serveur
			this.refRMI=((IArene) serveur).allocateRef();
			Naming.rebind("rmi://localhost:"+port+"/Console"+refRMI,this);
			
			//initialisation de la vue sur l'element
			ve=new VueElement(refRMI, pos, this, "Atterrissage...");
						
			//connexion au serveur
			((IArene) serveur).connect(ve);
			
			//affiche message si succes
			System.out.println("Console connectee ["+refRMI+"]");
 		} catch (Exception e) {
  			System.out.println("Erreur : la console n'a pa pu etre creee !\nCause : "+e.getCause());
  			e.printStackTrace();
 		}
	}


	/**
	 * Permet au serveur de faire "jouer" un tour a l'element.
	 * Calcule ses voisins (donnes par le serveur), cherche le plus proche, s'il est a proximite, lance l'interaction sinon se dirige vers lui (s'il existe un plus proche)
	 * Cette methode est execute chaque seconde  
	 */
	public void run() throws RemoteException {
		if(ve.getControleur().getElement() instanceof Combattant) {

			//decremente sa duree de vie
			ve.decrTTL(); 
			
			//mets a jour ses voisins 
			voisins = ((IArene) serveur).voisins(ve.getPoint(),refRMI);	
			
		
			// Recherche du plus proche, sinon erreur
			
			HashMap<Integer, HashMap<Integer,VueElement>> resultat = ve.getControleur().getElement().chercherElementProche(ve, voisins);
			this.parler("run");
			int distPlusProche = resultat.keySet().iterator().next();
			int refPlusProche =  resultat.get(distPlusProche).keySet().iterator().next();
			VueElement cible = resultat.get(distPlusProche).get(refPlusProche);
			
			
			if (distPlusProche<=1) {
				if(cible.getControleur().getElement() instanceof Item) {
					parler("Je tente de ramasser un objet");
					((IArene) serveur).ramasser(refRMI, refPlusProche);
				}
				else {
					/*On verifie que l'element n'est pas de la classe Hodor pour pouvoir attaquer*/
					if(cible.getControleur().getElement() instanceof Combattant &&
					!(this.getElement() instanceof Hodor)) {
						parler("Je combat "+refPlusProche);
						((IArene) serveur).interaction(refRMI, refPlusProche);
					}
				}
			}
			//sinon
			else { 
				if (refPlusProche!=0) {
					parler("Je me dirige vers "+refPlusProche);
				}
				else parler("J'erre..."); 
			
				//l'element courant se deplace vers le plus proche (s'il existe) sinon il erre
				seDirigerVers(refPlusProche);	
			}
		}
	}
	
	/**
	 * Deplace ce sujet d'une case en direction du sujet dont la reference est donnee en parametre
	 * @param ref la reference de l'element cible
	 */
	public void seDirigerVers(int ref) {
		Point pvers;
		
		//si la cible est l'element meme, il reste sur place
		if (ref==ve.getRef()) return;

		//s'il n'y a pas de reference la plus proche
		if (ref==0) {
			//si le point ou l'element se trouve est le point d'errance precedemment defini
			if ((pointErrance!=null) && (pointErrance.equals(ve.getPoint()))) { 
				//le point d'errance est reinitialise
				pointErrance=null;
			}
			if (pointErrance==null) {
				//initialisation aleatoire
				Random r=new Random();
				pointErrance=new Point(r.nextInt(100), r.nextInt(100));
			}
			//la cible devient le nouveau point d'errance
			pvers=pointErrance;
		} 
		//sinon la cible devient le point sur lequel se trouve l'element le plus proche
		else {
			pvers=voisins.get(ref).getPoint();
		}
		
		//si l'element n'existe plus (cas posible: deconnexion du serveur), le point reste sur place
		if (pvers==null) return;
		
		//calcule la direction pour atteindre la ref (+1/-1 par rapport a la position courante)
		int dx=(int) (pvers.getX()-ve.getPoint().x);
		if (dx!=0)	
			dx=dx/Math.abs(dx);
		int dy=(int) (pvers.getY()-ve.getPoint().y);
		if (dy!=0)  
			dy=dy/Math.abs(dy);
		
		//instancie le point destination
		Point dest = new Point(ve.getPoint().x+dx,ve.getPoint().y+dy);
		
		//si le point destination est libre
		if (UtilitaireConsole.caseVide(dest,voisins)) {		
			//l'element courant se deplace
			ve.setPoint(dest);
		} 
		else {
			//cherche la case libre la plus proche dans la direction de la cible
			Point top = UtilitaireConsole.meilleurPoint(ve.getPoint(),dest,voisins);
			//deplace l'element courant sur celle-la
			ve.setPoint(top);
		}
	}

	/**
	 * Appelle par le serveur pour faire la MAJ du sujet 
	 */
	public VueElement update() throws RemoteException {
		VueElement aux=(VueElement) ve.clone();
		aux.setPhrase(ve.getPhrase()); 
		return aux;
	}

	/**
	 * Deconnexion du controleur du serveur
	 * @param cause le message a afficher comme cause de la deconnexion
	 */
	public void shutDown(String cause) throws RemoteException {
		System.out.println("Console "+refRMI+" deconnectee : "+cause);
		System.exit(1);
	}

	public Element getElement() throws RemoteException {
		return elem;
	}
	
	public VueElement getVueElement() throws RemoteException {
		return ve;
	}
	
	public void parler(String s) throws RemoteException {
		ve.setPhrase(s);	
	}
	

	public void perdrePV(int viePerdue) throws RemoteException {
		if (this.elem.getPV()-viePerdue<0) this.elem.setPV(0);
		else this.elem.setPV(this.elem.getPV()-viePerdue);
		System.out.println("Ouch, j'ai perdu " + viePerdue + " points de vie. Il me reste " + this.elem.getPV() + " points de vie.");		
	}
	
	public void ramasserObjet(IConsole objet) throws RemoteException {
		
		int bonusAttaque,bonusBouclier,bonusPV,bonusEsquive,bonusSac,
		newAttaque, newBouclier, newPV, newEsquive, newSac;
		
		
		bonusAttaque=((Item)(objet.getElement())).getBonusAttaque();
		if (((Combattant)this.elem).getAttaque()+((Item)(objet.getElement())).getBonusAttaque()>150)
			newAttaque=150;
			else newAttaque = ((Combattant)this.elem).getAttaque()+bonusAttaque;
		((Combattant)this.elem).setAttaque(newAttaque);
		
		bonusBouclier=((Item)(objet.getElement())).getBonusBouclier();
		if (((Combattant)this.elem).getBouclier()+((Item)(objet.getElement())).getBonusBouclier()>150)
			newBouclier=150;
			else newBouclier = ((Combattant)this.elem).getBouclier()+bonusBouclier;
		((Combattant)this.elem).setBouclier(newBouclier);

		bonusPV=((Item)(objet.getElement())).getBonusPV();
		if (((Combattant)this.elem).getPV()+((Item)(objet.getElement())).getBonusPV()>150)
			newPV=150;
			else newPV = ((Combattant)this.elem).getPV()+bonusPV;
		((Combattant)this.elem).setPV(newPV);
		
		bonusEsquive=((Item)(objet.getElement())).getBonusEsquive();
		if (((Combattant)this.elem).getEsquive()+((Item)(objet.getElement())).getBonusEsquive()>150)
			newEsquive=150;
			else newEsquive = ((Combattant)this.elem).getEsquive()+bonusEsquive;
		((Combattant)this.elem).setEsquive(newEsquive);

		bonusSac=((Item)(objet.getElement())).getBonusSac();
		if (((Combattant)this.elem).getSac()+((Item)(objet.getElement())).getBonusSac()>5)
			newSac=5;
			else newSac = ((Combattant)this.elem).getSac()+bonusSac;
		((Combattant)this.elem).setSac(newSac);
	}
		
	
	public String afficher() throws RemoteException{
		return this.elem.toString();
	}

	/**
	 * Ajout l'element dans la liste des elements connus (combattants et Items)
	 * @param ref l'element a ajouter
	 */
	public void ajouterConnu(int ref) throws RemoteException {
		elem.ajouterConnu(ref);
	}
		
}
