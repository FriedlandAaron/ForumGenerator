package FourmUser;

import java.util.Date;
import java.util.Vector;

import ForumComponent.Forum;
import ForumComponent.Post;


public class User {
	
	public enum Status {
	    GUEST, MEMBER, ADMINISTRATOR, SUPER_ADMINISTRATOR
	}
	private String _username;
	private String _password;
	private Forum _forum;


	private Vector<Post> _threads;	
	private Vector<User> _friends;
	private Date _start_date;
	private Status _status;	

	public User(String username, String password ,String status ) {
		this._status = convertStringToStatus(status) ;
		this._username = username ;
		this._password = password ;
		this._threads = new Vector<Post>();
		this._friends = new Vector<User>();
		this._start_date= new Date();
	}

	public User(Forum forum ,String username, String password ,String status ) {
		this._username = username ;
		this._password = password ;
		this._forum = forum;
		this._threads = new Vector<Post>();
		this._friends = new Vector<User>();
		this._start_date= new Date();
		this._status =convertStringToStatus(status) ;		
	}
	



	public User(Forum forum, String username) {
		this._username =username ; 
		this._password = "" ;
		this._forum = forum;
		this._threads = new Vector<Post>();
		this._friends = new Vector<User>();
		this._start_date= new Date();
		this._status = Status.GUEST;	
	}
	

	public String get_username() {
		return _username;
	}
	public Forum get_forum() {
		return _forum;
	}
	public void set_username(String _username) {
		this._username = _username;
	}

	public void set_password(String _password) {
		this._password = _password;
	}
	
	public void set_forum(Forum _forum) {
		this._forum = _forum;
	}
	
	public boolean isUser(String string) {
		return convertStringToStatus(string)== this._status;

	}
	
	private Status convertStringToStatus(String string){
		if(string.equals("GUEST"))
			return 	Status.GUEST;
		if(string.equals("MEMBER"))
			return Status.MEMBER;
		if(string.equals("ADMINISTRATOR"))
			return Status.ADMINISTRATOR;
		if(string.equals("SUPER_ADMINISTRATOR"))
			return Status.SUPER_ADMINISTRATOR;
		return null;
	}
	
	public String get_password() {
		return _password;
	}






//----------------------------------------------------------------------------------------------

	

}
