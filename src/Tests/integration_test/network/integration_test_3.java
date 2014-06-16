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


public class integration_test_3 extends TestCase {

	private ClientHandler cl_handler ;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(){
			public void run() {
				try {
					int port = 6663 , poolSize =3;					
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
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6663); 
        cl_handler = new ClientHandler(connectionHandler);
	}

	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		
	}

	@Test
	public void test() {

		assertTrue(cl_handler.register("alin", "1234321", "1234321"));
		assertTrue(cl_handler.register("sapir", "lllll", "lllll"));
		assertTrue(cl_handler.register("yosi", "mkn", "mkn"));
		
		//addnig several sub-fourm by admins only
		cl_handler.login("bobi_1", "kikdoskd");
		cl_handler.createSubForum("Sport" , (new String[]{"alin"}) );
		cl_handler.createSubForum("Music" ,  (new String[]{"sapir"}));
		cl_handler.createSubForum("Animals" ,  (new String[]{"yosi"}));
		cl_handler.createSubForum("Animals" ,  (new String[]{"sapir"}));
		cl_handler.logout();
		
		Vector<ISubForum> list_sub = cl_handler.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(cl_handler.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0).get_theme()));
		}
		
		ISubForum sub_animals = cl_handler.search_subforum("Theme","Animals");
		ISubForum sub_Music = cl_handler.search_subforum("Moderator","sapir");
		

		
		assertTrue(sub_animals.get_theme().equals("Animals"));		
		assertTrue(sub_Music.get_theme().equals("Music"));


	 //delete sub forum 
		 assertTrue(cl_handler.login("bobi_1", "kikdoskd"));
		 assertTrue(cl_handler.deleteSubForum(sub_Music.get_theme()));
		 assertTrue(cl_handler.logout());
		 sub_Music = cl_handler.search_subforum("Theme", "Music");
		 assertTrue(sub_Music == null);
	}

}

