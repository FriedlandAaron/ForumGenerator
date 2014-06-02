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
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageReturnType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageType;


public class ClientHandler implements IUserHandler {
	private ConnectionHandler _connectionHandler; 
	public ClientHandler(ConnectionHandler connectionHandler) {
		this._connectionHandler = connectionHandler;
	}
	
	public void close_connect() {
	      _connectionHandler.sendForumMessage( new ForumMessage(ForumMessageType.CLOSE,  new  Vector<Object>()));
	      ForumMessage respoenseMessage = _connectionHandler.getForumMessage();
	      if(respoenseMessage ==null || !respoenseMessage.get_type().equals(ForumMessageType.CLOSE))
		       	System.out.println("Error in closing connection");
	      this._connectionHandler.closeConnection();
	}
	
	private  Object send_Commend(ForumMessageCommendType minor_command,  Vector<Object> args ,Object defult_return ,ForumMessageReturnType return_type) {
		  ForumMessage forumMessage = new ForumMessage(ForumMessageType.COMMAND,minor_command , args);
	      _connectionHandler.sendForumMessage(forumMessage);
	      
	      ForumMessage respoenseMessage = _connectionHandler.getForumMessage();
	        if(respoenseMessage ==null || !respoenseMessage.get_type().equals(ForumMessageType.REPLY))
	        	return defult_return;	        

	        if(respoenseMessage.get_minor_type().equals(ForumMessageReturnType.ERROR)){
	        	System.out.println(respoenseMessage.get_arg(0));
	        	return defult_return;
	        }
	        if(!respoenseMessage.get_minor_type().equals(return_type))
	        	return defult_return;
	        return  respoenseMessage.get_arg(0);   
	}
	
	//IUserHandler - interfacs

	public boolean login(String username, String password) {		
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(password);        
        return (boolean) this.send_Commend(ForumMessageCommendType.LOGIN, args, false, ForumMessageReturnType.BOOLEAN);		
	}

	public boolean logout(){
        return (boolean) this.send_Commend(ForumMessageCommendType.LOGOUT,  new  Vector<Object>(), false, ForumMessageReturnType.BOOLEAN);		
	}

