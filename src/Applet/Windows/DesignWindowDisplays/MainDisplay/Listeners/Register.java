package Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Applet.Windows.DesignWindow;

public class Register implements ActionListener {
	private DesignWindow designWindow ; 
	private JTextField username ; 
	private JTextField password ;
	private JTextField password_repeated ;

	
	public Register(DesignWindow designWindow, JTextField username, JTextField password ,JTextField password_repeated ,JTextField email) {
		this.designWindow =designWindow;
		this.username = username;
		this.password = password;
		this.password_repeated = password_repeated;
	}

	public void actionPerformed(ActionEvent e) {
		String u =this.username.getText().trim();
		String p = this.password.getText().trim();
		String p_r =this.password_repeated.getText().trim();
		if(u.equals("")|| p.equals("") || p_r.equals("") ){
			JOptionPane.showMessageDialog(this.designWindow, "Please complete all fields");
			return;
		}
			
			
		boolean reg =this.designWindow.getClientHandler().register(u,p,p_r) ;
		if(reg){
			JOptionPane.showMessageDialog(this.designWindow, "The registration process was successful ");
			this.designWindow.setCurSlide(0);
		}
		else
			JOptionPane.showMessageDialog(this.designWindow, "Registration failed, Possible Causes:\n"
					+ "1. Username is used allready.\n"
					+ "2. Password is used allready.\n"
					+ "3. Password and repeated_Password unmatch.\n"
					+ "4. Email is worng "
					);
	}

}
