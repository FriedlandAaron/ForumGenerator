package Applet.Windows;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import Applet.Windows.DesignWindowDisplays.Display;
import Applet.Windows.DesignWindowDisplays.DownToolBar;
import Applet.Windows.DesignWindowDisplays.LeftToolBar;
import Applet.Windows.DesignWindowDisplays.RightToolBar;
import Applet.Windows.DesignWindowDisplays.Slide;
import Applet.Windows.DesignWindowDisplays.UpToolBar;
import Applet.Windows.DesignWindowDisplays.MainDisplay.MainDispaly;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.SubForumDispaly;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Network_layer.reactorClient.ConnectionHandler;
import Network_layer.reactorServer.ForumServer;
import Service_Layer.ClientHandler;
import Service_Layer.IUserHandler;

@SuppressWarnings("serial")
/**
 * This class is the Design Window JFrame of this program Slide Show
 * @author Hadar Amran & Hod Amran 
 */
public class DesignWindow extends JApplet implements Runnable {
	
	private IUserHandler clientHandler  ; 
	private UpToolBar up_toolbar;
	private LeftToolBar left_toolbar;
	private DownToolBar down_label;
	private  RightToolBar right_toolbar;
	private Slide curSlide;
	private Display curDisplay ; 
	private Display[] displays ; 
	private final Thread observe = new Thread(this);
	private final int listen_port = 4446 ; 
	private final Object Refresh_lock  = new Object();
	private Date last_time_refresh = new Date() ;
	
	//public int counter_feed=0;

	
	//Constructor
	public void init(){
		(new Thread(new Runnable(){public void run() {	ForumServer.main(new String[]{});}})).start();
		this.connect();   
		this.fake_information();	
		getContentPane().setLayout(new BorderLayout());		
		
		Rectangle maxBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().
		getMaximumWindowBounds();		 
		this.setSize(maxBounds.width, maxBounds.height);
		
		this.displays = new Display[]{
				new MainDispaly(this),
				new SubForumDispaly(this)
				};
		this.curDisplay = this.displays[0];
		
		left_toolbar= this.curDisplay.getLeft_toolbar();
		this.curSlide=left_toolbar.getSlide(0);
		up_toolbar=this.curDisplay.getUp_toolbar();
		this.down_label=this.curDisplay.getDown_label() ; 
		this.right_toolbar = this.curDisplay.getRightToolBar();
		
		this.getContentPane().add(up_toolbar,BorderLayout.NORTH);		
		JScrollPane scroller=new JScrollPane(left_toolbar);
		this.getContentPane().add(scroller,BorderLayout.WEST);
		this.getContentPane().add(curSlide,BorderLayout.CENTER);	
		this.getContentPane().add(down_label ,BorderLayout.PAGE_END);
		this.getContentPane().add(right_toolbar , BorderLayout.LINE_END);
		this.setFocusable(true);
		this.requestFocusInWindow();		
		
		 setVisible(true);	
		 
		 //Refresh containers
		 this.Refresh();		
	}

	private void connect() {
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 4444); 
        clientHandler = new ClientHandler(connectionHandler);
        this.observe.start();
        this.clientHandler.observe(this.listen_port  );  

       	
	}

	//getters and setter
	public LeftToolBar getLeft_toolbar(){return this.left_toolbar;}
	public Slide getCurSlide(){return this.curSlide;}	
	public UpToolBar getUpToolBar(){return this.up_toolbar;}
	
	//methods
	public void setCurSlide(int i){
		int N = this.left_toolbar.getSizeOfSlides();
		if(i<0||i>=N)throw new RuntimeException("the index to setCurSlide is out of bound");		
		this.curSlide=this.left_toolbar.getSlide(i);			
		// updating the tool bar  panel of the "current slide is"		
		 this.up_toolbar.changeToSlide(this.curSlide.getName());
		 //Refresh containers
		this.Refresh();		
	}
	
	//Refresh this window
	public void Refresh(){		
		synchronized(Refresh_lock){
		 	this.getContentPane().removeAll();
			this.curDisplay.Refresh();
			this.curSlide=this.left_toolbar.getSlide(this.curSlide.getIndex());			
			this.getContentPane().add(up_toolbar,BorderLayout.NORTH);
			JScrollPane scroller=new JScrollPane(left_toolbar);
			this.getContentPane().add(scroller,BorderLayout.WEST);
			this.getContentPane().add(curSlide,BorderLayout.CENTER);
			this.getContentPane().add(down_label ,BorderLayout.PAGE_END);
			this.getContentPane().add(right_toolbar , BorderLayout.LINE_END);

			this.invalidate();
			this.validate();
			this.repaint();		
			this.last_time_refresh = new Date();
		}
	}

	public void switch_display(int index_deisplay) {
		if(index_deisplay >= this.displays.length || index_deisplay <0)
			return ;

		this.curDisplay = this.displays[index_deisplay];	
		this.left_toolbar= this.curDisplay.getLeft_toolbar();
		this.curSlide=left_toolbar.getSlide(0);
		this.up_toolbar=this.curDisplay.getUp_toolbar();
		this.down_label=this.curDisplay.getDown_label() ;
		this.right_toolbar =this.curDisplay.getRightToolBar();
		this.Refresh();
	}

	public IUserHandler getClientHandler() {
		return clientHandler;
	}

