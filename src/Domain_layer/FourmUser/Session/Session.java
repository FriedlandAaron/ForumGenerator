package Domain_layer.FourmUser.Session;

import java.util.Date;

public class Session implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Date start;
	private Date end;
	private Session_Logger logger;
	private boolean sassion_end ;
	public Session(){
		this.start = new Date();
		this.end = new Date();
		this.logger = new Session_Logger();
		this.sassion_end =false;
	}
	public void addAction(String commend){
		if(sassion_end)
			return;
		this.logger.addentry(commend, new Date());
		this.end = new Date();
	}
	public void end_Sassion(){
		this.sassion_end=true;
		this.end = new Date();
	}
	public Date getStart() {
		return start;
	}
	public Date getEnd() {
		return end;
	}
	public Session_Logger getLogger() {
		return logger;
	}
	public boolean isSassion_end() {
		return sassion_end;
	}
	
	public String toString() {
		return "Session [\n\tstart=" + start + ", tend=" + end
				+ ", \n\tlogger=" + logger + "]";
	}
}
