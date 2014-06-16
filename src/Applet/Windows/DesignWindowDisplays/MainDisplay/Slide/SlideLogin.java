package Applet.Windows.DesignWindowDisplays.MainDisplay.Slide;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.Slide;
import Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners.Clear;
import Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners.Login;
/**
 *  This class represents a slide in the design Window. 
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public class SlideLogin extends Slide{	
	private DesignWindow d;	
	
	public SlideLogin(String name,int i, DesignWindow d){	
		super(name ,  i);
		
		this.setName(name);
		this.d=d;
		this.setBackground(Color.WHITE);
		GridBagLayout grid = new GridBagLayout();
		this.setLayout(grid);
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel l= new JLabel("Enter username and password:");
		l.setFont(new Font("Times New Roman", 1, 24));
		l.setBackground(Color.WHITE);
		 c.anchor =GridBagConstraints.CENTER;
		 c.fill = GridBagConstraints.HORIZONTAL;
		 c.insets = new Insets(10,10,10,10);
		 c.gridwidth =6;
		 c.gridx=0;
		 c.gridy=0;		
		 
		this.add(l,c);	
		
		l= new JLabel("username:");
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBackground(Color.WHITE);
		 c.weightx =1 ; 
		 c.gridwidth =1;

		 c.gridx=0;
		 c.gridy=1;			
		this.add(l,c);	
		
		
		l= new JLabel("password:");
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBackground(Color.WHITE);
		 c.gridx=0;
		 c.gridy=2;			
		this.add(l,c);
		
		JTextField username =new JTextField();
		 c.gridx=1;
		 c.gridy=1;			
		this.add(username,c);
		
		JPasswordField password =new JPasswordField();
		 c.gridx=1;
		 c.gridy=2;			
		this.add(password,c);
		
		JButton b = new JButton("login");
		b.addActionListener(new Login(d,username , password));
		 c.gridx=0;
		 c.gridy=3;	
		this.add(b,c);		
		
		b = new JButton("clear");
		b.addActionListener(new Clear(new JTextField[]{username ,password}));
		 c.gridx=1;
		 c.gridy=3;	
		this.add(b,c);
		
	}
	
	public DesignWindow getDeasignWindow(){return this.d;}
	
}
