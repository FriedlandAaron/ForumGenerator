package TestSuite;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ForumComponent.Post;
import ForumComponent.SubForum;
import FourmUser.User;

public class PostTest {
	
	private SubForum subForum;
	private User user1;
	private User user2;
	private Post post;
	

	@Before
	public void setUp() throws Exception {
		subForum = new SubForum("Nothing", new Vector<User>(), new Vector<Date>());
		user1 = new User("aaronf", "hello", "MEMBER");
		user2 = new User("hadara", "hello", "MEMBER");
		post = new Post("Welcome", "This is a test.", user1, subForum);
		user1.add_thread(post);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddReplyPost() {
		Post reply = new Post("Reply", "This is a reply test.", user2, subForum, post);
		post.addReplyPost(reply);
		Vector<Post> replies = post.get_replies();
		assertFalse(replies.isEmpty());
		assertTrue(replies.get(0) == reply);
	}

	@Test
	public void testGet_subForum() {
		SubForum test = post.get_subForum();
		assertTrue(test == subForum);
	}

	@Test
	public void testGet_author() {
		User test = post.get_author();
		assertTrue(test == user1);
	}

	@Test
	public void testDelete() {
		Vector<Post> user1Posts = user1.get_threads();
		assertTrue(user1Posts.contains(post));
		post.delete();
		assertFalse(user1Posts.contains(post));
	}

}
