package Applet.Windows.DesignWindowDisplays;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class UpToolBar  extends JPanel{
	
	public abstract void changeToSlide(String name);
	public abstract void init_label(String name);
	public abstract void Refresh();
	public  void init_left_toolbar(LeftToolBar left_toolbar){} ;

}
