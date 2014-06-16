package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component;

import javax.swing.tree.DefaultMutableTreeNode;

import Domain_layer.ForumComponent.IPost;

@SuppressWarnings("serial")
public class NodePost extends DefaultMutableTreeNode{
	private NodePost parent;
	private IPost post ;
	public NodePost(NodePost parent,  IPost post){
		super(post.get_header());
		this.post = post ; 	
		this.parent = parent; 
	}
	public IPost get_Post(){
		return this.post;
	}
	public  NodePost get_Parent(){
		return this.parent;
	}
}
