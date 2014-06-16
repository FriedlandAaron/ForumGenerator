package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

import utils.ValidMessage;
import Domain_layer.ForumComponent.Logger.*;
import Domain_layer.FourmUser.Complaint;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;
import Network_layer.FourmMail.MailHandler;

@SuppressWarnings("serial")
public class Forum implements IForum  , java.io.Serializable {

	private String _theme;
	private Vector<IUser> _members;
	private Vector<IUser> _administrators;
	private Vector<ISubForum> _subForums;
	private Action_Logger _action_logger;
	private Error_Logger _error_logger;
	private IPolicy _policy ; 
	private Vector<MemberType> _memberTypes;
	private Vector<String[]> _waitingList;
	
	private final String _mail ; 
	private final String _mail_pass ; 


	
	private static int guest_serial = 0;

	public Forum(IPolicy policy , Vector<IUser> administrators  , String theme){
		
		this._theme = theme;
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
		this._mail = "ForumGeneratorWSEP142@gmail.com" ; 
		this._mail_pass =  "MiraBalaban";
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
		 return F;
	}

//--------------------------------------------------------------------------------------------------------
	public void addMember(String username, String password) {
		 _members.add(new User(this,  username,  password ,Status.MEMBER));
	}
	
	public String get_theme() {
		return _theme;
	}

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


	public void add_to_waitingList(String username, String password,String email, String code) {
		this._waitingList.add(new String[]{username ,password,email  , code});
	}

	
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


	public boolean register_Email(String username, String password,	String repeated_password  ,  String email) {
		if(this.isMember(username)|| (!password.equals(repeated_password)) || this.passExists(password)) {
			return false;
		}
		String code = this.generateCodeString();
		this.add_to_waitingList(username , password ,email , code );
		String[] to = new String[1];
		to[0] = email;		
		return MailHandler.send_massage(this._mail , this._mail_pass , to , "Welcome to the Forum: \""+this._theme+"\"", "To finish the registration process, please submit the code: \n" +code );
	}

