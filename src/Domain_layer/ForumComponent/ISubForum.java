package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;

import Domain_layer.FourmUser.IUser;

public interface ISubForum {

	/**
	 * Opens a new thread in the subforum by adding it to
	 * its thread vector.
	 * @param post	the new thread
	 */
	public boolean openThread(IPost post) ;
	public String get_theme();
	public boolean isModerator(String modName);
	public Vector<IPost> showThreads();
	public void deletePost(IPost post);
	public void delete();
	public IUser getModerator(String modName);
	public Vector<IUser> get_moderators();
	public Vector<Date> get_moderator_dates();
	public Vector<IUser> get_suspended_moderators();
	public Vector<IPost> get_posts_pending();
	public int numPostsSubForum();
	public void addModerator(IUser user);
	public void removeModerator(IUser user);
	
}
