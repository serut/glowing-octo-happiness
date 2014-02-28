package interaction;

import individu.Combattant;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Random;

import controle.IConsole;
import serveur.Arene;

public class DuelBasic implements IDuel {

	private Arene arene;          //l'arene sur laquelle le jeu a lieu
	private Remote refAttaquant;  //la reference de l'attaquant
	private Remote refDefenseur;  //la reference du defenseur
	
	/**
	 * Constructeur
	 * @param arene l'arene du jeu
	 * @param refAttaquant la reference de l'attaquant
	 * @param refDefenseur la reference du defenseur
	 * @throws RemoteException
	 */
	public DuelBasic(Arene arene, Remote refAttaquant, Remote refDefenseur) throws RemoteException{
		this.arene = arene;
		this.refAttaquant = refAttaquant;
		this.refDefenseur = refDefenseur;
	}

	/**
	 * Realise le combat 
	 */
	public int realiserCombat() throws RemoteException {
		Remote ratt = this.getRefAttaquant();
		IConsole catt = (IConsole) ratt;
		Combattant att = (Combattant) (catt).getElement();
		int attaqueAtt = att.getAttaque();
		 
		
		Remote rdef = this.getRefDefenseur();	
		IConsole cdef = (IConsole) rdef;
		Combattant def = (Combattant) (cdef).getElement();
		int attaqueDef = def.getAttaque();
		int bouclierDef = def.getBouclier();
		int esquiveDef = def.getEsquive();
		
		
		/* Règle de combat : 
		 * ----------------
		 *  
		 *  On tire au sort le premier joueur à frapper.
		 *  Le nombre de points enlevés par coups répond à la règle
		 *  	Coup = MAX(rand[1:25],AttaqueAtt-AttaqueDef) - BouclierDef*rand[0:1/2]
		 *	Le coup est esquivé si rand[0:100]<=2*EsquiveDef
		 */
		
		int coup;
		int r1 = (new Random()).nextInt(100);
		int r2 = (new Random()).nextInt(15)+1;	/* J'ai pitié pour les faibles */
		int r3 = (new Random()).nextInt(100);
		int r4 = (new Random()).nextInt(500);
		if(r4<=esquiveDef) {
			coup=0; 
			cdef.parler("HOP J'ai esquivé !");
		}
		else { coup = max(r2,attaqueAtt-attaqueDef-(int)(bouclierDef*0.1*r3));
			}
		
		if(r1<=50){
			catt.perdrePV(coup);
			cdef.parler("Je viens de te mettre "+coup+"!");
		}
		else{
			cdef.perdrePV(coup);
			catt.parler("Je viens de te mettre "+coup+"!");
		}
		
		return 0;
	}


	private int max(int a, int b) {
		if (a>b) return a;
		else return b;
	}

	public Remote getRefAttaquant() throws RemoteException {
		return refAttaquant;
	}

	public Remote getRefDefenseur() throws RemoteException {
		return refDefenseur;
	}

}
