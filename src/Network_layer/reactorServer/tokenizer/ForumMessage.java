package Network_layer.reactorServer.tokenizer;

import java.util.Vector;


public class ForumMessage implements Message<ForumMessage>, java.io.Serializable {

	public enum ForumMessageType  {	COMMAND ,REPLY ,CLOSE}
	public interface ForumMessageMinorType{};
	public enum ForumMessageReturnType  implements ForumMessageMinorType  {ERROR , BOOLEAN, VEC_ISUBFORUM, ISUBFORUM, VEC_IPOST, INTEGER, 
																			STRING, IFORUM, VEC_IUSER, VEC_COMP, STARTDATE, STATUS, MEMBERTYPE, IUSER  };
																			
	public enum ForumMessageCommendType implements ForumMessageMinorType {LOGIN , LOGOUT, REGISTER, CR_SUBFORUM, SH_SUBFORUM, CR_THREAD, CR_REPLYPOST, SEARCH_SUBFORUM, 
																			CHAN_POLICY, SH_REPLYPOST, SH_THREADPOST, DELETE_POST, DELETE_SUBFORUM, ADD_COMP_MOD, ADD_MEMBER_TYPE, 
																			REM_MEMBER_TYPE, GET_NUM_MEMBER_TYPE, GET_THREADS, GET_REPLYPOST, GET_PASS, GET_USERNAME, GET_FORUM, 
																			GET_FRIENDS, GET_COMP, GET_STARTDATE, GET_STATUS, GET_EMAIL, GET_TYPE, GET_CUR_USR,
																			ADD_MODERATOR, DEL_MODERATOR, NUM_SUBFORUM, NUM_SHARED_MOD, NUM_POSTS_SUBFORUM, NUM_POSTS_USER, MODERATORS_LIST, MODERATORS_REPORT, SET_METHOD_POLICY};

	private static final long serialVersionUID = 1L;	 
	private  ForumMessageType type;
	private  ForumMessageMinorType minor_type;
	private  int argsc;
	private  Vector<Object> args;
	
	public ForumMessage(ForumMessageType type  , Vector<Object> args){
		this.type = type;
		this.argsc = args.size();
		this.minor_type = null;
		this.args = new Vector<Object>();
		if(args!=null)
			for(int i=0 ; i<args.size() ; i++)
				this.args.add(args.get(i));
	}
	public ForumMessage(ForumMessageType type  ,ForumMessageMinorType minor_type ,  Vector<Object> args){
		this.type = type;
		this.minor_type = minor_type;
		this.argsc = args.size();
		this.args = new Vector<Object>();
		if(args!=null)
			for(int i=0 ; i<args.size() ; i++)
				this.args.add(args.get(i));
	}
	
	//-------------------------------------------------------------------------------------------

	public String toString() {
		StringBuilder s = new StringBuilder("ForumMessage:\n");
		s.append("type: ").append(this.type).append("\n");
		if(this.minor_type==null)	
			s.append("type: ").append(this.type).append("\n");
		else 	
			s.append("type_minor: ").append(this.minor_type).append("\n");
		s.append("argsc:").append(this.argsc).append("\n");
		s.append("args: ").append(this.args).append("\n");
		return s.toString();
	}
	
	public ForumMessageType get_type(){
		return this.type;
	}
	
	public ForumMessageMinorType get_minor_type(){
		return this.minor_type;
	}

	
	public int get_argsc(){
		return this.argsc;
	}
	public Object get_arg(int index){
		if(index >= this.argsc || index <0)
			return null;
		return this.args.get(index);
	}
	
	public Object[] get_Object_Array(){
		return this.args.toArray();
	}

	//templets
	
	public static ForumMessage create_CloseReplay() {
		Vector<Object> args =  new  Vector<Object>();
		return 	new ForumMessage(ForumMessageType.CLOSE  , args);
	}
	public static ForumMessage create_ErrorReplay(String str) {
		return 	create_Replay(ForumMessageReturnType.ERROR ,str);
	}
	public static ForumMessage create_Replay(ForumMessageMinorType minor, Object return_val) {
		Vector<Object> args =  new  Vector<Object>();
		args.add(return_val);
		return 	new ForumMessage(ForumMessageType.REPLY ,minor , args);
	}
	
	
/*	
	public static void main(String[] args) {
		Vector<Object> args1 =  new  Vector<Object>();
		args1.add("STRING_MESSAGE");
		args1.add("hadaramran");
		ForumMessage msg=new ForumMessage("REPLY" ,args1);
		
		String serializedForumMessage = "";

		   // serialize the object
		   try {
		       ByteArrayOutputStream bo = new ByteArrayOutputStream();
		       ObjectOutputStream so = new ObjectOutputStream(bo);
		       so.writeObject(msg);
		       so.flush();
		       //serializedForumMessage = bo.toString();
		       
		       
		       serializedForumMessage =new String(Base64.encodeBase64(bo.toByteArray()));
		   } catch (Exception e) {
		       System.out.println(e);
		       System.exit(1);
		   } 
		 System.out.println("\n^^^^^^^^^^^^^^^^^^\n");
		 System.out.println("="+serializedForumMessage+"=");
		 System.out.println("\n^^^^^^^^^^^^^^^^^^\n");

		 
		 ForumMessage forumMessage=null;
		 // deserialize the object
	      try {
	    	  
	          byte b[] = Base64.decodeBase64(serializedForumMessage); 
	          ByteArrayInputStream bi = new ByteArrayInputStream(b);
	          ObjectInputStream si = new ObjectInputStream(bi);
	          forumMessage = (ForumMessage) si.readObject();
	      } catch (Exception e) {
	          System.out.println(e);
	          System.exit(1);
	      }
	      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
	      
	      System.out.println(forumMessage.toString());
		   
    } 
  */  
	
}
