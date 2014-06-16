package Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Applet.Windows.DesignWindow;

public class Register_Email implements ActionListener {
	private DesignWindow designWindow ; 
	private JTextField username ; 
	private JTextField password ;
	private JTextField password_repeated ;
	private JTextField email;
	private Component  parent;

	public Register_Email(Component  parent ,DesignWindow designWindow, JTextField username, JTextField password ,JTextField password_repeated ,JTextField email) {
		this.parent = parent ;
		this.designWindow =designWindow;
		this.username = username;
		this.password = password;
		this.password_repeated = password_repeated;
		this.email = email;
	}

	public void actionPerformed(ActionEvent e) {
		
		String user =this.username.getText().trim();
		String pass = this.password.getText().trim();
		String pass_r =this.password_repeated.getText().trim();
		String email = this.email.getText().trim();
		if(user.equals("")|| pass.equals("") || pass_r.equals("") || email.equals("")){
			JOptionPane.showMessageDialog(parent, "Please complete all fields");
			return;
		}		
			
		boolean reg =this.designWindow.getClientHandler().register_Email(user,pass,pass_r ,email) ;
		if(!reg){
			JOptionPane.showMessageDialog(parent, "Registration failed, Possible Causes:\n"
					+ "1. Username is used allready.\n"
					+ "2. Password is used allready.\n"
					+ "3. Password and repeated_Password unmatch.\n"
					+ "4. Email is worng "
					);
			return ;
		}
		
		boolean submit_code = false;
		String code = null;
		while(!submit_code){
			 code = (String)JOptionPane.showInputDialog(
					parent,
                    "Submit the code sent to you via email:\n" ,
                    "Register  submit code",
                    JOptionPane.PLAIN_MESSAGE,
                    null,null , null);
			if(code == null)
				return ;
			submit_code = this.designWindow.getClientHandler().submit_code_registertion(user, code);
				 
		}		
		
		JOptionPane.showMessageDialog(this.designWindow, "The registration process was successful ");
		this.designWindow.setCurSlide(0);		
	}
}
