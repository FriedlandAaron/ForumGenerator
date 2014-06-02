package Tests.FourmUserTests;

import static org.junit.Assert.*;

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
import Service_Layer.UserHandler;

public class UserHandlerTest {
	private UserHandler super_admin ; 
	private UserHandler member_1 ;
	private UserHandler member_2 ;
	private UserHandler member_3 ;
	private UserHandler member_4 ;

	

	@Before
	public void setUp() throws Exception {

		
		//create forum components
		Policy p = new Policy();
		Vector<String[]> admins = new  Vector<String[]>(); 
		String[] a1 = {"hadar_1" , "ssdasda"} , a2 =  {"hadar_2" , "05402sda"}  , a3  = {"hadar_3" , "ADAS45"};
		admins.add(a1);	admins.add(a2);	admins.add(a3);		
		
		//create forum

		IForum forum =Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Cat");		
		this.super_admin = new UserHandler(forum);
		this.super_admin.login( "hadaramran",  "12374567");
		
		this.member_1 = new UserHandler(forum);
		this.member_2 = new UserHandler(forum);
		this.member_3 = new UserHandler(forum);
		this.member_4 = new UserHandler(forum);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegister() {
		assertTrue(this.member_1.register("margairt", "ahsfdhgsvahd", "ahsfdhgsvahd"));
		assertTrue(this.member_2.register("hagfshgds", "1s31dsa", "1s31dsa"));
		
		assertFalse(this.member_3.register("12321", "d", "da"));
		assertFalse(this.member_4.register("hagfshgds", "asd", "asd"));
		
		
		assertTrue(this.member_1.register("2", "ahsfdhgsvcahd", "ahsfdhgsvcahd"));
		assertTrue(this.member_2.register("4", "ggggg", "ggggg"));
		
		
	}

	@Test
	public void testLogin() {
		assertFalse(this.member_1.login("margairt", "ahsfdhgsvahd"));
		
		this.member_1.register("margairt", "ahsfdhgsvahd", "ahsfdhgsvahd");
		this.member_2.register("hagfshgds", "1s31dsa", "1s31dsa");
		
		assertTrue(this.member_1.login("margairt", "ahsfdhgsvahd"));
		assertTrue(this.member_2.login("margairt", "ahsfdhgsvahd"));
		
		assertFalse(this.member_2.login("hagfshgds", "1s31dsa"));		
		assertTrue(this.member_2.logout());
		assertTrue(this.member_2.login("hagfshgds", "1s31dsa"));
	}

	@Test
	public void testLogout() {
		assertFalse(this.member_3.logout());
		assertFalse(this.member_4.logout());
		
		this.member_3.register("margairt", "ahsfdhgsvahd", "ahsfdhgsvahd");
		this.member_4.register("hagfshgds", "1s31dsa", "1s31dsa");
		
		assertFalse(this.member_3.logout());
		assertFalse(this.member_4.logout());		
		
		this.member_3.login("margairt", "ahsfdhgsvahd");
		this.member_4.login("margairt", "ahsfdhgsvahd");
		
		assertTrue(this.member_3.logout());
		assertTrue(this.member_4.logout());		
		
		assertFalse(this.member_3.logout());
		assertFalse(this.member_4.logout());	

	}



	@Test
	public void testCreateSubForum() {
		this.member_3.register("alin", "mimi", "mimi");
		this.member_3.register("sapir", "33333", "33333");
		this.member_3.register("yosi", "wqdfsdsd", "wqdfsdsd");


		this.member_1.login("hadar_1", "ssdasda");
		
		assertTrue(this.member_1.createSubForum("Sport" , (new String[]{"alin"}) ));
		assertTrue(this.member_1.createSubForum("Music" ,  (new String[]{"sapir"})));
		assertTrue(this.member_1.createSubForum("Animals" ,  (new String[]{"yosi"})));
		assertFalse(this.member_1.createSubForum("Animals" ,  (new String[]{"sapir"})));
	}

	@Test
	public void testDeleteSubForum() {
		this.member_3.register("alin", "mimi", "mimi");
		this.member_3.register("sapir", "33333", "33333");
		this.member_3.register("yosi", "wqdfsdsd", "wqdfsdsd");


		this.member_1.login("hadar_1", "ssdasda");
		
		assertTrue(this.member_1.createSubForum("Sport" , (new String[]{"alin"}) ));
		assertTrue(this.member_1.createSubForum("Music" ,  (new String[]{"sapir"})));
		assertTrue(this.member_1.createSubForum("Animals" ,  (new String[]{"yosi"})));
		assertFalse(this.member_1.createSubForum("Animals" ,  (new String[]{"sapir"})));
		
		assertTrue(this.member_1.deleteSubForum(this.member_1.search_subforum("Theme", "Music")));
		assertTrue(this.member_1.search_subforum("Theme", "Music")==null);
		assertTrue(this.member_3.show_sub_forum().size()==2);		
		assertTrue(this.member_1.createSubForum("Music" ,  (new String[]{"sapir"})));

		
	}

	@Test
	public void testShow_sub_forum() {
		this.member_3.register("alin", "mimi", "mimi");
		this.member_3.register("sapir", "33333", "33333");
		this.member_3.register("yosi", "wqdfsdsd", "wqdfsdsd");


		this.member_1.login("hadar_1", "ssdasda");
		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );
		this.member_1.createSubForum("Music" ,  (new String[]{"sapir"}));
		this.member_1.createSubForum("Animals" ,  (new String[]{"yosi"}));		
		
		Vector<ISubForum> subs = this.member_4.show_sub_forum();
		for(ISubForum sub : subs){
			assertTrue(sub.get_theme().equals("Sport")
					||	sub.get_theme().equals("Music")
					||	sub.get_theme().equals("Animals")) ;	
			assertTrue(sub.get_moderators().get(0).get_username().equals("alin")||
						sub.get_moderators().get(0).get_username().equals("sapir")||
						sub.get_moderators().get(0).get_username().equals("yosi"));
			assertFalse(sub.get_theme().equals("jutn"));
		}
	}


