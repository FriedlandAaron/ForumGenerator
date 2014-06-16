package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.Moderators_Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTree;

public class show_complient implements ActionListener {

	private JTree tree;

	public show_complient(JTree tree) {
		this.tree = tree ; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			NodeComplaint node = (NodeComplaint)tree.getLastSelectedPathComponent();
			if(node == null)
				return;
			
			System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");

		}
		catch(Exception exeption){
			
		}
	}

}
