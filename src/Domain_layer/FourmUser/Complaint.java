package Domain_layer.FourmUser;

import java.util.Date;

@SuppressWarnings("serial")
public class Complaint implements   IComplaint  , java.io.Serializable {
	private IUser _submitter;
	private IUser _complaint_on_user;
	private Date _date;
	private String _theme;
	private String _body;
	
	public Complaint(IUser submitter ,IUser complaint_on_user  ,String theme ,String body){
		this._submitter = 	submitter;
		this._complaint_on_user = complaint_on_user;
		this._date = new Date();
		this._theme = theme;
		this._body = body;
	}

	public IUser get_submitter() {
		return _submitter;
	}

	public void set_submitter(IUser _submitter) {
		this._submitter = _submitter;
	}

	public IUser get_complaint_on_user() {
		return _complaint_on_user;
	}

	public void set_complaint_on_user(IUser _complaint_on_user) {
		this._complaint_on_user = _complaint_on_user;
	}

	public Date get_date() {
		return _date;
	}

	public void set_date(Date _date) {
		this._date = _date;
	}

	public String get_theme() {
		return _theme;
	}

	public void set_theme(String _theme) {
		this._theme = _theme;
	}

	public String get_body() {
		return _body;
	}

	public void set_body(String _body) {
		this._body = _body;
	}
	
	
}
