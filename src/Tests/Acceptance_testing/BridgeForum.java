package Tests.Acceptance_testing;

import java.util.Vector;

import Domain_layer.ForumComponent.IForum;



public interface BridgeForum {
	IForum openNewForum(Member m , String theme , Vector<Member> admin , Policy p  );
	boolean registerMemberToForum(Member m);
	//boolean disconnectForum(Member m, SystemFor f);
	//boolean addNewPost(Member m, subForum sf, Post p);
	boolean deletePost(Member m, subForum sf, Post p);
	boolean addReplyPost(Member m, String header, String body);
	boolean editPost(Member m, Post p);
	Domain_layer.ForumComponent.SubForum addNewSubForum(Member m, String theme);
	boolean logIn(Member m);
	


	
}
