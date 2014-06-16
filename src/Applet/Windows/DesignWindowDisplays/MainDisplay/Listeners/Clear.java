package Applet.Windows.DesignWindowDisplays.MainDisplay.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class Clear implements ActionListener {
	private JTextField [] fields ;
	public Clear(JTextField [] fields){
		this.fields = new JTextField[fields.length];
		for(int i = 0 ; i<this.fields.length ; i++)
			this.fields[i] = fields[i];		
	}
	public void actionPerformed(ActionEvent e) {
		for(int i = 0 ; i<this.fields.length ; i++)
			this.fields[i].setText("");
	}

}
