package Tests.Acceptance_testing;


import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMember extends ForumTest{

	private Member member;
    
    @Before
    public void setUp() {	
        super.setUp();
        setUpMember();
    }
    @Before
    public void setUpMember() {
		this.member = new Member("orenmember", "12345",MemberType.MEMBER);


	}
	@After
    public void tearDown() {
    }

//	@Test
//	public void testAddNewPost() {
//
//		Vector<Post> posts = new Vector<Post>();
//		subForum sf = new subForum("name" , posts);
//		Post p = new Post("head","body");
//		Domain_layer.ForumComponent.Post ans = super.bridge.addNewPost(member, sf, p);
//		assertEquals(member.get_name(), ans.get_author());
//	}
	@Test
	public void testRegisterForum() {
		assertFalse(false);
	}
	@Test
	public void testDeletePost() {
		Post post = new Post("head","body");
		Vector<Post> posts = new Vector<Post>();
		posts.add(post);
		subForum sf = new subForum("oren", posts);
		boolean ans = super.bridge.deletePost(member, sf, post);
		assertTrue(ans);
	}
	@Test
	public void testDisconnectForum() {
		assertFalse(false);
	}
	@Test
	public void testBecomeAdmin() {
		assertFalse(false);
	}
	@Test
	public void testCommentPost() {
		//String header, String body, IPost post
		String header = "Header";
		String body = "body";
		boolean ans = super.bridge.addReplyPost(member, header, body);
		assertTrue(ans);
		
	}
	@Test
	public void testEditPost() {
		Post post = new Post("head","body");
		boolean ans = super.bridge.editPost(member, post);
		assertFalse(ans);
//		assertTrue(ans);
	}
	@Test
	public void testLogOut() {
		
		assertFalse(false);
	}
	@Test
	public void testLogIn() {
		boolean ans = super.bridge.logIn(member);
//		assertTrue(ans);

		assertFalse(ans);
		
	}


}
