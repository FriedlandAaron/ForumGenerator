package ForumComponent;

import java.util.Date;
import java.util.Vector;

import FourmUser.User;

public class Post {

	private String _header;
	private String _body;
	private Vector<Post> _replies;
	private Post _parent_post;
	private User _author;
	private Date _date;
	private SubForum _subForum;

	public Post(String header , String body ,User author , SubForum subForum  ){
		this._header = header ; 
		this._body = body ; 
		this._author = author ; 
		this._subForum = subForum ; 
		
		this._parent_post = null;		
		this._date =new Date() ;
		this._replies = new Vector<Post>() ;
		
	}
	public Post(String header , String body ,User author , SubForum subForum ,  Post parent_post ){
		this._header = header ; 
		this._body = body ; 
		this._author = author ; 
		this._subForum = subForum ; 
		this._parent_post = parent_post;
		
		this._date =new Date() ;
		this._replies = new Vector<Post>() ;
		
	}
	
	public void addReplyPost(Post reply_post) {
		_replies.add(reply_post);
	}
	
	public SubForum get_subForum() {
		return _subForum;
	}
	
	public User get_author() {
		return _author;
	}
	public void delete() {
		for(int i=0; i< this._replies.size() ; i++){
			this._replies.get(i).delete();
		}
	}
}
