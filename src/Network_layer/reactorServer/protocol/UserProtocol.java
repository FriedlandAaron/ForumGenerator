package Network_layer.reactorServer.protocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
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
	public void InitUserHandler(IUserHandler userHandler) {
		this._userHandler= userHandler;
	}
	
	private ForumMessage processCommendMessage(ForumMessageCommendType commend, ForumMessage msg) {
		
		ForumMessage replay_massegse = null ;
		switch (commend) {
        case LOGIN: 			replay_massegse = command("login",new Class[]{String.class ,String.class} ,ForumMessageReturnType.BOOLEAN, msg) ;													break;
        case LOGOUT:			replay_massegse = command("logout",new Class[]{} ,ForumMessageReturnType.BOOLEAN, msg) ;																			break;
        case REGISTER:			replay_massegse = command("register",new Class[]{String.class ,String.class, String.class} , ForumMessageReturnType.BOOLEAN ,msg);									break;
        case CR_SUBFORUM:		replay_massegse = command("createSubForum",new Class[]{String.class ,String[].class} , ForumMessageReturnType.BOOLEAN ,msg ); 										break;
        case SH_SUBFORUM:  		replay_massegse = command("show_sub_forum",new Class[]{} , ForumMessageReturnType.VEC_ISUBFORUM ,msg);																break;
        case CR_THREAD:    		replay_massegse = command("create_thread",new Class[]{String.class ,String.class ,ISubForum.class }  , ForumMessageReturnType.BOOLEAN ,msg  );					 	break;
        case CR_REPLYPOST: 		replay_massegse = command("createReplyPost",new Class[]{String.class ,String.class ,IPost.class }  , ForumMessageReturnType.BOOLEAN ,msg  );   						break;
        case SEARCH_SUBFORUM:	replay_massegse = command("search_subforum",new Class[]{String.class ,String.class  }  , ForumMessageReturnType.ISUBFORUM ,msg  );   								break;
        case CHAN_POLICY:		replay_massegse = command("changePolicy",new Class[]{ IPolicy.class}  , ForumMessageReturnType.BOOLEAN ,msg  );														break;       
        case SH_REPLYPOST:		replay_massegse = command("show_ReplyPost",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																	break;    
        case SH_THREADPOST:		replay_massegse = command("show_TreadPost",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																	break;    
        case DELETE_POST:		replay_massegse = command("deletePost",new Class[]{IPost.class}, ForumMessageReturnType.BOOLEAN ,msg  );															break;   
        case DELETE_SUBFORUM:	replay_massegse = command("deleteSubForum",new Class[]{ISubForum.class}, ForumMessageReturnType.BOOLEAN ,msg  );													break;   
        case ADD_COMP_MOD:		replay_massegse = command("addcomplaintModerator",new Class[]{ISubForum.class , String.class , String.class , String.class}, ForumMessageReturnType.BOOLEAN ,msg  );break;   
        case ADD_MEMBER_TYPE:  	replay_massegse = command("addMemberType",new Class[]{String.class }, ForumMessageReturnType.BOOLEAN ,msg);															break;   
        case REM_MEMBER_TYPE:	replay_massegse = command("removeMemberType",new Class[]{String.class }, ForumMessageReturnType.BOOLEAN ,msg);												     	break; 
        case GET_NUM_MEMBER_TYPE:replay_massegse= command("getNumberOfMemberTypes",new Class[]{}, ForumMessageReturnType.INTEGER ,msg  );															break;
       //getters and settrs
        case GET_THREADS:		replay_massegse = command("get_threads",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																	break;
        case GET_REPLYPOST:		replay_massegse = command("get_reaplayPosts",new Class[]{}, ForumMessageReturnType.VEC_IPOST ,msg  );																break;
        case GET_PASS:			replay_massegse = command("get_password",new Class[]{}, ForumMessageReturnType.STRING ,msg  );																		break;
        case GET_USERNAME:		replay_massegse = command("get_username",new Class[]{}, ForumMessageReturnType.STRING ,msg  );																		break;
        case GET_FORUM:			replay_massegse = command("get_forum",new Class[]{}, ForumMessageReturnType.IFORUM ,msg  );																			break;
        case GET_FRIENDS:		replay_massegse = command("get_friends",new Class[]{}, ForumMessageReturnType.VEC_IUSER ,msg  );																	break;
        case GET_COMP:			replay_massegse = command("get_complaints",new Class[]{}, ForumMessageReturnType.VEC_COMP ,msg  );																	break;
        case GET_STARTDATE:		replay_massegse = command("get_start_date",new Class[]{}, ForumMessageReturnType.STARTDATE ,msg  );																	break;
        case GET_STATUS:		replay_massegse = command("get_status",new Class[]{}, ForumMessageReturnType.STATUS ,msg  );																		break;
        case GET_EMAIL:			replay_massegse = command("get_email",new Class[]{}, ForumMessageReturnType.STRING ,msg  );																			break;
        case GET_TYPE:			replay_massegse = command("get_type",new Class[]{}, ForumMessageReturnType.MEMBERTYPE ,msg  );																		break;
        case GET_CUR_USR:		replay_massegse = command("get_current_user",new Class[]{}, ForumMessageReturnType.IUSER ,msg);																		break;
        case ADD_MODERATOR:		replay_massegse = command("addModerator",new Class[]{String.class ,String.class }, ForumMessageReturnType.BOOLEAN ,msg);											break;
        case DEL_MODERATOR:		replay_massegse = command("removeModerator",new Class[]{String.class ,String.class }, ForumMessageReturnType.BOOLEAN ,msg);											break;
        case NUM_SUBFORUM:		replay_massegse = command("numSubForum",new Class[]{}, ForumMessageReturnType.INTEGER ,msg);																		break;
        case NUM_SHARED_MOD:	replay_massegse = command("numSharedModeratorsSubForum",new Class[]{}, ForumMessageReturnType.INTEGER ,msg);														break;
        case NUM_POSTS_SUBFORUM:replay_massegse = command("numPostsSubForum",new Class[]{String.class}, ForumMessageReturnType.INTEGER ,msg);														break;
        case NUM_POSTS_USER:	replay_massegse = command("numPostsUser",new Class[]{String.class}, ForumMessageReturnType.INTEGER ,msg);															break;
        case MODERATORS_LIST:	replay_massegse = command("Moderators_list",new Class[]{}, ForumMessageReturnType.VEC_IUSER ,msg);																	break;
        case MODERATORS_REPORT:	replay_massegse = command("Moderators_Report",new Class[]{}, ForumMessageReturnType.STRING ,msg);										break;
        case SET_METHOD_POLICY: replay_massegse = command("setMethodPolicy",new Class[]{}, ForumMessageReturnType.STRING ,msg);																		break;
        default: 
        	replay_massegse = ForumMessage.create_ErrorReplay("Error in processCommendMessage: unrecognized command ");  break;
		}
		return replay_massegse;
	}
	//-----------------------------commend-------------------------
	private ForumMessage command(String func_name , Class<?>[] func_args ,ForumMessageMinorType minor ,   ForumMessage msg){
		try {
			Method m = IUserHandler.class.getMethod(func_name ,func_args);
			return ForumMessage.create_Replay( minor ,m.invoke(this._userHandler, msg.get_Object_Array()) );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        	return  ForumMessage.create_ErrorReplay("Error in command: " + e.toString());  
		}		
	}
}