	@Test
	public void testSearch_subforum() {
		this.member_3.register("alin", "mimi", "mimi");
		this.member_3.register("sapir", "33333", "33333");
		this.member_3.register("yosi", "wqdfsdsd", "wqdfsdsd");


		this.member_1.login("hadar_1", "ssdasda");
		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );
		this.member_1.createSubForum("Music" ,  (new String[]{"sapir"}));
		this.member_1.createSubForum("Animals" ,  (new String[]{"yosi"}));
		
		assertTrue(this.member_4.search_subforum("Theme", "Sport").get_theme().equals("Sport"));
		assertTrue(this.member_4.search_subforum("Theme", "Music").get_theme().equals("Music"));
		assertTrue(this.member_4.search_subforum("Theme", "Animals").get_theme().equals("Animals"));
		assertTrue(this.member_4.search_subforum("Theme", "df") == null);
		
		assertTrue(this.member_4.search_subforum("Moderator", "alin").get_theme().equals("Sport"));
		assertTrue(this.member_4.search_subforum("Moderator", "sapir").get_theme().equals("Music"));
		assertTrue(this.member_4.search_subforum("Moderator", "yosi").get_theme().equals("Animals"));
		assertTrue(this.member_4.search_subforum("Moderator", "hadar") == null);

		
	}

	@Test
	public void testChangePolicy() {
		Policy p = new Policy();
		
		assertFalse(this.member_1.changePolicy(p));		
		this.member_1.login("hadar_1", "ssdasda");
		assertTrue(this.member_1.changePolicy(p));

	}
	
	@Test
	public void testCreate_thread() {
		this.member_3.register("alin", "jjjjjjj", "jjjjjjj");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );
		assertTrue(this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", "Sport")));
		assertFalse(this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", "")));
	}

	@Test
	public void testCreateReplyPost() {
		this.member_3.register("alin", "jahsgd", "jahsgd");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Music" , (new String[]{"alin"}) );

		ISubForum sub_sport = member_1.search_subforum("Theme", "Music");
		Vector<IPost> threads = sub_sport.showThreads();
		if(threads.size()>0) {
			assertTrue(member_1.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		}
	}

	@Test
	public void testShow_ReplyPost() {
		this.member_3.register("alin", "jahsgd", "jahsgd");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Music" , (new String[]{"alin"}) );

		this.member_3.register("alin", "vvv", "vvv");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );
		this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", "Sport"));
		this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", ""));
		
		ISubForum sub_sport = member_1.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();		
		assertTrue(member_1.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		
		Vector<IPost> member1_ReplyPost =  member_1.show_ReplyPost();
		assertTrue(member1_ReplyPost !=null);
		assertTrue(member1_ReplyPost.size()==1);
	}

	@Test
	public void testShow_TreadPost() {
		this.member_3.register("alin", "jahsgd", "jahsgd");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );

		ISubForum sub_sport = member_1.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();
		assertTrue(threads.size()==0);
		assertTrue(this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", "Sport")));
		threads = sub_sport.showThreads();
		assertTrue(threads.size()==1);
		threads.get(0).get_body().equals("ashgd");		


	}

	@Test
	public void testDeletePost() {
		
		this.member_3.register("alin", "jahsgd", "jahsgd");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Music" , (new String[]{"alin"}) );

		this.member_3.register("alin", "mccccc", "mccccc");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Sport" , (new String[]{"alin"}) );
		this.member_4.create_thread("sjhgdjahs", "ashgd", this.member_4.search_subforum("Theme", "Sport"));
		
		ISubForum sub_sport = member_1.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();		
		assertTrue(member_1.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		
		
		// delete replay post 
		Vector<IPost> member1_ReplyPost =  member_1.show_ReplyPost();
		assertTrue(member_1.deletePost(member1_ReplyPost.get(0)));
		
		// delete Thread post 
		Vector<IPost> member1_ThreadPost =  member_1.show_TreadPost();
		while(member1_ThreadPost.size() > 0)
			assertTrue(member_1.deletePost(member1_ThreadPost.get(0)));	
		
		 assertTrue(member1_ThreadPost.size()==0);
		 


	}


	@Test
	public void testAddcomplaintModerator() {
		this.member_3.register("yosi", "123", "123");
		this.member_1.login("hadar_1", "ssdasda");		
		this.member_1.createSubForum("Animals" , (new String[]{"yosi"}) );
		ISubForum sub_animals = member_1.search_subforum("Theme", "Animals");		 
		 
		 //add complaint
		 IUser moderator = sub_animals.getModerator("yosi");
		 assertTrue(moderator!=null);
		 assertTrue(member_1.create_thread("hadar" , "lkjhas" , sub_animals));
		 assertTrue(member_1.addcomplaintModerator(sub_animals , "yosi" , "theme_complient" , "body_complient"));	}

	@Test
	public void testAddMemberType() {
		// Add member types
		assertTrue(this.super_admin.addMemberType("Gold"));
		assertTrue(this.super_admin.addMemberType("Silver"));
		assertTrue(this.super_admin.addMemberType("Bronze"));
		UserHandler admin = new UserHandler(this.member_1.get_current_user().get_forum());
		admin.login("hadar_2" , "05402sda");
		assertFalse(admin.addMemberType("Platinum"));
		assertFalse(member_1.addMemberType("Platinum"));
		
	
	}

	@Test
	public void testRemoveMemberType() {
		// Add member types
		assertTrue(this.super_admin.addMemberType("Gold"));
		assertTrue(this.super_admin.addMemberType("Silver"));
		assertTrue(this.super_admin.addMemberType("Bronze"));
		
		UserHandler admin = new UserHandler(this.member_1.get_current_user().get_forum());
		admin.login("hadar_2" , "05402sda");
		
		// Remove member types
		assertTrue(this.super_admin.removeMemberType("Bronze"));
		assertFalse(admin.removeMemberType("Silver"));
	}

	@Test
	public void testGetNumberOfMemberTypes() {
		// Add member types
		assertTrue(this.super_admin.addMemberType("Gold"));
		assertTrue(this.super_admin.addMemberType("Silver"));
		assertTrue(this.super_admin.addMemberType("Bronze"));
		UserHandler admin = new UserHandler(this.member_1.get_current_user().get_forum());
		admin.login("hadar_2" , "05402sda");
		
		// Remove member types
		assertTrue(this.super_admin.removeMemberType("Bronze"));
		
		// Check number of member types in forum
		assertTrue(this.super_admin.getNumberOfMemberTypes() == 3);
		assertFalse(admin.getNumberOfMemberTypes() == 3);
	}

}
