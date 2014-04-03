package Tests.Acceptance_testing;

import java.util.Vector;

public class DatabaseForum  {
	

	//ADD METHODS AND VARS OF THE FORUM !!
	public Vector<Member> listOfMembers;
	public Vector<Member> bannedMembers;
	public Vector<Member> listOfAdministrators;
	public Vector<subForum> listOfSubForums;
	public Policy policy;
	
	public DatabaseForum(){
		listOfAdministrators = new Vector<Member>();
		listOfMembers = new Vector<Member>();
		listOfSubForums = new Vector<subForum>();
		bannedMembers = new Vector<Member>();
		policy = new Policy(); 
	}
		


}
