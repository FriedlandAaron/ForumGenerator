package Applet.Windows.DesignWindowDisplays.MainDisplay;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;

import Applet.Listeners.ChangeSlides;
import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.LeftToolBar;
import Applet.Windows.DesignWindowDisplays.Slide;
import Applet.Windows.DesignWindowDisplays.Listeners.Switch_display;
import Applet.Windows.DesignWindowDisplays.MainDisplay.Slide.SlideLogin;
import Applet.Windows.DesignWindowDisplays.MainDisplay.Slide.SlideRegister;
/**
 * This Class represent the Switch Slide Area in the design window. 
 * @author Hadar Amran & Hod Amran
 */
@SuppressWarnings("serial")
public class LeftToolBar_Main extends  LeftToolBar{
	private Vector<Slide> v;
	private DesignWindow myDWindow; 
	public LeftToolBar_Main(DesignWindow d){
		super();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();		
		this.setBackground(Color.lightGray);
		this.myDWindow=d;
		JButton b;		


		v=new Vector<Slide>();
		//Login
		v.add(new SlideLogin("Login",0,this.myDWindow));
		b= new JButton("Login");
		b.addActionListener(new ChangeSlides(0,this));
		c.weightx = 0;
		c.insets = new Insets(10,10,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth =1;
		c.gridx=0;
		c.gridy=0;
		this.add(b,c);	
		 	
		//Register
		v.add(new SlideRegister("Register",1,this.myDWindow));

			b= new JButton("Register");
			b.addActionListener(new ChangeSlides(1,this));
			c.weightx = 0;
			c.insets = new Insets(10,10,10,10);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth =1;
			c.gridx=0;
			c.gridy=1;
		 	this.add(b,c);	
		 	
		
		b= new JButton("Enter as a guest");
		b.addActionListener(new Switch_display( this.getDesignWindow() , 1));
		c.weightx = 0;
		c.insets = new Insets(10,10,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth =1;
		c.gridx=0;
		c.gridy=2;
		this.add(b,c);	
		 
	}	
	public void Refresh(){		
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
	
	//getter
	public DesignWindow getDesignWindow(){return this.myDWindow;}
	public Slide getSlide(int i){return v.get(i);}	
	public int getSizeOfSlides(){return v.size();}
}
