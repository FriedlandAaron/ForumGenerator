package ForumComponent;


public interface IForum {
	
	/**
	 * Adds a newly registered member to the list of members which is
	 * maintained by the Forum.
	 * @param new_member	the member being added to the forum database
	 */
	public void addMember(User new_member);
	
	
	/**
	 * Attempts to login to the forum system using the given credentials.
	 * If the username provided is not registered in the forum database,
	 * the login fails and false is returned.
	 * If the credentials are wrong, the login will fail and false is
	 * returned. If the username exists and the credentials are correct,
	 * the method will return true to the user so that he may update his
	 * status. 
	 * @param 	username	username provided by user
	 * @param 	password	password provided by user
	 * @return	false if username doesn't exist or of credentials are wrong, true otherwise
	 */
	public boolean login(String username, String password);
	
}
