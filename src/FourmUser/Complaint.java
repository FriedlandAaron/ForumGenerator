package FourmUser;

import java.util.Date;

public class Complaint {
	private User _submitter;
	private User _complaint_on_user;
	private Date _date;
	private String _theme;
	private String _body;
	
	public Complaint(User submitter ,User complaint_on_user  ,String theme ,String body){
		this._submitter = 	submitter;
		this._complaint_on_user = complaint_on_user;
		this._date = new Date();
		this._theme = theme;
		this._body = body;
	}
}
