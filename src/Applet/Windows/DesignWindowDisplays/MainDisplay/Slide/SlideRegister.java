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
import Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners.Register;
import Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners.Register_Email;
/**
 *  This class represents a slide in the design Window. 
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public class SlideRegister extends Slide{	
	private DesignWindow d;	
	
	public SlideRegister(String name,int i, DesignWindow d){	
		super(name , i);
		
		this.setName(name);
		this.d=d;
		this.setBackground(Color.WHITE);
		GridBagLayout grid = new GridBagLayout();
		this.setLayout(grid);
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel l= new JLabel("Enter Details:");
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
		
		JTextField username =new JTextField();
		 c.gridx=1;
		 c.gridy=1;			
		this.add(username,c);
		
		
		l= new JLabel("password:");
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBackground(Color.WHITE);
		 c.gridx=0;
		 c.gridy=2;			
		this.add(l,c);
		
		JPasswordField password =new JPasswordField( );
		 c.gridx=1;
		 c.gridy=2;			
		this.add(password,c);
		
		l= new JLabel("repeated password:");
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBackground(Color.WHITE);
		 c.gridx=0;
		 c.gridy=3;			
		this.add(l,c);
		
		JPasswordField repeated_password =new JPasswordField( );
		 c.gridx=1;
		 c.gridy=3;			
		this.add(repeated_password,c);
		
		l= new JLabel("email:");
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBackground(Color.WHITE);
		 c.gridx=0;
		 c.gridy=4;			
		this.add(l,c);
		
		JTextField email =new JTextField( );
		 c.gridx=1;
		 c.gridy=4;			
		this.add(email,c);
		
		JButton b = new JButton("register");
		b.addActionListener(new Register(d, username ,password ,repeated_password ,email));

		 c.gridx=0;
		 c.gridy=5;	
		this.add(b,c);
		
		b = new JButton("register by email");
		b.addActionListener(new Register_Email(this , d,username ,password ,repeated_password ,email));

		 c.gridx=0;
		 c.gridy=6;	
		this.add(b,c);
				
		b = new JButton("clear");
		b.addActionListener(new Clear(new JTextField[]{username ,password ,repeated_password ,email}));

		 c.gridx=1;
		 c.gridy=5;	
		this.add(b,c);
				
	}
	
	public DesignWindow getDeasignWindow(){return this.d;}
	
}
