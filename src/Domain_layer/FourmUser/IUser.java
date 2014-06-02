package Domain_layer.FourmUser;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.*;
import Domain_layer.FourmUser.User.Status;

public interface IUser {

	public void set_type(MemberType _type);
	public String get_username();
	public IForum get_forum();
	public void set_username(String _username);
	public void set_password(String _password);
	public void set_forum(IForum _forum);
	public String get_password() ;
	public void add_thread(IPost p);
	public void add_replyPost(IPost p);
	public Vector<IPost> get_threads() ;
	public Vector<IPost> get_reaplayPosts() ;
	public void deletePost(IPost post);
	public boolean isPostedInSubForum(ISubForum subForum);
	public void add_complaint(Complaint complaint);
	
	
	public Vector<IPost> get_replyPosts() ;
	public Vector<IUser> get_friends() ;
	public Vector<Complaint> get_complaints() ;
	public Date get_start_date() ;
	public String get_email();
	public MemberType get_type();
	public Status getStatus();
	public int numPostsUser();
	
	
	
}
