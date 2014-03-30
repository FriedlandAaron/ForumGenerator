package test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ForumComponent.*;
import FourmUser.UserHandler;

public class usecase1 {
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
		this.forum = super_admin.createForum(p ,admins);		

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
		assertFalse(guest_2.register("hodamr", "1234321", "1234321"));
		assertTrue(guest_2.register("alin", "5fs0d2SW", "5fs0d2SW"));
		assertTrue(guest_2.register("sapir", "gjkg5Ofjh", "gjkg5Ofjh"));
		assertTrue(guest_2.register("yosi", "hs5F%4j3h", "hs5F%4j3h"));
		
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		assertTrue(admin.login("bobi_1", "kikdoskd"));
		assertTrue(admin.add_sub_forum("Sport" , (new String[]{"alin"}) ));
		assertTrue(admin.add_sub_forum("Music" ,  (new String[]{"sapir"})));
		assertTrue(admin.add_sub_forum("Animals" ,  (new String[]{"yosi"})));
		
		UserHandler member_1 = (new UserHandler(forum));
		Vector<SubForum> list_sub = member_1.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(member_1.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0)));
		}
		
		
		
		


	}

}
