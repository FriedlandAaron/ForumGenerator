package Domain_layer.ForumComponent.Logger;

import java.util.ArrayList;
import java.util.Date;

import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;

public class Action_Logger   implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<Action_Logger_Entry> entrys;
	public Action_Logger(){
		entrys = new ArrayList<Action_Logger_Entry>();
	}
	public void addentry(String username ,String ip,ForumMessageCommendType commend , Date date) {
		entrys.add(new Action_Logger_Entry(username, ip , commend ,date ));
	}

}
