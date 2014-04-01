package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ForumComponent.*;
import FourmUser.*;


public class integration_test {
	private Forum forum;
	private UserHandler super_admin ;

	@Before
	public void setUp() throws Exception {
		//create superadmin
		this.super_admin = new UserHandler("SUPER_ADMINISTRATOR" , "hadaramran" , "12374567");
		
		//create forum

		Policy p = new Policy();
		Vector<String[]> admins = new  Vector<String[]>(); 
		String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
		admins.add(a1);
		admins.add(a2);
		admins.add(a3);		
		this.forum = super_admin.createForum(p ,admins, "Say");		

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
			
		
		//guest_1 usecaes
		UserHandler guest_1 = new UserHandler(forum);
		assertTrue(guest_1.register("hodamr", "1234321", "1234321"));
		assertTrue(guest_1.login("hodamr", "1234321"));
		assertTrue(guest_1.logout());
		assertFalse(guest_1.logout());
		
		
		UserHandler guest_2 = new UserHandler(forum);
		assertFalse(guest_2.register("hodamr", "4", "4" ));
		assertTrue(guest_2.register("alin", "5fs0d2SW", "5fs0d2SW"));
		assertTrue(guest_2.register("sapir", "gjkg5Ofjh", "gjkg5Ofjh"));
		assertTrue(guest_2.register("yosi", "hs5F%4j3h", "hs5F%4j3h"));
		
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		assertTrue(admin.login("bobi_1", "kikdoskd"));
		assertTrue(admin.createSubForum("Sport" , (new String[]{"alin"}) ));
		assertTrue(admin.createSubForum("Music" ,  (new String[]{"sapir"})));
		assertTrue(admin.createSubForum("Animals" ,  (new String[]{"yosi"})));
		assertFalse(admin.createSubForum("Animals" ,  (new String[]{"sapir"})));
		
		
		//search for diff subforum and open new_thred_on them
		UserHandler member_1 = (new UserHandler(forum));
		Vector<SubForum> list_sub = member_1.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(member_1.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0)));
		}
		
		SubForum sub_animals = member_1.search_subforum("Theme","Animals");
		SubForum sub_Music = member_1.search_subforum("Moderator","sapir");
		
		assertTrue(sub_animals.get_theme().equals("Animals"));		
		assertTrue(sub_Music.get_theme().equals("Music"));

		// Adding a reply post to the existing post
		SubForum sub_sport = member_1.search_subforum("Theme", "Sport");
		Vector<Post> threads = sub_sport.showThreads();
		if(threads.size()>0) {
			assertTrue(member_1.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		}
		
		// Change policy
		Policy p2 = new Policy();
		assertTrue(admin.changePolicy(p2));
		
		// delete replay post 
		Vector<Post> member1_ReplyPost =  member_1.show_ReplyPost();
		if(member1_ReplyPost.size() > 0)
			assertTrue(member_1.deletePost(member1_ReplyPost.get(0)));



		// delete Thread post 
		Vector<Post> member1_ThreadPost =  member_1.show_TreadPost();
		if(member1_ThreadPost.size() > 0)
			assertTrue(member_1.deletePost(member1_ThreadPost.get(0)));		
		
		 member1_ThreadPost =  member_1.show_TreadPost();
		 System.out.println(member1_ThreadPost.size());
		 assertTrue(member1_ThreadPost.size()==0);
		 
		 //delete sub forum 
		 admin.deleteSubForum(sub_Music);
		 sub_Music = member_1.search_subforum("Theme", "Music");
		 assertTrue(sub_Music == null);
		 
		 //add complaint
		 User moderator = sub_animals.getModerator("yosi");
		 assertTrue(moderator!=null);
		 assertTrue(member_1.create_thread("hadar" , "lkjhas" , sub_animals));
		 assertTrue(member_1.addcomplaintModerator(sub_animals , "yosi" , "theme_complient" , "body_complient"));
		 

	}

}
