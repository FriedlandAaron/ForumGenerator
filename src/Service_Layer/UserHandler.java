package Service_Layer;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.MemberType;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;



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
	
//	public boolean register_Email(String username, String password, String repeated_password , String email) {
//		return this._current_user_forum.register_Email(username ,password ,repeated_password ,email);
//	}

	
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
	public boolean create_thread(String header , String body , ISubForum subForum ) {	
		synchronized(_current_user_forum){
			return this._current_user_forum.create_thread(_current_user , header , body , subForum);	
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
	public boolean deleteSubForum(ISubForum sub_forum) {
		synchronized(_current_user_forum){
			return this._current_user_forum.deleteSubForum(_current_user , sub_forum);	
		}
	}
	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word , String theme ,String body ) {
		synchronized(_current_user_forum){
			return this._current_user_forum.addcomplaintModerator(_current_user , sub_fourm, search_word , theme , body );
		}
	}
	public boolean addMemberType(String type ) {
		synchronized(_current_user_forum){
			return  this._current_user_forum.addMemberType( _current_user , type);
		}
	}
	public boolean removeMemberType(String type) {
		synchronized(_current_user_forum){
			return  this._current_user_forum.removeMemberType( _current_user , type);
		}
	}
	public int getNumberOfMemberTypes() {
		synchronized(_current_user_forum){
			return  this._current_user_forum.getNumberOfTypes(_current_user);
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
	
	public int numPostsUser(String username) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.numPostsUser(username);
		}
	}

	public Vector<IUser> Moderators_list() {
		synchronized(_current_user_forum){	
			return this._current_user_forum.Moderators_list();
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
	public Vector<IPost> get_threads() {
		synchronized(_current_user_forum){		
			return this._current_user.get_threads();
		}
	}

	public Vector<IPost> get_reaplayPosts() {
		synchronized(_current_user_forum){	
			return this._current_user.get_reaplayPosts();
		}
	}
	
	public String get_password() {
		synchronized(_current_user_forum){	
			return this._current_user.get_password();
		}
	}
	public String get_username() {
		synchronized(_current_user_forum){	
			return this._current_user.get_username();
		}
	}
	public IForum get_forum() {
		synchronized(_current_user_forum){	
			return this._current_user_forum;
		}
	}
	public Vector<IPost> get_replyPosts() {
		synchronized(_current_user_forum){	
			return this._current_user.get_reaplayPosts();
		}
	}

	public Vector<IUser> get_friends() {
		synchronized(_current_user_forum){	
			return this._current_user.get_friends();
		}
	}

	public Vector<Complaint> get_complaints() {
		synchronized(_current_user_forum){	
			return this._current_user.get_complaints();
		}
	}

	public Date get_start_date() {
		synchronized(_current_user_forum){	
			return  this._current_user.get_start_date();
		}
	}

	public Status get_status() {
		synchronized(_current_user_forum){		
			return this._current_user.getStatus();
		}
	}

	public String get_email() {
		synchronized(_current_user_forum){	
			return this._current_user.get_email();
		}
	}

	public MemberType get_type() {
		synchronized(_current_user_forum){		
			return this._current_user.get_type();
		}
	}
	public IUser get_current_user() {
		synchronized(_current_user_forum){		
			return _current_user;
		}
	}

	public Status getUserStatus() {
		synchronized(_current_user_forum){	
			return this._current_user.getStatus();
		}
	}

	
	public void close_connect() {	}

	@Override
	public boolean create_thread(String header, String body, String string) {
		synchronized(_current_user_forum){	
			return this._current_user_forum.create_thread(_current_user , header ,body ,string );
		}		
	}



	

}
