package Tests.UnitTest;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Post;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;

public class UserTest extends TestCase  {

	private IUser user_1 = new User("hadaramr", "hugidjhf" ,  Status.ADMINISTRATOR) ; 
	private IUser user_2 = new User("hod", "kajsj#jhd" ,Status.SUPER_ADMINISTRATOR) ; 
	private IUser user_3 = new User("hod1", "kajsj#f" ,Status.MEMBER) ; 
	private IUser user_4 = new User("hod2", "kajdfsj#s" ,Status.GUEST) ;  
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testAdd_thread() {
		
		
		IPost post1 = Post.create_post("hedaer1" ,"body1" ,user_1 ,null);
		IPost post2 = Post.create_post("hedaer2" ,"body2" ,user_2 , null);
		IPost post3 = Post.create_post("hedaer3" ,"body3" ,user_3 , null );
		IPost post4 = Post.create_post("hedaer4" ,"body4" ,user_4 , null  );
 
		user_1.add_thread(post1);
		user_2.add_thread(post2);
		user_3.add_thread(post3);
		user_4.add_thread(post4);
		
		assertTrue(user_1.get_threads().contains(post1));
		assertTrue(user_2.get_threads().contains(post2));
		assertTrue(user_3.get_threads().contains(post3));
		assertTrue(user_4.get_threads().contains(post4));
		
		user_1.add_thread(post4);
		user_2.add_thread(post3);
		user_3.add_thread(post2);
		user_4.add_thread(post1);
		
		assertFalse(user_1.get_threads().contains(post4));
		assertFalse(user_2.get_threads().contains(post3));
		assertFalse(user_3.get_threads().contains(post2));
		assertFalse(user_4.get_threads().contains(post1));	
	}

	@Test
	public void testAdd_replyPost() {
		IPost post1 = Post.create_post("hedaer1" ,"body1" ,user_1 ,null);
		IPost post2 = Post.create_post("hedaer2" ,"body2" ,user_2 , null);
		IPost post3 = Post.create_post("hedaer3" ,"body3" ,user_3 , null );
		IPost post4 = Post.create_post("hedaer4" ,"body4" ,user_4 , null  );
 
		user_1.add_replyPost(post1);
		user_2.add_replyPost(post2);
		user_3.add_replyPost(post3);
		user_4.add_replyPost(post4);
		
		assertTrue(user_1.get_replyPosts().contains(post1));
		assertTrue(user_2.get_replyPosts().contains(post2));
		assertTrue(user_3.get_replyPosts().contains(post3));
		assertTrue(user_4.get_replyPosts().contains(post4));
		
		user_1.add_replyPost(post4);
		user_2.add_replyPost(post3);
		user_3.add_replyPost(post2);
		user_4.add_replyPost(post1);
		
		assertFalse(user_1.get_replyPosts().contains(post4));
		assertFalse(user_2.get_replyPosts().contains(post3));
		assertFalse(user_3.get_replyPosts().contains(post2));
		assertFalse(user_4.get_replyPosts().contains(post1));	
	}

	@Test
	public void testDeletePost() {		
		
		IPost post1 = Post.create_post("hedaer1" ,"body1" ,user_1 ,null);
		IPost post2 = Post.create_post("hedaer2" ,"body2" ,user_1 , null);
		IPost post3 = Post.create_post("hedaer3" ,"body3" ,user_1 , null );
		IPost post4 = Post.create_post("hedaer4" ,"body4" ,user_1 , null  );
 
		user_1.add_thread(post1);
		user_1.add_thread(post2);
		user_1.add_thread(post3);
		user_1.add_thread(post4);
		
		assertTrue(user_1.get_threads().contains(post1));
		assertTrue(user_1.get_threads().contains(post2));
		assertTrue(user_1.get_threads().contains(post3));
		assertTrue(user_1.get_threads().contains(post4));
		
		user_1.deletePost(post1) ;				
		assertFalse(user_1.get_threads().contains(post1));		
		assertTrue(user_1.get_threads().contains(post2));
		assertTrue(user_1.get_threads().contains(post3));
		assertTrue(user_1.get_threads().contains(post4));
		
		user_1.deletePost(post2) ;	
		assertFalse(user_1.get_threads().contains(post2));
		assertTrue(user_1.get_threads().contains(post3));
		
		
		user_1.deletePost(post3) ;	
		assertFalse(user_1.get_threads().contains(post3));
		assertTrue(user_1.get_threads().contains(post4));
		
		user_1.deletePost(post4) ;	
		assertFalse(user_1.get_threads().contains(post4));

	}

	@Test
	public void testIsPostedInSubForum() {
		ISubForum sub_forum = new SubForum("titanic" , null , null);
		
		assertFalse(user_1.isPostedInSubForum(sub_forum));
		IPost post1 = Post.create_post("hedaer1" ,"body1" ,user_1 ,sub_forum);
		sub_forum.openThread(post1);
		user_1.add_thread(post1);		
		assertTrue(user_1.isPostedInSubForum(sub_forum));
		user_1.deletePost(post1);
		assertFalse(user_1.isPostedInSubForum(sub_forum));

		
	
	}

	@Test
	public void testAdd_complaint() {
		Complaint c1 = new Complaint(user_1 ,user_2 , "hsghdgsh ", "jahsgdjhas" );
		Complaint c2 = new Complaint(user_1 ,user_1 , "hsghdgsh ", "jahsgdjhas" );
		Complaint c3 = new Complaint(user_2 ,user_2 , "hsghdgsh ", "jahsgdjhas" );

		this.user_1.add_complaint(c1);
		this.user_1.add_complaint(c2);
		this.user_1.add_complaint(c3);
		
		assertTrue(this.user_1.get_complaints().contains(c1));
		assertFalse(this.user_1.get_complaints().contains(c2));
		assertFalse(this.user_1.get_complaints().contains(c3));

	}

}
