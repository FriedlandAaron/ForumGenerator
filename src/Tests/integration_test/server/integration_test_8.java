package Tests.integration_test.server;

import java.util.Vector;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.Policy;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_8 extends TestCase  {
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
		//this.forum.init_Forum();

		
		UserHandler guest_1 = new UserHandler(forum);
		guest_1.register("alin", "1234321", "1234321");
		guest_1.register("sapir", "1234321", "1234321");
		guest_1.register("yosi", "1234321", "1234321");
	}

	@After
	public void tearDown() throws Exception {
		//this.forum.close_Forum();
	}

	@Test
	public void test() {	

		//un automate test
		/*
		IUserHandler user = new UserHandler(this.forum);
		assertTrue(user.register_Email("hodamr", "bamobm", "bamobm" , "hadaramran@gmail.com"));
		System.out.println("enter the submit code:");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String code = s.nextLine();
		assertTrue(user.submit_code_registertion("hodamr", code)); 		
		
		assertTrue(user.login("hodamr", "bamobm"));
		assertTrue(user.logout());
		assertFalse(user.logout());		
		*/
			
	}
	
}
