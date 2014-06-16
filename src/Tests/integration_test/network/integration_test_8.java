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


public class integration_test_8 extends TestCase {
	private ClientHandler cl_handler ;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(){
			public void run() {
				try {
					int port = 6668 , poolSize =3;					
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
		};		
		myThread.start();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
    	//init
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6668); 
        cl_handler = new ClientHandler(connectionHandler);		

        cl_handler.register("alin", "1234321", "1234321");
        cl_handler.register("sapir", "123", "123");
        cl_handler.register("yosi", "222", "222");
	}


	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		

	}



	@Test
	public void test() {	
		//un automate test
		/*
		assertTrue(cl_handler.register_Email("hodamr", "bamobm", "bamobm" , "hadaramran@gmail.com"));
		System.out.println("enter the submit code:");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String code = s.nextLine();
		assertTrue(cl_handler.submit_code_registertion("hodamr", code)); 
		
		
		assertTrue(cl_handler.login("hodamr", "bamobm"));
		assertTrue(cl_handler.logout());
		assertFalse(cl_handler.logout());		
		
		*/
	}	 

}
