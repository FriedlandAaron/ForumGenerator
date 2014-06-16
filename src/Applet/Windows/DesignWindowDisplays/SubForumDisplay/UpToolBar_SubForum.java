package Applet.Windows.DesignWindowDisplays.SubForumDisplay;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.LeftToolBar;
import Applet.Windows.DesignWindowDisplays.UpToolBar;
import Applet.Windows.DesignWindowDisplays.Listeners.Switch_display;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.Sessions.Sessions;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.SubForums_Actions.SubForums_Actions;
import Domain_layer.FourmUser.User.Status;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class UpToolBar_SubForum extends  UpToolBar{
	private DesignWindow designWindow;
	private LeftToolBar left_toolbar; 
	public UpToolBar_SubForum(DesignWindow designWindow  ){
		super();
		this.designWindow = designWindow;
		
		this.setLayout(new BoxLayout(this , BoxLayout.X_AXIS));
		this.setBackground(Color.green);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));		 	
		init();
	}
	
	public void init(){
		this.removeAll();
		JLabel l= new JLabel( this.designWindow.getClientHandler().get_forum_name());
		l.setFont(new Font("Times New Roman", 1, 24));
		l.setBackground(Color.WHITE);
		l.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(l);
		
	 	Box box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); ; 		
		this.add(box);
		
		JButton b= new JButton("SubForums Actions");
		b.addActionListener(new SubForums_Actions(designWindow ));
	 	this.add(b);			 

	 	if(	 Status.MEMBER.has_permission(this.designWindow.getClientHandler().get_status())){
		 	box=Box.createHorizontalBox() ; 
		 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); ; 		
			this.add(box);

			b= new JButton("My Sessions");
			b.addActionListener(new Sessions(designWindow));
			this.add(b);
	 	}
	 	box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); ; 		
		this.add(box);

		b= new JButton("Logout");
		b.addActionListener(new Switch_display(designWindow,0 ));
		this.add(b);	
	}
	public void init_label(String name){
		
		
	}
	/**
	 * update the label of cur slide in this toolBar to index i we get has a parm.
	 * @param i
	 */
	public void changeToSlide(String name){
	}
	public  void init_left_toolbar(LeftToolBar left_toolbar){
		this.left_toolbar = left_toolbar;
	} ;

	public void Refresh(){	
		init();
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}

	public LeftToolBar getLeft_toolbar() {
		return left_toolbar;
	}
}