	public boolean register(String username, String password,String repeated_password) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(password); 
        args.add(repeated_password); 
        return (boolean) this.send_Commend(ForumMessageCommendType.REGISTER, args, false, ForumMessageReturnType.BOOLEAN);		
	}

	public boolean createSubForum(String theme, String[] moderators_names) {
		Vector<Object> args = new  Vector<Object>();
        args.add(theme);
        args.add(moderators_names); 
        return (boolean) this.send_Commend(ForumMessageCommendType.CR_SUBFORUM, args, false, ForumMessageReturnType.BOOLEAN);
	}

	@SuppressWarnings("unchecked")
	public Vector<ISubForum> show_sub_forum() {
        return (Vector<ISubForum>) this.send_Commend(ForumMessageCommendType.SH_SUBFORUM, new  Vector<Object>(), new Vector<ISubForum>(), ForumMessageReturnType.VEC_ISUBFORUM);
	}

	public boolean create_thread(String header, String body, ISubForum subForum) {
		Vector<Object> args = new  Vector<Object>();
        args.add(header);
        args.add(body); 
        args.add(subForum); 
        return (boolean) this.send_Commend(ForumMessageCommendType.CR_THREAD, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean createReplyPost(String header, String body, IPost post) {
		Vector<Object> args = new  Vector<Object>();
        args.add(header);
        args.add(body); 
        args.add(post); 
        return (boolean) this.send_Commend(ForumMessageCommendType.CR_REPLYPOST, args, false, ForumMessageReturnType.BOOLEAN);
	}
	
	public ISubForum search_subforum(String category_search, String search_word) {
		Vector<Object> args = new  Vector<Object>();
        args.add(category_search);
        args.add(search_word); 
        return (ISubForum) this.send_Commend(ForumMessageCommendType.SEARCH_SUBFORUM, args, null, ForumMessageReturnType.ISUBFORUM);
	}
	
	public boolean changePolicy(IPolicy p2) {
		Vector<Object> args = new  Vector<Object>();
        args.add(p2);
        return (boolean) this.send_Commend(ForumMessageCommendType.CHAN_POLICY, args, false, ForumMessageReturnType.BOOLEAN);
	}

	@SuppressWarnings("unchecked")
	public Vector<IPost> show_ReplyPost() {
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.SH_REPLYPOST,  new  Vector<Object>(), new Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}
	
	@SuppressWarnings("unchecked")
	public Vector<IPost> show_TreadPost() {
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.SH_THREADPOST,  new  Vector<Object>(), new Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}

	public boolean deletePost(IPost post) {
		Vector<Object> args = new  Vector<Object>();
        args.add(post);
        return (boolean) this.send_Commend(ForumMessageCommendType.DELETE_POST, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean deleteSubForum(ISubForum sub_forum) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum);
        return (boolean) this.send_Commend(ForumMessageCommendType.DELETE_SUBFORUM, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word, String theme, String body) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_fourm);
        args.add(search_word);
        args.add(theme);
        args.add(body);
        return (boolean) this.send_Commend(ForumMessageCommendType.ADD_COMP_MOD, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean addMemberType(String type) {
		Vector<Object> args = new  Vector<Object>();
        args.add(type);
        return (boolean) this.send_Commend(ForumMessageCommendType.ADD_MEMBER_TYPE, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean removeMemberType(String type) {
		Vector<Object> args = new  Vector<Object>();
        args.add(type);
        return (boolean) this.send_Commend(ForumMessageCommendType.REM_MEMBER_TYPE, args, false, ForumMessageReturnType.BOOLEAN);
	}

	public int getNumberOfMemberTypes() {
        return (int) this.send_Commend(ForumMessageCommendType.GET_NUM_MEMBER_TYPE, new  Vector<Object>(), 0, ForumMessageReturnType.INTEGER);
	}

	@SuppressWarnings("unchecked")
	public Vector<IPost> get_threads() {
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.GET_THREADS, new  Vector<Object>(), new Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}

	@SuppressWarnings("unchecked")
	public Vector<IPost> get_reaplayPosts() {
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.GET_REPLYPOST, new  Vector<Object>(), new Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}

	//setters and getters
	public String get_password() { return (String) this.send_Commend(ForumMessageCommendType.GET_PASS, new  Vector<Object>(), "", ForumMessageReturnType.STRING);}
	public String get_username() { return (String) this.send_Commend(ForumMessageCommendType.GET_USERNAME, new  Vector<Object>(), "", ForumMessageReturnType.STRING);}
	public IForum get_forum() {	return (IForum) this.send_Commend(ForumMessageCommendType.GET_FORUM, new  Vector<Object>(), null, ForumMessageReturnType.IFORUM);}
	public Vector<IPost> get_replyPosts() {	return get_reaplayPosts();}	
	@SuppressWarnings("unchecked")
	public Vector<IUser> get_friends() {return (Vector<IUser>) this.send_Commend(ForumMessageCommendType.GET_FRIENDS, new  Vector<Object>(), null, ForumMessageReturnType.VEC_IUSER);}
	@SuppressWarnings("unchecked")
	public Vector<Complaint> get_complaints() {	return (Vector<Complaint>) this.send_Commend(ForumMessageCommendType.GET_COMP, new  Vector<Object>(), null, ForumMessageReturnType.VEC_COMP);}
	public Date get_start_date() {	return (Date) this.send_Commend(ForumMessageCommendType.GET_STARTDATE, new  Vector<Object>(), null, ForumMessageReturnType.STARTDATE);}
	public Status get_status() {return (Status) this.send_Commend(ForumMessageCommendType.GET_STATUS, new  Vector<Object>(), null, ForumMessageReturnType.STATUS);}
	public String get_email() {	return (String) this.send_Commend(ForumMessageCommendType.GET_EMAIL, new  Vector<Object>(), "", ForumMessageReturnType.STRING);}
	public MemberType get_type() {return (MemberType) this.send_Commend(ForumMessageCommendType.GET_TYPE, new  Vector<Object>(), "", ForumMessageReturnType.MEMBERTYPE);}
	public IUser get_current_user() {return (IUser) this.send_Commend(ForumMessageCommendType.GET_CUR_USR, new  Vector<Object>(), "", ForumMessageReturnType.IUSER);}
	public Status getUserStatus() {	return get_status();}
	public boolean addModerator(String sub_forum_theme,	String username_to_moderate) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum_theme);
        args.add(username_to_moderate);
        return (boolean) this.send_Commend(ForumMessageCommendType.ADD_MODERATOR, args, false, ForumMessageReturnType.BOOLEAN);
	}
	public boolean removeModerator(String sub_forum_theme,String username_to_moderate) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum_theme);
        args.add(username_to_moderate);
        return (boolean) this.send_Commend(ForumMessageCommendType.DEL_MODERATOR, args, false, ForumMessageReturnType.BOOLEAN);
	}
	public int numSubForum() {   				return (int) this.send_Commend(ForumMessageCommendType.NUM_SUBFORUM, new  Vector<Object>(), false, ForumMessageReturnType.INTEGER);}
	public int numSharedModeratorsSubForum() { 	return (int) this.send_Commend(ForumMessageCommendType.NUM_SHARED_MOD, new  Vector<Object>(), false, ForumMessageReturnType.INTEGER);}
	public int numPostsSubForum(String sub_forum_theme) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum_theme);
        return (int) this.send_Commend(ForumMessageCommendType.NUM_POSTS_SUBFORUM, args, false, ForumMessageReturnType.INTEGER);
	}
	public int numPostsUser(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (int) this.send_Commend(ForumMessageCommendType.NUM_POSTS_USER, args, false, ForumMessageReturnType.INTEGER);
	}
	@SuppressWarnings("unchecked")
	public Vector<IUser> Moderators_list() { return ( Vector<IUser>) this.send_Commend(ForumMessageCommendType.MODERATORS_LIST, new  Vector<Object>(), false, ForumMessageReturnType.VEC_IUSER);}
	public String Moderators_Report() {return (String) this.send_Commend(ForumMessageCommendType.MODERATORS_REPORT, new  Vector<Object>(), false, ForumMessageReturnType.STRING);}
	
	public boolean setMethodPolicy(String Methodname, Status s) {
		return (boolean) this.send_Commend(ForumMessageCommendType.SET_METHOD_POLICY, new  Vector<Object>(), false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean create_thread(String header, String body, String string) {
		// TODO Auto-generated method stub
		return false;
	}
}
