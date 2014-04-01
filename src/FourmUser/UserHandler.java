package FourmUser;

import java.util.Date;
import java.util.Vector;

import ForumComponent.Forum;
import ForumComponent.Policy;
import ForumComponent.Post;
import ForumComponent.SubForum;


public class UserHandler {
	private static int guest_serial = 0;
	private User _current_user;

	public UserHandler(String status ,String username, String password) {
		if(status != "SUPER_ADMINISTRATOR" ){
			throw new RuntimeException("User handler for user other than super administrator must be created with a Forum.");
		}
		_current_user = new User(username ,password  , status );
	} 
	public UserHandler( Forum _forum) {
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
		
		this._current_user.get_forum().addMember_Email(username ,password ,email );
		return true;
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
	
	public Forum createForum(Policy p, Vector<String[]> admins , String theme) {
		if(!this._current_user.isUser("SUPER_ADMINISTRATOR"))
			throw new RuntimeException("User does not have enough privilages.");
		 Vector<User> administrators = new Vector<User>();
		 for(int i =0 ; i< admins.size(); i++){
			 administrators.add(new User(admins.get(i)[0],
					 					 admins.get(i)[1] ,
					 					 "ADMINISTRATOR"));
		 }
		 Forum F=  new Forum( p,administrators ,theme);
		 for(int i =0 ; i< administrators.size(); i++) 
			 administrators.get(i).set_forum(F);
		 return F;
	}
	public boolean createSubForum(String theme, String[] moderators_names) {
		Forum forum = this._current_user.get_forum();
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		if(this._current_user.get_forum().search_subforum_byTheme(theme) != null)
			return false;
		Vector<User> moderators = new Vector<User>();
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

	public Vector<SubForum> show_sub_forum() {
		return this._current_user.get_forum().list_sub_forum();
	}
	public boolean create_thread(String header , String body , SubForum subForum ) {
		if(header == null && body == null)
			return false;
		Post p = new Post(header, body, _current_user, subForum);
		subForum.openThread(p);
		this._current_user.add_thread(p);
		return true;		
		
	}
	public boolean createReplyPost(String header, String body, Post post) {
		if(header == null && body == null)
			return false;
		Post p = new Post(header, body, _current_user, post.get_subForum());
		post.addReplyPost(p);
		this._current_user.add_replyPost(p);
		return true;	
	}
	public SubForum search_subforum(String category_search, String search_word) {
		if(category_search.equals("Theme"))
			return this._current_user.get_forum().search_subforum_byTheme(search_word);
		else if (category_search.equals("Moderator"))
			return this._current_user.get_forum().search_subforum_byModerator(search_word);
		else
			return null;
	}

	public boolean changePolicy(Policy p2) {
		Forum forum = this._current_user.get_forum();
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		forum.set_policy(p2);
		return true;
	}
	public Vector<Post> show_ReplyPost() {
		return this._current_user.get_reaplayPosts();
	}
	public Vector<Post> show_TreadPost() {
		return this._current_user.get_threads();
	}
	public boolean deletePost(Post post) {
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
	public boolean deleteSubForum(SubForum sub_forum) {
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
			return false;
		Forum f  = this._current_user.get_forum();
		f.deleteSubForum(sub_forum);
		sub_forum.delete();
		return true;
	}
	public boolean addcomplaintModerator(SubForum sub_fourm, String search_word , String theme ,String body ) {
		 User moderator = sub_fourm.getModerator(search_word);
		 if(moderator == null)
			 return false;
		 if(!this._current_user.isPostedInSubForum(sub_fourm))
			 return false;	
		 moderator.add_complaint(new Complaint(this._current_user ,moderator ,theme , body  ));
		 return true;

	}

	
	
}
