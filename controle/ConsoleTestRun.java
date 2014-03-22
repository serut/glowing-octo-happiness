package controle;

import individu.Combattant;
import individu.Item;
import individu.classe.Guerrier;
import individu.classe.Hodor;
import individu.classe.Marchand;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

public class ConsoleTestRun extends TestCase {
	/**
	 * Modification du code du projet pour que les tests fonctionnent :
	 * - ajout sur Console.seDirigerVers() d'un try catch
	 * - ajout de Console.getVoisin() pour simuler un voisin dont le point serait null
	 */
	private Console joueur1;
	private int refJoueur1;
	private Console joueur2;
	private int refJoueur2;
	private Console joueur3;
	private int refJoueur3;
	private Console joueur4;
	private int refJoueur4;
	private Console joueur5;
	private int refJoueur5;
	private Console objet;
	private int refObjet;
	@Override
	protected void setUp() throws Exception {
		int r1 = 50;
		int r2 = 50;
		try {
			Combattant guerrier = new Guerrier("Guerrier");
			joueur1 = new Console(guerrier, r1, r2);
			refJoueur1 = joueur1.getVueElement().getRef();
			
			
			Combattant hodor = new Hodor("Guerrier");
			joueur2 = new Console(hodor, r1+1, r2);
			refJoueur2 = joueur2.getVueElement().getRef();
			

			Combattant marchand = new Marchand("Guerrier");
			joueur3 = new Console(marchand, r1+5, r2);
			refJoueur3 = joueur3.getVueElement().getRef();
			
			
			Combattant guerrier2 = new Guerrier("Guerrier");
			joueur4 = new Console(guerrier2, r1+4, r2+1);
			refJoueur4 = joueur4.getVueElement().getRef();
			
			Combattant guerrierSeul = new Guerrier("Guerrier");
			joueur5 = new Console(guerrierSeul, r1+15, r2+10);
			refJoueur5 = joueur5.getVueElement().getRef();
			
			
			int r3 = new Random().nextInt(100);
			int r4 = new Random().nextInt(100);
			int r5 = new Random().nextInt(100);
			int r6 = new Random().nextInt(100);
			int r7 = new Random().nextInt(10)-5;
			Item pomme = new Item("� A:"+r3+" B:"+r4+" E:"+r5+" V:"+r6+" S:"+r7, r3, r4, r5, r6, r7);
			objet = new Console(pomme, r1+6, r2);
			refObjet = objet.getVueElement().getRef();
		} catch (Exception e) {
			fail();
		} 
	}

	@Test
	public void test1RunObjet() {
		try {
			objet.run();
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test2RunRammasserObjet() {
		try {
			joueur3.run();
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test3RunCombatPourUnHodor() {
		try {
			joueur2.run(); // Il ramasse l'objet
			joueur2.getVoisin(refJoueur1).setPoint(null);
			joueur2.seDirigerVers(refJoueur1);
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test4RunCombat() {
		try {
			joueur1.run(); // Il ramasse l'objet
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void test5RunErrer() {
		try {
			joueur5.run(); // Il ramasse l'objet
		} catch (Exception e) {
			fail();
		}
	}

}
