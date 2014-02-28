/**
 * 
 */
package individu.classe;

import java.awt.Point;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import utilitaires.UtilitaireConsole;
import individu.Combattant;
import interfaceGraphique.VueElement;

/**
 * @author daolf
 *	
 */
public class Guerrier extends Combattant {

	/**
	 * @param nom
	 */
	public Guerrier(String nom) 
	{
		super(nom,0,25,25,2);
		int attaque =(new Random().nextInt(75)+25);
		super.setAttaque(attaque);
	}
/*Ici la strategie consiste simplement en aller vers le plus prés*/
	
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche(VueElement ve, Hashtable<Integer,VueElement> voisins){
		HashMap<Integer, HashMap<Integer,VueElement>> resultat = new HashMap<Integer, HashMap<Integer,VueElement>>();

		int distPlusProche = 100;
		int refPlusProche = 0;

		for(Integer ref:voisins.keySet()) {
			Point target = voisins.get(ref).getPoint();
			if (UtilitaireConsole.distanceChebyshev(ve.getPoint(),target)<distPlusProche) {
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
