package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Applet.Windows.DesignWindowDisplays.SubForumDisplay.RightToolBar_SubForum;

public class Refresh implements ActionListener {

	private RightToolBar_SubForum rightToolBar_SubForum ; 
	public Refresh(RightToolBar_SubForum rightToolBar_SubForum) {
		this.rightToolBar_SubForum= rightToolBar_SubForum ; 
	}

	public void actionPerformed(ActionEvent e) {
		this.rightToolBar_SubForum.getMyDWindow().Refresh();
	}

}
