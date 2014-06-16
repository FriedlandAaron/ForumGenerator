package Service_Layer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;
import Domain_layer.FourmUser.Session.Session;
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
	//	String password_new  = PasswordEncryptor.encrypt(password) ;
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(password);        
        return (boolean) this.send_Commend(ForumMessageCommendType.LOGIN, args, false, ForumMessageReturnType.BOOLEAN);		
	}

	public boolean logout(){
        return (boolean) this.send_Commend(ForumMessageCommendType.LOGOUT,  new  Vector<Object>(), false, ForumMessageReturnType.BOOLEAN);		
	}

	public boolean register(String username, String password,String repeated_password) {
	//	String password_new  = PasswordEncryptor.encrypt(password) ;
	//	String repeated_password_new   = PasswordEncryptor.encrypt(repeated_password) ; 
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(password); 
        args.add(repeated_password); 
        return (boolean) this.send_Commend(ForumMessageCommendType.REGISTER, args, false, ForumMessageReturnType.BOOLEAN);
	}
	

	
	public boolean register_Email(String username, String password,	String repeated_password, String email) {
	//	String password_new  = PasswordEncryptor.encrypt(password) ;
	//	String repeated_password_new   = PasswordEncryptor.encrypt(repeated_password) ; 
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(password); 
        args.add(repeated_password); 
        args.add(email);
        return (boolean) this.send_Commend(ForumMessageCommendType.REGISTER_EMAIL, args, false, ForumMessageReturnType.BOOLEAN);		
	}

	
	public boolean submit_code_registertion(String username, String code) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        args.add(code);
        return (boolean) this.send_Commend(ForumMessageCommendType.SUBMIT_CODE_REG, args, false, ForumMessageReturnType.BOOLEAN);
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

	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word, String theme, String body) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_fourm);
        args.add(search_word);
        args.add(theme);
        args.add(body);
        return (boolean) this.send_Commend(ForumMessageCommendType.ADD_COMP_MOD, args, false, ForumMessageReturnType.BOOLEAN);
	}

	/*
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
	*/


	//setters and getters
	public String get_username() { return (String) this.send_Commend(ForumMessageCommendType.GET_USERNAME, new  Vector<Object>(), "", ForumMessageReturnType.STRING);}
	public IForum get_forum() {	return (IForum) this.send_Commend(ForumMessageCommendType.GET_FORUM, new  Vector<Object>(), null, ForumMessageReturnType.IFORUM);}
	public Status get_status() {return (Status) this.send_Commend(ForumMessageCommendType.GET_STATUS, new  Vector<Object>(), null, ForumMessageReturnType.STATUS);}
	public IUser get_current_user() {return (IUser) this.send_Commend(ForumMessageCommendType.GET_CUR_USR, new  Vector<Object>(), "", ForumMessageReturnType.IUSER);}
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
	public int numPosts_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (int) this.send_Commend(ForumMessageCommendType.NUM_POSTS_USER, args, false, ForumMessageReturnType.INTEGER);
	}
	public String Moderators_list(String sub_forum_theme) { 
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum_theme);
		return ( String) this.send_Commend(ForumMessageCommendType.MODERATORS_LIST,args, "", ForumMessageReturnType.STRING);}
	public String Moderators_Report() {return (String) this.send_Commend(ForumMessageCommendType.MODERATORS_REPORT, new  Vector<Object>(), false, ForumMessageReturnType.STRING);}
	
	public boolean setMethodPolicy(String Methodname, Status s) {
		return (boolean) this.send_Commend(ForumMessageCommendType.SET_METHOD_POLICY, new  Vector<Object>(), false, ForumMessageReturnType.BOOLEAN);
	}

	public boolean create_thread(String header, String body, String sub_forum_theme) {
		Vector<Object> args = new  Vector<Object>();
        args.add(header);
        args.add(body);
        args.add(sub_forum_theme);
        return (boolean) this.send_Commend(ForumMessageCommendType.CR_THREAD, args, false, ForumMessageReturnType.BOOLEAN);
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public Vector<IPost> get_theardsSubForum(String subforumPost) {
		Vector<Object> args = new  Vector<Object>();
        args.add(subforumPost);
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.GET_THREADS_SUBFORUM, args, new  Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}



	public String get_forum_name() {
		return (String) this.send_Commend(ForumMessageCommendType.FORUM_NAME, new  Vector<Object>(), "DEFAULT_NAME", ForumMessageReturnType.STRING);
	}

	@SuppressWarnings("unchecked")
	public Vector<IPost> get_replyPosts(IPost parent) {
		Vector<Object> args = new  Vector<Object>();
        args.add(parent);
        return (Vector<IPost>) this.send_Commend(ForumMessageCommendType.GET_REPLYPOST_POST, args, new  Vector<IPost>(), ForumMessageReturnType.VEC_IPOST);
	}

	@Override
	public String[] show_sub_forum_names() {
        return (String[]) this.send_Commend(ForumMessageCommendType.SH_SUBFORUM_NAMES,  new  Vector<Object>(),new String[]{}, ForumMessageReturnType.STRING_ARRAY);
	}

	@Override
	public boolean deleteSubForum(String sub_forum) {
		Vector<Object> args = new  Vector<Object>();
        args.add(sub_forum);
        return (boolean) this.send_Commend(ForumMessageCommendType.DELETE_SUBFORUM_THEME, args, false, ForumMessageReturnType.BOOLEAN);
	}
	
	public void observe(int port) {
		String ip="";
		try {
			 ip = InetAddress.getLocalHost().getHostAddress();        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Vector<Object> args = new  Vector<Object>();
        args.add(ip);
        args.add(port);
        boolean result = (boolean) this.send_Commend(ForumMessageCommendType.OBSERVE, args, false, ForumMessageReturnType.BOOLEAN);
        if(!result)
        	throw new RuntimeException("OBSERVE FAIL");
        
	}

	
	
	
	
	
	@SuppressWarnings("unchecked")
	public Vector<Session> get_sessions() {
		return (Vector<Session>) this.send_Commend(ForumMessageCommendType.SESSIONS, new  Vector<Object>(), new  Vector<Session>(), ForumMessageReturnType.VEC_SESSIONS);
	}	
	
	
	@SuppressWarnings("unchecked")
	public Vector<Complaint> get_userComplaint(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (Vector<Complaint>) this.send_Commend(ForumMessageCommendType.GET_COMP, args, false, ForumMessageReturnType.VEC_COMP);
	}

	
	
	
	
	
	/******************emty methods******************/
	@Override
	public void log_action(String func_name, Date date) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void log_error(String func_name, Date date, Exception e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public int start_sassion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void end_sassion(int sassion_number) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void add_sassion(String func_name, int sassion_number) {
		// TODO Auto-generated method stub		
	}

	
	//REPORTS
	@Override
	public int numPostsForum() {
        return (int) this.send_Commend(ForumMessageCommendType.NUM_POSTS_FORUM,new  Vector<Object>(),-1, ForumMessageReturnType.INTEGER);
	}

	
	public Status get_status_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (Status) this.send_Commend(ForumMessageCommendType.GET_STATUS_USER,args,Status.GUEST, ForumMessageReturnType.STATUS);
	}

	public Date get_start_date_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (Date) this.send_Commend(ForumMessageCommendType.GET_STARTDATE_USER,args,new Date(), ForumMessageReturnType.STARTDATE);
	}

	public String get_email_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (String) this.send_Commend(ForumMessageCommendType.GET_EMAIL_USER,args,"", ForumMessageReturnType.STRING);
	}

	public int numSassions_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (int) this.send_Commend(ForumMessageCommendType.SESSIONS_NUM_USER,args,-1, ForumMessageReturnType.INTEGER);
	}

	public String moderator_subforum_list_user(String username) {
		Vector<Object> args = new  Vector<Object>();
        args.add(username);
        return (String) this.send_Commend(ForumMessageCommendType.MODERATORS_SUBFROUM_LIST_USER,args,"", ForumMessageReturnType.STRING);
	}




	

}
