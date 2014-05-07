package Tests.integration_test;

import static org.junit.Assert.*;

import java.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.*;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_5 {
	private IForum forum;
	private IUserHandler super_admin ;

	@Before
	public void setUp() throws Exception {
		//create forum components

		Policy p = new Policy();
		Vector<String[]> admins = new  Vector<String[]>(); 
		String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
		admins.add(a1);
		admins.add(a2);
		admins.add(a3);	
		
		//create forum		
		this.forum = Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Cat");	
		this.super_admin = new UserHandler(forum);
		this.super_admin.login( "hadaramran",  "12374567");
		this.forum.init_Forum();

		
		UserHandler guest_1 = new UserHandler(forum);

		guest_1.register("alin", "1234321", "1234321");
		guest_1.register("sapir", "1234321", "1234321");
		guest_1.register("yosi", "1234321", "1234321");
		
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		admin.login("bobi_1", "kikdoskd");
		admin.createSubForum("Sport" , (new String[]{"alin"}) );
		
	

	}

	@After
	public void tearDown() throws Exception {
		this.forum.close_Forum();
	}

	@Test
	public void test() {	
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		admin.login("bobi_1", "kikdoskd");
		admin.createSubForum("Sport" , (new String[]{"alin"}) );
		
		//search for diff subforum and open new_thred_on them
		UserHandler member_1 = (new UserHandler(forum));
		Vector<ISubForum> list_sub = member_1.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(member_1.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0)));
		}	

		// Adding a reply post to the existing post
		ISubForum sub_sport = member_1.search_subforum("Theme", "Sport");
		Vector<IPost> threads = sub_sport.showThreads();
		if(threads.size()>0) {
			assertTrue(member_1.createReplyPost("hahaha", "yadayadayada", threads.get(0)));
		}
		
		// Change policy
		Policy p2 = new Policy();
		assertTrue(admin.changePolicy(p2));
		
		// delete replay post 
		Vector<IPost> member1_ReplyPost =  member_1.show_ReplyPost();
		if(member1_ReplyPost.size() > 0)
			assertTrue(member_1.deletePost(member1_ReplyPost.get(0)));



		// delete Thread post 
		Vector<IPost> member1_ThreadPost =  member_1.show_TreadPost();
		if(member1_ThreadPost.size() > 0)
			assertTrue(member_1.deletePost(member1_ThreadPost.get(0)));		
		
		 member1_ThreadPost =  member_1.show_TreadPost();
		 assertTrue(member1_ThreadPost.size()==0);
		 
		 
		 
//		 //add complaint
//		 IUser moderator = sub_animals.getModerator("yosi");
//		 assertTrue(moderator!=null);
//		 assertTrue(member_1.create_thread("hadar" , "lkjhas" , sub_animals));
//		 assertTrue(member_1.addcomplaintModerator(sub_animals , "yosi" , "theme_complient" , "body_complient"));
//		 
//		 
//		 // Registration by e-mail
//		 
//			 UserHandler guest_5 = new UserHandler(forum);
//			 assertTrue(guest_5.register_Email("hadaramran_ghgd", "hhhhhhhh","hhhhhhhh", "ForumGeneratorWSEP142Test1@gmail.com"));
//			 
//			// Imitate guest 5 response e-mail
//				 Mail guest_5_email =new Mail("ForumGeneratorWSEP142Test1@gmail.com", "MiraBalaban" , null);
//				 guest_5_email.open_Store();
//				 try {
//					assertTrue(guest_5_email.send(new String[]{"ForumGeneratorWSEP142@gmail.com"}, "highfoeum", "ajgdjagdjgas"));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				 guest_5_email.close_Store();
//				 
//				 
//				//create the Scanner
//				 Scanner terminalInput = new Scanner(System.in);				 
//				  terminalInput.nextLine();				 
//				this.forum.checkValidationEmails();
//				assertTrue(this.forum.isMember("hadaramran_ghgd"));
//			
//			 
//			 
//		// Add member types
//		assertTrue(this.super_admin.addMemberType("Gold" , this.forum));
//		assertTrue(this.super_admin.addMemberType("Silver" ,this.forum));
//		assertTrue(this.super_admin.addMemberType("Bronze" ,this.forum));
//		assertFalse(admin.addMemberType("Platinum" ,this.forum));
//		assertFalse(member_1.addMemberType("Platinum" ,this.forum));
//		
//		// Remove member types
//		assertTrue(this.super_admin.removeMemberType("Bronze", this.forum));
//		assertFalse(admin.removeMemberType("Silver", this.forum));
//		
//		// Check number of member types in forum
//		assertTrue(this.super_admin.getNumberOfMemberTypes(forum) == 3);
//		assertFalse(admin.getNumberOfMemberTypes(forum) == 3);
	}

}
