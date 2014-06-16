package Tests.integration_test.network;

import java.util.Vector;

import junit.framework.TestCase;

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


public class integration_test_1 extends TestCase   {

	private ClientHandler cl_handler ;
	//private IForum forum;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(){
			public void run() {
				try {
					int port = 6661 , poolSize =3;					
					//create forum components
					Policy p = new Policy();
					Vector<String[]> admins = new  Vector<String[]>(); 
					String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
					admins.add(a1);	admins.add(a2);	admins.add(a3);	
					
					//create forum		
					IForum forum = Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Music-Forum");	
					//this.forum = forum;
					Reactor<ForumMessage> reactor = Reactor.startForumServer(port, poolSize ,forum);
					Thread thread = new Thread(reactor);
					thread.start();			
					Reactor.logger_info("Reactor is ready on port " + reactor.getPort());
					thread.join();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		};		
		
		myThread.start();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
    	//init
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6661); 
        cl_handler = new ClientHandler(connectionHandler);
	}

	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		
		
	}

	@Test
	public void test() {
		assertTrue(cl_handler.login("hadaramran", "12374567"));
		assertTrue(cl_handler.logout());
		assertFalse(cl_handler.logout());
		
		
		assertTrue(cl_handler.register("hodamr", "1234321", "1234321"));
		assertTrue(cl_handler.login("hodamr", "1234321"));
		assertTrue(cl_handler.logout());
		assertFalse(cl_handler.logout());			
				
		assertFalse(cl_handler.register("hodamr", "4", "4"));
		assertTrue(cl_handler.register("alin", "5fs0d2SW", "5fs0d2SW"));
		assertTrue(cl_handler.register("sapir", "gjkg5Ofjh", "gjkg5Ofjh"));
		assertTrue(cl_handler.register("yosi", "hs5F%4j3h", "hs5F%4j3h"));	
		
		assertFalse(cl_handler.register("mima", "hs5F%4j3h", "hs5F%4j3h"));		
		assertFalse(cl_handler.register("mima", "1234321", "1234321"));		

	}

}
