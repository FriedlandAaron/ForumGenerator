package ForumComponent;

import java.util.Date;
import java.util.Vector;

public class Post {

	private String _header;
	private String _body;
	private Vector<Post> _replies;
	private Post _parent_post;
	private User _author;
	private Date _date;
	private SubForum _subForum;
	
	public void addReplyPost(Post reply_post) {
		_replies.add(reply_post);
	}
	
}
