package Domain_layer.FourmUser;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.MemberType;


public class User implements IUser {
	
	public enum Status {
	    GUEST, MEMBER, ADMINISTRATOR, SUPER_ADMINISTRATOR
	}
	
	private String _username;
	private String _password;
	private IForum _forum;
	private Vector<IPost> _threads;
	private Vector<IPost> _replyPosts;
	private Vector<IUser> _friends;
	private Vector<Complaint> _complaints;
	private Date _start_date;
	private Status _status;
	private String _email;
	private MemberType _type;

	public User(String username, String password, String status) {
		this._status = convertStringToStatus(status);
		this._username = username;
		this._password = password;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._friends = new Vector<IUser>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._email = "";
	}
	
	public User(IForum forum, String username, String password, String status) {
		this._username = username;
		this._password = password;
		this._forum = forum;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._friends = new Vector<IUser>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status =convertStringToStatus(status);
		this._email = "";
		this._type = this._forum.getMemberTypeByName("Default");
	}

	public User(IForum forum, String username, String password, String status, String email) {
		this._username = username;
		this._password = password;
		this._forum = forum;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._friends = new Vector<IUser>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status = convertStringToStatus(status);
		this._email = email;
		this._type = this._forum.getMemberTypeByName("Default");		
	}
	
	public User(IForum iForum, String username) {
		this._username = username;
		this._password = "";
		this._forum = iForum;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._friends = new Vector<IUser>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status = Status.GUEST;
		this._email = "";
		this._type = this._forum.getMemberTypeByName("Default");

	}
//--------------------------------------------------------------------
	public void set_type(MemberType _type) {
		this._type = _type;
	}
	public String get_username() {
		return _username;
	}
	public IForum get_forum() {
		return _forum;
	}
	public void set_username(String _username) {
		this._username = _username;
	}

	public void set_password(String _password) {
		this._password = _password;
	}
	
	public void set_forum(IForum _forum) {
		this._forum = _forum;
	}
	
	public boolean isUser(String status) {
		return convertStringToStatus(status)== this._status;
	}
	
	private Status convertStringToStatus(String status) {
		if(status.equals("GUEST")) {
			return Status.GUEST;
		}
		if(status.equals("MEMBER")) {
			return Status.MEMBER;
		}
		if(status.equals("ADMINISTRATOR")) {
			return Status.ADMINISTRATOR;
		}
		if(status.equals("SUPER_ADMINISTRATOR")) {
			return Status.SUPER_ADMINISTRATOR;
		}
		return null;
	}
	
	public String get_password() {
		return _password;
	}

	public void add_thread(IPost p) {
		if(p.get_author() == this)
			this._threads.add(p);
	}

	public void add_replyPost(IPost p) {
		if(p.get_author() == this)
			this._replyPosts.add(p);
	}

	public Vector<IPost> get_threads() {
		return _threads;
	}

	public Vector<IPost> get_reaplayPosts() {
		return _replyPosts;
	}

	public void deletePost(IPost post) {
		for(int i = 0; i < this._replyPosts.size(); i++) {
			if(this._replyPosts.get(i) == post) {
				this._replyPosts.remove(i);
			}
		}
		for(int i = 0; i < this._threads.size(); i++) {
			if(this._threads.get(i) == post) {
				this._threads.remove(i);
			}
		}
	}

	public boolean isPostedInSubForum(ISubForum subForum) {
		for(int i = 0 ; i < this._threads.size(); i++) {
			if(this._threads.get(i).get_subForum()==subForum) {
				return true;
			}
		}
		for(int i = 0 ; i< this._replyPosts.size(); i++) {
			if(this._replyPosts.get(i).get_subForum()==subForum) {
				return true;
			}
		}
		return false;
	}

	public void add_complaint(Complaint complaint) {
		if(complaint.get_submitter() == this && complaint.get_complaint_on_user() != this )
			this._complaints.add(complaint);
		
	}

	public Vector<IPost> get_replyPosts() {
		return _replyPosts;
	}

	public Vector<IUser> get_friends() {
		return _friends;
	}

	public Vector<Complaint> get_complaints() {
		return _complaints;
	}

	public Date get_start_date() {
		return _start_date;
	}

	public Status get_status() {
		return _status;
	}

	public String get_email() {
		return _email;
	}

	public MemberType get_type() {
		return _type;
	}


	
	
	

	

}
