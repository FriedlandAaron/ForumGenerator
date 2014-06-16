package Service_Layer;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;
import Domain_layer.FourmUser.Session.Session;

public interface IUserHandler {
	public boolean register(String username, String password, String repeated_password) ;
	public boolean register_Email(String username, String password, String repeated_password , String email);
	public boolean submit_code_registertion(String username , String code);
	public boolean login(String username, String password);
	public boolean logout();
	public boolean createSubForum(String theme, String[] moderators_names);
	public Vector<ISubForum> show_sub_forum();

	public boolean create_thread(String header, String body, String sub_forum_theme);
	public boolean createReplyPost(String header, String body, IPost post);
	public boolean deletePost(IPost post);	
	public boolean deleteSubForum(String sub_forum);
	
	public boolean setMethodPolicy(String Methodname , Status s) ;

//MODERATORS	
	public boolean addcomplaintModerator(ISubForum sub_fourm, String moderator_name , String theme ,String body ) ;
	public boolean addModerator(String sub_forum_theme ,String username_to_moderate);
	public boolean removeModerator(String sub_forum_theme ,String username_to_moderate);
	public String  Moderators_Report();		
	public Vector<Complaint> get_userComplaint(String username);

/*
//MEMBER TYPE
	public boolean addMemberType(String type );
	public boolean removeMemberType(String type);
	public int getNumberOfMemberTypes();
	
	*/
	
//Getters
	public String get_username() ;
	public Status get_status() ;
	
//Connections
	public void close_connect() ;
		
	
//gui version 1 
	public Vector<IPost> get_theardsSubForum(String subforum);
	public Vector<IPost> get_replyPosts(IPost parent);
	public String[] show_sub_forum_names();


//observe
	public void observe(int port);

//logger
	public void log_action( String func_name, Date date);
	public void log_error(String func_name, Date date, Exception e);

	
//sesions
	public int start_sassion();
	public void end_sassion(int sassion_number);
	public void add_sassion(String func_name, int sassion_number);
	public Vector<Session> get_sessions();

//REPORTS:
	
	//forum:
		public String get_forum_name();
		public int	numSubForum();
		public int	numSharedModeratorsSubForum();
		public int	numPostsForum();

	
	//sabForum
		//number of moderators from 
		public String   Moderators_list(String sub_forum_theme);
		//number of threads from get_theardsSubForum.size 
		public int	numPostsSubForum(String sub_forum_theme);

	//user
		public Status get_status_user(String username) ;
		public Date get_start_date_user(String username);
		public String get_email_user(String username);
		public int	numPosts_user(String username);
		public int  numSassions_user(String username);
		public String moderator_subforum_list_user(String username);	
		
	//test
		public IUser get_current_user();
		public ISubForum search_subforum(String category_search, String search_word);
		public boolean changePolicy(IPolicy p2);
		public Vector<IPost> show_ReplyPost();
		public Vector<IPost> show_TreadPost() ;


}
