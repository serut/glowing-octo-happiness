package individu.classe;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import utilitaires.UtilitaireConsole;
import individu.Combattant;
import individu.Item;
import interfaceGraphique.VueElement;

public class Hodor extends Combattant {

	/**
	 * @param nom
	 */

	public Hodor(String nom) 
	{
		super(nom,0,40,85,2);
	}
	
	/*Hodor est idiot Hodor vas aleatoirement aller vers quelqun */
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche(VueElement ve, Hashtable<Integer,VueElement> voisins){
		HashMap<Integer, HashMap<Integer,VueElement>> resultat = new HashMap<Integer, HashMap<Integer,VueElement>>();

		int distPlusProche = 100;
		int refPlusProche = 0;
		/* Strategie du random */			
		Set<Integer> tabref = voisins.keySet();
		Integer[] t = new Integer[tabref.size()];
		Random r = new Random();
		int s = tabref.size();
		tabref.toArray(t);
		if(tabref.size() >= 1 && t[0]!=null)
		{
			/* On trouve un ele aleatoire dans la liste de voisins */
			int l = r.nextInt(s);
			refPlusProche=t[l];
		}
		else refPlusProche=0;

		HashMap<Integer,VueElement> cible = new HashMap<Integer,VueElement>();
		cible.put((Integer)refPlusProche, voisins.get(refPlusProche));
		resultat.put((Integer)distPlusProche, cible);

		return resultat;
	}

}
