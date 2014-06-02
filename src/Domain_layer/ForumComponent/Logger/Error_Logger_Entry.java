package Domain_layer.ForumComponent.Logger;

import java.util.Date;

import Network_layer.reactorServer.tokenizer.ForumMessage.ForumMessageCommendType;

public class Error_Logger_Entry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username ; 
	private String ip;
	private ForumMessageCommendType commend;
	private Date date ;
	private Exception exception;
	
	public Error_Logger_Entry(String username, String ip,ForumMessageCommendType commend, Date date ,Exception e) {
		this.username = username;
		this.ip = ip;
		this.commend = commend;
		this.date = date ; 
		this.exception = e;
	}
	@Override
	public String toString() {
		return "Error_Logger_Entry [username=" + username + ", ip=" + ip
				+ ", commend=" + commend + ", date=" + date + ", exception="
				+ exception + "]";
	}

}
