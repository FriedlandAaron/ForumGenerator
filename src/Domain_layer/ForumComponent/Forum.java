package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.Logger.*;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;

@SuppressWarnings("serial")
public class Forum implements IForum  , java.io.Serializable {

	private String _theme;
	private Vector<IUser> _members;
	private Vector<IUser> _banned_members;
	private Vector<IUser> _administrators;
	private Vector<ISubForum> _subForums;
	private Action_Logger _action_logger;
	private Error_Logger _error_logger;
	private IPolicy _policy ; 
	//private MailHandler _mailHandler ; 
	private Vector<MemberType> _memberTypes;
	private Vector<String[]> _waitingList;
	
	private static int guest_serial = 0;

	public Forum(IPolicy policy , Vector<IUser> administrators  , String theme){
		
		this._theme = theme;
		this._banned_members= new Vector<IUser>();
		this._administrators = administrators ;
		this._subForums = new Vector<ISubForum>();
		this._action_logger = new Action_Logger();
		this._error_logger = new Error_Logger();
		if(policy!=null)
			this._policy = policy;
		else
			this._policy = new Policy();
		this._members = new Vector<IUser>();
		for(int i=0 ; i < this._administrators.size() ; i++){
			this._members.add(this._administrators.get(i));
		}
		//_mailHandler = new MailHandler("ForumGeneratorWSEP142@gmail.com", "MiraBalaban");
		this._memberTypes = new Vector<MemberType>();
		this._memberTypes.add(new MemberType("Default"));
		this._waitingList = new Vector<String[]>();
	}

	public static String generateGuestString(){
		return "@guest_" + (guest_serial ++);
	}
	
	public static IForum createForum(String username_super, String password_super , IPolicy p, Vector<String[]> admins , String theme) {
		 Vector<IUser> administrators = new Vector<IUser>();
		 administrators.add(new User(username_super ,password_super ,Status.SUPER_ADMINISTRATOR ));
		 for(int i =0 ; i< admins.size(); i++){
			 administrators.add(new User(admins.get(i)[0],
					 					 admins.get(i)[1] ,
					 					Status.ADMINISTRATOR));
		 }
		 IForum F=  new Forum( p,administrators ,theme);
		 for(int i =0 ; i< administrators.size(); i++) 
			 administrators.get(i).set_forum(F);
		 return F;
	}

//----------------------------------------------------------------------------------
	public void addMember(String username, String password) {
		 _members.add(new User(this,  username,  password ,Status.MEMBER));
	}
	
	public String get_theme() {
		return _theme;
	}


//	public MailHandler get_mailHandler() {
//		return _mailHandler;
//	}


	public	boolean isMember(String username) {
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return true;
		}
		return false;
	}

	public	boolean isMember(String username ,String password) {
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username) && _members.get(i).get_password().equals(password))
				return true;
		}
		return false;
	}
	
	public IUser getMember(String username) {
		if(!isMember(username)){
			throw new RuntimeException() ; 
 		}
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return _members.get(i);
		}
		return null;
	}


	public void addSubForum(ISubForum subForum) {
		this._subForums.add(subForum);
	}


	public Vector<ISubForum> show_sub_forum() {	
		return this._subForums;
	}


	public ISubForum search_subforum_byTheme(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).get_theme().equals(search_word))
				return this._subForums.get(i);
		return null;
	}


	public ISubForum search_subforum_byModerator(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).isModerator(search_word))
				return this._subForums.get(i);
		return null;
	}
	
	
	public void set_policy(IPolicy _policy) {
		this._policy = _policy;
	}


	public void deleteSubForum(ISubForum sub_forum) {
		for(int i=0 ; i<this._subForums.size() ; i++)
			if( this._subForums.get(i).get_theme().equals(sub_forum.get_theme()))
				this._subForums.remove(i);		
	}
	
//	public void init_Forum(){
//		this._mailHandler.init_Mail_Handler();
//	}
//	public void close_Forum(){
//		this._mailHandler.close_Mail_Handler();
//		
//	}
	
	public boolean addMemberType(IUser current_user, String name){
		if(!this._policy.addMemberType( current_user ))	
			return false;
		_memberTypes.add(new MemberType(name));
		return true ; 
	}
	public MemberType getMemberTypeByName(String name) {
		for(int i = 0; i < this._memberTypes.size(); i++) {
			if(this._memberTypes.get(i).get_typeName().equals(name))
				return this._memberTypes.get(i);
		}
		return null;
	}
	public boolean removeMemberType(IUser current_user , String name){
		if(!this._policy.removeMemberType( current_user ))	
			return false;
		MemberType m = null;
		for(int i = 0; i < this._memberTypes.size(); i++) {
			if(this._memberTypes.get(i).get_typeName().equals(name) && i != 0){
				m = this._memberTypes.get(i);
				this._memberTypes.remove(i);
				break;
			}
		}
		if(m!= null){
			for(int i=0 ; i< this._members.size() ; i++)
				this._members.get(i).set_type(this._memberTypes.get(0));
			for(int i=0 ; i< this._banned_members.size() ; i++)
				this._banned_members.get(i).set_type(this._memberTypes.get(0));
			for(int i=0 ; i< this._administrators.size() ; i++)
				this._administrators.get(i).set_type(this._memberTypes.get(0));
			return true;
		}		
		return false;
	}
	
	public int getNumberOfTypes(IUser current_user){
		if(!this._policy.getNumberOfTypes( current_user ))	
			return 0;
		return this._memberTypes.size();
	}


	public void add_to_waitingList(String username, String password,String email) {
		this._waitingList.add(new String[]{username ,password,email });


	}


