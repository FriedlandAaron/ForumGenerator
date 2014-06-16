package Tests.Acceptance_testing;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.FourmUser.IUser;
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.reactor.Reactor;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Service_Layer.ClientHandler;
import Service_Layer.IUserHandler;


public class clientTesting extends ForumTest {

	private static int port_con = 8100 ; 
	private IForum forum;
	private IUserHandler userHandler;
	private int port ;
	@Before
	public void setUp() {
		super.setUp();
		try {
			port = port_con;
			port_con++;
			int poolSize = 3;

			//create forum components
			Policy p = new Policy();
			Vector<String[]> admins = new  Vector<String[]>(); 
			String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
			admins.add(a1);
			admins.add(a2);
			admins.add(a3);	

			//create forum		
			forum = Forum.createForum( "aaronf" , "987654321" ,p ,admins, "Music-Forum");	
			Reactor<ForumMessage> reactor = Reactor.startForumServer(port, poolSize ,forum);
			Thread thread = new Thread(reactor);
			thread.start();			
			Reactor.logger_info("Reactor is ready on port " + reactor.getPort());
		} 
		catch (Exception e) {
			assertTrue(false);
		}
		setUpUserHandler();
		userHandler.register("alin", "1234321", "1234321");
		userHandler.register("sapir", "lllll", "lllll");
		userHandler.register("yosi", "mkn", "mkn");
	}
	
