package individu.classe;



import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;

import utilitaires.UtilitaireConsole;
import individu.Combattant;
import individu.Item;
import interfaceGraphique.VueElement;

public class Marchand extends Combattant {

	/**
	 * @param nom
	 */

	public Marchand(String nom) 
	{
		super(nom,25,25,25,20);
	}

	
	
/*Ici la strategie consiste a aller vers tous les objets le plus rapidement possible et n'attaquer que si le combattant a moins de 50% de la 
 * vie du marchand*/
	
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche(VueElement ve, Hashtable<Integer,VueElement> voisins){
		HashMap<Integer, HashMap<Integer,VueElement>> resultat = new HashMap<Integer, HashMap<Integer,VueElement>>();

		int distPlusProche = 100;
		int refPlusProche = 0;
		boolean yAller = false;

		for(Integer ref:voisins.keySet()) {
			Point target = voisins.get(ref).getPoint();
			/* Ici on regarde si on a un item ou un mec avec 50% au plus de nos pv */
			try {
				yAller = (voisins.get(ref).getControleur().getElement() instanceof Item ) 
						|| (voisins.get(ref).getControleur().getElement().getPV() < this.getPV()*0.5);
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
