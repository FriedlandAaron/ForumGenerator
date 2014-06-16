package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import Applet.Windows.DesignWindowDisplays.SubForumDisplay.RightToolBar_SubForum;

@SuppressWarnings("serial")
public class Refresh_Button extends JButton implements ActionListener{
	private boolean isflashing =false;
	private Color defult;
	private Timer TimerAtFocus = new Timer(200, this);
	private RightToolBar_SubForum rightToolBar_SubForum ; 

	public Refresh_Button(String name, RightToolBar_SubForum rightToolBar_SubForum){
		super(name);
		this.defult = this.getBackground();
		this.rightToolBar_SubForum = rightToolBar_SubForum;
	}

	public synchronized void Start_flashing() {
		if(!isflashing){
			this.setText("Need To Refresh");
			this.TimerAtFocus.start();
			this.isflashing = true;
			
		}
	}
	public synchronized void Stop_flashing() {
		if(isflashing){
			this.setText("Refresh");
			this.TimerAtFocus.stop();
			this.TimerAtFocus= new Timer(200, this);
			this.setBackground(this.defult);
			this.isflashing = false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.TimerAtFocus){
			Color cuurent = this.getBackground();
			if(cuurent == this.defult ){
				this.setBackground(Color.lightGray);
			}
			else
				this.setBackground(this.defult );			
			this.rightToolBar_SubForum.repaint();
		}
	}

}