	@Before
	public void setUpUserHandler() {
		System.out.println("Client testing: setting up user handler");
		ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) port); 
		userHandler = new ClientHandler(connectionHandler);
	}
	
	@After
	public void tearDown() {
		userHandler.close_connect();
	}

	@Test
	public void testAddNewPost() {
	}
	
	@Test
	public void testLogin() {
		assertTrue(userHandler.login("aaronf", "987654321"));
		assertTrue(userHandler.logout());
		assertFalse(userHandler.logout());
		assertFalse(false);
	}
	
	@Test
	public void testRegister() {
		assertFalse(userHandler.register("aaronf", "987654321", "987654321"));
		assertTrue(userHandler.register("friedlan", "54321", "54321"));
		assertTrue(userHandler.login("friedlan", "54321"));
		assertTrue(userHandler.logout());
		assertFalse(userHandler.logout());
	}
	
	@Test
	public void testCreateSearchAndDeleteSubForum() {

		// Create subforums
		assertTrue(userHandler.login("bobi_1", "kikdoskd"));
		assertTrue(userHandler.createSubForum("Sport", (new String[]{"alin"})));
		assertTrue(userHandler.createSubForum("Music", (new String[]{"sapir"})));
		assertTrue(userHandler.createSubForum("Animals", (new String[]{"yosi"})));
		assertFalse(userHandler.createSubForum("Animals", (new String[]{})));
		
		// Search subforums by theme and by moderator
		ISubForum sportSubForum = userHandler.search_subforum("Moderator", "alin");
		ISubForum animalsSubForum = userHandler.search_subforum("Theme", "Animals");
		ISubForum musicSubForum = userHandler.search_subforum("Moderator", "sapir");
		
		assertTrue(animalsSubForum.get_theme().equals("Animals"));		
		assertTrue(musicSubForum.get_theme().equals("Music"));
		
		// Delete subforums 
		assertTrue(userHandler.deleteSubForum(musicSubForum.get_theme()));
		assertTrue(userHandler.deleteSubForum(animalsSubForum.get_theme()));
		assertTrue(userHandler.deleteSubForum(sportSubForum.get_theme()));
		assertTrue(userHandler.logout());
		musicSubForum = userHandler.search_subforum("Theme", "Music");
		assertTrue(musicSubForum == null);
	}
	
	@Test
	public void testCreatePostAndReplyPost() {
		userHandler.login("bobi_1", "kikdoskd");
		userHandler.createSubForum("Sport" , (new String[]{"alin"}) );
        
		Vector<ISubForum> list_sub = userHandler.show_sub_forum();
		if(list_sub.size()>0){
			// Create post
			assertTrue(userHandler.create_thread("Hello" , "This is a test post" , list_sub.get(0).get_theme()));
		}	

		// Adding a reply post to the existing post
		ISubForum sportSubForum = userHandler.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sportSubForum.showThreads();
		if(threads.size()>0) {
			assertTrue(userHandler.createReplyPost("Hi", "This is a reply post", threads.get(0)));
		}
		assertTrue(userHandler.deleteSubForum(sportSubForum.get_theme()));
        userHandler.logout();
	}
	
	@Test
	public void testChangePolicyAndDeletePosts() {
		userHandler.login("bobi_1", "kikdoskd");
		userHandler.createSubForum("Sport", (new String[]{"alin"}));
		
		// Add new thread to subforum
		Vector<ISubForum> list_sub = userHandler.show_sub_forum();
		if(list_sub.size() > 0){
			assertTrue(userHandler.create_thread("Hi", "This is a test thread" , list_sub.get(0).get_theme()));
		}	

		// Adding a reply post to the existing post
		ISubForum sub_sport = userHandler.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();
		if(threads.size() > 0) {
			assertTrue(userHandler.createReplyPost("Hello", "This is a reply post", threads.get(0)));
		}

		// Change policy
		Policy p2 = new Policy();
		assertTrue(userHandler.changePolicy(p2));

	}
	
	@Test
	public void testAddComplaint() {
		userHandler.login("bobi_1", "kikdoskd");
		userHandler.createSubForum("Sport" , (new String[]{"alin"}) );

		Vector<ISubForum> list_sub = userHandler.show_sub_forum();
		if(list_sub.size() > 0){
			assertTrue(userHandler.create_thread("Hi" , "This is a test thread" , list_sub.get(0).get_theme()));
		}	

		// Adding a reply post to the existing post
		ISubForum sportSubForum = userHandler.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sportSubForum.showThreads();
		if(threads.size() > 0) {
			assertTrue(userHandler.createReplyPost("Hello", "This is a reply thread", threads.get(0)));
		}
		
		// Add complaint
		IUser moderator = sportSubForum.getModerator("alin");
		assertTrue(moderator != null);
		assertTrue(userHandler.addcomplaintModerator(sportSubForum , "alin" , "theme_complient" , "body_complient"));
		
		userHandler.deleteSubForum(sportSubForum.get_theme());
		assertTrue(userHandler.logout());
	}
	

	
	@Test
	public void testSubForumAndRemovingModerators() {
		
		assertTrue(userHandler.numSubForum() == 0);
		assertTrue(userHandler.numSharedModeratorsSubForum()==0);

		// Making new subforum
		assertTrue(userHandler.login("bobi_1", "kikdoskd"));
		assertTrue(userHandler.createSubForum("Sport" , (new String[]{"alin"})));

		//numSubForum
		assertTrue(userHandler.numSubForum() == 1);
		assertTrue(userHandler.createSubForum("Animals", (new String[]{"yosi"})));	

		//add moderator
		assertTrue(userHandler.addModerator("Sport", "sapir"));
		assertTrue(userHandler.addModerator("Animals", "sapir"));

		//numSubForum
		assertTrue(userHandler.numSubForum() == 2);

		//numSharedModeratorsSubForum
		assertTrue(userHandler.numSharedModeratorsSubForum()== 1);

		//remove moderator
		assertTrue(userHandler.removeModerator("Animals", "sapir"));
		assertTrue(userHandler.numSharedModeratorsSubForum() == 0);
		assertTrue(userHandler.removeModerator("Sport", "sapir"));
		assertTrue(userHandler.addModerator("Animals", "sapir"));
		assertTrue(userHandler.numSharedModeratorsSubForum() == 0);
		assertTrue(userHandler.addModerator("Sport", "sapir"));
		assertTrue(userHandler.numSharedModeratorsSubForum() == 1);

		

		ISubForum sportSubForum = userHandler.search_subforum("Theme", "Sport");
		ISubForum animalsSubForum = userHandler.search_subforum("Theme", "Animals");
		
		userHandler.register("user1", "user1", "user1");
		userHandler.login("user1", "user1");

		// Number of posts
		// This test runs after all other tests which add posts, which is why
		// number of posts is 6, 7, 8
		assertTrue(sportSubForum != null);
		assertTrue(userHandler.numPostsSubForum("Sport")== 0);
		assertTrue(userHandler.numPosts_user(userHandler.get_username()) == 0);
		assertTrue(userHandler.create_thread("Hi" , "Thread 1 in sports" , sportSubForum.get_theme()));
		assertTrue(userHandler.numPostsSubForum("Sport")== 1);
		assertTrue(userHandler.numPosts_user(userHandler.get_username()) == 1);
		assertTrue(userHandler.create_thread("Hello" , "Thread 1 in animals" , animalsSubForum.get_theme()));
		assertTrue(userHandler.numPostsSubForum("Animals")== 1);
		assertTrue(userHandler.numPosts_user(userHandler.get_username()) == 2);
		
		userHandler.logout();
		userHandler.login("bobi_1", "kikdoskd");

		// Moderators_Report
		System.out.println(userHandler.Moderators_Report()); 		

		//new policy
		IPolicy p = new Policy();
		IPolicy p_2 = new Policy();
		assertTrue(userHandler.changePolicy(p));
		assertTrue(userHandler.changePolicy(p_2));
		
		userHandler.deleteSubForum(sportSubForum.get_theme());
		userHandler.deleteSubForum(animalsSubForum.get_theme());
		assertTrue(userHandler.logout());
	}

}