package Network_layer.reactorServer.protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Date;

import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Network_layer.reactorServer.reactor.ReactorData;
import Network_layer.reactorServer.tokenizer.ForumMessage;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageMinorType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageReturnType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageType;
import Service_Layer.IUserHandler;

/**
 * a simple implementation of the server protocol interface
 */
public class UserProtocol implements AsyncServerProtocol<ForumMessage> {
	
	
	private IUserHandler _userHandler;
	private boolean _shouldClose ;
	private boolean _connectionTerminated ;
	private ReactorData<ForumMessage> reactor_data;
	private PrintWriter out = null;
	private int sassion_number = -1;

	private Object notify_functions_lock = new Object();

	public UserProtocol() {
		this._userHandler = null;
		this._shouldClose = false;
		this._connectionTerminated = false;
	}
		
	/**
	 * processes a message<BR>
	 *
	 * @param msg the ForumMessage to process
	 * @return the ForumMessage reply that should be sent to the client, or null if no reply needed
	 */
	public ForumMessage processMessage(ForumMessage msg) {
		if (this._connectionTerminated) 
			return null;		
		if (this.isEnd(msg)) {
			this._shouldClose = true;
			return ForumMessage.create_CloseReplay();
		}	
		if(!msg.get_type().equals(ForumMessageType.COMMAND))
			return ForumMessage.create_ErrorReplay("Error: ForumMessage is not COMMAND or CLOSE type");
		ForumMessageCommendType commend ;
		try{
			commend = (ForumMessageCommendType) msg.get_minor_type();			
		}  catch (Exception e) {
			return ForumMessage.create_ErrorReplay("Error in process COMMAND Message: unrecognized command OR minor_type commend null: "+e.toString());
		} 	
		if(commend == null)
			return ForumMessage.create_ErrorReplay("Error in process COMMAND Message:minor_type commend null: ");
		return processCommendMessage(commend , msg);
	}
	public PrintWriter getOut() {
		return out;
	}
	public boolean isEnd(ForumMessage msg) {
		return msg.get_type().equals(ForumMessageType.CLOSE);
	}
	
	public boolean shouldClose() {
		return this._shouldClose;
	}
	
	public void connectionTerminated() {
		this._connectionTerminated = true;		
	}
	
	
	/**
	 *  the function get a ServerMember and inisilized the protocol serverMember. 
	 *  @param ServerMember the Server member that this Protocol his.
	 */
	public void InitUserHandler(IUserHandler userHandler ) {
		this._userHandler= userHandler;
	}
	
	
	public void InitReactorData(ReactorData<ForumMessage> reactor_data ) {
		this.reactor_data= reactor_data;
	}

	
	
