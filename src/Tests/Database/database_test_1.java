package Tests.Database;

import java.util.UUID;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataBase_Layer.DataMapper;
import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;


public class database_test_1 {


	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	
		//create forum components

		Policy p = new Policy();
		Vector<String[]> admins = new  Vector<String[]>(); 
		String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
		admins.add(a1);
		admins.add(a2);
		admins.add(a3);	
		
		//create forum		
		IForum forum = Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Cat");	
		//this.forum.init_Forum();
    	String userId = UUID.randomUUID().toString().substring(0, 5);
		IUser user = new User(forum ,userId , "Password1", Status.MEMBER);
		DataMapper.addUser(user);
		DataMapper.getUser(userId);
	
	}
}
