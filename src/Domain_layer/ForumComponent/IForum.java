package Domain_layer.ForumComponent;

import java.util.Vector;

import Domain_layer.ForumComponent.Logger.*;
import Domain_layer.FourmUser.*;
import Domain_layer.FourmUser.User.Status;

//hijg my is aaron

public interface IForum {
	
	public void addMember(String username, String password);
	public String get_theme();
	//public MailHandler get_mailHandler();
	public	boolean isMember(String username);
	public	boolean isMember(String username ,String password);
	public IUser getMember(String username) ;
	public void addSubForum(ISubForum subForum);
	public Vector<ISubForum> show_sub_forum();
	public ISubForum search_subforum_byTheme(String search_word);
	public ISubForum search_subforum_byModerator(String search_word);
	public void set_policy(IPolicy _policy);
	public void deleteSubForum(ISubForum sub_forum);
	//public void init_Forum();
	//public void close_Forum();
	public boolean addMemberType(IUser current_user, String name);
	public MemberType getMemberTypeByName(String name) ;
	public boolean removeMemberType(IUser current_user, String name);
	public int getNumberOfTypes(IUser current_user);
	//public void add_to_waitingList(String username, String password,String email);
	//public void checkValidationEmails();
	
	
	public Vector<IUser> get_members() ;
	public Vector<IUser> get_banned_members() ;
	public Vector<IUser> get_administrators();
	public Vector<ISubForum> get_subForums();
	public Action_Logger get_action_logger();
	public Error_Logger get_error_logger() ;
	public IPolicy get_policy();
	public Vector<MemberType> get_memberTypes() ;
	public Vector<String[]> get_waitingList() ;
	
	
	
	//------------------------------------------------------
	public boolean register(String username, String password,String repeated_password);
	//public boolean register_Email(String username, String password,	String repeated_password ,  String email);
	public IUser login(IUser current_user, String username, String password);
	public IUser logout(IUser current_user);
	public boolean createSubForum(IUser _current_user, String theme,String[] moderators_names);
	public boolean create_thread(IUser current_user , String header, String body, ISubForum subForum);
	public boolean createReplyPost(IUser current_user, String header,String body, IPost post);
	public ISubForum search_subforum(String category_search, String search_word);
	public boolean changePolicy(IUser current_user , IPolicy p2);
	public boolean deletePost(IUser current_user, IPost post);
	public boolean deleteSubForum(IUser current_user, ISubForum sub_forum);
	public boolean addcomplaintModerator(IUser current_user,ISubForum sub_fourm, String search_word, String theme, String body);
	
	public int numSubForum();
	public boolean addModerator(IUser current_user ,String sub_forum_theme,	String username_to_moderate);
	public boolean removeModerator(IUser current_user , String sub_forum_theme,String username_to_moderate);
	public int numPostsSubForum(String sub_forum_theme);
	public int numPostsUser(String username);
	public Vector<IUser> Moderators_list();
	public String Moderators_Report();
	public int numSharedModeratorsSubForum();
	public boolean setMethodPolicy(IUser _current_user, String methodname,			Status s);
	
	
	//data base
	public int get_id();
	public boolean create_thread(IUser user ,String header, String body, String string);
	
}
