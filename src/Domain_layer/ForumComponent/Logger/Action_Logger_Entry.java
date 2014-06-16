package Domain_layer.ForumComponent.Logger;

import java.util.Date;

public class Action_Logger_Entry implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	private String username ; 
	private String commend;
	private Date date ;
	
	
	public Action_Logger_Entry(String username,String commend, Date date) {
		this.username = username;
		this.commend = commend;
		this.date = date;
	}


	@Override
	public String toString() {
		return "Action_Logger_Entry [username=" + username 
				+ ", commend=" + commend + ", date=" + date + "]";
	}

}