	private ForumMessage processCommendMessage(ForumMessageCommendType commend, ForumMessage msg) {
		
		ForumMessage replay_massegse = null ;
		switch (commend) {
        case LOGIN: 			replay_massegse = command("login",new Class[]{String.class ,String.class} ,ForumMessageReturnType.BOOLEAN, msg) ;													break;
        case LOGOUT:			replay_massegse = command("logout",new Class[]{} ,ForumMessageReturnType.BOOLEAN, msg) ;																			break;
        case REGISTER:			replay_massegse = command("register",new Class[]{String.class ,String.class, String.class} , ForumMessageReturnType.BOOLEAN ,msg);									break;
        case CR_SUBFORUM:		replay_massegse = command("createSubForum",new Class[]{String.class ,String[].class} , ForumMessageReturnType.BOOLEAN ,msg ); 										break;
        case SH_SUBFORUM:  		replay_massegse = command("show_sub_forum",new Class[]{} , ForumMessageReturnType.VEC_ISUBFORUM ,msg);																break;
        case CR_THREAD: 		replay_massegse = command("create_thread",new Class[]{String.class, String.class ,String.class}, ForumMessageReturnType.BOOLEAN ,msg);								break;
        case CR_REPLYPOST: 		replay_massegse = command("createReplyPost",new Class[]{String.class ,String.class ,IPost.class }  , ForumMessageReturnType.BOOLEAN ,msg  );   						break;
        case SEARCH_SUBFORUM:	replay_massegse = command("search_subforum",new Class[]{String.class ,String.class  }  , ForumMessageReturnType.ISUBFORUM ,msg  );   								break;
        case CHAN_POLICY:		replay_massegse = command("changePolicy",new Class[]{ IPolicy.class}  , ForumMessageReturnType.BOOLEAN ,msg  );														break;       
        case SH_REPLYPOST:		replay_massegse = command("show_ReplyPost",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																	break;    
        case SH_THREADPOST:		replay_massegse = command("show_TreadPost",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																	break;    
        case DELETE_POST:		replay_massegse = command("deletePost",new Class[]{IPost.class}, ForumMessageReturnType.BOOLEAN ,msg  );															break;   
        case ADD_COMP_MOD:		replay_massegse = command("addcomplaintModerator",new Class[]{ISubForum.class , String.class , String.class , String.class}, ForumMessageReturnType.BOOLEAN ,msg  );break;   
        case ADD_MEMBER_TYPE:  	replay_massegse = command("addMemberType",new Class[]{String.class }, ForumMessageReturnType.BOOLEAN ,msg);															break;   
        case REM_MEMBER_TYPE:	replay_massegse = command("removeMemberType",new Class[]{String.class }, ForumMessageReturnType.BOOLEAN ,msg);												     	break; 
        case GET_NUM_MEMBER_TYPE:replay_massegse= command("getNumberOfMemberTypes",new Class[]{}, ForumMessageReturnType.INTEGER ,msg  );															break;
       //getters and settrs
        case GET_USERNAME:		replay_massegse = command("get_username",new Class[]{}, ForumMessageReturnType.STRING ,msg  );																		break;
        case GET_FORUM:			replay_massegse = command("get_forum",new Class[]{}, ForumMessageReturnType.IFORUM ,msg  );																			break;
        case GET_STATUS:		replay_massegse = command("get_status",new Class[]{}, ForumMessageReturnType.STATUS ,msg  );																		break;
        case GET_CUR_USR:		replay_massegse = command("get_current_user",new Class[]{}, ForumMessageReturnType.IUSER ,msg);																		break;
        case ADD_MODERATOR:		replay_massegse = command("addModerator",new Class[]{String.class ,String.class }, ForumMessageReturnType.BOOLEAN ,msg);											break;
        case DEL_MODERATOR:		replay_massegse = command("removeModerator",new Class[]{String.class ,String.class }, ForumMessageReturnType.BOOLEAN ,msg);											break;
        case NUM_SUBFORUM:		replay_massegse = command("numSubForum",new Class[]{}, ForumMessageReturnType.INTEGER ,msg);																		break;
        case NUM_SHARED_MOD:	replay_massegse = command("numSharedModeratorsSubForum",new Class[]{}, ForumMessageReturnType.INTEGER ,msg);														break;
        case NUM_POSTS_SUBFORUM:replay_massegse = command("numPostsSubForum",new Class[]{String.class}, ForumMessageReturnType.INTEGER ,msg);														break;
        case NUM_POSTS_USER:	replay_massegse = command("numPosts_user",new Class[]{String.class}, ForumMessageReturnType.INTEGER ,msg);															break;
        case MODERATORS_LIST:	replay_massegse = command("Moderators_list",new Class[]{String.class}, ForumMessageReturnType.STRING ,msg);																	break;
        case MODERATORS_REPORT:	replay_massegse = command("Moderators_Report",new Class[]{}, ForumMessageReturnType.STRING ,msg);																	break;
        case SET_METHOD_POLICY: replay_massegse = command("setMethodPolicy",new Class[]{}, ForumMessageReturnType.STRING ,msg);																		break;
        case GET_COMP: 			replay_massegse = command("get_userComplaint",new Class[]{String.class }, ForumMessageReturnType.VEC_COMP ,msg);													break;

        //EMAIL
        case REGISTER_EMAIL: replay_massegse = command("register_Email",new Class[]{String.class, String.class, String.class  ,String.class}, ForumMessageReturnType.BOOLEAN ,msg);					break;
        case SUBMIT_CODE_REG: replay_massegse = command("submit_code_registertion",new Class[]{String.class, String.class }, ForumMessageReturnType.BOOLEAN ,msg);									break;


        //GUI
        case FORUM_NAME:		replay_massegse = command("get_forum_name",new Class[]{}, ForumMessageReturnType.STRING ,msg  );																	break;
        case GET_THREADS_SUBFORUM:		replay_massegse = command("get_theardsSubForum",new Class[]{String.class}, ForumMessageReturnType.VEC_IPOST ,msg);											break;
        case GET_REPLYPOST_POST: replay_massegse = command("get_replyPosts",new Class[]{IPost.class	 }, ForumMessageReturnType.VEC_IPOST ,msg);														break;
        case SH_SUBFORUM_NAMES : replay_massegse = command("show_sub_forum_names",new Class[]{	 }, ForumMessageReturnType.STRING_ARRAY ,msg);														break;
        case DELETE_SUBFORUM_THEME: replay_massegse = command("deleteSubForum",new Class[]{String.class}, ForumMessageReturnType.BOOLEAN ,msg  );													break;   
        case OBSERVE :  replay_massegse = this.observe(msg); break;
        
        //SESSIONS
        case SESSIONS: replay_massegse = command("get_sessions",new Class[]{}, ForumMessageReturnType.VEC_SESSIONS ,msg  );													break;   

        
        //REPORTS
        case NUM_POSTS_FORUM: replay_massegse = command("numPostsForum",new Class[]{}, ForumMessageReturnType.INTEGER ,msg  );													break;   
        case GET_STATUS_USER:		replay_massegse = command("get_status_user",new Class[]{String.class}, ForumMessageReturnType.STATUS ,msg  );																		break;
        case GET_STARTDATE_USER:		replay_massegse = command("get_start_date_user",new Class[]{String.class}, ForumMessageReturnType.STARTDATE ,msg  );																	break;
        case GET_EMAIL_USER:		replay_massegse = command("get_email_user",new Class[]{String.class}, ForumMessageReturnType.STRING ,msg  );																	break;
        case SESSIONS_NUM_USER:		replay_massegse = command("numSassions_user",new Class[]{String.class}, ForumMessageReturnType.INTEGER ,msg  );																	break;
        case MODERATORS_SUBFROUM_LIST_USER:		replay_massegse = command("moderator_subforum_list_user",new Class[]{String.class}, ForumMessageReturnType.STRING ,msg  );																	break;

        
        
        
        default: 
        	replay_massegse = ForumMessage.create_ErrorReplay("Error in processCommendMessage: unrecognized command ");  break;
		}
		return replay_massegse;
	}



	//-----------------------------commend-------------------------
	private ForumMessage command(String func_name , Class<?>[] func_args ,ForumMessageMinorType minor ,   ForumMessage msg){
		try {
			Method m = IUserHandler.class.getMethod(func_name ,func_args);
			ForumMessage msg_ret  = ForumMessage.create_Replay( minor ,m.invoke(this._userHandler, msg.get_Object_Array()) );
			this._userHandler.log_action( func_name, new Date());
			
			sassion_func(func_name ,msg_ret );				
				
			if(msg_ret.get_minor_type()==ForumMessageReturnType.BOOLEAN && msg_ret.get_arg(0).equals(true) )
				this.notify(func_name , this.getSubForumFromOpreation(func_name ,msg));
			return msg_ret;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
			this._userHandler.log_error(func_name , new Date(), e);
        	return  ForumMessage.create_ErrorReplay("Error in command: " + e.toString());  
		} catch	(Exception e){
			this._userHandler.log_error(func_name , new Date(), e);
        	return  ForumMessage.create_ErrorReplay("Error in command: " + e.toString());  
		}
	}
	

	private final String[] sassion_functions = {"createSubForum" ,"create_thread" ,"createReplyPost" ,"deletePost" ,"deleteSubForum","changePolicy","setMethodPolicy",	"addMemberType" ,"removeMemberType" ,"addModerator" ,"removeModerator"	} ; 
	private void sassion_func(String func_name, ForumMessage msg_ret) {
		if(func_name.equals("login") && msg_ret.get_arg(0).equals(true)){
			this.sassion_number = this._userHandler.start_sassion();
		}
		else if(func_name.equals("logout") && msg_ret.get_arg(0).equals(true)){
			this.sassion_number=-1;
			this._userHandler.end_sassion(this.sassion_number);
		}
		else{
			for(String func: sassion_functions){
				if(func.equals(func_name)){
					this._userHandler.add_sassion(func_name ,this.sassion_number);
				}
			}
		}		
	}


	private ForumMessage observe(ForumMessage msg) {
		 msg.get_Object_Array();
		 Object [] params = msg.get_Object_Array();
		 try{
			 String ip = (String) params[0];
			 int port =  (int) params[1];
			 @SuppressWarnings("resource")
			 Socket socket = new Socket(ip, port);
			 out = new PrintWriter(socket.getOutputStream(), true);
			 this.reactor_data.addObserver(out);
		 }catch(ClassCastException | IOException e){
			 System.out.println(e);
			return ForumMessage.create_Replay( ForumMessage.ForumMessageReturnType.BOOLEAN ,false );
		}	
		 
		return ForumMessage.create_Replay( ForumMessage.ForumMessageReturnType.BOOLEAN ,true );
	}
	
	private synchronized void notify(String func_name , String subForum) {

		synchronized(notify_functions_lock){
			for(String[] func: notify_functions){
				if(func[0].equals(func_name)){
					Date d = new Date();
					String str_notify = String.format("%tD;%<tT;"+this._userHandler.get_username()+";"+func[1]+";in sub forum "+subForum+";On: %<tD At: %<tT" ,d) ; 
					this.reactor_data.getExecutor().execute(new Notify_object(this.reactor_data , this.out , str_notify));
					return;
				}
			}
			for(String func: notify_functions_Secret){
				if(func.equals(func_name)){ 
					this.reactor_data.getExecutor().execute(new Notify_object(this.reactor_data , this.out ,"Secret"));
					return;
				}
			}
		}
	}

	private final String[][] notify_functions = {
			{"createSubForum" , "create a new SubForum" } ,
			{"create_thread" , "create a new thread"},
			{"createReplyPost" , "create a new reply post"}  ,
			{"deletePost" , "delete a post"} ,
			{"deleteSubForum" , "delete a subForum"}	,
			} ; 
	
	private final String[] notify_functions_Secret = {"changePolicy","setMethodPolicy",	"addMemberType" ,"removeMemberType" ,"addModerator" ,"removeModerator"	} ; 


	
	private String getSubForumFromOpreation(String func_name, ForumMessage msg) {
		try{
			if(func_name.equals("createSubForum")) 		return (String)msg.get_arg(0);
			if(func_name.equals("create_thread"))		return  ((ISubForum)msg.get_arg(2)).get_theme();
			if(func_name.equals("createReplyPost")) 	return ((IPost)msg.get_arg(2)).get_subForum().get_theme();
			if(func_name.equals("deletePost")) 			return ((IPost)msg.get_arg(0)).get_subForum().get_theme();
			if(func_name.equals("deleteSubForum")) 		return ((ISubForum)msg.get_arg(0)).get_theme();				
		}
		catch (Exception e){
		}
		return "Defulte_SubForum";
	}
	/*
	public static void main(String[] args ){
		String str_notify = String.format("_%tD_%<tT_" ,new Date()) ; 
		System.out.println(str_notify);		
	}
	*/
	
}
