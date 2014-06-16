package DataBase_Layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.ForumComponent.MemberType;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.SubForum;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;

public class DataMapper implements IDataMapper {
	
	private static String dbURL    = "jdbc:mysql://localhost:3306/test";
	private static String username ="root";
    private static String password = "pass";
    
    public DataMapper(){
    }
    
    public void addUser(IUser user){
    	try{
    		Connection dbConn = DriverManager.getConnection(dbURL, username, password);
			String insertUser = "INSERT INTO forumDB.USERS"
							  + "(username, password, forum_id, start_date, status, email, member_type) VALUES"
							  + "(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = dbConn.prepareStatement(insertUser);
			preparedStatement.setString(1, user.get_username());
			preparedStatement.setString(2, user.get_password());
			//preparedStatement.setInt(3, user.get_forum().get_id());
			Date start_date = user.get_start_date();
			preparedStatement.setTimestamp(4, new Timestamp(start_date.getTime()));
			preparedStatement.setString(5, user.getStatus().toString());
			preparedStatement.setString(6, user.get_email());
			preparedStatement.executeUpdate();
	    		    	
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    }
    
 public IUser getUser(String user_name){
	 IUser user = null;
	 try{
			Connection dbConn = DriverManager.getConnection(dbURL, username, password);
			Statement st = dbConn.createStatement();
			String query = "SELECT * FROM forumDB.USERS WHERE username='" + user_name + "'; ";
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
			{
				String username = rs.getString("username");
				String password = rs.getString("password");
				Date start_date = rs.getTimestamp("start_date");
				Status status = Status.valueOf(rs.getString("status"));
				String email = rs.getString("email");
				MemberType member_type = new MemberType(rs.getString("member_type"));
				
				user = new User(username, password, status);
				
				user.set_start_date(start_date);
				user.set_type(member_type);
				user.set_email(email);
			
		    }
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    	return user;
    }

 	public void addSubForum(ISubForum subforum){
 		try{		
    		Connection dbConn = DriverManager.getConnection(dbURL, username, password);
			String insertUser = "INSERT INTO forumDB.SUBFORUMS"
							  + "(theme)  VALUES"
							  + "(?)";
			PreparedStatement preparedStatement = dbConn.prepareStatement(insertUser);
			preparedStatement.setString(1, subforum.get_theme());
			preparedStatement.executeUpdate();
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
 	}
 	
 	public ISubForum getSubForum(String theme){
 		ISubForum subforum = null;
 		 try{
 				Connection dbConn = DriverManager.getConnection(dbURL, username, password);
 				Statement st = dbConn.createStatement();
 				String query = "SELECT * FROM forumDB.SUBFORUMS WHERE theme='" + theme + "'; ";
 				ResultSet rs = st.executeQuery(query);
 				while (rs.next())
 				{
 					String db_theme = rs.getString("theme");
 					subforum = new SubForum(db_theme, null, null); 				
 			    }
 	    	}
 	    	catch (SQLException e) 
 	    	{
 	    		e.printStackTrace();
 	    	}
 	    	return subforum;
 	   }
 	public void addPost(IPost post){
 		try{		
    		Connection dbConn = DriverManager.getConnection(dbURL, username, password);
			String insertUser = "INSERT INTO forumDB.POSTS"
							  + "(post_id, header, body, parent_post, subforum, username, date)  VALUES"
							  + "(?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStatement = dbConn.prepareStatement(insertUser,  Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setInt(1, post.get_id());
			preparedStatement.setString(2, post.get_header());
			preparedStatement.setString(3, post.get_body());
			
			if(post.get_parent_post() != null){
				preparedStatement.setInt(4, post.get_parent_post().get_id());
			}
			else preparedStatement.setNull(4, 0);
			
			preparedStatement.setString(5, post.get_subForum().get_theme());
			preparedStatement.setString(6, post.get_author().get_username());
			preparedStatement.setTimestamp(7, new Timestamp(post.get_date().getTime()));
			preparedStatement.executeUpdate();
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
 	}
 	
 	public IPost getPost(int id){
 		IPost post = null;
 		 try{
 				Connection dbConn = DriverManager.getConnection(dbURL, username, password);
 				Statement st = dbConn.createStatement();
 				String query = "SELECT * FROM forumDB.POSTS WHERE post_id='" + post + "'; ";
 				ResultSet rs = st.executeQuery(query);
 				while (rs.next())
 				{
 					rs.getInt("post_id");
 					rs.getString("header");
 					rs.getString("body");
 					rs.getInt("parent_post");
 					rs.getString("subforum");
 					rs.getString("username");
 					rs.getTimestamp("date");
 					
 					//post = new Post.create_post(db_post_id, db_header, db_body, IUser author, ISubForum subForum , IPost parent_post)			
 			    }
 	    	}
 	    	catch (SQLException e) 
 	    	{
 	    		e.printStackTrace();
 	    	}
 	    	return post;
 	   }
}
