package Service_Layer;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;
import Domain_layer.FourmUser.Session.Session;



public class UserHandler implements IUserHandler {
	private IUser _current_user;
	private IForum _current_user_forum;

	public UserHandler( IForum _forum) {
		_current_user = new User(_forum ,Forum.generateGuestString() );
		_current_user_forum = _forum;
	} 	
	
//---------------------------------------------------------------------------------------------------------------
	
	public boolean register(String username, String password, String repeated_password) {
		synchronized(_current_user_forum){
			return this._current_user_forum.register(username ,password ,repeated_password  );
		}
	}
	
	public boolean register_Email(String username, String password, String repeated_password , String email) {
		synchronized(_current_user_forum){
			return this._current_user_forum.register_Email(username ,password ,repeated_password ,email);
		}
	}
	
	public boolean submit_code_registertion(String username, String code) {
		synchronized(_current_user_forum){
			return this._current_user_forum.submit_code_registertion(username ,code);
		}
	}


	
	public boolean login(String username, String password) {	
		synchronized(_current_user_forum){
			IUser user = this._current_user_forum.login(this._current_user , username, password);
			if(user==null)
				return false;
			this._current_user = user;
			return true;		
		}	
	}
	
	public boolean logout() {	
		synchronized(_current_user_forum){
			IUser user = this._current_user_forum.logout(this._current_user);
			if(user==null)
				return false;
			this._current_user = user;
			return true;	
		}
	}
	

	public boolean createSubForum(String theme, String[] moderators_names) {	
		synchronized(_current_user_forum){
			return this._current_user_forum.createSubForum(_current_user, theme,  moderators_names);
		}
	}

	public Vector<ISubForum> show_sub_forum() {
		synchronized(_current_user_forum){
			return this._current_user_forum.show_sub_forum();
		}
	}
	public boolean createReplyPost(String header, String body, IPost post) {
		synchronized(_current_user_forum){
			return this._current_user_forum.createReplyPost(_current_user , header , body ,  post);		
		}
	}
	public ISubForum search_subforum(String category_search, String search_word) {
		synchronized(_current_user_forum){
			return this._current_user_forum.search_subforum(category_search , search_word);	
		}
	}
	public boolean changePolicy(IPolicy p2) {
		synchronized(_current_user_forum){
			return this._current_user_forum.changePolicy(_current_user , p2);	
		}
	}
	public Vector<IPost> show_ReplyPost() {
		synchronized(_current_user_forum){
			return this._current_user.get_reaplayPosts();
		}
	}
	public Vector<IPost> show_TreadPost() {
		synchronized(_current_user_forum){
			return this._current_user.get_threads();
		}
	}
	public boolean deletePost(IPost post) {		
		synchronized(_current_user_forum){
			return this._current_user_forum.deletePost(_current_user , post);		
		}
	}	

	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word , String theme ,String body ) {
		synchronized(_current_user_forum){
			return this._current_user_forum.addcomplaintModerator(_current_user , sub_fourm, search_word , theme , body );
		}
	}
	

	public boolean addModerator(String sub_forum_theme,String username_to_moderate) {
		synchronized(_current_user_forum){
			return this._current_user_forum.addModerator(_current_user,  sub_forum_theme, username_to_moderate);
		}
	}

	public boolean removeModerator(String sub_forum_theme,	String username_to_moderate) {
		synchronized(_current_user_forum){
			return this._current_user_forum.removeModerator(_current_user ,sub_forum_theme ,username_to_moderate );
		}
	}

	public int numSubForum() {
		synchronized(_current_user_forum){
			return this._current_user_forum.numSubForum();
		}
	}

	public int numSharedModeratorsSubForum() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.numSharedModeratorsSubForum();
		}
	}

	public int numPostsSubForum(String sub_forum_theme) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.numPostsSubForum(sub_forum_theme);
		}
	}
	
	public int numPosts_user(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.numPostsUser(username);
		}
	}

	public String Moderators_list(String sub_forum_theme) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.Moderators_list( _current_user,sub_forum_theme);
		}
	}

	public String Moderators_Report() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.Moderators_Report();
		}
	}	
	
	
	public boolean setMethodPolicy( String Methodname,	Status s) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.setMethodPolicy(_current_user , Methodname ,s );
		}
	}
//--------------------------------------------------------------------



	

	public String get_username() {
		synchronized(_current_user_forum){	
			return this._current_user.get_username();
		}
	}

	public Status get_status() {
		synchronized(_current_user_forum){		
			return this._current_user.getStatus();
		}
	}

	public IUser get_current_user() {
		synchronized(_current_user_forum){		
			return _current_user;
		}
	}


	
	public void close_connect() {	}

	@Override
	public boolean create_thread(String header, String body, String sub_forum_theme) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.create_thread(_current_user , header ,body ,sub_forum_theme );
		}		
	}


	
	public Vector<IPost> get_theardsSubForum(String subforum) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_theardsSubForum(_current_user , subforum);
		}
	}
	

	@Override
	public String get_forum_name() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_theme();
		}
	}

	
	public Vector<IPost> get_replyPosts(IPost parent) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_replyPosts(_current_user, parent);
		}
	}

	@Override
	public String[] show_sub_forum_names() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.show_sub_forum_names();
		}
	}

	
	public boolean deleteSubForum(String sub_forum) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.deleteSubForum(_current_user ,sub_forum);
		}
	}

	public void observe(int port) {}

	
	public void log_action( String func_name, Date date) {
		synchronized(_current_user_forum){	
			this._current_user_forum.log_action(this._current_user.get_username(), func_name, date);
		}
	}

	
	public void log_error(String func_name, Date date, Exception e) {
		synchronized(_current_user_forum){	
			this._current_user_forum.log_error(this._current_user.get_username(), func_name, date , e);
		}
	}

	
	public int start_sassion() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.start_sassion(this._current_user);
		}
	}

	@Override
	public void end_sassion(int sassion_number) {
		synchronized(_current_user_forum){	
			this._current_user_forum.end_sassion(this._current_user, sassion_number);
		}		
	}

	@Override
	public void add_sassion(String func_name, int sassion_number) {
		synchronized(_current_user_forum){	
			this._current_user_forum.add_sassion(this._current_user, func_name , sassion_number);
		}			
	}

	@Override
	public Vector<Session> get_sessions() {
		synchronized(_current_user_forum){	
			return this._current_user.get_sessions();
		}
	}
	

	public Vector<Complaint> get_userComplaint(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_userComplaint(this._current_user , username);
		}
	}

	
	//REPORTS

	public int numPostsForum() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.numPostsForum(this._current_user );
		}
	}

	public Status get_status_user(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_status_user(this._current_user , username);
		}
	}

	public Date get_start_date_user(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_start_date_user(this._current_user , username);
		}
	}

	public String get_email_user(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.get_email(this._current_user , username);
		}
	}

	public int  numSassions_user(String username){
		synchronized(_current_user_forum){	
			return this._current_user_forum.numSassions_user(this._current_user , username);
		}
	}

	public String moderator_subforum_list_user(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.moderator_subforum_list_user(this._current_user , username);
		}
	}
}
