package Tests.ForumComponentTests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Post;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;

public class PostTest {
	
	private SubForum subForum;
	private IUser user1;
	private IUser user2;
	private IPost post;
	

	@Before
	public void setUp() throws Exception {
		subForum = new SubForum("Nothing", new Vector<IUser>(), new Vector<Date>());
		user1 = new User("aaronf", "hello",  Status.MEMBER);
		user2 = new User("hadara", "hello",  Status.MEMBER);
		post =  Post.create_post("Welcome", "This is a test.", user1, subForum);
		user1.add_thread(post);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddReplyPost() {
		Post reply =  Post.create_post("Reply", "This is a reply test.", user2, subForum, post);
		post.addReplyPost(reply);
		Vector<IPost> replies = post.get_replies();
		assertFalse(replies.isEmpty());
		assertTrue(replies.get(0) == reply);
	}

	@Test
	public void testGet_subForum() {
		ISubForum test = post.get_subForum();
		assertTrue(test == subForum);
	}

	@Test
	public void testGet_author() {
		IUser test = post.get_author();
		assertTrue(test == user1);
	}

	@Test
	public void testDelete() {
		Vector<IPost> user1Posts = user1.get_threads();
		assertTrue(user1Posts.contains(post));
		post.delete();
		assertFalse(user1Posts.contains(post));
	}

}
