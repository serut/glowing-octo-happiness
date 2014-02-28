/**
 * 
 */
package individu;

import interfaceGraphique.VueElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;


public class Element implements IElement, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;                                                      //le nom de l'element
	private int PV;                                                         //le nombre de vies de l'element
	private ArrayList<Integer> elementsConnus = new ArrayList<Integer>();    //les references des elements avec lesquels on a joue
	
	/**
	 * Constructeur
	 * @param nom le nom de l'element a creer
	 * le nombre de vie est défini à la création de l'objet
	 */
	public Element(String nom){
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}

	public int getPV() {
		return this.PV;
	}
	
	public void setPV(int vie){
		this.PV = vie;
	}
	
	public ArrayList<Integer> getElementsConnus() {
		return this.elementsConnus;
	}

	public void ajouterConnu(int ref) {
		elementsConnus.add(ref);		
	}

	public String toString(){
		return this.getNom();
	}
	public HashMap<Integer, HashMap<Integer,VueElement>> chercherElementProche (VueElement ve, Hashtable<Integer,VueElement> voisins)
	{
		return null;
	};
}
