package Tests.Acceptance_testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	private IForum forum;
	private IUserHandler userHandler;

	@Before
	public void setUp() {
		super.setUp();
		try {
			int port = 7000;
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
		ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 7000); 
		userHandler = new ClientHandler(connectionHandler);
	}
	
	@After
	public void tearDown() {
		userHandler.close_connect();
		System.out.println("df");
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
		assertTrue(userHandler.deleteSubForum(musicSubForum));
		assertTrue(userHandler.deleteSubForum(animalsSubForum));
		assertTrue(userHandler.deleteSubForum(sportSubForum));
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
			assertTrue(userHandler.create_thread("Hello" , "This is a test post" , list_sub.get(0)));
		}	

		// Adding a reply post to the existing post
		ISubForum sportSubForum = userHandler.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sportSubForum.showThreads();
		if(threads.size()>0) {
			assertTrue(userHandler.createReplyPost("Hi", "This is a reply post", threads.get(0)));
		}
		assertTrue(userHandler.deleteSubForum(sportSubForum));
        userHandler.logout();
	}
	
	@Test
	public void testChangePolicyAndDeletePosts() {
		userHandler.login("bobi_1", "kikdoskd");
		userHandler.createSubForum("Sport", (new String[]{"alin"}));
		
		// Add new thread to subforum
		Vector<ISubForum> list_sub = userHandler.show_sub_forum();
		if(list_sub.size() > 0){
			assertTrue(userHandler.create_thread("Hi", "This is a test thread" , list_sub.get(0)));
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

		// Delete reply post
		Vector<IPost> member1_ReplyPost = userHandler.show_ReplyPost();
		if(member1_ReplyPost.size() > 0)
			assertTrue(userHandler.deletePost(member1_ReplyPost.get(0)));

		// delete Thread post 
		Vector<IPost> member1_ThreadPost = userHandler.show_TreadPost();
		if(member1_ThreadPost.size() > 0)
			assertTrue(userHandler.deletePost(member1_ThreadPost.get(0)));		

		member1_ThreadPost = userHandler.show_TreadPost();
//		assertTrue(member1_ThreadPost.size() == 0);
		userHandler.deleteSubForum(sub_sport);
		assertTrue(userHandler.logout());

	}
	
	@Test
	public void testAddComplaint() {
		userHandler.login("bobi_1", "kikdoskd");
		userHandler.createSubForum("Sport" , (new String[]{"alin"}) );

		Vector<ISubForum> list_sub = userHandler.show_sub_forum();
		if(list_sub.size() > 0){
			assertTrue(userHandler.create_thread("Hi" , "This is a test thread" , list_sub.get(0)));
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
		
		userHandler.deleteSubForum(sportSubForum);
		assertTrue(userHandler.logout());
	}
	
	@Test
	public void testAddAndRemoveMemberTypes() {
		
		// Add member types
		userHandler.login("aaronf", "987654321");
		assertTrue(userHandler.addMemberType("Gold"));
		assertTrue(userHandler.addMemberType("Silver"));
		assertTrue(userHandler.addMemberType("Bronze"));
		userHandler.logout();

		userHandler.login("bobi_1", "kikdoskd");		
		assertFalse(userHandler.addMemberType("Platinum"));
		userHandler.logout();
		assertFalse(userHandler.addMemberType("Platinum"));

		// Remove member types
		userHandler.login("aaronf", "987654321");
		assertTrue(userHandler.removeMemberType("Bronze"));
		userHandler.logout();
		userHandler.login("bobi_1", "kikdoskd");		
		assertFalse(userHandler.removeMemberType("Silver"));
		userHandler.logout();

		// Check number of member types in forum
		userHandler.login("aaronf", "987654321");
		assertTrue(userHandler.getNumberOfMemberTypes() == 3);
		userHandler.logout();

		userHandler.login("bobi_1", "kikdoskd");
		assertFalse(userHandler.getNumberOfMemberTypes() == 3);
		userHandler.logout();
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

		//Moderators_list
		Vector<IUser> list  = userHandler.Moderators_list();
		assertTrue(list.size()==3);
		for(IUser user : list)
			assertTrue(user.get_username().equals("sapir")||
					user.get_username().equals("yosi")||
					user.get_username().equals("alin"));

		ISubForum sportSubForum = userHandler.search_subforum("Theme", "Sport");
		ISubForum animalsSubForum = userHandler.search_subforum("Theme", "Animals");
		
		userHandler.register("user1", "user1", "user1");
		userHandler.login("user1", "user1");

		// Number of posts
		// This test runs after all other tests which add posts, which is why
		// number of posts is 6, 7, 8
		assertTrue(sportSubForum != null);
		assertTrue(userHandler.numPostsSubForum("Sport")== 0);
		assertTrue(userHandler.numPostsUser(userHandler.get_username()) == 6);
		assertTrue(userHandler.create_thread("Hi" , "Thread 1 in sports" , sportSubForum));
		assertTrue(userHandler.numPostsSubForum("Sport")== 1);
		assertTrue(userHandler.numPostsUser(userHandler.get_username()) == 7);
		assertTrue(userHandler.create_thread("Hello" , "Thread 1 in animals" , animalsSubForum));
		assertTrue(userHandler.numPostsSubForum("Animals")== 1);
		assertTrue(userHandler.numPostsUser(userHandler.get_username()) == 8);
		
		userHandler.logout();
		userHandler.login("bobi_1", "kikdoskd");

		// Moderators_Report
		System.out.println(userHandler.Moderators_Report()); 		

		//new policy
		IPolicy p = new Policy();
		IPolicy p_2 = new Policy();
		assertTrue(userHandler.changePolicy(p));
		assertTrue(userHandler.changePolicy(p_2));
		
		userHandler.deleteSubForum(sportSubForum);
		userHandler.deleteSubForum(animalsSubForum);
		assertTrue(userHandler.logout());
	}

}