package Applet.Windows.DesignWindowDisplays.SubForumDisplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.RightToolBar;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar.Auto_Refresh;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar.Refresh;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar.Refresh_Button;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class RightToolBar_SubForum extends  RightToolBar implements ActionListener {

		private final Dimension dimension = new Dimension(250, 75);
		private final Dimension B_dimension = new Dimension(250, 30);
		private final Dimension C_dimension = new Dimension(250, 30);


		private JPanel news ;
		private boolean auto_refresh = false;  
		private final DesignWindow myDWindow;
		private Vector<String> feeds; 
		private Object feeds_lock = new Object();
		private Refresh_Button refresh;
		private JButton set_auto_refresh;
		private JButton reset_auto_refresh;

		private JCheckBoxMenuItem remindRef;
		private boolean need_to_refresh;



	public RightToolBar_SubForum(final DesignWindow myDWindow){
		
		//init
		this.myDWindow = myDWindow; 
		this.feeds =new Vector<String>();
		this.setBackground(Color.yellow);
		this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
		remindRef = new JCheckBoxMenuItem("Flashing When I Need To Refresh") ;
		remindRef.setAlignmentX(CENTER_ALIGNMENT);		
		remindRef.setMaximumSize(C_dimension);
		remindRef.setMinimumSize(C_dimension);
		remindRef.setPreferredSize(C_dimension);
		remindRef.setBorder(new EmptyBorder(5, 0, 5, 5));
		remindRef.setSelected(true);
		remindRef.addActionListener(this);
		
		
		set_auto_refresh = new JButton("Enable Auto Refresh" );
		set_auto_refresh.addActionListener(new Auto_Refresh(this ,true));
		set_auto_refresh.setAlignmentX(CENTER_ALIGNMENT);		
		set_auto_refresh.setMaximumSize(B_dimension);
		set_auto_refresh.setMinimumSize(B_dimension);
		set_auto_refresh.setPreferredSize(B_dimension);
		set_auto_refresh.setBorder(new EmptyBorder(5, 0, 5, 5));
		
		reset_auto_refresh = new JButton("Disable Auto Refresh" );
		reset_auto_refresh.addActionListener(new Auto_Refresh(this ,false));
		reset_auto_refresh.setAlignmentX(CENTER_ALIGNMENT);		
		reset_auto_refresh.setMaximumSize(B_dimension);
		reset_auto_refresh.setMinimumSize(B_dimension);
		reset_auto_refresh.setPreferredSize(B_dimension);
		reset_auto_refresh.setBorder(new EmptyBorder(5, 0, 5, 5));

		this.add(set_auto_refresh);

		//components
		
	 	Box box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 0, 10,10) ); 	 	
		this.add(box);
		
		refresh = new Refresh_Button("Refresh" ,this);
		refresh.setAlignmentX(CENTER_ALIGNMENT);	
		refresh.addActionListener(new Refresh(this ));
		refresh.setMaximumSize(B_dimension);
		refresh.setMinimumSize(B_dimension);
		refresh.setPreferredSize(B_dimension);
		refresh.setBorder(new EmptyBorder(10, 0, 10, 10));
		this.add(refresh);
		this.add(this.remindRef);

	 	box=Box.createHorizontalBox() ; 
		box.setBorder(new EmptyBorder(5, 0, 5, 5) ); 	 	
		this.add(box);
		JLabel l= new JLabel( "News Feed:");
		l.setFont(new Font("Times New Roman", 1, 24));
		l.setBackground(Color.WHITE);
		l.setAlignmentX(CENTER_ALIGNMENT);
		l.setBorder(new EmptyBorder(10, 0, 10, 10) );
		this.add(l);
		
		
		this.news = new JPanel();
		this.news.setBackground(Color.pink);
		this.news.setLayout(new BoxLayout(this.news , BoxLayout.Y_AXIS));

		JScrollPane scroll_new =new  JScrollPane(this.news);
		scroll_new.setAlignmentX(CENTER_ALIGNMENT);
		this.add(scroll_new);
	}	
	

	public void add_new_feed(String inputLine , boolean need_to_refresh) {
		synchronized(feeds_lock){
			if(this.feeds.size()==0){
				if(this.remindRef.getState())
					this.refresh.Start_flashing();
				
				this.need_to_refresh = true; 
			}
			this.feeds.add(inputLine);
		}
		if(!need_to_refresh)
			return;		
		if(this.auto_refresh ){
			this.myDWindow.Refresh();
		}
	}
	private void falsh_feeds() {
		synchronized(feeds_lock){
			Box box;
			JPanel jpanel ; 
			String last_feed ;
			Component [] components = this.news.getComponents();
			this.news.removeAll();
			while(this.feeds.size()>0){
				last_feed = this.feeds.lastElement();
				if(!last_feed.equals("Secret")){
					box=Box.createHorizontalBox() ; 
				 	box.setBorder(new EmptyBorder(5, 5, 5, 5) ); 	 	
					this.news.add(box);
					
					jpanel= new JPanel();
					jpanel.add(new JLabel(SubForumDispaly.string_to_html(last_feed)));
					jpanel.setAlignmentX(CENTER_ALIGNMENT);
					jpanel.setMaximumSize(dimension);
					jpanel.setMinimumSize(dimension);
					jpanel.setPreferredSize(dimension);
					jpanel.setBackground(Color.white);
					jpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
					this.news.add(jpanel);
				}				
				this.feeds.remove(this.feeds.size()-1);			
			}
			
			int iter = 0 ;
			while(this.news.getComponentCount()<81 && iter < components.length){
				this.news.add(components[iter]);
				iter++;
			}	
			this.refresh.Stop_flashing();
			this.need_to_refresh = false; 
		}
	}

	public void Refresh(){	
		 this.falsh_feeds();
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
	
	public DesignWindow getMyDWindow() {
		return myDWindow;
	}

	public void set_Auto_Refresh() {
		synchronized(feeds_lock){
			this.auto_refresh =true;
			this.remove(0);
			this.add(this.reset_auto_refresh , 0);
			this.refresh.setEnabled(false);
			this.remindRef.setEnabled(false);
		}
		this.myDWindow.Refresh();
	}
	public void reset_Auto_Refresh() {
		synchronized(feeds_lock){
			this.auto_refresh =false;
			this.remove(0);
			this.add(this.set_auto_refresh , 0);
			this.refresh.setEnabled(true);
			this.remindRef.setEnabled(true);
		}
		this.myDWindow.Refresh();
	}


	
	public void actionPerformed(ActionEvent e ) {
		synchronized(feeds_lock){
			 AbstractButton aButton = (AbstractButton) e.getSource();
		     boolean selected = aButton.getModel().isSelected();

		     if (selected) {
		    	 if( this.need_to_refresh)
					this.refresh.Start_flashing();
		     }
		     else{
	    		 this.refresh.Stop_flashing();
		     }
			this.invalidate();
			this.validate();
			this.repaint();

		}
		
	}


}
