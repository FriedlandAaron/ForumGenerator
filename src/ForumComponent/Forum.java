package ForumComponent;

import java.util.Vector;

import javax.mail.Address;

import FourmMail.*;
import FourmUser.*;

public class Forum  {

	private String _theme;

	private Vector<User> _members;
	private Vector<User> _banned_members;
	private Vector<User> _administrators;
	private Vector<SubForum> _subForums;
	private Logger _action_logger;
	private Logger _error_logger;
	private Policy _policy ; 
	private MailHandler _mailHandler ; 
	private Vector<MemberType> _memberTypes;
	private Vector<String[]> _waitingList;
	
	public Forum(Policy policy , Vector<User> administrators  , String theme){
		this._theme = theme;
		this._banned_members= new Vector<User>();
		this._administrators = administrators ;
		this._subForums = new Vector<SubForum>();
		this._action_logger = new Logger();
		this._error_logger = new Logger();
		this._policy = policy;
		this._members = new Vector<User>();
		for(int i=0 ; i < this._administrators.size() ; i++){
			this._members.add(this._administrators.get(i));
		}
		this._mailHandler = new MailHandler("ForumGeneratorWSEP142@gmail.com", "MiraBalaban");
		this._memberTypes = new Vector<MemberType>();
		this._memberTypes.add(new MemberType("Default"));
		this._waitingList = new Vector<String[]>();
	}


//----------------------------------------------------------------------------------
	public void addMember(String username, String password) {
		 _members.add(new User(this,  username,  password , "MEMBER"));
	}
	
	public String get_theme() {
		return _theme;
	}


	public MailHandler get_mailHandler() {
		return _mailHandler;
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
	
	public User getMember(String username) {
		if(!isMember(username)){
			throw new RuntimeException() ; 
 		}
		for(int i = 0 ; i< _members.size() ; i++){
			if(_members.get(i).get_username().equals(username))
				return _members.get(i);
		}
		return null;
	}


	public void addSubForum(SubForum subForum) {
		this._subForums.add(subForum);
	}


	public Vector<SubForum> list_sub_forum() {	
		return this._subForums;
	}


	public SubForum search_subforum_byTheme(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).get_theme().equals(search_word))
				return this._subForums.get(i);
		return null;
	}


	public SubForum search_subforum_byModerator(String search_word) {
		for(int i=0 ; i < this._subForums.size(); i++)
			if(this._subForums.get(i).isModerator(search_word))
				return this._subForums.get(i);
		return null;
	}
	
	
	public void set_policy(Policy _policy) {
		this._policy = _policy;
	}


	public void deleteSubForum(SubForum sub_forum) {
		for(int i=0 ; i<this._subForums.size() ; i++)
			if( this._subForums.get(i)==sub_forum)
				this._subForums.remove(i);
	}
	
	public void init_Forum(){
		this._mailHandler.init_Mail_Handler();
	}
	public void close_Forum(){
		this._mailHandler.close_Mail_Handler();
		
	}
	
	public void addMemberType(String name) {
		_memberTypes.add(new MemberType(name));
	}
	public MemberType getMemberTypeByName(String name) {
		for(int i = 0; i < this._memberTypes.size(); i++) {
			if(this._memberTypes.get(i).get_typeName().equals(name))
				return this._memberTypes.get(i);
		}
		return null;
	}
	public void removeMemberType(String name){
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
		}		
	}
	
	public int getNumberOfTypes(){
		return this._memberTypes.size();
	}


	public void add_to_waitingList(String username, String password,String email) {
		this._waitingList.add(new String[]{username ,password,email });


	}


	public void checkValidationEmails() {		
		Vector<String> msg_senders =this._mailHandler.getEmailsSenders();
		for(String sender_email:msg_senders){
//			System.out.println(sender_email);
			for(String[] user : this._waitingList){
				if(sender_email.equals(user[2]));
					this.addMemberEmail(user[0], user[1] , user[2]);
			}
		}
	}


	private void addMemberEmail(String username, String password, String email) {
		 _members.add(new User(this,  username,  password , "MEMBER" ,email));		
	}

	
}
