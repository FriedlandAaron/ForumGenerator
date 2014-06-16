package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.NodePost;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.PostView;
import Domain_layer.ForumComponent.IPost;

public class PostSelection implements TreeSelectionListener {

	private  JTree tree ;
	private PostView postView;

	public PostSelection(JTree tree, PostView postView) {
		this.postView = postView;
		this.tree = tree;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		NodePost node = (NodePost)tree.getLastSelectedPathComponent();
		if (node == null) return;
		IPost post =node.get_Post();		
		String path = path(node);
		 node = (NodePost)tree.getLastSelectedPathComponent();

		postView.setPost(post, path);
	}

	private String path(NodePost node) {
		Vector<NodePost> nodes =  getPath(node);
		NodePost nodeInfo =  nodes.get(0);		
		StringBuilder sb = new StringBuilder("|> "+nodeInfo.get_Post().get_header().trim());
		for( int i=1 ; i< nodes.size() ; i++ ){
			sb.append("\n");
			for(int j = 1 ; j<= i ; j++)
				sb.append("\t");
			nodeInfo =  nodes.get(i);
			sb.append("|> ").append(nodeInfo.get_Post().get_header().trim());
		}
		 
		return sb.toString();
	}

	private Vector<NodePost> getPath(NodePost node) {
		Vector<NodePost> nodes = new Vector<NodePost>();
		    if (node != null) {
		      nodes.add(node);
		      node = node.get_Parent();
		      while (node != null) {
		        nodes.add(0, node);
		        node = node.get_Parent();
		      }
		    }
		    return nodes;
	}



}
