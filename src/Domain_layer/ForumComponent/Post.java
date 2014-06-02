package Domain_layer.ForumComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import Domain_layer.FourmUser.IUser;

@SuppressWarnings("serial")
public class Post implements IPost  , java.io.Serializable {
	
    private static int counter = 0;
    private static ArrayList<IPost> instanses = new   ArrayList<IPost>() ;
    public  static IPost getIPost(long id){return Post.instanses.get((int) id); }
    
	private int _id;
	private String _header;
	private String _body;
	private Vector<IPost> _replies;
	private IPost _parent_post;
	private IUser _author;
	private Date _date;
	private ISubForum _subForum;

	public static Post create_post(String header, String body, IUser author, ISubForum subForum){
		Post p =new  Post(header , body , author ,subForum  );
		Post.instanses.add(p);
		return p;
	}
	
	public static Post create_post(String header, String body, IUser author, ISubForum subForum , IPost parent_post){
		Post p =new  Post(header , body , author ,subForum, parent_post  );
		Post.instanses.add(p);
		return p;
	}

	private Post(String header, String body, IUser author, ISubForum subForum) {
		this._header = header;
		this._body = body;
		this._author = author;
		this._subForum = subForum;	
		this._parent_post = null;
		this._date = new Date();
		this._replies = new Vector<IPost>();		
		this._id = counter;
		counter++;	
	}
	
	private Post(String header, String body, IUser author, ISubForum subForum, IPost parent_post) {
		this._header = header;
		this._body = body;
		this._author = author;
		this._subForum = subForum;
		this._parent_post = parent_post;
		this._date = new Date();
		this._replies = new Vector<IPost>();		
		this._id = counter;
		counter++;	
	}
//-----------------------------------------------------------------------
	



	public void addReplyPost(IPost reply_post) {
		_replies.add(reply_post);
	}
	
	public ISubForum get_subForum() {
		return _subForum;
	}
	
	public IUser get_author() {
		return _author;
	}
	
	public Vector<IPost> get_replies() {
		return _replies;
	}
	
	public void delete() {
		for(int i=0; i< this._replies.size() ; i++){
			this._replies.get(i).delete();
		}
		this._author.deletePost(this);
	}

	public String get_header() {
		return _header;
	}

	public String get_body() {
		return _body;
	}

	public IPost get_parent_post() {
		return _parent_post;
	}

	public Date get_date() {
		return _date;
	}
	
	public int get_id(){
		return this._id;
	}

	@Override
	public int numDescendants() {
		int size = this._replies.size(); 
		for(int i=0 ; i< this._replies.size() ; i++)
			size = size +this._replies.get(i).numDescendants();
		return size;	
	}


	
}
