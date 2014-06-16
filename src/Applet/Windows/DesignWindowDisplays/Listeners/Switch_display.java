package Applet.Windows.DesignWindowDisplays.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Applet.Windows.DesignWindow;

public class Switch_display implements ActionListener {

	private int index_deisplay ; 
	private DesignWindow designWindow ; 
	public Switch_display(DesignWindow designWindow, int i ) {
		index_deisplay = i ; 
		this.designWindow = designWindow ; 
	}

	public void actionPerformed(ActionEvent e) {
		if(index_deisplay == 0)
			this.designWindow.getClientHandler().logout();
		this.designWindow.switch_display( this.index_deisplay);
		
	}

}
