package Network_layer.reactorClient;

import java.util.Vector;

import Network_layer.reactorServer.tokenizer.ForumMessage;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageReturnType;
import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageType;


public class ClientHandler {
	private ConnectionHandler _connectionHandler; 
	public ClientHandler(ConnectionHandler connectionHandler) {
		this._connectionHandler = connectionHandler;
	}

	public boolean login(String username, String password) {
		Vector<Object> args1 = new  Vector<Object>();
        args1.add(username);
        args1.add(password);
        ForumMessage forumMessage = new ForumMessage(ForumMessageType.COMMAND,ForumMessageCommendType.LOGIN , args1);
        _connectionHandler.sendForumMessage(forumMessage);
        
        ForumMessage respoenseMessage = _connectionHandler.getForumMessage();
        if(respoenseMessage ==null || !respoenseMessage.get_type().equals(ForumMessageType.REPLY))
        	return false;
        

        if(respoenseMessage.get_minor_type().equals(ForumMessageReturnType.ERROR)){
        	System.out.println(respoenseMessage.get_arg(0));
        	return false;
        }
        if(!respoenseMessage.get_minor_type().equals(ForumMessageReturnType.BOOLEAN))
        	return false;
        return  (boolean) respoenseMessage.get_arg(0);    	
		
	}

	public boolean logout(){
		Vector<Object> args1 = new  Vector<Object>();
        ForumMessage forumMessage = new ForumMessage(ForumMessageType.COMMAND,ForumMessageCommendType.LOGOUT , args1);
        _connectionHandler.sendForumMessage(forumMessage);
        
        ForumMessage respoenseMessage = _connectionHandler.getForumMessage();
        if(respoenseMessage ==null || !respoenseMessage.get_type().equals(ForumMessageType.REPLY))
        	return false;        

        if(respoenseMessage.get_minor_type().equals(ForumMessageReturnType.ERROR)){
        	System.out.println(respoenseMessage.get_arg(0));
        	return false;
        }
        if(!respoenseMessage.get_minor_type().equals(ForumMessageReturnType.BOOLEAN))
        	return false;
        return  (boolean) respoenseMessage.get_arg(0);  
		
	}


}
