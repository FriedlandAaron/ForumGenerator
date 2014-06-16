package Applet.Windows.DesignWindowDisplays;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class RightToolBar  extends JPanel{
	
	public abstract void Refresh();
	public void add_new_feed(String inputLine, boolean need_to_refresh) {}

}
