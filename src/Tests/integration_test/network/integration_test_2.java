package Tests.integration_test.network;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.Policy;
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.reactor.Reactor;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Service_Layer.ClientHandler;


public class integration_test_2 implements Runnable {

	private ClientHandler cl_handler ;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(this);		

		
		myThread.start();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
    	//init
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6662); 
        cl_handler = new ClientHandler(connectionHandler);
	}

	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		
	}

	@Test
	public void test() {
		assertTrue(cl_handler.register("alin", "1234321", "1234321"));
		assertTrue(cl_handler.register("sapir", "kkkkkkk", "kkkkkkk"));
		assertTrue(cl_handler.register("yosi", "ffffff", "ffffff"));
		
		//addnig several sub-fourm by admins only
		
		assertTrue(cl_handler.login("bobi_1", "kikdoskd"));
		assertTrue(cl_handler.createSubForum("Sport" , (new String[]{"alin"}) ));
		assertTrue(cl_handler.createSubForum("Music" ,  (new String[]{"sapir"})));
		assertTrue(cl_handler.createSubForum("Animals" ,  (new String[]{"yosi"})));
		assertFalse(cl_handler.createSubForum("Animals" ,  (new String[]{"sapir"})));	
	}
	
	public void run() {
		try {
			int port = 6662 , poolSize =3;					
			//create forum components
			Policy p = new Policy();
			Vector<String[]> admins = new  Vector<String[]>(); 
			String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
			admins.add(a1);	admins.add(a2);	admins.add(a3);	
			
			//create forum		
			IForum forum = Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Music-Forum");	
			Reactor<ForumMessage> reactor = Reactor.startForumServer(port, poolSize ,forum);
			Thread thread = new Thread(reactor);
			thread.start();			
			Reactor.logger_info("Reactor is ready on port " + reactor.getPort());
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
