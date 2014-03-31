package ForumComponent;

import java.util.Date;
import java.util.Vector;

import FourmUser.User;

public class SubForum {

	private String _theme;
	private Vector<User> _moderators;
	private Vector<Date> _moderator_dates;
	private Vector<User> _suspended_moderators;
	private Vector<Post> _threads;
	private Vector<Post> _posts_pending;
	
	public SubForum(String theme, Vector<User> moderators, Vector<Date> moderator_dates){
		this._theme=theme;
		this._moderators= moderators;
		this._moderator_dates = moderator_dates;
		this._suspended_moderators= new Vector<User>();
		this._threads= new Vector<Post>();
		this._posts_pending = new Vector<Post>();		
	}
	
	public boolean openThread(Post post) {
		_threads.add(post);
		return true;
	}
	public String get_theme() {
		return _theme;
	}

	public boolean isModerator(String search_word) {
		for(int i=0 ; i< this._moderators.size();i++)
			if(this._moderators.get(i).get_username().equals(search_word))
				return true;
		return false;
	}

	public Vector<Post> showThreads() {
		return _threads;
	}

	
}
