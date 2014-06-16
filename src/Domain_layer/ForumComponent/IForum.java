package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;

//hijg my is aaron

public interface IForum {
	
	public String get_theme();
	public Vector<ISubForum> show_sub_forum();
	/*
	public boolean addMemberType(IUser current_user, String name);
	public MemberType getMemberTypeByName(String name) ;
	public boolean removeMemberType(IUser current_user, String name);
	public int getNumberOfTypes(IUser current_user);	
	*/
	
	//tests
	public ISubForum search_subforum(String category_search, String search_word);
	public boolean changePolicy(IUser current_user , IPolicy p2);

	//-----------------------------------------------------------------------------------------------------
	public boolean register(String username, String password,String repeated_password);
	public boolean register_Email(String username, String password,	String repeated_password ,  String email);
	public boolean submit_code_registertion(String username, String code);	
	public IUser login(IUser current_user, String username, String password);
	public IUser logout(IUser current_user);
	public boolean createSubForum(IUser _current_user, String theme,String[] moderators_names);
	public boolean create_thread(IUser current_user , String header, String body, ISubForum subForum);
	public boolean createReplyPost(IUser current_user, String header,String body, IPost post);
	public boolean deletePost(IUser current_user, IPost post);
	public boolean deleteSubForum(IUser current_user, String sub_forum) ;
	public boolean addcomplaintModerator(IUser current_user,ISubForum sub_fourm, String search_word, String theme, String body);
	
	public int numSubForum();
	public boolean addModerator(IUser current_user ,String sub_forum_theme,	String username_to_moderate);
	public boolean removeModerator(IUser current_user , String sub_forum_theme,String username_to_moderate);
	public int numPostsSubForum(String sub_forum_theme);
	public int numPostsUser(String username);
	public String Moderators_list(IUser _current_user, String sub_forum_theme);
	public String Moderators_Report();
	public int numSharedModeratorsSubForum();
	public boolean setMethodPolicy(IUser _current_user, String methodname,	Status s);	
	
	//data base
	public boolean create_thread(IUser user ,String header, String body, String sub_forum_theme);
	
	//logger
	public void log_action(String username, String commend, Date date) ;
	public void log_error(String username , String commend, Date date, Exception e) ;
	
	//gui_new
	public Vector<IPost> get_theardsSubForum(IUser _current_user,  	String subforumPost);
	public Vector<IPost> get_replyPosts(IUser _current_user, IPost parent);
	public String[]		 show_sub_forum_names();
	
	//sassion
	public int start_sassion(IUser _current_user);
	public void end_sassion(IUser _current_user, int sassion_number);
	public void add_sassion(IUser _current_user, String func_name,	int sassion_number);
	public Vector<Complaint> get_userComplaint(IUser _current_user,	String username);
	
	
	public int numPostsForum(IUser current_user);
	public Status get_status_user(IUser _current_user, String username);
	public Date get_start_date_user(IUser _current_user, String username);
	public String get_email(IUser _current_user, String username);
	public int numSassions_user(IUser _current_user, String username);
	public String moderator_subforum_list_user(IUser _current_user,	String username);
		
}
