package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Slide;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.Slide;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.PostView;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.TreeView;
import Domain_layer.ForumComponent.ISubForum;
/**
 *  This class represents a slide in the design Window. 
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public class SlideSubForum extends Slide  {	
	private Dimension minimumSize = new Dimension(200, 200);
	private DesignWindow designWindow;
	private TreeView treeView;
	private PostView postView;	
	public SlideSubForum(String name,int i, DesignWindow d, ISubForum subForum){	
		super(name ,i );		
		this.designWindow=d;
		this.setBackground(Color.WHITE);
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		
		postView = new PostView();
		JScrollPane postViewScroll = new JScrollPane(postView);
		treeView = new TreeView(subForum , postView , d);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(postViewScroll );
        postView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(250);
        //Add the split pane to this panel.
        add(splitPane); 
	}
	
	

	public DesignWindow getDeasignWindow(){return this.designWindow;}

	
}
