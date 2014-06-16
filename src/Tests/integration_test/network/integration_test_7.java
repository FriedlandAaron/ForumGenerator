package Tests.integration_test.network;

import java.util.Vector;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.reactor.Reactor;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Service_Layer.ClientHandler;


public class integration_test_7 extends TestCase  {
	private ClientHandler cl_handler ;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(){
			public void run() {
				try {
					int port = 6667 , poolSize =3;					
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
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6667); 
        cl_handler = new ClientHandler(connectionHandler);		

        cl_handler.register("alin", "1234321", "1234321");
        cl_handler.register("sapir", "1234321", "1234321");
        cl_handler.register("yosi", "1234321", "1234321");
	}


	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		

	}



	@Test
	public void test() {	

		//addnig several sub-fourm by admins only
		cl_handler.login("bobi_1", "kikdoskd");
		cl_handler.createSubForum("Sport" , (new String[]{"alin"}) );
		cl_handler.createSubForum("Animals" , (new String[]{"yosi"}) );	
		cl_handler.logout();
		
		//search for diff subforum and open new_thred_on them
		Vector<ISubForum> list_sub = cl_handler.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(cl_handler.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0).get_theme()));
		}	
		
		/*

		// Add member types
		cl_handler.login("hadaramran", "12374567");
		assertTrue(	cl_handler.addMemberType("Gold"));
		assertTrue(	cl_handler.addMemberType("Silver"));
		assertTrue(	cl_handler.addMemberType("Bronze"));
		cl_handler.logout();
		
		cl_handler.login("bobi_1", "kikdoskd");		
		assertFalse(cl_handler.addMemberType("Platinum"));
		cl_handler.logout();
		assertFalse(cl_handler.addMemberType("Platinum"));
		
		// Remove member types
		cl_handler.login("hadaramran", "12374567");
		assertTrue(cl_handler.removeMemberType("Bronze"));
		cl_handler.logout();
		cl_handler.login("bobi_1", "kikdoskd");		
		assertFalse(cl_handler.removeMemberType("Silver"));
		cl_handler.logout();

		
		// Check number of member types in forum
		cl_handler.login("hadaramran", "12374567");
		assertTrue(cl_handler.getNumberOfMemberTypes() == 3);
		cl_handler.logout();
		
		cl_handler.login("bobi_1", "kikdoskd");
		assertFalse(cl_handler.getNumberOfMemberTypes() == 3);
		cl_handler.logout();
		*/

		
	}


}
