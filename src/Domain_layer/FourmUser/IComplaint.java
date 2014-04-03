package Domain_layer.FourmUser;

import java.util.Date;

public interface IComplaint {
	public IUser get_submitter() ;

	public void set_submitter(IUser _submitter);

	public IUser get_complaint_on_user() ;

	public void set_complaint_on_user(IUser _complaint_on_user);

	public Date get_date() ;

	public void set_date(Date _date);

	public String get_theme() ;

	public void set_theme(String _theme);

	public String get_body() ;

	public void set_body(String _body) ;

	
}
