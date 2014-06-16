package Applet.Windows.DesignWindowDisplays.MainDisplay;
import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.*;





public class MainDispaly extends Display {
	public MainDispaly(DesignWindow d) {
		super(d,
				new UpToolBar_Main(d) ,
				new LeftToolBar_Main(d) ,
				new DownToolBar_Main() ,
				new RightToolBar_Main());
		this.getUp_toolbar().init_label(this.getLeft_toolbar().getSlide(0).getName());
	}
}
