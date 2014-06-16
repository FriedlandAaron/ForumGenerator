package Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Applet.Windows.DesignWindow;

public class Login implements ActionListener {
	private DesignWindow designWindow ; 
	private JTextField username ; 
	private JTextField password ;
	
	public Login(DesignWindow designWindow, JTextField username, JTextField password) {
		this.designWindow =designWindow;
		this.username = username;
		this.password = password;
	}

	public void actionPerformed(ActionEvent e) {
		
		boolean log =this.designWindow.getClientHandler().login(this.username.getText().trim(),
													this.password.getText().trim()) ;
		if(log)
			this.designWindow.switch_display(1);
		else
			JOptionPane.showMessageDialog(this.designWindow, "Login failed, Possible Causes:\n"
					+ "1. Wrong Username\n"
					+ "2. Wrong Password\n"
					);;
	}

	
	
	

}
