package Tests.Acceptance_testing;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.IForum;

public class TestSuperAdmin extends ForumTest {
	private Member superAdmin;
    
	@Before
    public void setUp() {	
        super.setUp();
        superAdmin = new Member("oren", "12345", MemberType.SUPERADMIN);

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
//		Domain_layer.ForumComponent.Post ans = super.bridge.addNewPost(superAdmin, sf, p);
//		assertEquals(superAdmin.get_name(), ans.get_author());
//	}
	@Test
	public void testCreateNewForum() {
		
		String theme = "something";
		Member admin = new Member("a", "1", MemberType.ADMIN);
		Vector<Member> admins = new Vector<Member>();
		admins.add(admin);
		Policy p = new Policy();
		IForum F =  super.bridge.openNewForum(superAdmin, theme, admins, p);
		assertEquals(F.get_theme(), theme);
	}
	@Test
	public void testDeletePost() {
		Post post = new Post("head","body");
		Vector<Post> posts = new Vector<Post>();
		posts.add(post);
		subForum sf = new subForum("oren", posts);
		boolean ans = super.bridge.deletePost(superAdmin, sf, post);
		assertTrue(ans);
	}
	@Test
	public void testLogIn() {
		boolean ans = super.bridge.logIn(superAdmin);
		assertFalse(ans);
//		assertTrue(ans);
		
	}
}