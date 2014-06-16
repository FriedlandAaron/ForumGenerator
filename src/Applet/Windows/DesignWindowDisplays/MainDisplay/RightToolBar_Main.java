package Applet.Windows.DesignWindowDisplays.MainDisplay;

import Applet.Windows.DesignWindowDisplays.RightToolBar;
/**
 * 
 * @author hadaramran
 *
 */
@SuppressWarnings("serial")
public class RightToolBar_Main extends  RightToolBar{
	
	public void Refresh(){		
		 this.invalidate();
		 this.validate();
		 this.repaint();				
	}
}
