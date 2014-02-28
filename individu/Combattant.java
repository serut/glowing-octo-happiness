/**
 * 
 */
package individu;


import java.util.Random;


/**
 * @author Vincent Deniau
 *
 */
public class Combattant extends Element {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int attaque; 		//Attaque ou force [0:150] du perso          
	private int bouclier;      	//D�fense ou bouclier [0:150] du perso (sera pond�r�)          
	private int esquive;        //Capacit� [0:150] � esquiver un coup (sera pond�r�)                     
	private int sac;			//Capacit� [0:5] en 'cases' du sac

	
	
	/**
	 * @param nom
	 * @param attaque
	 * @param bouclier
	 * @param esquive
	 * @param sac
	 */
	public Combattant(String nom, int attaque, int bouclier, int esquive, int sac) {

		super(nom);		//Au d�part, chaque perso a [25:100] PV	
		this.setPV(new Random().nextInt(75)+25);
		this.attaque = attaque;
		this.bouclier = bouclier;
		this.esquive = esquive;
		this.sac = 2;
	}

	

	/**
	 *
	 */
	public int getVie() {
		return super.getPV();
	}

	/**
	 *
	 */
	public int getAttaque() {
		return this.attaque;
	}
	
	/**
	 *
	 */
	public int getBouclier() {
		return this.bouclier ;
	}
	
	/**
	 * @return the esquive
	 */
	public int getEsquive() {
		return esquive;
	}
	
	/**
	 * @return the sac
	 */
	public int getSac() {
		return sac;
	}

	
	
	
	/**
	 * @param the attaque
	 */
	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}


	/**
	 * @param esquive the esquive to set
	 */
	public void setEsquive(int esquive) {
		this.esquive = esquive;
	}

	/**
	 * @param sac the sac to set
	 */
	public void setSac(int sac) {
		this.sac = sac;
	}



	/**
	 * @param bouclier the bouclier to set
	 */
	public void setBouclier(int bouclier) {
		this.bouclier = bouclier;
	}



	/**
	 * Recalcule le nombre de vies du combattant en enlevant celles qu'il a perdues
	 * @param viePerdue le nombre de vies perdues par le combattant
	 */
	public void perdrePV(int viePerdue) {
		super.setPV(super.getPV() - viePerdue);
	}	
}
