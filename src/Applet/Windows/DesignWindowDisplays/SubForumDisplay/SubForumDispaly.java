package Applet.Windows.DesignWindowDisplays.SubForumDisplay;
import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.*;





public class SubForumDispaly extends Display {
	public SubForumDispaly(DesignWindow d) {
		super(d,
				new UpToolBar_SubForum(d ) ,
				new LeftToolBar_SubForum(d) ,
				new DownToolBar_SubForum(d) ,
				new RightToolBar_SubForum(d));
		this.getUp_toolbar().init_label(this.getLeft_toolbar().getSlide(0).getName());

	}
	
	public static String string_to_html(String string) {
		string =string.replaceAll("\n", "<br>").replaceAll("\t","&nbsp;") ;
		String html = "<html><body>"+string+"</body></html>";
		return html; 
	}
}
