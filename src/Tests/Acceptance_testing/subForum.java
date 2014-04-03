package Tests.Acceptance_testing;

import java.util.Vector;

public class subForum {
	String name;
	Vector<Post> posts;


	public subForum(String name, Vector<Post> posts) {
		this.name=name;
		this.posts=posts;
		
	}

	public Object getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public Object getPosts() {
		// TODO Auto-generated method stub
		return posts;
	}

}
