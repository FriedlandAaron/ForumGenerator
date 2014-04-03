package Tests.ForumComponentTests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;

public class ForumTest {
	private Forum forum;

	@Before
	public void setUp() throws Exception {
		Policy p = new Policy();
		Vector<String[]> admins = new  Vector<String[]>(); 
		String[] a1 = {"admin_1" , "kikdoskd"} , a2 =  {"admin_2" , "ksisodhah"}  , a3  = {"admin_3" , "jhgJGG"};
		admins.add(a1);
		admins.add(a2);
		admins.add(a3);		
		Vector<IUser> administrators = new Vector<IUser>();
		 for(int i =0 ; i< admins.size(); i++){
			 administrators.add(new User(admins.get(i)[0],
					 					 admins.get(i)[1] ,
					 					 "ADMINISTRATOR"));
		 }
		this.forum = new Forum(p ,administrators , "TEST_FORUM" );
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddMember() {
		this.forum.addMember("hadar", "ooooo");
		assertTrue(this.forum.isMember("hadar"));
		assertTrue(this.forum.getMember("hadar") !=null);
	}

	@Test
	public void testIsMemberString() {
		assertFalse(this.forum.isMember("hadar") );
		assertTrue(this.forum.isMember("admin_1") );
		assertTrue(this.forum.isMember("admin_2") );
		assertTrue(this.forum.isMember("admin_3") );

		this.forum.addMember("hadar", "ooooo");
		assertTrue(this.forum.isMember("hadar"));


	}

	@Test
	public void testIsMemberStringString() {
		assertFalse(this.forum.isMember("hadar" , "ahjgsh") );
		assertFalse(this.forum.isMember("admin_1" , "ahjgsh") );
		assertTrue(this.forum.isMember("admin_1" ,"kikdoskd") );
		assertTrue(this.forum.isMember("admin_2" ,"ksisodhah") );
		assertTrue(this.forum.isMember("admin_3" ,"jhgJGG") );		
		assertFalse(this.forum.isMember("admin_3" ,"s") );
		
		this.forum.addMember("hadar", "ooooo");
		assertTrue(this.forum.isMember("hadar" ,"ooooo") );
		assertFalse(this.forum.isMember("hadar" , "ahjgsh") );
	}

	@Test
	public void testAddSubForum() {
		this.forum.addMember("hadar", "ooooo");
		this.forum.addMember("hod", "ooooo");
		IUser u1 =  this.forum.getMember("hadar");
		IUser u2 = this.forum.getMember("hod");
		Vector<IUser> v_u = new Vector<IUser>() ; 
		v_u.add(u1);
		v_u.add(u2);
		Vector<Date> v_d = new Vector<Date>() ;
		v_d.add(new Date());
		v_d.add(new Date());	
		ISubForum sub= new SubForum("Music" , v_u ,v_d );
		this.forum.addSubForum(sub);
		
		assertTrue(this.forum.search_subforum_byModerator("hadar") == sub);
		assertTrue(this.forum.search_subforum_byModerator("hod") == sub);
		assertTrue(this.forum.search_subforum_byTheme("Music") == sub);
		assertTrue(this.forum.list_sub_forum().contains(sub));		


	}

	@Test
	public void testList_sub_forum() {
		this.forum.addMember("hadar", "ooooo");
		this.forum.addMember("hod", "ooooo");
		IUser u1 =  this.forum.getMember("hadar");
		IUser u2 = this.forum.getMember("hod");
		Vector<IUser> v_u = new Vector<IUser>() ; 
		v_u.add(u1);
		v_u.add(u2);
		Vector<Date> v_d = new Vector<Date>() ;
		v_d.add(new Date());
		v_d.add(new Date());	
		ISubForum sub_1= new SubForum("Music" , v_u ,v_d );
		ISubForum sub_2= new SubForum("Animaks" , v_u ,v_d );

		this.forum.addSubForum(sub_1);
		this.forum.addSubForum(sub_2);		
		assertTrue(this.forum.list_sub_forum().contains(sub_1));
		assertTrue(this.forum.list_sub_forum().contains(sub_2));
		this.forum.deleteSubForum(sub_1);
		assertFalse(this.forum.list_sub_forum().contains(sub_1));
		assertTrue(this.forum.list_sub_forum().contains(sub_2));

	}
	@Test
	public void testSearch_subforum_byTheme() {
		this.forum.addMember("hadar", "ooooo");
		this.forum.addMember("hod", "ooooo");
		IUser u1 =  this.forum.getMember("hadar");
		IUser u2 = this.forum.getMember("hod");
		Vector<IUser> v_u = new Vector<IUser>() ; 
		v_u.add(u1);
		v_u.add(u2);
		Vector<Date> v_d = new Vector<Date>() ;
		v_d.add(new Date());
		v_d.add(new Date());	
		ISubForum sub_1= new SubForum("Music" , v_u ,v_d );
		ISubForum sub_2= new SubForum("Animaks" , v_u ,v_d );

		this.forum.addSubForum(sub_1);
		this.forum.addSubForum(sub_2);		
	

		assertTrue(this.forum.search_subforum_byTheme("Music") == sub_1);
		assertTrue(this.forum.search_subforum_byTheme("Animaks") == sub_2);
		assertFalse(this.forum.search_subforum_byTheme("ssss") == sub_2);
		assertFalse(this.forum.search_subforum_byTheme("Music") == sub_2);
		assertFalse(this.forum.search_subforum_byTheme("Animaks") == sub_1);
		
		this.forum.deleteSubForum(sub_2);

		assertTrue(this.forum.search_subforum_byTheme("Animaks") == null);
		assertTrue(this.forum.search_subforum_byTheme("Music") == sub_1);
		assertFalse(this.forum.search_subforum_byTheme("Music") == sub_2);
		this.forum.deleteSubForum(sub_1);

		assertTrue(this.forum.search_subforum_byTheme("Music") == null);




	}

	@Test
	public void testSearch_subforum_byModerator() {
		this.forum.addMember("hadar", "ooooo");
		this.forum.addMember("hod", "ooooo");
		this.forum.addMember("mira", "ooooo");
		this.forum.addMember("moty", "ooooo");

		IUser u1 =  this.forum.getMember("hadar");
		IUser u2 = this.forum.getMember("hod");
		IUser u3 = this.forum.getMember("mira");
		IUser u4 = this.forum.getMember("moty");

		Vector<IUser> v_u = new Vector<IUser>() ; 
		Vector<IUser> v_u_2 = new Vector<IUser>() ; 

		v_u.add(u1);
		v_u.add(u2);
		v_u_2.add(u3);
		v_u_2.add(u4);
		
		Vector<Date> v_d = new Vector<Date>() ;
		v_d.add(new Date());
		v_d.add(new Date());	
		ISubForum sub_1= new SubForum("Music" , v_u ,v_d );
		ISubForum sub_2= new SubForum("Animaks" , v_u_2 ,v_d );

		this.forum.addSubForum(sub_1);
		this.forum.addSubForum(sub_2);		
	

		assertTrue(this.forum.search_subforum_byModerator("hadar") == sub_1);
		assertTrue(this.forum.search_subforum_byModerator("hod") == sub_1);
		assertTrue(this.forum.search_subforum_byModerator("mira") == sub_2);
		assertTrue(this.forum.search_subforum_byModerator("moty") == sub_2);
		
		assertTrue(this.forum.search_subforum_byModerator("gggg") == null);
		assertTrue(this.forum.search_subforum_byModerator("moggggty") == null);

		assertFalse(this.forum.search_subforum_byModerator("hadar") == sub_2);
		assertFalse(this.forum.search_subforum_byModerator("hod") == null);
		assertFalse(this.forum.search_subforum_byModerator("mira") == sub_1);
		assertFalse(this.forum.search_subforum_byModerator("moty") == null);

		
		this.forum.deleteSubForum(sub_2);

		assertTrue(this.forum.search_subforum_byModerator("mira") == null);
		assertTrue(this.forum.search_subforum_byModerator("moty") == null);
		this.forum.deleteSubForum(sub_1);

		assertTrue(this.forum.search_subforum_byModerator("hadar") == null);
		assertTrue(this.forum.search_subforum_byModerator("hod") == null);
		
		assertFalse(this.forum.search_subforum_byModerator("hadar") == sub_1);
		assertFalse(this.forum.search_subforum_byModerator("hod") == sub_1);
		assertFalse(this.forum.search_subforum_byModerator("mira") == sub_2);
		assertFalse(this.forum.search_subforum_byModerator("moty") == sub_2);
}

	@Test
	public void testDeleteSubForum() {
		this.forum.addMember("hadar", "ooooo");
		this.forum.addMember("hod", "ooooo");
		IUser u1 =  this.forum.getMember("hadar");
		IUser u2 = this.forum.getMember("hod");
		Vector<IUser> v_u = new Vector<IUser>() ; 
		v_u.add(u1);
		v_u.add(u2);
		Vector<Date> v_d = new Vector<Date>() ;
		v_d.add(new Date());
		v_d.add(new Date());	
		ISubForum sub_1= new SubForum("Music" , v_u ,v_d );
		ISubForum sub_2= new SubForum("Animaks" , v_u ,v_d );

		this.forum.addSubForum(sub_1);
		this.forum.addSubForum(sub_2);		
		assertTrue(this.forum.list_sub_forum().contains(sub_1));
		assertTrue(this.forum.list_sub_forum().contains(sub_2));
		this.forum.deleteSubForum(sub_1);
		assertFalse(this.forum.list_sub_forum().contains(sub_1));
		assertTrue(this.forum.list_sub_forum().contains(sub_2));
		this.forum.deleteSubForum(sub_2);
		assertFalse(this.forum.list_sub_forum().contains(sub_2));

	}

	@Test
	public void testAddMemberType() {
		assertTrue(this.forum.getMemberTypeByName("Default")!= null);
		assertEquals(this.forum.getNumberOfTypes() , 1);

		this.forum.addMemberType("RED");
		assertEquals(this.forum.getNumberOfTypes() , 2);
		assertEquals(this.forum.getMemberTypeByName("RED").get_typeName() , "RED");
		
		assertTrue(this.forum.getMemberTypeByName("GREEN")== null);


		this.forum.addMemberType("GREEN");
		assertTrue(this.forum.getMemberTypeByName("GREEN")!= null);
		assertEquals(this.forum.getMemberTypeByName("GREEN").get_typeName() , "GREEN");


		this.forum.addMemberType("BLACK");	
		
		assertEquals(this.forum.getNumberOfTypes() , 4);

		this.forum.addMemberType("GRAY");
		
		assertEquals(this.forum.getNumberOfTypes() , 5);

		assertEquals(this.forum.getMemberTypeByName("BLACK").get_typeName() , "BLACK");
		

		
	}

	@Test
	public void testGetMemberTypeByName() {
		assertTrue(this.forum.getMemberTypeByName("Default")!= null);
		assertEquals(this.forum.getNumberOfTypes() , 1);
		assertEquals(this.forum.getMemberTypeByName("Default").get_typeName() , "Default");
		
		this.forum.addMemberType("RED");
		this.forum.addMemberType("GREEN");
		this.forum.addMemberType("BLACK");	
		this.forum.addMemberType("GRAY");
		
		
		assertEquals(this.forum.getNumberOfTypes() , 5);
		assertEquals(this.forum.getMemberTypeByName("BLACK").get_typeName() , "BLACK");
		assertEquals(this.forum.getMemberTypeByName("GREEN").get_typeName() , "GREEN");
		assertEquals(this.forum.getMemberTypeByName("GRAY").get_typeName() , "GRAY");
		this.forum.removeMemberType("RED");
		assertTrue(this.forum.getMemberTypeByName("RED")== null);
		assertEquals(this.forum.getNumberOfTypes() , 4);
		this.forum.removeMemberType("BLACK");
		this.forum.removeMemberType("GREEN");
		this.forum.removeMemberType("GRAY");
		assertTrue(this.forum.getMemberTypeByName("BLACK")== null);
		assertTrue(this.forum.getMemberTypeByName("GREEN")== null);
		assertTrue(this.forum.getMemberTypeByName("GRAY")== null);	

	}

	@Test
	public void testRemoveMemberType() {
		this.forum.addMemberType("RED");
		this.forum.addMemberType("GREEN");
		this.forum.addMemberType("BLACK");	
		this.forum.addMemberType("GRAY");
		
		this.forum.removeMemberType("BLACK");
		this.forum.removeMemberType("GREEN");
		this.forum.removeMemberType("GRAY");
		assertEquals(this.forum.getNumberOfTypes() , 2);

		assertTrue(this.forum.getMemberTypeByName("BLACK")== null);
		assertTrue(this.forum.getMemberTypeByName("GREEN")== null);
		assertTrue(this.forum.getMemberTypeByName("GRAY")== null);	
		this.forum.removeMemberType("Default");
		assertEquals(this.forum.getNumberOfTypes() , 2);
		assertTrue(this.forum.getMemberTypeByName("Default")!= null);
		assertTrue(this.forum.getMemberTypeByName("RED")!= null);

		this.forum.removeMemberType("RED");
		assertEquals(this.forum.getNumberOfTypes() , 1);

		assertTrue(this.forum.getMemberTypeByName("RED")== null);	
		
	}

	@Test
	public void testGetNumberOfTypes() {
		assertEquals(this.forum.getNumberOfTypes() , 1);

		this.forum.addMemberType("RED");
		assertEquals(this.forum.getNumberOfTypes() , 2);

		this.forum.addMemberType("GREEN");
		this.forum.addMemberType("BLACK");	
		this.forum.addMemberType("GRAY");
		assertEquals(this.forum.getNumberOfTypes() , 5);

		
		this.forum.removeMemberType("BLACK");
		this.forum.removeMemberType("GREEN");
		this.forum.removeMemberType("GRAY");
		assertEquals(this.forum.getNumberOfTypes() , 2);
		this.forum.removeMemberType("Default");
		this.forum.removeMemberType("RED");
		assertEquals(this.forum.getNumberOfTypes() , 1);

	}

}
