package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.Sessions;

import javax.swing.tree.DefaultMutableTreeNode;

import Domain_layer.FourmUser.Session.Session;
import Domain_layer.FourmUser.Session.Session_Logger_Entry;


public class NodeSession extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeSession(int index, Session s) {
		super(String.format("Session "+index +"; start date: %tD %<tT ; end date: %tD %<tT ",s.getStart() , s.getEnd()));
	}

	public NodeSession(Session_Logger_Entry entry) {
		super(String.format("Action: "+entry.getCommend() + " ; date: %tD %<tT ",entry.getDate()));
	}
}
