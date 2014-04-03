package Tests.integration_test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.*;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_1 {
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

	}

	@After
	public void tearDown() throws Exception {
		this.forum.close_Forum();
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
		assertFalse(guest_2.register("hodamr", "4", "4"));
		assertTrue(guest_2.register("alin", "5fs0d2SW", "5fs0d2SW"));
		assertTrue(guest_2.register("sapir", "gjkg5Ofjh", "gjkg5Ofjh"));
		assertTrue(guest_2.register("yosi", "hs5F%4j3h", "hs5F%4j3h"));		


	}

}
