package Applet.Windows.DesignWindowDisplays;

import Applet.Windows.DesignWindow;


public abstract class Display {
	private DesignWindow myDWindow ; 
	private UpToolBar up_toolbar;
	private LeftToolBar left_toolbar;
	private DownToolBar down_label;
	private RightToolBar rightToolBar;
	


	public Display(DesignWindow myDWindow, UpToolBar up_toolbar, 
						LeftToolBar left_toolbar, DownToolBar down_label ,RightToolBar rightToolBar ) {
		super();
		this.myDWindow = myDWindow;
		this.up_toolbar = up_toolbar;
		this.left_toolbar = left_toolbar;
		this.down_label = down_label;
		this.rightToolBar = rightToolBar;
		
		this.up_toolbar.init_left_toolbar(this.left_toolbar);
	}
	
	
	public UpToolBar getUp_toolbar() {
		return up_toolbar;
	}
	public LeftToolBar getLeft_toolbar() {
		return left_toolbar;
	}
	public DownToolBar getDown_label() {
		return down_label;
	}
	public DesignWindow getMyDWindow() {
		return myDWindow;
	}
	public RightToolBar getRightToolBar() {
		return rightToolBar;
	}

	public void Refresh(){
		this.up_toolbar.Refresh();
		this.left_toolbar.Refresh(); 
		this.down_label.Refresh();
		this.rightToolBar.Refresh();
	}



}
