package Domain_layer.ForumComponent.Logger;

import java.util.Date;

public class Error_Logger_Entry implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username ; 
	private String commend;
	private Date date ;
	private Exception exception;
	
	public Error_Logger_Entry(String username,String commend, Date date ,Exception e) {
		this.username = username;
		this.commend = commend;
		this.date = date ; 
		this.exception = e;
	}
	@Override
	public String toString() {
		return "Error_Logger_Entry [username=" + username 
				+ ", commend=" + commend + ", date=" + date + ", exception="
				+ exception + "]";
	}

}
