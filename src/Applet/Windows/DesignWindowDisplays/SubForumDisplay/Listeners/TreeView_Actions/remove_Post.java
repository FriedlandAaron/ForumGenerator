package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.NodePost;
import Domain_layer.ForumComponent.ISubForum;


public class remove_Post implements ActionListener {
	private DesignWindow designWindow ;
	private  JTree tree ;

	public remove_Post(DesignWindow designWindow , JTree tree) {
		this.designWindow =designWindow;
		this.tree = tree;
	}	
	public void actionPerformed(ActionEvent e) {	
		/*
		this.designWindow.right_toolbar.add_new_feed("hadaramran:"+this.designWindow.counter_feed+" ; hgjdhgsd ;\n\t jshvdjlhad ; jbhdjhsd");
		this.designWindow.counter_feed++;
		*/
		
		
		
		NodePost np = (NodePost)tree.getLastSelectedPathComponent() ;
		if(np == null){
			JOptionPane.showMessageDialog(this.designWindow, "Please select Message first.");
			return;
		}
	    if(this.designWindow.getClientHandler().deletePost(np.get_Post())){
	    	System.out.println("==================================================================================================================================================");
			Vector<ISubForum> subForums = this.designWindow.getClientHandler().show_sub_forum();
			ISubForum sub;
			for(int i = 0 ; i < subForums.size() ; i++ ){
				 sub = subForums.get(i);		
				System.out.println(sub);
			}
			System.out.println("==================================================================================================================================================");
	    	//this.designWindow.getLeft_toolbar().setNeedToRefresh();
			this.designWindow.Refresh();	    			 
			JOptionPane.showMessageDialog(this.designWindow, "The Removal Post process was successful ");
	    }
	    else
	    	JOptionPane.showMessageDialog(this.designWindow, "The Removal Post process failed, Possible Causes:\n"
			+ "1. You have no authority to perform this action.\n"
			+ "2. The Post has been removed allready.\n"
		);

	      	
	}

}
