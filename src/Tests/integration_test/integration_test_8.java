package Tests.integration_test;

import java.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.*;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_8 {
	private IForum forum;
	private IUserHandler super_admin ;

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
		this.forum.init_Forum();
		
		UserHandler guest_1 = new UserHandler(forum);
		guest_1.register("alin", "1234321", "1234321");
		guest_1.register("sapir", "1234321", "1234321");
		guest_1.register("yosi", "1234321", "1234321");
	}

	@After
	public void tearDown() throws Exception {
		this.forum.close_Forum();
	}

	@Test
	public void test() {	

//		// Registration by e-mail
//			 UserHandler guest_5 = new UserHandler(forum);
//			 assertTrue(guest_5.register_Email("hadaramran_ghgd", "hhhhhhhh","hhhhhhhh", "ForumGeneratorWSEP142Test1@gmail.com"));
//			 
//		// Imitate guest 5 response e-mail
//		Mail guest_5_email =new Mail("ForumGeneratorWSEP142Test1@gmail.com", "MiraBalaban" , null);
//		guest_5_email.open_Store();
//		try {
//			assertTrue(guest_5_email.send(new String[]{"ForumGeneratorWSEP142@gmail.com"}, "highfoeum", "ajgdjagdjgas"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 guest_5_email.close_Store();
//				 
//				 
//		//create the Scanner
//		Scanner terminalInput = new Scanner(System.in);				 
//		terminalInput.nextLine();				 
//		this.forum.checkValidationEmails();
//		assertTrue(this.forum.isMember("hadaramran_ghgd"));
			
	}
	
}
