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
	}
	
	
	public Policy(Status pol_createSubForum, Status pol_changePolicy,
			Status pol_deletePost, Status pol_deleteSubForum,
			Status pol_addModerator, Status pol_removeModerator,
			Status pol_getNumberOfTypes, Status pol_removeMemberType,
			Status pol_addMemberType, Status pol_setMethodPolicy) {
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
	}


	//--------------------------------------------------------------------------
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
}