/*--------------------------------------------------------------------------------------------------------------------------------*/
	public void run() {
        BufferedReader in = null;
        try {
       	 @SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(this.listen_port);
            Socket clientSocket = serverSocket.accept();
			 in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
        String inputLine;

        try {
			while ((inputLine = in.readLine()) != null) {         
				if(inputLine.equals("Close"))
					break;
				if(!inputLine.trim().equals("")){
					this.process_feed(inputLine);					
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "The Server Has Collosed! \n stytem closing...");
			System.exit(0);
		}
	}
	
	private void process_feed(String inputLine) {

			
        String[] msg;
        String feed ;
		boolean need_to_refresh = true	;	
        msg = inputLine.split(";");
		feed = "";
		for(int i=2 ; i<msg.length ; i++)
			if(i==2)
				feed = msg[i];
			else
				feed = feed + "\n" +  msg[i];
		synchronized(Refresh_lock){
			try{
				String[] day = msg[0].split("/");
				String[] time= msg[1].split(":");

				@SuppressWarnings("deprecation")
				Date d = new  Date(Integer.decode(day[2])+100 ,Integer.decode(day[0])-1 , Integer.decode(day[1]) ,
						Integer.decode(time[0])  ,Integer.decode(time[1]),Integer.decode(time[2]) );
				if(this.last_time_refresh.compareTo(d) > 0){
					need_to_refresh =false;
				}
			}
			catch(Exception e){
			}
		}
		
		if(inputLine.equals("Secret")){
			this.right_toolbar.add_new_feed("Secret" ,need_to_refresh );
			return;
		}
		this.right_toolbar.add_new_feed(feed ,need_to_refresh );				
	}
/*-----------------------------------------------------------------------------------------------------------------------------------*/
	


	private void fake_information() {
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		int deep = 2  , numberReplay = 2 , number_subForums = 1 , number_theards =2; 

		String[] subforums_names = {"Music" , "Sport" , "Life" , "Work"};
		
		//								, "Math" ,"Food" , "Computer", "Tree" , "Node"  , "Love" , "Relationship" ,
		//									"Health " , "Family", "Army" , "Support","Spirituality" , "Culture" , "News","Big Brother", "Traffic lights" ,"Ashdod" , "Mermaid"};
		
		clientHandler.login("oren", "pass");
		
		for(int i=0 ; i<subforums_names.length ; i++ ){
			clientHandler.createSubForum(subforums_names[i], new String[]{});
		}
		
		//get sub_forum list 
		Vector<ISubForum>  subForums = getClientHandler().show_sub_forum();
		ISubForum sub;
		Vector<IPost> threads ;
		for(int i = 0 ; i < number_subForums ; i++ ){
			System.out.println("i:"+i+">>>>fake_information");
			sub = subForums.get(i);			
			for(int j = 0 ; j < number_theards ; j++   ){
				System.out.println(">>>>>>>>fake_information");

				clientHandler.create_thread("Threads header number: "+j+" of subforum: "+ sub.get_theme(),"body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post\n"
																						+ "body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post"
																						, sub.get_theme());
			}
			threads = clientHandler.get_theardsSubForum(sub.get_theme());
			for(int j = 0 ; j < threads.size() ; j++){
				System.out.println("<<<<<<<<<<<<<<<fake_information");
				creatRecReplayPost_fake_information(threads.get(j) , numberReplay , deep );
			}
		}
		
		System.out.println("==================================================================================================================================================");
		subForums = getClientHandler().show_sub_forum();
		for(int i = 0 ; i < number_subForums ; i++ ){
			sub = subForums.get(i);		
			System.out.println(sub);
		}
		System.out.println("==================================================================================================================================================");
		clientHandler.logout();

	}

	private void creatRecReplayPost_fake_information(IPost parent , int numberReplay, int deep) {
		if(deep <=0 ) 
			return;
		System.out.println(">>>>>>>>>>>fake_information [ numberReplay: "+ numberReplay+" deep:"+deep+"]");

		for(int i = 0 ; i < numberReplay ; i++)
			clientHandler.createReplyPost("ReplayPost  header number: "+i+ " |DEEP: "+deep +". ","body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post\n"
																							+ "body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post body of post"
																							, parent);
		Vector<IPost> posts ;
		posts = clientHandler.get_replyPosts(parent); 
		for(int j = 0 ; j < posts.size() ; j++){
			System.out.println(">>>>>>>>>>>fake_information [ numberReplay: "+ numberReplay+" deep:"+deep+", j = "+j+"]");
			creatRecReplayPost_fake_information(posts.get(j) , numberReplay , deep -1 );
		}
	}
	
	
}
