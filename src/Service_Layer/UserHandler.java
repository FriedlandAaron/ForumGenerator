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

	public UserHandler( IForum _forum) {
		_current_user = new User(_forum ,Forum.generateGuestString() );
	} 	
	
//----------------------------------------------------------------------------------------------
	
	public boolean register(String username, String password, String repeated_password) {
		return this._current_user.get_forum().register(username ,password ,repeated_password  );
	}
	
	public boolean register_Email(String username, String password, String repeated_password , String email) {
		return this._current_user.get_forum().register_Email(username ,password ,repeated_password ,email);
	}

	
	public boolean login(String username, String password) {		
		IUser user = this._current_user.get_forum().login(this._current_user , username, password);
		if(user==null)
			return false;
		this._current_user = user;
		return true;			
	}
	
	public boolean logout() {			
		IUser user = this._current_user.get_forum().logout(this._current_user);
		if(user==null)
			return false;
		this._current_user = user;
		return true;		
	}
	

	public boolean createSubForum(String theme, String[] moderators_names) {		
		return this._current_user.get_forum().createSubForum(_current_user, theme,  moderators_names);
	}

	public Vector<ISubForum> show_sub_forum() {
		return this._current_user.get_forum().show_sub_forum();
	}
	public boolean create_thread(String header , String body , ISubForum subForum ) {			
		return this._current_user.get_forum().create_thread(_current_user , header , body , subForum);		
	}
	public boolean createReplyPost(String header, String body, IPost post) {
		return this._current_user.get_forum().createReplyPost(_current_user , header , body ,  post);		
	}
	public ISubForum search_subforum(String category_search, String search_word) {
		return this._current_user.get_forum().search_subforum(category_search , search_word);	
	}
	public boolean changePolicy(IPolicy p2) {
		return this._current_user.get_forum().changePolicy(_current_user , p2);	
	}
	public Vector<IPost> show_ReplyPost() {
		return this._current_user.get_reaplayPosts();
	}
	public Vector<IPost> show_TreadPost() {
		return this._current_user.get_threads();
	}
	public boolean deletePost(IPost post) {		
		return this._current_user.get_forum().deletePost(_current_user , post);				
	}	
	public boolean deleteSubForum(ISubForum sub_forum) {
		return this._current_user.get_forum().deleteSubForum(_current_user , sub_forum);			
	}
	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word , String theme ,String body ) {
		return this._current_user.get_forum().addcomplaintModerator(_current_user , sub_fourm, search_word , theme , body );
	}
	public boolean addMemberType(String type , IForum forum) {
		return forum.addMemberType( _current_user , type);
	}
	public boolean removeMemberType(String type, IForum forum) {
		return forum.removeMemberType( _current_user , type);
	}
	public int getNumberOfMemberTypes(IForum forum) {
		return forum.getNumberOfTypes(_current_user);
	}

//--------------------------------------------------------------------
	public Vector<IPost> get_threads() {
		return this._current_user.get_threads();
	}

	public Vector<IPost> get_reaplayPosts() {
		return this._current_user.get_reaplayPosts();
	}
	
	public String get_password() {
		return this.get_password();
	}
	public String get_username() {
		return this._current_user.get_username();
	}
	public IForum get_forum() {
		return this.get_forum();
	}
	public Vector<IPost> get_replyPosts() {
		return this._current_user.get_reaplayPosts();
	}

	public Vector<IUser> get_friends() {
		return this._current_user.get_friends();
	}

	public Vector<Complaint> get_complaints() {
		return this._current_user.get_complaints();
	}

	public Date get_start_date() {
		return this.get_start_date();
	}

	public Status get_status() {
		return this.get_status();
	}

	public String get_email() {
		return this.get_email();
	}

	public MemberType get_type() {
		return this.get_type();
	}
	public IUser get_current_user() {
		return _current_user;
	}

	
	public Status getUserStatus() {
		return this._current_user.getStatus();
	}	
}
