package Tests.Acceptance_testing;

import java.util.Date;
import java.util.Vector;

import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.IPolicy;
//import Domain_layer.ForumComponent.Policy;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.IUser;
//import Service_Layer.IUserHandler;
//import Service_Layer.UserHandler;




public class BridgeProxy implements BridgeForum {
//	private DatabaseForum database;
	

	public BridgeProxy() {
	}

	public void setRealBridge() {
//		Vector<Users> listOfUsers = new Vector<Users>();
//		for (int i=0; i<DBData.users.length; i++){
//			system.addUsersStudent(DBData.users[i][0], DBData.users[i][1]);
//			}
//		for (int i=0; i<DBData.students.length;i++){
//			system.addUsersStudent(DBData.students[i][0], DBData.students[i][1]);
//		}
		//ADD MEMBERS OF THE FORUM TO DATABASE !!
//		this.database = database;
	}



	@Override
	public boolean registerMemberToForum(Member m) {
		Service_Layer.IUserHandler user = new  Service_Layer.UserHandler(new Forum(null ,new  Vector<IUser>()  ,""));
		
		return user.register(m.get_name(), m.get_password(), m.get_password());
		
		
	}


		

//	public boolean addNewPost(Member m, subForum sf, Post p) {
//		
//		if (m.get_Type() != MemberType.GUEST){
//			Service_Layer.IUserHandler super_admin = new Service_Layer.UserHandler("SUPER_ADMINISTRATOR" , m.get_name() , m.get_password());
//			Domain_layer.ForumComponent.SubForum sb = new Domain_layer.ForumComponent.SubForum(sf.getName(),sf.getPosts() );
//			return super_admin.create_thread("head", "body", sb);
//		}
//		else return false;
//		
//	}

	public IForum openNewForum(Member m, String theme,Vector<Member> admin, Tests.Acceptance_testing.Policy p) {
		if (m.get_Type() == MemberType.SUPERADMIN){
			IPolicy p1 = null;
			Vector<String[]> admins = new  Vector<String[]>(); 
			for(int i=0 ; i< admin.size(); i++)
				admins.add(new String[]{admin.get(i).get_name(), admin.get(i).get_password()});		
			return Forum.createForum( m.get_name(), m.get_password(), p1, admins, theme);
			}
		else return null;
	}

	@Override
	public boolean deletePost(Member m, subForum sf, Post p) {
		Service_Layer.IUserHandler user = new Service_Layer.UserHandler(new Forum(null ,new  Vector<IUser>()  ,""));
		
		Domain_layer.ForumComponent.ISubForum  sub = new Domain_layer.ForumComponent.SubForum("hagsfhg", new Vector<IUser>(), new Vector<Date>()) ;
		Domain_layer.ForumComponent.Post post =  Domain_layer.ForumComponent.Post.create_post(p.getHead(), p.getBody() ,user.get_current_user()  , sub);
		return user.deletePost(post);
	}

	@Override
	public boolean addReplyPost(Member m, String header, String body) {
		if (m.get_Type() != MemberType.GUEST){
			Service_Layer.IUserHandler user = new Service_Layer.UserHandler(new Forum(null ,new  Vector<IUser>()  ,""));
			Domain_layer.ForumComponent.Post post =  Domain_layer.ForumComponent.Post.create_post(null , null ,null ,null );
			return user.createReplyPost(header, body, post);
		}
		else return false;
			
		

	}

	@Override
	public boolean editPost(Member m,Post p) {
		return false;
	}

	@Override
	public SubForum addNewSubForum(Member m, String theme) {		
		return null;
	}

	@Override
	public boolean logIn(Member m) {
		Service_Layer.IUserHandler user = new Service_Layer.UserHandler(new Forum(null ,new  Vector<IUser>()  ,""));
		return user.login(m.get_name(), m.get_password());
		
	}












}