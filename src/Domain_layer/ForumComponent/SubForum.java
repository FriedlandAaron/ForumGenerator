package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;

import Domain_layer.FourmUser.IUser;

@SuppressWarnings("serial")
public class SubForum implements ISubForum  , java.io.Serializable {

	private String _theme;
	private Vector<IUser> _moderators;
	private Vector<Date>  _moderator_dates;
	private Vector<IUser> _suspended_moderators;
	private Vector<IPost> _threads;
	private Vector<IPost> _posts_pending;
	
	public SubForum( String theme, Vector<IUser> moderators, Vector<Date> moderator_dates) {
		this._theme = theme;
		this._moderators = moderators;
		this._moderator_dates = moderator_dates;
		this._suspended_moderators = new Vector<IUser>();
		this._threads = new Vector<IPost>();
		this._posts_pending = new Vector<IPost>();
	}
//---------------------------------------------------
	public boolean openThread(IPost post) {
		_threads.add(post);
		return true;
	}
	
	public String get_theme() {
		return _theme;
	}

	public boolean isModerator(String modName) {
		for(int i = 0 ; i < this._moderators.size(); i++) {
			if(this._moderators.get(i).get_username().equals(modName)) {
				return true;
			}
		}
		return false;
	}

	public Vector<IPost> showThreads() {
		return _threads;
	}

	public void deletePost(IPost post) {
		for(int i = 0 ; i < this._threads.size(); i++) {
			if(this._threads.get(i).get_id()==post.get_id()) {
				this._threads.remove(i);
			}
		}
	}

	public void delete() {
		for(int i = 0; i < this._threads.size(); i++) {
			this._threads.get(i).delete();
		}
		this._threads.removeAllElements();
	}

	public IUser getModerator(String modName) {
		for(int i = 0; i < this._moderators.size(); i++) {
			if(this._moderators.get(i).get_username().equals(modName)) {
				return this._moderators.get(i);
			}
		}
		return null;
	}
	
	public Vector<IUser> get_moderators() {
		return _moderators;
	}

	public Vector<Date> get_moderator_dates() {
		return _moderator_dates;
	}

	public Vector<IUser> get_suspended_moderators() {
		return _suspended_moderators;
	}

	public Vector<IPost> get_posts_pending() {
		return _posts_pending;
	}
	public int numPostsSubForum() {
		int size = this._threads.size();
		for(int i=0 ; i<this._threads.size(); i++)
			size = size +this._threads.get(i).numDescendants();
		return size;		
	}
	public void addModerator(IUser user) {
		this._moderators.add(user);
		this._moderator_dates.add(new Date());
	}
	public void removeModerator(IUser user) {
		int i = this._moderators.indexOf(user);
		if(i!=-1){
			this._moderators.remove(i);
			this._moderator_dates.remove(i);			
		}
	}
	
}
