package Applet.Windows.DesignWindowDisplays.MainDisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import Applet.Windows.DesignWindowDisplays.DownToolBar;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class DownToolBar_Main extends DownToolBar {
	
	public DownToolBar_Main(){
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));	
		
		JLabel l= new JLabel("Created by:        Oren Cohen _ Irina Komov _ Hadar Amram _ Hod Amram _ Aaron Freadland ");
		l.setFont(new Font("Times New Roman", 1, 18));
		c.weightx = 0;		
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth =1;
		c.gridx=1;
		c.gridy=0;
		this.add(l,c);
	}	
	public void Refresh(){		
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
}
