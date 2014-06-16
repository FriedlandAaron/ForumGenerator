package Tests.integration_test.server;

import java.util.Vector;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;
import Service_Layer.IUserHandler;
import Service_Layer.UserHandler;


public class integration_test_9 extends TestCase  {
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

		assertTrue(guest_1.register("alin", "1234321", "1234321"));
		assertTrue(	guest_1.register("sapir", "1234454545321", "1234454545321"));
		assertTrue(guest_1.register("yosi", "6875698756", "6875698756"));
		


	}

	@After
	public void tearDown() throws Exception {
		//this.forum.close_Forum();
	}

	@Test
	public void test() {	
		UserHandler admin = new UserHandler(forum);


		//num
		assertTrue(admin.numSubForum() == 0);
		assertTrue(admin.numSharedModeratorsSubForum()==0);

		//addnig several sub-fourm by admins only
		assertTrue(admin.login("bobi_1", "kikdoskd"));
		assertTrue(admin.createSubForum("Sport" , (new String[]{"alin"})));
		
		//numSubForum
		assertTrue(admin.numSubForum() == 1);
		assertTrue(admin.createSubForum("Animals" , (new String[]{"yosi"})));	
	
		//add moderator
		assertTrue(admin.addModerator("Sport", "sapir"));
		assertTrue(admin.addModerator("Animals", "sapir"));
		
		//numSubForum
		assertTrue(admin.numSubForum() == 2);
		
		//numSharedModeratorsSubForum
		assertTrue(admin.numSharedModeratorsSubForum()== 1);
		
		//remove moderator
		assertTrue(admin.removeModerator("Animals", "sapir"));
		assertTrue(admin.numSharedModeratorsSubForum()== 0);
		assertTrue(admin.removeModerator("Sport", "sapir"));
		assertTrue(admin.addModerator("Animals", "sapir"));
		assertTrue(admin.numSharedModeratorsSubForum()== 0);
		assertTrue(admin.addModerator("Sport", "sapir"));
		assertTrue(admin.numSharedModeratorsSubForum()== 1);

		

		//search for diff subforum and open new_thred_on them
		ISubForum sub = admin.search_subforum("Theme", "Sport");
		ISubForum sub_2 = admin.search_subforum("Theme", "Animals");

		//posrts numbber 
		assertTrue(sub!= null);	
		assertTrue(admin.numPostsSubForum("Sport")== 0);
		assertTrue(admin.numPosts_user(admin.get_username())== 0);
		assertTrue(admin.create_thread("machckj" , "lalalskls slkd ajhs d " , sub.get_theme()));
		assertTrue(admin.numPostsSubForum("Sport")== 1);
		assertTrue(admin.numPosts_user(admin.get_username())== 1);
		assertTrue(admin.create_thread("machckj" , "lalalskls slkd ajhs d " , sub_2.get_theme()));
		assertTrue(admin.numPostsSubForum("Animals")== 1);
		assertTrue(admin.numPosts_user(admin.get_username())== 2);


		// Moderators_Report
		System.out.println(admin.Moderators_Report()); 		
		
		//new policy
		IPolicy p = new Policy();
		IPolicy p_2 = new IPolicy(){
			public boolean login(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean logout(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean createSubForum(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean changePolicy(IUser current_user){return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean deletePost(IUser current_user){return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean deleteSubForum(IUser current_user){return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean addModerator(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean removeModerator(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean getNumberOfTypes(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean removeMemberType(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean addMemberType(IUser current_user) {return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean setMethodPolicy(IUser current_user,String Methodname, Status s) {return false;}
			public boolean get_userComplaint(IUser current_user){return current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
			public boolean numPostsForum(IUser current_user) {
				return false;
			}
			@Override
			public boolean get_status_user(IUser _current_user) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean get_start_date_user(IUser _current_user) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean get_email(IUser _current_user) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean numSassions_user(IUser _current_user) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean moderator_subforum_list_user(IUser _current_user) {
				// TODO Auto-generated method stub
				return false;
			}	

		};
		assertTrue(admin.changePolicy(p));
		assertTrue(admin.changePolicy(p_2));
		assertFalse(admin.changePolicy(p));

	}
}
