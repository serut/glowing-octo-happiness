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

public class Normal extends Combattant{

	/**
	 * @param nom
	 */

	public Normal(String nom) 
	{
		super(nom,33,33,33,3);
	}


	static int nbobjet=0;
	/**
	 * Cherche l'element le plus proche vers lequel se didiger
	 * @param ve l'element courant
	 * @param voisins les elements voisins
	 * @return un hashmap contenant la distance a parcourir vers l'element le plus proche, son identifiant et sa vue
	 */
	public HashMap<Integer, HashMap<Integer,VueElement>> 
	chercherElementProche(VueElement ve, Hashtable<Integer,VueElement> voisins){

		/* On change la strategie de maniere a ce que le plus proche soit en fait le plus loin 
		 * sauf si c'est un objet */
		HashMap<Integer, HashMap<Integer,VueElement>> resultat = new HashMap<Integer, HashMap<Integer,VueElement>>();
		int distPlusProche = 100;
		int refPlusProche = 0;

		HashMap<Integer,VueElement> cible = new HashMap<Integer,VueElement>();

		/* Strategie du random */			
		Set<Integer> tabref = voisins.keySet();
		Integer[] t = new Integer[tabref.size()];
		Random r = new Random();
		int s = tabref.size();
		int ref=0;
		tabref.toArray(t);
		/* On verifie qu'il y a des gens dans notre champs de visions */
		if(tabref.size() >= 1 && t[0]!=null)
		{
			/* On trouve un ele aleatoire dans la liste de voisins */
			int l = r.nextInt(s);
			ref=t[l];
			/* Si l'element est un objet equipement */
			try {
				if (voisins.get(ref).getControleur().getElement() instanceof Item){
					if (nbobjet ==0){
						refPlusProche=ref;
						if(UtilitaireConsole.distanceChebyshev(ve.getPoint(),voisins.get(ref).getPoint()) == 1 ) nbobjet = 1;
					}
					else 
						ref = 0;
				} 
				else
				{


					if(voisins.get(ref).getControleur().getElement().getPV() <= ve.getControleur().getElement().getPV() * 0.5 )
					{
						refPlusProche=ref;
					}
					/* si si le joueur est a une case de nous on l'attaque*/
					else if(UtilitaireConsole.distanceChebyshev(ve.getPoint(),voisins.get(ref).getPoint()) == 1)
					{
						refPlusProche=ref;
					}
				} 

			}
			catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ref!=0)distPlusProche = UtilitaireConsole.distanceChebyshev(ve.getPoint(),voisins.get(ref).getPoint());
		cible.put((Integer)refPlusProche,voisins.get(refPlusProche));
		resultat.put((Integer)distPlusProche, cible);

		return resultat;
	}
}
