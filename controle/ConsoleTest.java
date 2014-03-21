package controle;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import serveur.Arene;

public class ConsoleTest extends TestCase {

	private Thread serveur;
	protected void setUp() throws Exception {
		serveur = new Thread() {
		    public void run() {
				try {
			    	int port=5099;	//par defaut, port=5099
					
					System.out.println("Creation du registre RMI sur le port "+port+"...");
					
					java.rmi.registry.LocateRegistry.createRegistry(port);
				    
				    System.out.println("Creation du serveur sur le port "+port+"...");
				    Arene server;
					server = new Arene(port);
				    
				    while(true) {
				    	System.out.println("[Arene sur "+InetAddress.getLocalHost().getCanonicalHostName()+":"+port+"] "
				    			+DateFormat.getTimeInstance().format(new Date())
				    			+" ("+server.countClients()+" clients)"
				    			+"\n");
				    	try {
				    		Thread.sleep(1000*60); //delai = 1 minute
				    	} catch (InterruptedException e) {}
				    }

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }  
		};
		serveur.start();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
