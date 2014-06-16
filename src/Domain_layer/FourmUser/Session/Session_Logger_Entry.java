package Domain_layer.FourmUser.Session;

import java.util.Date;

public class Session_Logger_Entry implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	private String commend;
	private Date date ;
	
	public Session_Logger_Entry(String commend, Date date) {
		this.commend = commend;
		this.date = date;
	}


	@Override
	public String toString() {
		return "\t\t\tSession_Logger_Entry [commend=" + commend + ", date=" + date + "]";
	}

	public String getCommend() {
		return commend;
	}

	public Date getDate() {
		return date;
	}
}
