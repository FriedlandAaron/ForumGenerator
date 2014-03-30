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
			throw new RuntimeException();
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
	
	public void addNewPost(SubForum sub_forum, Post post) {
		sub_forum.openThread(post);
	}
	
	public void addReplyPost(Post reply_post, Post post) {
		post.addReplyPost(reply_post);
	}
	public Forum createForum(Policy p, Vector<String[]> admins) {
		if(!this._current_user.isUser("SUPER_ADMINISTRATOR"))
			throw new RuntimeException();
		 Vector<User> administrators = new Vector<User>();
		 for(int i =0 ; i< admins.size(); i++){
			 administrators.add(new User(admins.get(i)[0],
					 					 admins.get(i)[1] ,
					 					 "ADMINISTRATOR"));
		 }
		 Forum F=  new Forum( p,administrators);
		 for(int i =0 ; i< administrators.size(); i++) 
			 administrators.get(i).set_forum(F);
		 return F;
	}
	public boolean add_sub_forum(String theme, String[] moderators_names) {
		Forum forum = this._current_user.get_forum();
		if(!(this._current_user.isUser("SUPER_ADMINISTRATOR") || this._current_user.isUser("ADMINISTRATOR")) )
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
		return true;		
		
	}

	
	
}
