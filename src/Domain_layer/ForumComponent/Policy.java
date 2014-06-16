package Domain_layer.ForumComponent;

import java.lang.reflect.Field;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User.Status;

@SuppressWarnings("serial")
public class Policy implements IPolicy   , java.io.Serializable  {

	private Status createSubForum;
	private Status changePolicy;
	private Status deletePost;
	private Status deleteSubForum;
	private Status addModerator;
	private Status removeModerator;
	private Status getNumberOfTypes;
	private Status removeMemberType;
	private Status addMemberType;
	private Status setMethodPolicy;
	private Status get_userComplaint;
	//NEED TO ADD
	private Status numPostsForum;
	private Status get_status_user;
	private Status get_start_date_user;
	private Status get_email;
	private Status numSassions_user;
	private Status moderator_subforum_list_user;

	public Policy(){
		createSubForum = 	Status.ADMINISTRATOR;
		changePolicy = 		Status.ADMINISTRATOR;
		deletePost = 		Status.ADMINISTRATOR;
		deleteSubForum = 	Status.ADMINISTRATOR;
		addModerator = 		Status.ADMINISTRATOR;
		removeModerator = 	Status.ADMINISTRATOR;
		getNumberOfTypes = 	Status.SUPER_ADMINISTRATOR;
		removeMemberType = 	Status.SUPER_ADMINISTRATOR;
		addMemberType =		Status.SUPER_ADMINISTRATOR;		
		setMethodPolicy =	Status.SUPER_ADMINISTRATOR;		
		get_userComplaint = Status.SUPER_ADMINISTRATOR;
		numPostsForum =  Status.SUPER_ADMINISTRATOR;
		get_status_user = Status.SUPER_ADMINISTRATOR;
		get_start_date_user= Status.SUPER_ADMINISTRATOR;
		get_email= Status.SUPER_ADMINISTRATOR;
		numSassions_user= Status.SUPER_ADMINISTRATOR;
		moderator_subforum_list_user= Status.SUPER_ADMINISTRATOR;
	}
	
	
	public Policy(Status pol_createSubForum, Status pol_changePolicy,
			Status pol_deletePost, Status pol_deleteSubForum,
			Status pol_addModerator, Status pol_removeModerator,
			Status pol_getNumberOfTypes, Status pol_removeMemberType,
			Status pol_addMemberType, Status pol_setMethodPolicy ,
			Status get_userComplaint, Status numPostsForum, Status get_status_user, Status get_start_date_user,
			Status get_email, Status numSassions_user, Status moderator_subforum_list_user) {
		this.createSubForum = pol_createSubForum;
		this.changePolicy = pol_changePolicy;
		this.deletePost = pol_deletePost;
		this.deleteSubForum = pol_deleteSubForum;
		this.addModerator = pol_addModerator;
		this.removeModerator = pol_removeModerator;
		this.getNumberOfTypes = pol_getNumberOfTypes;
		this.removeMemberType = pol_removeMemberType;
		this.addMemberType = pol_addMemberType;
		this.setMethodPolicy = pol_setMethodPolicy;
		this.get_userComplaint = get_userComplaint;
		
		this.numPostsForum = numPostsForum;
		this.get_status_user = get_status_user;
		this.get_start_date_user = get_start_date_user;
		this.get_email = get_email;
		this.numSassions_user = numSassions_user;
		this.moderator_subforum_list_user = moderator_subforum_list_user;

	}


	//---------------------------------------------------------------------------------------
	public boolean login(IUser current_user) {
		return current_user.getStatus().equals(Status.GUEST);
	}
	public boolean logout(IUser current_user) {
		return Status.MEMBER.has_permission(current_user.getStatus());
	}
	public boolean createSubForum(IUser current_user) {
		return this.createSubForum.has_permission(current_user.getStatus());
	}
	public boolean changePolicy(IUser current_user) {
		return this.changePolicy.has_permission(current_user.getStatus());
	}
	public boolean deletePost(IUser current_user) {
		return this.deletePost.has_permission(current_user.getStatus());
	}
	public boolean deleteSubForum(IUser current_user) {
		return this.deleteSubForum.has_permission(current_user.getStatus());
	}
	public boolean addModerator(IUser current_user) {
		return this.addModerator.has_permission(current_user.getStatus());
	}
	public boolean removeModerator(IUser current_user) {
		return this.removeModerator.has_permission(current_user.getStatus());
	}
	public boolean getNumberOfTypes(IUser current_user) {
		return this.getNumberOfTypes.has_permission(current_user.getStatus());
	}
	public boolean removeMemberType(IUser current_user) {
		return this.removeMemberType.has_permission(current_user.getStatus());
	}	
	public boolean addMemberType(IUser current_user) {
		return this.addMemberType.has_permission(current_user.getStatus());
	}
	
	public boolean setMethodPolicy(IUser current_user , String Methodname , Status s) {
		if(!this.setMethodPolicy.has_permission(current_user.getStatus()))
			return false;

		Field[] fileds  = Policy.class.getDeclaredFields();
		for(Field f : fileds){
			if(f.getName().equals(Methodname)){
				try {
					f.set(this, s);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					return false;
				} 
				return true;
			}
		}
		return false;
	}

	public boolean get_userComplaint(IUser _current_user) {	return this.get_userComplaint.has_permission(_current_user.getStatus());}
	public boolean numPostsForum(IUser current_user) {return this.numPostsForum.has_permission(current_user.getStatus());}
	//need to implemnts
	public boolean get_status_user(IUser _current_user) {return this.get_status_user.has_permission(_current_user.getStatus());}
	public boolean get_start_date_user(IUser _current_user) {return this.get_start_date_user.has_permission(_current_user.getStatus());}
	public boolean get_email(IUser _current_user) {	return this.get_email.has_permission(_current_user.getStatus());}
	public boolean numSassions_user(IUser _current_user) {return this.numSassions_user.has_permission(_current_user.getStatus());}
	public boolean moderator_subforum_list_user(IUser _current_user) {return this.moderator_subforum_list_user.has_permission(_current_user.getStatus());}
}
