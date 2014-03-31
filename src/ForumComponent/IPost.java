package ForumComponent;

public interface IPost {

	/**
	 * Adds reply post to the post. The reply post is held in
	 * a vector of posts.
	 * @param reply_post	reply to the post
	 */
	public void addReplyPost(Post reply_post);
	
}
