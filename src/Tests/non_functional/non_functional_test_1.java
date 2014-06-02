package Tests.non_functional;

import static org.junit.Assert.*;

import java.util.Vector;

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


public class non_functional_test_1 implements Runnable {
	private static final int CLIENTS = 10;
	private static  int ERRORS =0;
	private int counter = 0;
	private ClientHandler cl_handler_Main;
	
	
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
	}
	@After
	public void tearDown() throws Exception {}

	@Test
	public void test() {	
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6610); 
        cl_handler_Main = new ClientHandler(connectionHandler);	
        
        
        assertTrue(cl_handler_Main.register("alin", "555", "555"));
        assertTrue(cl_handler_Main.register("sapir", "5575", "5575"));
        assertTrue(cl_handler_Main.register("yosi", "5571235", "5571235"));
        //addnig several sub-fourm by admins only
        assertTrue(cl_handler_Main.login("bobi_1", "kikdoskd"));
        assertTrue(cl_handler_Main.createSubForum("Sport" , (new String[]{"alin"}) ));
        assertTrue(cl_handler_Main.createSubForum("Animals" , (new String[]{"yosi"}) ));
        assertTrue(cl_handler_Main.createSubForum("Music" , (new String[]{"sapir"}) ));	

        assertTrue(cl_handler_Main.logout());
        
        
        
		Runnable t ;
		Vector<Thread> myThreads = new Vector<Thread>();
		for(int i=0 ; i< CLIENTS ; i++){
			t = new Runnable(){
				private ClientHandler cl_handler;
				private final int id = counter++ ;			
				public void run() {
			        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6610); 
			        cl_handler = new ClientHandler(connectionHandler);		
			        assertTrue(cl_handler.register("hadar_client_"+this.id, "mimamo"+this.id, "mimamo"+this.id));
			        assertTrue(cl_handler.register("hod_client_"+this.id, "mimamoi"+this.id, "mimamoi"+this.id));
			        
			        
			        assertTrue(cl_handler.login("hod_client_"+this.id, "mimamoi"+this.id));
			        
					ISubForum sub = cl_handler.search_subforum("Theme", "Sport");
					ISubForum sub_2 = cl_handler.search_subforum("Theme", "Animals");

					assertTrue(sub!= null);	
					assertTrue(sub_2!= null);	
					
					assertTrue(cl_handler.create_thread("hod" , "lalalskls slkd ajhs d " , sub));
					if(this.id %2 ==0)
						assertTrue(cl_handler.create_thread("hadar" , "lalalskls slkd ajhs d " , sub_2));

					assertTrue(cl_handler.logout());
					assertTrue(cl_handler.login("bobi_2", "ksisodhah"));
					
					ISubForum sub_3 = cl_handler.search_subforum("Theme", "Music");
					assertTrue(sub_3!= null);	
					assertTrue(cl_handler.create_thread("hod" , "lalalskls slkd ajhs d " , sub_3));
			        
			        cl_handler.close_connect();		
				}
				private void assertTrue(boolean register) {
					if(!register)
						ERRORS++;
				}
			};
			myThreads.add( new Thread(t));
		}
		for(int i=0 ; i< myThreads.size() ; i++){
			myThreads.get(i).run();
		}
		
		for(int i=0 ; i< myThreads.size() ; i++){
			try {
				myThreads.get(i).join();
			} catch (InterruptedException e) {
			}
		}
		assertTrue(ERRORS==0);
		assertTrue(cl_handler_Main.numPostsSubForum("Sport")==CLIENTS);
		assertTrue(cl_handler_Main.numPostsSubForum("Music")==CLIENTS);

		assertTrue(cl_handler_Main.numPostsSubForum("Animals")==(CLIENTS/2 +CLIENTS%2));
		assertTrue(cl_handler_Main.numPostsUser("bobi_2")==CLIENTS);

	}


	public void run() {
		try {
			int port = 6610 , poolSize =CLIENTS+3;					
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
