package ForumComponent;

public interface ISubForum {

	/**
	 * Opens a new thread in the subforum by adding it to
	 * its thread vector.
	 * @param post	the new thread
	 */
	public void openThread(Post post);
	
}
