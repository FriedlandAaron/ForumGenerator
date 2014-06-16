package Applet.Windows.DesignWindowDisplays.MainDisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.UpToolBar;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class UpToolBar_Main extends  UpToolBar{
		private DesignWindow designWindow; 
	public UpToolBar_Main(DesignWindow designWindow ){
		super();
		this.designWindow = designWindow;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
		
		
		JLabel l= new JLabel( this.designWindow.getClientHandler().get_forum_name());
		l.setFont(new Font("Times New Roman", 1, 24));
		l.setBackground(Color.WHITE);

		c.weightx = 1;		
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(10,10,10,10);

		 c.gridwidth =1;
		 c.gridx=0;
		 c.gridy=0;
		this.add(l,c);
		
	}
	
	public void init_label(String name){
		JLabel l ;
		GridBagConstraints c  =new GridBagConstraints();
		l= new JLabel(name);
		l.setFont(new Font("Times New Roman", 1, 14));
		l.setBackground(Color.WHITE);
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		c.weightx = 0;		
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(10,10,10,10);

		 c.gridwidth =1;
		 c.gridx=this.getComponentCount();
		 c.gridy=0;
		this.add(l,c);
	}
	/**
	 * update the label of cur slide in this toolBar to index i we get has a parm.
	 * @param i
	 */
	public void changeToSlide(String name){
		this.remove(this.getComponentCount()-1);
		GridBagConstraints c = new GridBagConstraints();		 
		//adding "The current slide key" button.
			JLabel l= new JLabel(name);	
			l.setFont(new Font("Times New Roman", 1, 14));
			l.setBackground(Color.WHITE);
			l.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			c.weightx = 0;		
			 c.fill = GridBagConstraints.HORIZONTAL;
			 c.insets = new Insets(10,10,10,10);

			 c.gridwidth =1;
			 c.gridx=this.getComponentCount();
			 c.gridy=0;
			this.add(l,c);	
	}
	public void Refresh(){		
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
}
