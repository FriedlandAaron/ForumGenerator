package Domain_layer.ForumComponent;

import java.util.Date;
import java.util.Vector;

import Domain_layer.FourmUser.IUser;

@SuppressWarnings("serial")
public class SubForum implements ISubForum  , java.io.Serializable {

	private String _theme;
	private Vector<IUser> _moderators;
	private Vector<Date>  _moderator_dates;
	private Vector<IPost> _threads;
	
	public SubForum( String theme, Vector<IUser> moderators, Vector<Date> moderator_dates) {
		this._theme = theme;
		this._moderators = moderators;
		this._moderator_dates = moderator_dates;
		this._threads = new Vector<IPost>();
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

	public Vector<IPost> get_threads() {
		return _threads;
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

	//new gui
	public IPost search_Post(String postSubject) {
		IPost p ;
		for(int i=0 ; i< this._threads.size() ; i++){
			p = this._threads.get(i);
			if(p.get_header().equals(postSubject))
				return p;
			p= p.search_RPost(postSubject);
			if(p!=null)
				return p;
		}
		return null;
			
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubForum other = (SubForum) obj;
		if (_theme == null) {
			if (other._theme != null)
				return false;
		} else if (!_theme.equals(other._theme))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder sb = new  StringBuilder("SubForum: " + _theme) ;
		for(IPost p : this._threads)
			sb.append("\n\t").append(p.toString(2));
		return sb.toString();
	}
	
	
	
}
