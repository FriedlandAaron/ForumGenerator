package Tests.integration_test;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.*;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_3 {
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

		UserHandler guest_1 = new UserHandler(forum);

		guest_1.register("alin", "1234321", "1234321");
		guest_1.register("sapir", "1234321", "1234321");
		guest_1.register("yosi", "1234321", "1234321");
		
		//addnig several sub-fourm by admins only
		UserHandler admin = new UserHandler(forum);
		admin.login("bobi_1", "kikdoskd");
		admin.createSubForum("Sport" , (new String[]{"alin"}) );
		admin.createSubForum("Music" ,  (new String[]{"sapir"}));
		admin.createSubForum("Animals" ,  (new String[]{"yosi"}));
		admin.createSubForum("Animals" ,  (new String[]{"sapir"}));
		
		//search for diff subforum and open new_thred_on them
		UserHandler member_1 = (new UserHandler(forum));
		Vector<ISubForum> list_sub = member_1.show_sub_forum();
		if(list_sub.size()>0){
			assertTrue(member_1.create_thread("machckj" , "lalalskls slkd ajhs d " , list_sub.get(0)));
		}
		
		ISubForum sub_animals = member_1.search_subforum("Theme","Animals");
		ISubForum sub_Music = member_1.search_subforum("Moderator","sapir");
		
		assertTrue(sub_animals.get_theme().equals("Animals"));		
		assertTrue(sub_Music.get_theme().equals("Music"));

		
	 //delete sub forum 
		 admin.deleteSubForum(sub_Music);
		 sub_Music = member_1.search_subforum("Theme", "Music");
		 assertTrue(sub_Music == null);

		

	}

}