package Tests.integration_test.network;

import java.io.Serializable;
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
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.reactor.Reactor;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Service_Layer.ClientHandler;


public class integration_test_9 extends TestCase{
	private ClientHandler cl_handler ;

	@Before
	public void setUp() throws Exception {
		//init server
		Thread myThread = new Thread(){
			public void run() {
				try {
					int port = 6669 , poolSize =3;					
					//create forum components
					Policy p = new Policy();
					Vector<String[]> admins = new  Vector<String[]>(); 
					String[] a1 = {"bobi_1" , "kikdoskd"} , a2 =  {"bobi_2" , "ksisodhah"}  , a3  = {"mira_123" , "jhgJGG"};
					admins.add(a1);	admins.add(a2);	admins.add(a3);	
					
					//create forum		
					IForum forum = Forum.createForum( "hadaramran" , "12374567" ,p ,admins, "Music-Forum");	
					Reactor<ForumMessage> reactor = Reactor.startForumServer(port, poolSize ,forum);
					Thread thread = new Thread(reactor);
					thread.start();			
					Reactor.logger_info("Reactor is ready on port " + reactor.getPort());
					thread.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		myThread.start();
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
    	//init
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6669); 
        cl_handler = new ClientHandler(connectionHandler);		

		assertTrue(cl_handler.register("alin", "1234321", "1234321"));
		assertTrue(	cl_handler.register("sapir", "1234454545321", "1234454545321"));
		assertTrue(cl_handler.register("yosi", "6875698756", "6875698756"));
	}


	@After
	public void tearDown() throws Exception {
		cl_handler.close_connect();		

	}

	@Test
	public void test() {	


		//num
		assertTrue(cl_handler.numSubForum() == 0);
		assertTrue(cl_handler.numSharedModeratorsSubForum()==0);

		//addnig several sub-fourm by admins only
		assertTrue(cl_handler.login("bobi_1", "kikdoskd"));
		assertTrue(cl_handler.createSubForum("Sport" , (new String[]{"alin"})));
		
		//numSubForum
		assertTrue(cl_handler.numSubForum() == 1);
		assertTrue(cl_handler.createSubForum("Animals" , (new String[]{"yosi"})));	
	
		//add moderator
		assertTrue(cl_handler.addModerator("Sport", "sapir"));
		assertTrue(cl_handler.addModerator("Animals", "sapir"));
		
		//numSubForum
		assertTrue(cl_handler.numSubForum() == 2);
		
		//numSharedModeratorsSubForum
		assertTrue(cl_handler.numSharedModeratorsSubForum()== 1);
		
		//remove moderator
		assertTrue(cl_handler.removeModerator("Animals", "sapir"));
		assertTrue(cl_handler.numSharedModeratorsSubForum()== 0);
		assertTrue(cl_handler.removeModerator("Sport", "sapir"));
		
		assertTrue(cl_handler.addModerator("Animals", "sapir"));
		assertTrue(cl_handler.numSharedModeratorsSubForum()== 0);
		assertTrue(cl_handler.addModerator("Sport", "sapir"));
		assertTrue(cl_handler.numSharedModeratorsSubForum()== 1);


		//search for diff subforum and open new_thred_on them
		ISubForum sub = cl_handler.search_subforum("Theme", "Sport");
		ISubForum sub_2 = cl_handler.search_subforum("Theme", "Animals");

		//posrts numbber 
		assertTrue(sub!= null);	
		assertTrue(cl_handler.numPostsSubForum("Sport")== 0);
		assertTrue(cl_handler.numPosts_user(cl_handler.get_username())== 0);
		assertTrue(cl_handler.create_thread("machckj" , "lalalskls slkd ajhs d " , sub.get_theme()));
		assertTrue(cl_handler.numPostsSubForum("Sport")== 1);
		assertTrue(cl_handler.numPosts_user(cl_handler.get_username())== 1);
		assertTrue(cl_handler.create_thread("machckj" , "lalalskls slkd ajhs d " , sub_2.get_theme()));
		assertTrue(cl_handler.numPostsSubForum("Animals")== 1);
		assertTrue(cl_handler.numPosts_user(cl_handler.get_username())== 2);


		// Moderators_Report
		System.out.println(cl_handler.Moderators_Report()); 		
		
		//new policy
		IPolicy p = new Policy();
		IPolicy p_2 = new Policy_test();
		assertTrue(cl_handler.changePolicy(p));
		assertTrue(cl_handler.changePolicy(p_2));
		assertFalse(cl_handler.changePolicy(p));

	}
	
	
	
	
	
	
}

@SuppressWarnings("serial")
class Policy_test implements IPolicy,   Serializable{
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
	public boolean get_userComplaint(IUser _current_user) {return _current_user.getStatus().equals(Status.SUPER_ADMINISTRATOR);}
	public boolean numPostsForum(IUser current_user) {
		// TODO Auto-generated method stub
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
