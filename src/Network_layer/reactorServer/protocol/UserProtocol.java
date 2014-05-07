package Network_layer.reactorServer.protocol;

import Network_layer.reactorServer.tokenizer.ForumMessage;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageMinorType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageReturnType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageType;
import Service_Layer.IUserHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        case LOGIN:  
        	replay_massegse = command("login",new Class[]{String.class ,String.class} ,ForumMessageReturnType.BOOLEAN, msg) ;
        	break;
        	
        case LOGOUT:
        	replay_massegse = command("logout",new Class[]{} ,ForumMessageReturnType.BOOLEAN, msg) ;
        	break;
        	
        default: 
        	replay_massegse = ForumMessage.create_ErrorReplay("Error in processCommendMessage: unrecognized command ");  break;
		}
		return replay_massegse;
	}
	
	//-----------------------------commend-------------------------
	
	private ForumMessage command(String func_name , Class<?>[] func_args ,ForumMessageMinorType minor ,   ForumMessage msg){
		try {
			Method m= IUserHandler.class.getMethod(func_name ,func_args);
			return ForumMessage.create_Replay( minor ,m.invoke(this._userHandler, msg.get_Object_Array()) );
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        	return  ForumMessage.create_ErrorReplay("Error in command: " + e.toString());  
		}		
	}

}
