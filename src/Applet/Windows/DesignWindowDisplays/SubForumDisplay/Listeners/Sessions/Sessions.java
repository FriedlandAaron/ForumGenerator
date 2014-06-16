package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.Sessions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.Icons.DiamondIcon;
import Domain_layer.FourmUser.Session.Session;
import Domain_layer.FourmUser.Session.Session_Logger_Entry;

public class Sessions implements ActionListener {
	private DesignWindow designWindow ; 
	private final Dimension dimension = new Dimension(1000,600);

	public Sessions(DesignWindow designWindow) {
		this.designWindow = designWindow ; 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(Color.white);
		Vector<Session> sessions  = this.designWindow.getClientHandler().get_sessions();
		JScrollPane treeView = new JScrollPane(init_tree(sessions));
		treeView.setMaximumSize(dimension);
		treeView.setMinimumSize(dimension);
		treeView.setPreferredSize(dimension);
  	 	JOptionPane.showMessageDialog(this.designWindow, treeView,"Sessions" ,JOptionPane.PLAIN_MESSAGE);  	 	


	}
	
	private JTree init_tree(Vector<Session> sessions) {
	    DefaultMutableTreeNode top =   new DefaultMutableTreeNode("Sessions:");
	    for(int index = 0 ; index<sessions.size() ; index++)
	    	top.add(init_node(index ,  sessions.get(index)));    
	    JTree tree = new JTree(top);
	    tree.setRootVisible(true); 	    
	    
	    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
	    //font
	    renderer.setFont(new Font( "Arial", Font.PLAIN, 18));
	    int rowHeight = tree.getRowHeight();
	    if (rowHeight <= 0) {
	      tree.setRowHeight(rowHeight - 1);
	    }
	    //icons
	    renderer.setLeafIcon(new DiamondIcon(Color.black));
	    renderer.setOpenIcon(new DiamondIcon(Color.BLUE));
	    renderer.setClosedIcon(new DiamondIcon(Color.black));
	    return tree;
	}

	private MutableTreeNode init_node(int index , Session s) {
		NodeSession top = new NodeSession(index , s);
		ArrayList<Session_Logger_Entry> entrys = s.getLogger().getEntrys();	 
		for(Session_Logger_Entry entry : entrys ){
			top.add(init_node(entry));		
		}
		return top;
	}
	private MutableTreeNode init_node(Session_Logger_Entry entry) {
		return new NodeSession(entry);
	}

}
