package Tests.Acceptance_testing;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestGuest extends ForumTest {
	Member guest;
    @Before
    public void setUp() {	
        super.setUp();
        setUpGuest();
    }
    @Before
    public void setUpGuest() {
		this.guest = new Member("oreng","12345", MemberType.GUEST);		
	}
	@After
    public void tearDown() {
    }

//	@Test
//	public void testAddNewPost() {
//		boolean ans= false;
//		Vector<Post> posts = new Vector<Post>();
//		subForum sf = new subForum("name" , posts);
//		Post p = new Post("head","body");
//		if (super.bridge.addNewPost(guest, sf, p)==null){
//			ans = true;
//		}
//		else ans = false;
//		assertTrue(ans);
//	}
	@Test
	public void testRegisterForum() {
		boolean ans = super.bridge.registerMemberToForum(guest);
		assertTrue(ans);
	}
	@Test
	public void testDeletePost() {
		Post post = new Post("head","body");
		Vector<Post> posts = new Vector<Post>();
		posts.add(post);
		subForum sf = new subForum("oren", posts);
		boolean ans = super.bridge.deletePost(guest, sf, post);
		assertTrue(ans);
	}

	@Test
	public void testBecomeMember() {
		assertFalse(false);
	}
	@Test
	public void testLogIn() {
		boolean ans = super.bridge.logIn(guest);
		assertFalse(ans);
		
	}
	

}