//	public void checkValidationEmails() {		
//		Vector<String> msg_senders =this._mailHandler.getEmailsSenders();
//		for(String sender_email:msg_senders){
////			System.out.println(sender_email);
//			for(String[] user : this._waitingList){
//				if(sender_email.equals(user[2]));
//					this.addMemberEmail(user[0], user[1] , user[2]);
//			}
//		}
//	}


//	private void addMemberEmail(String username, String password, String email) {
//		 _members.add(new User(this,  username,  password , "MEMBER" ,email));		
//	}


	
	
//--------------------------------------------------------------------------------
	
	public Vector<IUser> get_members() {return _members;}
	public Vector<IUser> get_banned_members() {	return _banned_members;}
	public Vector<IUser> get_administrators() {	return _administrators;	}
	public Vector<ISubForum> get_subForums() {return _subForums;}
	public Action_Logger get_action_logger() {	return _action_logger;}
	public Error_Logger get_error_logger() {	return _error_logger;}
	public IPolicy get_policy() {return _policy;}
	public Vector<MemberType> get_memberTypes() {return _memberTypes;}
	public Vector<String[]> get_waitingList() {	return _waitingList;}

//---------------------------------------------------------------------------------------------------
	public boolean register(String username, String password,String repeated_password) {	

		if(this.isMember(username)|| (!password.equals(repeated_password)) || this.passExists(password)) {
			return false;
		}
		
		this.addMember(username ,password);
		return true;
	}
	private boolean passExists(String password) {
		for(IUser user: this._members){
			if(user.get_password().equals(password))
				return true;
		}
		return false;
	}


