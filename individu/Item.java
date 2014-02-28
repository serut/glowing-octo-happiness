/**
 * 
 */
package individu;

/**
 * @author Vincent Deniau
 *
 */
public class Item extends Element{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String 	nom;	
	int 	bonusAttaque;		//[0:100]
	int 	bonusBouclier;		//[0:100]
	int 	bonusEsquive;		//[0:100]
	int 	bonusPV;			//[0:100]
	int 	bonusSac;			//[-5:5]

	public Item(String nom, int bonusAttaque, int bonusBouclier, int bonusEsquive, int bonusPV, int bonusSac) {
		
		super(nom);
		this.setPV(1);
		this.bonusAttaque = bonusAttaque;
		this.bonusBouclier = bonusBouclier;
		this.bonusPV = bonusPV;
		this.bonusEsquive = bonusEsquive;
		this.bonusSac = bonusSac;
	}
	
	public int getBonusAttaque(){
		return bonusAttaque;
	}
	
	public int getBonusBouclier(){
		return bonusBouclier;
	}
	
	public int getBonusEsquive(){
		return bonusEsquive;
	}
	
	public int getBonusPV(){
		return bonusPV;
	}
	
	public int getBonusSac(){
		return bonusSac;
	}	

}
