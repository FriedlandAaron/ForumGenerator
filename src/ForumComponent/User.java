package ForumComponent;

import java.util.Date;
import java.util.Vector;


public class User {

	private enum Status {
	    GUEST, MEMBER, ADMINISTRATOR, SUPER_ADMINISTRATOR
	}
	
	private Forum _forum;
	private Vector<Post> _threads;
	
	private String _username;
	private String _password;
	private Vector<User> _friends;
	private boolean _is_active;
	private Date _start_date;
	private int _status;
	
	private boolean _is_connected;

	
	public User(String username, String password) {
		// TODO Auto-generated constructor stub
	}
	public void set_is_connected(boolean _is_connected) {
		this._is_connected = _is_connected;
	}
	public String get_password() {
		return _password;
	}
	public void addNewPost(SubForum sub_forum, Post post) {
		sub_forum.openThread(post);
	}
	
	public void addReplyPost(Post reply_post, Post post) {
		post.addReplyPost(reply_post);
	}
	
	public boolean register(String username, String password, String repeated_password) {
		if(_forum.isMember(username)) {
			return false;
		}
		if(password.equals(repeated_password)) {
			User new_member = new User(username, password );
			_forum.addMember(new_member);
			return true;
		}
		return false;
	}
	
	public boolean login(String username, String password) {
		return _forum.login(username, password);
	}


}
