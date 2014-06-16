package Applet.Windows.DesignWindowDisplays;

import javax.swing.JPanel;

import Applet.Windows.DesignWindow;

@SuppressWarnings("serial")
public abstract class LeftToolBar  extends JPanel{
	public abstract DesignWindow getDesignWindow();
	public abstract Slide getSlide(int i);
	public abstract int getSizeOfSlides();
	public abstract void Refresh();

	public int searchSlide(String sub) {
		return -1;
	}

}
