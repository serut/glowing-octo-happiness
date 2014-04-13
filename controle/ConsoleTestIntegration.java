package controle;

import static org.junit.Assert.*;
import individu.Combattant;
import individu.Item;
import individu.classe.Guerrier;
import individu.classe.Hodor;
import individu.classe.Marchand;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import junit.framework.TestCase;

import org.junit.Test;

public class ConsoleTestIntegration extends TestCase {
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
			joueur1 = new Console(guerrier, r1-12, r2);
			refJoueur1 = joueur1.getVueElement().getRef();
			
			
			Combattant hodor = new Hodor("Guerrier");
			joueur2 = new Console(hodor, r1-11, r2);
			refJoueur2 = joueur2.getVueElement().getRef();
			

			Combattant marchand = new Marchand("Guerrier");
			joueur3 = new Console(marchand, r1+8, r2);
			refJoueur3 = joueur3.getVueElement().getRef();
			
			
			
			int r3 = new Random().nextInt(100);
			int r4 = new Random().nextInt(100);
			int r5 = new Random().nextInt(100);
			int r6 = new Random().nextInt(100);
			int r7 = new Random().nextInt(10)-5;
			Item pomme = new Item("× A:"+r3+" B:"+r4+" E:"+r5+" V:"+r6+" S:"+r7, r3, r4, r5, r6, r7);
			objet = new Console(pomme, r1+7, r2);
			refObjet = objet.getVueElement().getRef();
		} catch (Exception e) {
			fail();
		} 
	}

	@Test
	public void test1RunObjet() {
		try {
			joueur3.run();
			joueur3.run();
			joueur3.run();
			joueur3.run();
			System.out.println(objet.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void test2RunObjet() {
		try {
			joueur2.run();
			joueur2.run();
			joueur1.run();
			joueur1.run();
			System.out.println(objet.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
