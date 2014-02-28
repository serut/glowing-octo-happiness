package individu;

import interfaceGraphique.VueElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public interface IElement {
	/**
	 * Retourne le nom de l'element
	 */
	public String getNom();
	
	/**
	 * Retourne le nombre de vies de l'element
	 */
	public int getPV();
	
	/**
	 * Reinitialise le nombre de vies de l'element
	 * @param vie le nouveau nombre de vie
	 */
	
	public void setPV(int vie);
	
	/**
	 * Retourne les references des elements avec lesquels l'element courant a joue
	 */
	public ArrayList<Integer> getElementsConnus();
	
	/**
	 * Ajoute a la liste des elements connus (elements avec lesquels l'objet courant a joue) un nouvel element
	 * @param ref le reference de l'objet avec qui il joue
	 */
	public void ajouterConnu(int ref);
		
	/**
	 * Renvoie les informations concernant l'element courant
	 * @return chaine de caractere contenant au moins le nom de l'element et le nombre de vies tel qu'il sera affiche sur l'interface graphique
	 */
	public String toString();
	
	/**
	 * Ici la strategie est implementé , la fonction renvois vers quel élément se deplacer
	 * @param ve
	 * @param voisins
	 * @return
	 */
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche (VueElement ve, Hashtable<Integer,VueElement> voisins);
}
