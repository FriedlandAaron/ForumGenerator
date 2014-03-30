package ForumComponent;

import java.util.Vector;

public class Forum {

	private Vector<User> _members;
	private Vector<User> _banned_members;
	private Vector<User> _administrators;
	private Vector<SubForum> _subForums;
	private Logger _action_logger;
	private Logger _error_logger;
//	private Policy _policy;
	
	public void addMember(User new_member) {
		_members.add(new_member);
	}

	public boolean login(String username, String password) {
		if(!isMember(username)) {
			return false;
		}
		User m = getMember(username ) ; 
		
		if(password != m.get_password())
			return false;
		m.set_is_connected(true);
		return true;
	}

	boolean isMember(String username) {
		return false;
	}

	private User getMember(String username) {
		return null;
	}
	
	
	
}
