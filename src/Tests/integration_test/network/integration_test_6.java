package Tests.integration_test.network;

import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.FourmUser.IUser;
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.reactor.Reactor;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Service_Layer.ClientHandler;


public class integration_test_6 implements Runnable {
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
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6666); 
        cl_handler = new ClientHandler(connectionHandler);		

		assertTrue(cl_handler.register("alin", "1234321", "1234321"));
		assertTrue(cl_handler.register("sapir", "lllll", "lllll"));
		assertTrue(cl_handler.register("yosi", "mkn", "mkn"));
		
        cl_handler.login("bobi_1", "kikdoskd");
        cl_handler.createSubForum("Sport" , (new String[]{"alin"}) );
        cl_handler.createSubForum("Sport" , (new String[]{"alin"}) );
        cl_handler.createSubForum("Animals" , (new String[]{"yosi"}) );	
        cl_handler.logout();		
	}


	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		

	}


	@Test
	public void test() {	
		
		//search for diff subforum and open new_thred_on them
		Vector<ISubForum> list_sub = cl_handler.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(cl_handler.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0)));
		}	

		// Adding a reply post to the existing post
		ISubForum sub_sport = cl_handler.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();
		if(threads.size()>0) {
			assertTrue(cl_handler.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		}

		ISubForum sub_animals = cl_handler.search_subforum("Theme", "Animals");		 
		 
		 //add complaint
		 IUser moderator = sub_animals.getModerator("yosi");
		 assertTrue(moderator!=null);
		 assertTrue(cl_handler.create_thread("hadar" , "lkjhas" , sub_animals));
		 assertTrue(cl_handler.addcomplaintModerator(sub_animals , "yosi" , "theme_complient" , "body_complient"));
		 
	}
	
	public void run() {
		try {
			int port = 6666 , poolSize =3;					
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
