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


public class integration_test_2 extends TestCase  {
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

	}

	@After
	public void tearDown() throws Exception {
		//this.forum.close_Forum();
	}

	@Test
	public void test() {			
		UserHandler guest_1 = new UserHandler(forum);
		assertTrue(guest_1.register("alin", "1234321", "1234321"));
		assertTrue(guest_1.register("sapir", "lllll", "lllll"));
		assertTrue(guest_1.register("yosi", "mkn", "mkn"));
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		assertTrue(admin.login("bobi_1", "kikdoskd"));
		assertTrue(admin.createSubForum("Sport" , (new String[]{"alin"}) ));
		assertTrue(admin.createSubForum("Music" ,  (new String[]{"sapir"})));
		assertTrue(admin.createSubForum("Animals" ,  (new String[]{"yosi"})));
		assertFalse(admin.createSubForum("Animals" ,  (new String[]{"sapir"})));
	}

}
