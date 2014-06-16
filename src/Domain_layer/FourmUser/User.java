package Domain_layer.FourmUser;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.Session.Session;


@SuppressWarnings("serial")
public class User implements IUser  , java.io.Serializable{

	
	public enum Status {
		GUEST(0), 
		MEMBER(1),
		ADMINISTRATOR(2),
		SUPER_ADMINISTRATOR(3);

	    private int numVal;
	    Status(int numVal) { this.numVal = numVal;  }
	    private int getNumVal() {   return numVal;  }
	    public boolean has_permission(Status status){
	    	return 	this.getNumVal()<=  status.getNumVal();   	
	    }	    
	}
	
	private String _username;
	private String _password;
	private Vector<IPost> _threads;
	private Vector<IPost> _replyPosts;
	private Vector<Complaint> _complaints;
	private Date _start_date;
	private Status _status;
	private String _email;
	private Vector<Session> _sassions = new  Vector<Session>() ;



	public User(String username, String password, Status status) {
		this._status = status;
		this._username = username;
		this._password = password;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._email = "";
	}
	
	public User(IForum forum, String username, String password, Status status) {
		this._username = username;
		this._password = password;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status =status;
		this._email = "";
	}

	public User(IForum forum, String username, String password, Status status, String email) {
		this._username = username;
		this._password = password;
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status = status;
		this._email = email;
	}
	
	public User(IForum iForum, String username) {
		this._username = username;
		this._password = "";
		this._threads = new Vector<IPost>();
		this._replyPosts = new Vector<IPost>();
		this._complaints = new Vector<Complaint>();
		this._start_date = new Date();
		this._status = Status.GUEST;
		this._email = "";

	}
//--------------------------------------------------------------------

	public String get_username() {
		return _username;
	}

	public void set_username(String _username) {
		this._username = _username;
	}

	public void set_password(String _password) {
		this._password = _password;
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
			if(this._replyPosts.get(i).get_id()==post.get_id()) {
				this._replyPosts.remove(i);
			}
		}
		for(int i = 0; i < this._threads.size(); i++) {
			if(this._threads.get(i).get_id()==post.get_id()) {
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


	public Status getStatus() {
		return this._status;
	}

	@Override
	public int numPostsUser() {
		return this._replyPosts.size()+this._threads.size();
	}

	@Override
	public void set_start_date(Date start_date) {
		this._start_date = start_date ; 
		
	}

	@Override
	public void set_email(String email) {
		this._email = email ; 
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_password == null) {
			if (other._password != null)
				return false;
		} else if (!_password.equals(other._password))
			return false;
		/*if (_start_date == null) {
			if (other._start_date != null)
				return false;
		} else if (!_start_date.equals(other._start_date))
			return false;*/
		if (_status != other._status)
			return false;
/*		if (_type == null) {
			if (other._type != null)
				return false;
		} else if (!_type.equals(other._type))
			return false;*/
		if (_username == null) {
			if (other._username != null)
				return false;
		} else if (!_username.equals(other._username))
			return false;
		return true;
	}

	
	
	public int start_sassion() {
		synchronized (this._sassions) {
			int sassions_num = this._sassions.size();
			this._sassions.add(new Session());	
			return sassions_num;
		}
	}

	@Override
	public void end_sassion(int sassion_number) {
		if(sassion_number<this._sassions.size() && sassion_number>=0 )			
			this._sassions.get(sassion_number).end_Sassion();
	}

	@Override
	public void add_sassion(String func_name, int sassion_number) {
		if(sassion_number<this._sassions.size()  && sassion_number>=0 )			
			this._sassions.get(sassion_number).addAction(func_name);
	}

	public Vector<Session> get_sessions() {
		return _sassions;
	}

	@Override
	public int get_numSessions() {
		return _sassions.size();
	}
	
	

	

}
