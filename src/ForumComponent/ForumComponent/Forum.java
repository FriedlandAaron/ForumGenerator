package ForumComponent;

import java.util.Vector;

import FourmUser.User;
import FourmUser.User.Status;

public class Forum  {

	
	private Vector<User> _members;
	private Vector<User> _banned_members;
	private Vector<User> _administrators;
	private Vector<SubForum> _subForums;
	private Logger _action_logger;
	private Logger _error_logger;
	private Policy _policy ; 


	public Forum(Policy policy , Vector<User> administrators ){
		
		this._banned_members= new Vector<User>();
		this._administrators = administrators ;
		this._subForums = new Vector<SubForum>();
		this._action_logger = new Logger();
		this._error_logger = new Logger();
		this._policy = policy;
		this._members = new Vector<User>();
		for(int i=0 ; i < this._administrators.size() ; i++){
			this._members.add(this._administrators.get(i));
		}
	}


//----------------------------------------------------------------------------------
	public void addMember(String username, String password) {
		 _members.add(new User(this,  username,  password , "MEMBER"));
	}
	


	public	boolean isMember(String username) {
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return true;
		}
		return false;
	}

	public	boolean isMember(String username ,String password) {
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return true;
		}
		return false;
	}
	
	public User getMember(String username) {
		if(!isMember(username)){
			throw new RuntimeException() ; 
 		}
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return _members.get(i);
		}
		return null;
	}


	public void addSubForum(SubForum subForum) {
		this._subForums.add(subForum);
	}


	public Vector<SubForum> list_sub_forum() {	
		return this._subForums;
	}


	public SubForum search_subforum_byTheme(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).get_theme().equals(search_word))
				return this._subForums.get(i);
		return null;
	}


	public SubForum search_subforum_byModerator(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).isModerator(search_word))
				return this._subForums.get(i);
		return null;
	}



	
	
	
}
