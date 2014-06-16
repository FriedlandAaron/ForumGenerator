package Tests.Database;

import java.util.UUID;
import java.util.Vector;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataBase_Layer.DataMapper;
import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.ForumComponent.Post;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;


public class Subforum_test  extends TestCase {


	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
			
		String theme = UUID.randomUUID().toString().substring(0, 5);
		ISubForum subforum = new SubForum(theme, null, null);
		DataMapper data_mapper = new DataMapper();
		data_mapper.addSubForum(subforum);
		ISubForum db_subforum = data_mapper.getSubForum(theme);
		assertEquals(subforum, db_subforum);
		
		
		IForum forum = Forum.createForum( "hadaramran" , "12374567" ,new Policy() ,new  Vector<String[]>(), "Cat");
		String userId = UUID.randomUUID().toString().substring(0, 5);
		IUser user = new User(forum, userId, "password1", Status.MEMBER);
		data_mapper.addUser(user);
		Post post = Post.create_post("header1", "body1", user, subforum);
		data_mapper.addPost(post);
	}
}
