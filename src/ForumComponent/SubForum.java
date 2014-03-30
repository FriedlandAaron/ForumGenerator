package ForumComponent;

import java.util.Date;
import java.util.Queue;
import java.util.Vector;

public class SubForum {

	private String _theme;
	private Vector<User> _moderators;
	private Vector<Date> _moderator_dates;
	private Vector<User> _suspended_moderators;
	private Vector<Post> _threads;
	private Queue<Post> _posts_pending;
	
	public void openThread(Post post) {
		_threads.add(post);
	}
	
}
