package Domain_layer.FourmUser;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.*;
import Domain_layer.FourmUser.Session.Session;
import Domain_layer.FourmUser.User.Status;

public interface IUser {

	public String get_username();
	public void set_username(String _username);
	public void set_password(String _password);
	public String get_password() ;
	public void add_thread(IPost p);
	public void add_replyPost(IPost p);
	public Vector<IPost> get_threads() ;
	public Vector<IPost> get_reaplayPosts() ;
	public void deletePost(IPost post);
	public boolean isPostedInSubForum(ISubForum subForum);
	public void add_complaint(Complaint complaint);
	
	
	public Vector<IPost> get_replyPosts() ;
	public Vector<Complaint> get_complaints() ;
	public Date get_start_date() ;
	public String get_email();
	public Status getStatus();
	public int numPostsUser();
	public void set_start_date(Date start_date);
	public void set_email(String email);
	public int start_sassion();
	public void end_sassion(int sassion_number);
	public void add_sassion(String func_name, int sassion_number);
	public Vector<Session> get_sessions();
	public int get_numSessions();
	
	
	
}