	private String generateCodeString() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(""); 
		for(int i = 0  ; i< 10  ; i ++ )
			sb.append(r.nextInt(10));
		return sb.toString();			
	}

	
	public boolean submit_code_registertion(String username, String code) {
		
		String[] user ;
		for(int i=0 ; i< this._waitingList.size() ; i++){
			user = this._waitingList.get(i);
			if(user[0].equals(username) && user[3].equals(code)){				
				this.addMemberEmail(user[0], user[1] , user[2]);
				this._waitingList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	private void addMemberEmail(String username, String password, String email) {
		 _members.add(new User(this,  username,  password ,Status.MEMBER ,email));
	}

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
		
		if(this.search_subforum_byTheme(theme) != null)
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
		if(header == null || body == null)return false;
		if(header.equals("") || body.equals(""))return false;
		if(!(ValidMessage.isValidMessage(header) && 
			ValidMessage.isValidMessage(body)))
			return false;	
		
		if(subForum_to == null || current_user == null)
			return false;
		ISubForum subForum =this.search_subforum_byTheme(subForum_to.get_theme());		
		IPost p =  Post.create_post(header, body, current_user, subForum);
		if(subForum == null)
			return false;
		subForum.openThread(p);
		current_user.add_thread(p);
		return true;
	}

	public boolean createReplyPost(IUser current_user, String header,String body, IPost post_to) {		
		if(header == null || body == null)return false;
		if(header.equals("") || body.equals(""))return false;
		if(!(ValidMessage.isValidMessage(header) && 
				ValidMessage.isValidMessage(body)))
				return false;	
		
		IPost post = this.search_Post(post_to.get_id());
		IPost p =  Post.create_post(header, body, current_user, post.get_subForum() ,post);
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

	public boolean deletePost(IUser current_user, IPost post_del) {
		IPost post = this.search_Post(post_del.get_id());
		boolean has_permition= false;
		if(this._policy.deletePost( current_user ))	
			has_permition = true;
		if(post.get_subForum().isModerator(current_user.get_username()))
			has_permition = true;
		if(current_user.get_username().equals(post.get_author().get_username()))
			has_permition = true;
		
		if(!has_permition )
			return false;
		
		post.get_subForum().deletePost(post); // will delete if a thread
		post.delete(); //delete replaypost
		IPost parent = post.get_parent_post();
		if(parent!=null){
			parent.delete_replay(post);
		}
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


	public boolean create_thread(IUser user , String header, String body, String sub_forum_theme) {
		ISubForum sub_forum  = this.search_subforum_byTheme(sub_forum_theme);
		if(sub_forum == null)
			return false;
		
		return this.create_thread(user , header ,body , sub_forum );
	}

	
	public void log_action(String username,  String commend, Date date) {this._action_logger.addentry(username, commend, date);}
	public void log_error(String username, String commend, Date date, Exception e) {this._error_logger.addentry(username,  commend, date, e);}


	@Override
	public Vector<IPost> get_theardsSubForum(IUser _current_user	,String subforum) {
		ISubForum subForum = this.search_subforum_byTheme(subforum);
		if(subForum == null )
			return null;
		return subForum.get_threads();
	}


	@Override
	public Vector<IPost> get_replyPosts(IUser _current_user, IPost parent) {
		IPost p =Post.getIPost(parent.get_id());
		return p.get_replies();
	}

	@Override
	public String[] show_sub_forum_names() {
		String[] show_sub_forum_names = new String[this._subForums.size()];
		for(int i = 0 ; i<show_sub_forum_names.length ; i++)
			show_sub_forum_names[i]= this._subForums.get(i).get_theme();
		return show_sub_forum_names;
	}


	public boolean deleteSubForum(IUser current_user, String sub_forum) {		
		if(!this._policy.deleteSubForum( current_user ))	
			return false;
		ISubForum sub = this.search_subforum_byTheme(sub_forum);
		if(sub == null)
			return false;
		this.deleteSubForum(sub);
		sub.delete();
		return true;
	}

	@Override
	public int start_sassion(IUser _current_user) {
		return _current_user.start_sassion();
	}

	@Override
	public void end_sassion(IUser _current_user, int sassion_number) {
		_current_user.end_sassion(sassion_number);
	}

	@Override
	public void add_sassion(IUser _current_user, String func_name,int sassion_number) {
		_current_user.add_sassion(func_name ,sassion_number );
	}

	@Override
	public Vector<Complaint> get_userComplaint(IUser _current_user,	String username) {
		if(!this._policy.get_userComplaint( _current_user  ))	
			return null;
		IUser user = this.getMember(username);
		if(user == null)
			return null;
				
		return user.get_complaints();
	}

	
	
	//REPORTS
	public int numPostsForum(IUser current_user) {
		if(!this._policy.numPostsForum( current_user  ))	
			return -1;
		int size = 0;
		for(int i=0 ; i< this._subForums.size(); i++ )
			size += this._subForums.get(i).numPostsSubForum();
		return size;
	}

	public String Moderators_list(IUser _current_user, String sub_forum_theme) {
		if(!this._policy.get_userComplaint( _current_user  ))	
			return null;
		
		ISubForum sub = this.search_subforum_byTheme(sub_forum_theme);
		if(sub == null)
			return null;
		Vector<IUser> moderators = sub.get_moderators();
		String list =""; 
		for(int i= 0 ; i<moderators.size() ; i++){
			if(i==0)
				list = moderators.get(i).get_username();
			else
				list += ";"+moderators.get(i).get_username();
		}
		return list ; 
	}

	
	
	//------------------------------------need to implemnts-----------------------------------------------------//
	@Override
	public Status get_status_user(IUser _current_user, String username) {
		if(!_current_user.get_username().equals(username)
				&&!this._policy.get_status_user( _current_user  ))	
			return null;
		IUser user = this.getMember(username);
		if(user == null)
			return null;
		return user.getStatus();
	}

	@Override
	public Date get_start_date_user(IUser _current_user, String username) {
		if(!_current_user.get_username().equals(username) &&
				!this._policy.get_start_date_user( _current_user  ))	
			return null;
		IUser user = this.getMember(username);
		if(user == null)
			return null;
		return user.get_start_date();
	}

	@Override
	public String get_email(IUser _current_user, String username) {
		if(!_current_user.get_username().equals(username) &&
				!this._policy.get_email( _current_user  ))	
			return null;
		IUser user = this.getMember(username);
		if(user == null)
			return null;
		return user.get_email();
	}

	@Override
	public int numSassions_user(IUser _current_user, String username) {
		if(!_current_user.get_username().equals(username) &&
				!this._policy.numSassions_user( _current_user  ))	
			return -1;
		IUser user = this.getMember(username);
		if(user == null)
			return -1;
		return user.get_numSessions();
	}

	@Override
	public String moderator_subforum_list_user(IUser _current_user,	String username) {
		if(!_current_user.get_username().equals(username) &&
				!this._policy.moderator_subforum_list_user( _current_user  ))	
			return null;
		Vector<IUser> moderators ;
		String list =""; 
		for(int i= 0 ; i<this._subForums.size() ; i++){			
			moderators= this._subForums.get(i).get_moderators();
			for(IUser user :moderators ){
				if(user.get_username().equals(username)){
					if(list.equals("")){
						list = this._subForums.get(i).get_theme();
						break;
					}
					else{
						list += ";"+moderators.get(i).get_username();
						break;
					}
				}												
			}
		}
		return list;
	}




	

	
}
