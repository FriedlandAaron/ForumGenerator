package Domain_layer.ForumComponent;

import java.util.Vector;

import Domain_layer.FourmUser.*;
import Network_layer.FourmMail.MailHandler;

//hijg my is aaron

public interface IForum {
	

	public void addMember(String username, String password);
	public String get_theme();
	public MailHandler get_mailHandler();
	public	boolean isMember(String username);
	public	boolean isMember(String username ,String password);
	public IUser getMember(String username) ;
	public void addSubForum(ISubForum subForum);
	public Vector<ISubForum> list_sub_forum();
	public ISubForum search_subforum_byTheme(String search_word);
	public ISubForum search_subforum_byModerator(String search_word);
	public void set_policy(IPolicy _policy);
	public void deleteSubForum(ISubForum sub_forum);
	public void init_Forum();
	public void close_Forum();
	public void addMemberType(String name);
	public MemberType getMemberTypeByName(String name) ;
	public void removeMemberType(String name);
	public int getNumberOfTypes();
	public void add_to_waitingList(String username, String password,String email);
	public void checkValidationEmails();
	
	
	
	
	public Vector<IUser> get_members() ;
	public Vector<IUser> get_banned_members() ;
	public Vector<IUser> get_administrators();
	public Vector<ISubForum> get_subForums();
	public Logger get_action_logger();
	public Logger get_error_logger() ;
	public IPolicy get_policy();
	public Vector<MemberType> get_memberTypes() ;
	public Vector<String[]> get_waitingList() ;
	
}
