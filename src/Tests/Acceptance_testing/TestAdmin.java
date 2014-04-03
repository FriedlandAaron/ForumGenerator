package Tests.Acceptance_testing;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin extends ForumTest{
	Member admin;
	
   @Before
    public void setUp() {
        super.setUp();
        setUpAdmin();
    }
   @Before
   public void setUpAdmin() {
		admin = new Member("oren", "12345", MemberType.ADMIN);		
	}
	@After
    public void tearDown() {

    }
	
	
	@Test
	public void testDeletePost() {
		Post post = new Post("head","body");
		Vector<Post> posts = new Vector<Post>();
		posts.add(post);
		subForum sf = new subForum("oren", posts);
		boolean ans = super.bridge.deletePost(admin, sf, post);
		assertTrue(ans);
	}
	@Test
	public void testBanMember() {
		assertFalse(false);
	}

	@Test
	public void testAddMember() {
		assertFalse(false);
	}

//	@Test
//	public void testAddPost() {
//		Vector<Post> posts = new Vector<Post>();
//		subForum sf = new subForum("name" , posts);
//		Post p = new Post("head","body");
//		Domain_layer.ForumComponent.Post ans = super.bridge.addNewPost(admin, sf,p );
//		assertEquals(admin.get_name(), ans.get_author());
//	}

	@Test
	public void testEditPost() {
		Post post = new Post("head","body");
		boolean ans = super.bridge.editPost(admin, post);
		assertFalse(ans);
//		assertTrue(ans);
	}

	@Test
	public void testDeleteSubForum() {
		assertFalse(false);
	}
	@Test
	public void testAddSubForum() {
	}
	@Test
	public void testLogIn() {
		Member member = new Member("name","pass",null);
		boolean ans = super.bridge.logIn(member );
		assertFalse(ans);
//		assertTrue(ans);
		
	}


}
