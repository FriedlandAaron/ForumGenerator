package FourmUser;

import ForumComponent.*;

public interface IUser {

	/**
	 * Attempts to open a new thread in the corresponding subforum.
	 * This method only delegates the responsibility of posting the new message
	 * to the subforum.
	 * @param sub_forum	the subforum in which the new post will be posted
	 * @param post		the new post to be posted
	 */
	public void addNewPost(SubForum sub_forum, Post post);
	
	/**
	 * Attempts to add a reply message to an existing message in the forum.
	 * This method only delegates the responsibility of posting the reply
	 * message to the post that is being replied to.
	 * @param reply_post	the reply post being submitted
	 * @param post			the post being replied to
	 */
	public void addReplyPost(Post reply_post, Post post);
	
	/**
	 * Attempts to register a user to the forum, making him a member.
	 * If the username provided already exists in the forum, the method
	 * will fail and false will be returned. Also, if the typed password
	 * and the repeated password don't match, the method will fail and
	 * false will be returned. The method will register the new user in the
	 * fourm only if the username doesn't exist in the forum and the typed 
	 * passwords match.
	 * @param username			the submitted username
	 * @param password			the submitted password
	 * @param repeated_password	the submitted password repeated
	 * @return	false if usernmae exists in the forum or if passwords don't match, true otherwise
	 */
	public boolean register(String username, String password, String repeated_password);
	
	/**
	 * Attempts to login to the forum using the given credentials.
	 * This method only delegates the responsibilty of the login
	 * to the forum.
	 * @param username	the submitted username
	 * @param password	the submitted password
	 * @return	true if login succeeded, false otherwise
	 */
	public boolean login(String username, String password);
	
	/**
	 * Getter method. Returns the password field of the user.
	 * @return	a string representing the user password
	 */
	public String get_password();
	
	/**
	 * Setter method. Sets the boolean field which represents the user
	 * connection status.
	 * @param _is_connected		boolean value of connection status
	 */
	public void set_is_connected(boolean _is_connected);
	
}
