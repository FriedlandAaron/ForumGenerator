package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;

import Domain_layer.FourmUser.IUser;

public interface IPost {

	/**
	 * Adds reply post to the post. The reply post is held in
	 * a vector of posts.
	 * @param reply_post	reply to the post
	 */
	public void addReplyPost(IPost reply_post);
	public ISubForum get_subForum();
	public IUser get_author() ;
	public Vector<IPost> get_replies();
	public void delete();
	
	public String get_header();

	public String get_body();

	public IPost get_parent_post();

	public Date get_date();

}
