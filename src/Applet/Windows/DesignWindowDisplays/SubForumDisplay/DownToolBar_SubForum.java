package Applet.Windows.DesignWindowDisplays.SubForumDisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.DownToolBar;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class DownToolBar_SubForum extends DownToolBar {
	private DesignWindow myDWindow; 

	public DownToolBar_SubForum(DesignWindow myDWindow){
		super();
		this.myDWindow = myDWindow;
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.orange);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));	
		this.init_paint();		
	}	
	

	public void paint(Graphics g){	
		super.paint(g);	
	}


	private void init_paint() {
		this.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel l= new JLabel(SubForumDispaly.string_to_html("UserName: "+this.myDWindow.getClientHandler().get_username() +
							"\n Status: "+this.myDWindow.getClientHandler().get_status()));
		l.setFont(new Font("Times New Roman", 1, 18));
		l.setBackground(Color.WHITE);

		c.weightx = 1;		
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(10,10,10,10);

		 c.gridwidth =1;
		 c.gridx=0;
		 c.gridy=0;
		this.add(l,c);
		
		
		l= new JLabel("Created by:        Oren Cohen _ Irina Komov _ Hadar Amram _ Hod Amram _ Aaron Freadland ");
		l.setFont(new Font("Times New Roman", 1, 18));
		l.setBackground(Color.WHITE);

		 c.weightx = 1;		
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(10,10,10,10);

		 c.gridwidth =1;
		 c.gridx=1;
		 c.gridy=0;
		this.add(l,c);		
	}	
	public void Refresh(){		
		 this.init_paint();
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
}
