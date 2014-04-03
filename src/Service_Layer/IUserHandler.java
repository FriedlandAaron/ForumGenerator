package Service_Layer;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.MemberType;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;

public interface IUserHandler {
	public boolean register(String username, String password, String repeated_password) ;
	public boolean register_Email(String username, String password, String repeated_password , String email);
	public boolean login(String username, String password);
	public boolean logout();
	public IForum createForum(IPolicy p1, Vector<String[]> admins , String theme) ;
	public boolean createSubForum(String theme, String[] moderators_names);
	public Vector<ISubForum> show_sub_forum();
	public boolean create_thread(String header , String body , ISubForum subForum );
	public boolean createReplyPost(String header, String body, IPost post);
	public ISubForum search_subforum(String category_search, String search_word);
	public boolean changePolicy(IPolicy p2);
	public Vector<IPost> show_ReplyPost();
	public Vector<IPost> show_TreadPost() ;
	public boolean deletePost(IPost post);
	public boolean deleteSubForum(ISubForum sub_forum);
	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word , String theme ,String body ) ;
	public boolean addMemberType(String type , IForum forum);
	public boolean removeMemberType(String type, IForum forum);
	public int getNumberOfMemberTypes(IForum forum);
	
	
	public Vector<IPost> get_threads();
	public Vector<IPost> get_reaplayPosts() ;
	public String get_password() ;
	public String get_username() ;
	public IForum get_forum();
	public Vector<IPost> get_replyPosts();
	public Vector<IUser> get_friends();
	public Vector<Complaint> get_complaints();
	public Date get_start_date();
	public Status get_status() ;
	public String get_email();
	public MemberType get_type() ;
	public IUser get_current_user();
	
}
