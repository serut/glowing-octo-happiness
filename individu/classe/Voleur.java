package individu.classe;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;

import utilitaires.UtilitaireConsole;
import individu.Combattant;
import individu.Item;
import interfaceGraphique.VueElement;

public class Voleur extends Combattant {

	/**
	 * @param nom
	 */

	public Voleur(String nom) 
	{
		super(nom,25,25,75,2);
	}
	/*Le voleur lui n'attaque que les personnages ayant une faible vie*/
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche(VueElement ve, Hashtable<Integer,VueElement> voisins){
		HashMap<Integer, HashMap<Integer,VueElement>> resultat = new HashMap<Integer, HashMap<Integer,VueElement>>();

		int distPlusProche = 100;
		int refPlusProche = 0;
		boolean yAller = false;

		for(Integer ref:voisins.keySet()) {
			Point target = voisins.get(ref).getPoint();
			/* Ici on regarde si on a un item ou un mec avec 30 au plus de nos pv */
			try {
				/*TODO regarder si l'item peut etre recuperer ou pas */
				yAller =  voisins.get(ref).getControleur().getElement().getPV() < this.getPV()*0.3;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (UtilitaireConsole.distanceChebyshev(ve.getPoint(),target)<distPlusProche && yAller) {
				distPlusProche = UtilitaireConsole.distanceChebyshev(ve.getPoint(),target);
				refPlusProche = ref;
			}
		}

		HashMap<Integer,VueElement> cible = new HashMap<Integer,VueElement>();
		cible.put((Integer)refPlusProche, voisins.get(refPlusProche));
		resultat.put((Integer)distPlusProche, cible);

		return resultat;
	}

}
