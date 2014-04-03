package Service_Layer;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.MemberType;
import Domain_layer.ForumComponent.IPolicy;
import Domain_layer.ForumComponent.Post;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;



public class UserHandler implements IUserHandler {
	private static int guest_serial = 0;
	private IUser _current_user;

	public UserHandler(String status ,String username, String password) {
		if(status != "SUPER_ADMINISTRATOR" ){
			throw new RuntimeException("User handler for user other than super administrator must be created with a Forum.");
		}
		_current_user = new User(username ,password  , status );
	} 
	public UserHandler( IForum _forum) {
		_current_user = new User(_forum ,generateGuestString() );
	} 
	
	private static String generateGuestString(){
		return "@guest_" + (guest_serial ++);
	}
	
	
//----------------------------------------------------------------------------------------------
	
	public boolean register(String username, String password, String repeated_password) {
		if(this._current_user.get_forum().isMember(username)
				|| (!password.equals(repeated_password))) {
			return false;
		}
		this._current_user.get_forum().addMember(username ,password);
		return true;
	}
	
	public boolean register_Email(String username, String password, String repeated_password , String email) {
		if(this._current_user.get_forum().isMember(username)
				|| (!password.equals(repeated_password))) {
			return false;
		}
		
		IForum f = this._current_user.get_forum();
		f.add_to_waitingList(username , password ,email );
		String[] to = new String[1];
		to[0] = email;		
		return f.get_mailHandler().send_massage(to , "Welcome to the Forum: \""+f.get_theme()+"\"", "To finish the registration process, please reply to this e-mail.");
	}
	
	public boolean login(String username, String password) {
		if(!_current_user.isUser("GUEST")){
			return false;
		}
		if(this._current_user.get_forum().isMember( username,  password)){
			_current_user = this._current_user.get_forum().getMember(username);
			return true;
		}
		return false;
	}
	
	public boolean logout() {	
		if(	!(_current_user.isUser("MEMBER")||
				_current_user.isUser("ADMINISTRATOR")||
				_current_user.isUser("SUPER_ADMINISTRATOR"))){
			return false;
		}
		_current_user = new User(this._current_user.get_forum() ,generateGuestString() ); 
		return true;
		
	}
	
	public IForum createForum(IPolicy p, Vector<String[]> admins , String theme) {
		if(!this._current_user.isUser("SUPER_ADMINISTRATOR"))
			throw new RuntimeException("User does not have enough privilages.");
		 Vector<IUser> administrators = new Vector<IUser>();
		 for(int i =0 ; i< admins.size(); i++){
			 administrators.add(new User(admins.get(i)[0],
					 					 admins.get(i)[1] ,
					 					 "ADMINISTRATOR"));
		 }
		 IForum F=  new Forum( p,administrators ,theme);
		 for(int i =0 ; i< administrators.size(); i++) 
			 administrators.get(i).set_forum(F);
		 return F;
	}
	public boolean createSubForum(String theme, String[] moderators_names) {
		IForum forum = this._current_user.get_forum();
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		if(this._current_user.get_forum().search_subforum_byTheme(theme) != null)
			return false;
		Vector<IUser> moderators = new Vector<IUser>();
		Vector<Date> moderator_dates = new Vector<Date>();
		for(int i=0 ; i< moderators_names.length ;  i++){
			if(!forum.isMember(moderators_names[i]))
				return false;
			moderators.add(forum.getMember(moderators_names[i]));
			moderator_dates.add(new Date());
		}
		forum.addSubForum(new SubForum(theme ,moderators  ,moderator_dates));
		return true;
	}

	public Vector<ISubForum> show_sub_forum() {
		return this._current_user.get_forum().list_sub_forum();
	}
	public boolean create_thread(String header , String body , ISubForum subForum ) {
		if(header == null && body == null)
			return false;
		IPost p = new Post(header, body, _current_user, subForum);
		if(subForum == null)
			return false;
		subForum.openThread(p);
		this._current_user.add_thread(p);
		return true;		
		
	}
	public boolean createReplyPost(String header, String body, IPost post) {
		if(header == null && body == null)
			return false;
		IPost p = new Post(header, body, _current_user, post.get_subForum());
		post.addReplyPost(p);
		this._current_user.add_replyPost(p);
		return true;	
	}
	public ISubForum search_subforum(String category_search, String search_word) {
		if(category_search.equals("Theme"))
			return this._current_user.get_forum().search_subforum_byTheme(search_word);
		else if (category_search.equals("Moderator"))
			return this._current_user.get_forum().search_subforum_byModerator(search_word);
		else
			return null;
	}

	public boolean changePolicy(IPolicy p2) {
		IForum forum = this._current_user.get_forum();
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		forum.set_policy(p2);
		return true;
	}
	public Vector<IPost> show_ReplyPost() {
		return this._current_user.get_reaplayPosts();
	}
	public Vector<IPost> show_TreadPost() {
		return this._current_user.get_threads();
	}
	public boolean deletePost(IPost post) {
		boolean has_permition= false;
		if((this._current_user.isUser("SUPER_ADMINISTRATOR") ||
				this._current_user.isUser("ADMINISTRATOR"))  )
			has_permition = true;
		if(post.get_subForum().isModerator(this._current_user.get_username()))
			has_permition = true;
		if(this._current_user.get_username().equals(post.get_author().get_username()))
			has_permition = true;
		
		if(!has_permition )
			return false;
		
		post.get_subForum().deletePost(post); // will delete if a thread
		post.delete();
		return true;
			
	}
	
	public boolean deleteSubForum(ISubForum sub_forum) {
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		IForum f  = this._current_user.get_forum();
		f.deleteSubForum(sub_forum);
		sub_forum.delete();
		return true;
	}
	public boolean addcomplaintModerator(ISubForum sub_fourm, String search_word , String theme ,String body ) {
		 IUser moderator = sub_fourm.getModerator(search_word);
		 if(moderator == null)
			 return false;
		 if(!this._current_user.isPostedInSubForum(sub_fourm))
			 return false;	
		 moderator.add_complaint(new Complaint(this._current_user ,moderator ,theme , body  ));
		 return true;

	}
	public boolean addMemberType(String type , IForum forum) {
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR")))
				return false;
		forum.addMemberType(type);
		return true;
	}
	public boolean removeMemberType(String type, IForum forum) {
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR")))
			return false;
		forum.removeMemberType(type);
		return true;
	}
	public int getNumberOfMemberTypes(IForum forum) {
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR")))
			return 0;
		return forum.getNumberOfTypes();
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
	
	
}
