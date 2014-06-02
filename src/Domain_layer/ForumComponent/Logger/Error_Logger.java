package Domain_layer.ForumComponent.Logger;

import java.util.ArrayList;
import java.util.Date;

import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;

public class Error_Logger  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<Error_Logger_Entry> entrys;
	public Error_Logger(){
		entrys = new ArrayList<Error_Logger_Entry>();
	}
	public void addentry(String username ,String ip,ForumMessageCommendType commend , Date date , Exception e) {
		entrys.add(new Error_Logger_Entry(username, ip , commend ,date  ,e));
	}
}
