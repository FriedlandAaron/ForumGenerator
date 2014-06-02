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

public class DatabaseCreator{
	private String dbURL    = "jdbc:mysql://localhost:3306/test";
	private String username ="root";
    private String password = "pass";
    private String dbName   = "forumDB";
     
	public void createDatabase(){
		try {
			//getting database connection to MySQL server
			Connection dbConn = DriverManager.getConnection(dbURL, username, password);
			
			//create a Statement object for sending SQL statements to the database
			Statement st = dbConn.createStatement();
			 
			//create the database
			st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);	
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void createTables(){
		try{
			//TODO: tables to create: FORUMS, 
			
			//getting database connection to MySQL server
			Connection dbConn = DriverManager.getConnection(dbURL, username, password);
					
			//create a Statement object for sending SQL statements to the database
			Statement st = dbConn.createStatement();
			
			String forums = "CREATE TABLE IF NOT EXISTS forumDB.FORUMS " +
					        "(forum_id integer NOT NULL AUTO_INCREMENT, " +
					        " theme VARCHAR(20) NOT NULL, " + 
					        " PRIMARY KEY (forum_id)) ";
			st.executeUpdate(forums);
			
			String users = "CREATE TABLE IF NOT EXISTS forumDB.USERS " +
						   "(username VARCHAR(20) NOT NULL UNIQUE, " + 
			               " password VARCHAR(20) NOT NULL, " + 
			               " forum_id integer NOT NULL, " + 
			               " start_date DATE, " +
			               " is_banned BOOLEAN, " +
			               " status ENUM('GUEST', 'MEMBER', 'ADMINISTRATOR', 'SUPER_ADMINISTRATOR'), " + 
			               " email VARCHAR(30), " + 
			               " member_type VARCHAR(30), " +
			               " PRIMARY KEY (username)) " ;
						   //" FOREIGN KEY (forum_id) REFERENCES FORUMS(forum_id)) ";
						   
			st.executeUpdate(users);

			String subforums = "CREATE TABLE IF NOT EXISTS forumDB.SUBFORUMS " +
								"(subforum_id INTEGER NOT NULL AUTO_INCREMENT, " +
								" theme VARCHAR(20) NOT NULL,  " +
								" PRIMARY KEY (subforum_id)) ";
						
			st.executeUpdate(subforums);
			
			String subforums_moderators = "CREATE TABLE IF NOT EXISTS forumDB.SUBFORUMS_MODERATORS " +
					   					  "(subforum_id INTEGER NOT NULL, " + 
					   					  " moderator_id  VARCHAR(20) NOT NULL, " + 
					   					  " PRIMARY KEY (subforum_id,  moderator_id), " + 
					   					  " FOREIGN KEY (subforum_id) REFERENCES forumDB.SUBFORUMS(subforum_id), " + 
					   					  " FOREIGN KEY (moderator_id) REFERENCES forumDB.USERS(username)) ";
			
			st.executeUpdate(subforums_moderators);
			
			String moderators_dates = "CREATE TABLE IF NOT EXISTS forumDB.MODERATORS_DATES " +
					 				  "(subforum_id INTEGER NOT NULL, " + 
					 				  " moderator_id  VARCHAR(20) NOT NULL, " +
					 				  " date DATE, " +
					 				  " PRIMARY KEY (subforum_id,  moderator_id, date), " + 
					 				  " FOREIGN KEY (subforum_id,  moderator_id) REFERENCES  forumDB.SUBFORUMS_MODERATORS(subforum_id,  moderator_id)) "; 
					 				 
			st.executeUpdate(moderators_dates);
			
			String suspended_moderators = "CREATE TABLE IF NOT EXISTS forumDB.SUSPENDED_MODERATORS " +
 					  					  "(subforum_id INTEGER NOT NULL, " + 
 					  					  " moderator_id  VARCHAR(20) NOT NULL, " + 
 					  					  " PRIMARY KEY (subforum_id,  moderator_id), " + 
 					  					  " FOREIGN KEY (subforum_id) REFERENCES forumDB.SUBFORUMS(subforum_id), " + 
 					  					  " FOREIGN KEY (moderator_id) REFERENCES forumDB.USERS(username)) ";
			
			st.executeUpdate(suspended_moderators);
			
			String posts = "CREATE TABLE IF NOT EXISTS forumDB.POSTS " +
						   "(post_id INTEGER NOT NULL AUTO_INCREMENT, " +
						   " header VARCHAR(20) NOT NULL,  " +
						   " body VARCHAR(20) NOT NULL,  " +
						   " parent_post INTEGER, " +
						   " subforum_id INTEGER NOT NULL, " + 
						   " username VARCHAR(20) NOT NULL, " +
						   " date DATE, " +
						   " is_pending BOOLEAN, " +
						   " PRIMARY KEY (post_id), " +
						   " FOREIGN KEY (username) REFERENCES forumDB.USERS(username), " +
						   " FOREIGN KEY (subforum_id) REFERENCES forumDB.SUBFORUMS(subforum_id), " +
						   " FOREIGN KEY (parent_post) REFERENCES forumDB.POSTS(post_id)) ";
			
			st.executeUpdate(posts);
			
			String subforums_threads = "CREATE TABLE IF NOT EXISTS forumDB.SUBFORUMS_THREADS " +
									   "(subforum_id INTEGER NOT NULL, " + 
									   " post_id INTEGER NOT NULL , " + 
									   " PRIMARY KEY (subforum_id,  post_id), " + 
	  				   				   " FOREIGN KEY (subforum_id) REFERENCES forumDB.SUBFORUMS(subforum_id), " + 
	  				   				   " FOREIGN KEY (post_id) REFERENCES forumDB.POSTS(post_id)) ";
			
			st.executeUpdate(subforums_threads);
			
			String reply_posts = "CREATE TABLE IF NOT EXISTS forumDB.REPLY_POSTS " +
								 "(parent_post INTEGER NOT NULL, " +
								 " reply_post INTEGER NOT NULL, " +
								 " PRIMARY KEY (parent_post, reply_post), " +   
								 " FOREIGN KEY (parent_post) REFERENCES forumDB.POSTS(post_id), " +
								 " FOREIGN KEY (reply_post) REFERENCES forumDB.POSTS(post_id)) ";
			
			st.executeUpdate(reply_posts);
			
			String users_threads = "CREATE TABLE IF NOT EXISTS forumDB.USERS_THREADS " +
								   "(username VARCHAR(20) NOT NULL, " + 
								   " thread_id INTEGER NOT NULL, " + 
								   " PRIMARY KEY (username, thread_id), " + 
								   " FOREIGN KEY (username) REFERENCES forumDB.USERS(username), " + 
								   " FOREIGN KEY (thread_id) REFERENCES forumDB.POSTS(post_id)) ";
			
			st.executeUpdate(users_threads);
			
			String users_reply_posts = "CREATE TABLE IF NOT EXISTS forumDB.USERS_REPLY_POSTS " +
					   				   "(username VARCHAR(20) NOT NULL, " + 
					   				   " post_id INTEGER NOT NULL, " + 
					   				   " PRIMARY KEY (username, post_id), " + 
					   				   " FOREIGN KEY (username) REFERENCES forumDB.USERS(username), " +
					   				   " FOREIGN KEY (post_id) REFERENCES forumDB.POSTS(post_id)) ";
			
			st.executeUpdate(users_reply_posts);
					
			String users_friends = "CREATE TABLE IF NOT EXISTS forumDB.USERS_FRIENDS " +
					   			   "(username VARCHAR(20) NOT NULL, " + 
					   			   " friendname VARCHAR(20) NOT NULL, " + 
					   			   " PRIMARY KEY (username, friendname), " + 
					   			   " FOREIGN KEY (username) REFERENCES forumDB.USERS(username), " + 
					   			   " FOREIGN KEY (friendname) REFERENCES forumDB.USERS(username)) ";
			
			st.executeUpdate(users_friends);
			
			String complaints = "CREATE TABLE IF NOT EXISTS forumDB.COMPLAINTS " +
					            "(complaint_id INTEGER NOT NULL AUTO_INCREMENT, " +
								" submitter VARCHAR(20) NOT NULL, " +
							    " complaint_on_user VARCHAR(20) NOT NULL, " +
							    " date DATE, " +
						    	" theme VARCHAR(20) NOT NULL,  " +
								" body VARCHAR(20) NOT NULL,  " +
								" PRIMARY KEY (complaint_id), " +
								" FOREIGN KEY (submitter) REFERENCES forumDB.USERS(username), " +
								" FOREIGN KEY (complaint_on_user) REFERENCES forumDB.USERS(username)) ";

			st.executeUpdate(complaints);
			
			String users_complaints = "CREATE TABLE IF NOT EXISTS forumDB.USERS_COMPLAINTS " +
								      "(username VARCHAR(20) NOT NULL, " + 
		   			                  " complaint_id INTEGER NOT NULL, " +
		   			                  " date DATE, " +
		   			                  " PRIMARY KEY (username, complaint_id, date), " + 
		   			                  " FOREIGN KEY (username) REFERENCES forumDB.USERS(username), " +
		   			                  " FOREIGN KEY (complaint_id) REFERENCES forumDB.COMPLAINTS(complaint_id)) ";
			
			st.executeUpdate(users_complaints);
			
			
			
			
			
		}	
		catch (SQLException e) {
			e.printStackTrace();
		}		
				
	}
	
    public static void main(String args[]) {
    	DatabaseCreator dbConnector = new DatabaseCreator();
    	dbConnector.createDatabase();
    	dbConnector.createTables();
    }  
}
