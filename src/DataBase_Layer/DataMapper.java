package DataBase_Layer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import Domain_layer.ForumComponent.Forum;
import Domain_layer.ForumComponent.IForum;
import Domain_layer.ForumComponent.Policy;
import Domain_layer.FourmUser.IUser;
import Domain_layer.FourmUser.User;
import Domain_layer.FourmUser.User.Status;
import Service_Layer.UserHandler;
import java.util.UUID;

public class DataMapper {
	
	private static String dbURL    = "jdbc:mysql://localhost:3306/test";
	private static String username ="root";
    private static String password = "pass";
    
    public static void addUser(IUser user){
    	try{
    	
			Connection dbConn = DriverManager.getConnection(dbURL, username, password);
				
			Statement st = dbConn.createStatement();
			
	    	java.sql.Date sqlDate = new java.sql.Date(user.get_start_date().getTime());
	    
	    	String insertUser = "INSERT INTO forumDB.USERS " +
	                   			"VALUES (" +
	                   			"'" + user.get_username() + "', " + 
	                   			"'" + user.get_password() + "', " +
	    						user.get_forum().get_id() + ", " +
	    						"NULL, " +
	    						"FALSE, " + 
	    						"'" + user.getStatus() + "', " +
	    						"'blabla', " +
	    						"'" + user.get_type().get_typeName() + "')"; 
	    	System.out.println(insertUser);
	    	
	    	st.executeUpdate(insertUser);
	    		    	
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    public static void getUser(String user_name){
    	try{
    	
			Connection dbConn = DriverManager.getConnection(dbURL, username, password);
				
			Statement st = dbConn.createStatement();
			
			String query = "SELECT * FROM forumDB.USERS WHERE username='" + user_name + "'; ";
			
			ResultSet rs = st.executeQuery(query);
			
			rs.next();
			System.out.println(rs.getString("username"));
//			while (rs.next())
//			{
//				int id = rs.getInt("id");
//		        String firstName = rs.getString("first_name");
//		        String lastName = rs.getString("last_name");
//		        Date dateCreated = rs.getDate("date_created");
//		        boolean isAdmin = rs.getBoolean("is_admin");
//		        int numPoints = rs.getInt("num_points");
//		        
//		        // print the results
//		        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
//		    }
    	}
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    	}
    }

}
