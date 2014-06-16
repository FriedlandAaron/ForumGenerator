package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.Moderators_Actions;

import javax.swing.tree.DefaultMutableTreeNode;

import Domain_layer.FourmUser.Complaint;

public class NodeComplaint  extends DefaultMutableTreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Complaint complaint; 

	public NodeComplaint(int index, Complaint complaint) {
		super("Complaint "+index +", Subject: "+complaint.get_body());
		this.complaint = complaint;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	


}
