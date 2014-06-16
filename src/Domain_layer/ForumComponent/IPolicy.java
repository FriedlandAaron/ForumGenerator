package Domain_layer.ForumComponent;

import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;

public interface IPolicy {
	public boolean login(IUser current_user);
	public boolean logout(IUser current_user);
	public boolean createSubForum(IUser current_user);
	public boolean changePolicy(IUser current_user);
	public boolean deletePost(IUser current_user);
	public boolean deleteSubForum(IUser current_user);
	public boolean addModerator(IUser current_user);
	public boolean removeModerator(IUser current_user);
	public boolean getNumberOfTypes(IUser current_user);
	public boolean removeMemberType(IUser current_user);
	public boolean addMemberType(IUser current_user);
	public boolean setMethodPolicy(IUser current_user , String Methodname , Status s) ;
	public boolean get_userComplaint(IUser _current_user);
	public boolean numPostsForum(IUser current_user);
	public boolean get_status_user(IUser _current_user);
	public boolean get_start_date_user(IUser _current_user);
	public boolean get_email(IUser _current_user);
	public boolean numSassions_user(IUser _current_user);
	public boolean moderator_subforum_list_user(IUser _current_user);
}