//	@Override
//	public boolean register_Email(String username, String password,
//			String repeated_password  ,  String email) {
//		if(this.isMember(username)
//				|| (!password.equals(repeated_password))) {
//			return false;
//		}
//		
//		this.add_to_waitingList(username , password ,email );
//		String[] to = new String[1];
//		to[0] = email;		
//		return this.get_mailHandler().send_massage(to , "Welcome to the Forum: \""+this.get_theme()+"\"", "To finish the registration process, please reply to this e-mail.");
//	}




	public IUser login(IUser current_user , String username, String password) {
		if(!this._policy.login( current_user ))	return null;		
		
		if(this.isMember( username,  password))
			return this.getMember(username);
		return null;
	}


	public IUser logout(IUser current_user) {
		if(	!this._policy.logout(current_user))	return null;		 
		return new User(this ,generateGuestString() ); 
	}

	public boolean createSubForum( IUser current_user , String theme, String[] moderators_names) {		
		if(!this._policy.createSubForum( current_user ))	return false;	
		
		if(current_user.get_forum().search_subforum_byTheme(theme) != null)
			return false;
		Vector<IUser> moderators = new Vector<IUser>();
		Vector<Date> moderator_dates = new Vector<Date>();
		for(int i=0 ; i< moderators_names.length ;  i++){
			if(!this.isMember(moderators_names[i]))
				return false;
			moderators.add(this.getMember(moderators_names[i]));
			moderator_dates.add(new Date());
		}
		this.addSubForum(new SubForum(theme ,moderators  ,moderator_dates));
		return true;
		
	}

	public boolean create_thread(IUser current_user , String header, String body, ISubForum subForum_to) {
		if(subForum_to == null || current_user == null)
			return false;
		ISubForum subForum =this.search_subforum_byTheme(subForum_to.get_theme());		
		if(header == null || body == null)
			return false;
		IPost p =  Post.create_post(header, body, current_user, subForum);
		if(subForum == null)
			return false;
		subForum.openThread(p);
		current_user.add_thread(p);
		return true;
	}

	public boolean createReplyPost(IUser current_user, String header,String body, IPost post_to) {		
		IPost post = this.search_Post(post_to.get_id());
		if(header == null || body == null)
			return false;
		IPost p =  Post.create_post(header, body, current_user, post.get_subForum());
		post.addReplyPost(p);
		current_user.add_replyPost(p);
		return true;
	}

	private IPost search_Post(long id) {
		return Post.getIPost(id);
	}

	public ISubForum search_subforum(String category_search, String search_word) {
		if(category_search.equals("Theme"))
			return this.search_subforum_byTheme(search_word);
		else if (category_search.equals("Moderator"))
			return this.search_subforum_byModerator(search_word);
		else
			return null;
	}

	public boolean changePolicy(IUser current_user , IPolicy p2) {
		if(!this._policy.changePolicy( current_user ))	return false;

		this.set_policy(p2);
		return true;
	}

	public boolean deletePost(IUser current_user, IPost post) {
		boolean has_permition= false;
		if(!this._policy.deletePost( current_user ))	
			has_permition = true;
		if(post.get_subForum().isModerator(current_user.get_username()))
			has_permition = true;
		if(current_user.get_username().equals(post.get_author().get_username()))
			has_permition = true;
		
		if(!has_permition )
			return false;
		
		post.get_subForum().deletePost(post); // will delete if a thread
		post.delete();
		return true;
	}

	public boolean deleteSubForum(IUser current_user, ISubForum sub_forum) {		
		if(!this._policy.deleteSubForum( current_user ))	
			return false;

		this.deleteSubForum(sub_forum);
		sub_forum.delete();
		return true;
	}

	public boolean addcomplaintModerator(IUser current_user,ISubForum sub_fourm_to, String search_word, String theme, String body) {
		 ISubForum sub_fourm = this.search_subforum_byTheme(sub_fourm_to.get_theme());
		 if(sub_fourm == null)	return false;

		 IUser moderator = sub_fourm.getModerator(search_word);
		 if(moderator == null)	return false;
		 if(!current_user.isPostedInSubForum(sub_fourm)) return false;	
		 moderator.add_complaint(new Complaint(current_user ,moderator ,theme , body));
		 return true;
	}

	public int numSubForum() {
		return this._subForums.size();
	}

	public int numSharedModeratorsSubForum() {
		int size = 0 ; 
		Vector<IUser> moderators_list = new Vector<IUser>() , moderators_sub_list;
		IUser user;
		for(int i=0 ; i<this._subForums.size() ; i++){
			moderators_sub_list = this._subForums.get(i).get_moderators();
			for(int j=0 ; j<moderators_sub_list.size();j++){
				user = moderators_sub_list.get(j );
				if(!moderators_list.contains(user)) 
					moderators_list.add(user);
				else
					size++;
			}				
		}
		return size; 
	}

	public boolean addModerator( IUser current_user , String sub_forum_theme,	String username_to_moderate) {
		if(!this._policy.addModerator( current_user ))	
			return false;
		ISubForum sub_forum  = this.search_subforum_byTheme(sub_forum_theme);
		IUser user  = this.getMember(username_to_moderate);
		if(sub_forum == null || user == null)
			return false;		
		sub_forum.addModerator(user);
		return true;	
		
	}

	public boolean removeModerator(IUser current_user , String sub_forum_theme,	String username_to_moderate) {
		if(!this._policy.removeModerator( current_user ))	
			return false;
		ISubForum sub_forum  = this.search_subforum_byTheme(sub_forum_theme);
		IUser user  = this.getMember(username_to_moderate);
		if(sub_forum == null || user == null)
			return false;		
		sub_forum.removeModerator(user);
		return true;	
	}

	public int numPostsSubForum(String sub_forum_theme) {
		ISubForum sub_forum  = this.search_subforum_byTheme(sub_forum_theme);
		if(sub_forum == null)
			return -1;
		else
			return sub_forum.numPostsSubForum();
	}

	public int numPostsUser(String username) {
		IUser user  = this.getMember(username);
		if(user == null)
			return -1;
		else
			return user.numPostsUser();
	}

	public Vector<IUser> Moderators_list() {
		Vector<IUser> moderators_list = new Vector<IUser>() , moderators_sub_list;
		IUser user;
		for(int i=0 ; i<this._subForums.size() ; i++){
			moderators_sub_list = this._subForums.get(i).get_moderators();
			for(int j=0 ; j<moderators_sub_list.size();j++){
				user = moderators_sub_list.get(j);
				if(!moderators_list.contains(user)) 
					moderators_list.add(user);
			}				
		}
		return moderators_list; 
	}

	public String Moderators_Report() {
		StringBuilder sb = new StringBuilder("Moderators_Report: \n");
		
		Vector<IUser>  moderators_sub_list;
		IUser user;
		for(int i=0 ; i<this._subForums.size() ; i++){
			sb.append("\t").append("SubForum: ").append(this._subForums.get(i).get_theme()).append("\n");
			moderators_sub_list = this._subForums.get(i).get_moderators();
			for(int j=0 ; j<moderators_sub_list.size();j++){
				user = moderators_sub_list.get(j);
				sb.append("\t\tModerator: ").append(user.get_username()).append("\n\t\t\tDate: ");
				sb.append(this._subForums.get(i).get_moderator_dates().get(j)).append("\n");
			}				
		}
		return sb.toString(); 
	}

	public boolean setMethodPolicy(IUser current_user, String methodname, Status s) {
		return this._policy.setMethodPolicy(current_user, methodname, s);
	}

	public int get_id() {

		return 1;
	}

	public boolean create_thread(IUser user , String header, String body, String sub_forum_theme) {
		ISubForum sub_forum  = this.search_subforum_byTheme(sub_forum_theme);
		if(sub_forum == null)
			return false;
		
		return this.create_thread(user , header ,body , sub_forum );
	}


	









	

	
}
