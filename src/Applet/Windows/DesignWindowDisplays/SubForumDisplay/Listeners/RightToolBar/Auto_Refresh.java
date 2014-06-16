package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.RightToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Applet.Windows.DesignWindowDisplays.SubForumDisplay.RightToolBar_SubForum;

public class Auto_Refresh implements ActionListener {
	private  RightToolBar_SubForum rightToolBar_SubForum ; 
	private boolean is_set;
	public Auto_Refresh(RightToolBar_SubForum rightToolBar_SubForum, boolean isSet) {
		this.rightToolBar_SubForum = rightToolBar_SubForum;
		this.is_set = isSet;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.is_set)
			this.rightToolBar_SubForum.set_Auto_Refresh();
		else
			this.rightToolBar_SubForum.reset_Auto_Refresh();
	}

}
