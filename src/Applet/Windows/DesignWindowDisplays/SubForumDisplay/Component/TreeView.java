package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.Icons.DiamondIcon;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.PostSelection;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.add_replayPost;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.add_thread;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.remove_Post;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.Moderators_Actions.Moderators_Actions;
import Domain_layer.ForumComponent.IPost;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.User.Status;
/**
 * This abstract class represents a general Slide in this software to Slide Show.
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public  class TreeView extends JPanel {

	private ISubForum subForum;
	private DesignWindow designWindow;
	private PostView postView;
	public TreeView(ISubForum subForum, PostView postView  ,DesignWindow designWindow) {
		this.designWindow = designWindow;
		this.postView = postView;
		this.setBackground(Color.orange);
		
		BoxLayout box_lay = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box_lay);
		this.subForum = subForum;
		
		this.init_TreeView();		
	}
	private void init_TreeView() {
		this.removeAll();
		JLabel l;
		JButton b;
		JPanel sub_panel =new JPanel();
		sub_panel.setLayout(new BoxLayout(sub_panel, BoxLayout.X_AXIS));
		sub_panel.setBackground(this.getBackground());
		
		JTree tree = init_tree(postView);
		
		l= new JLabel(subForum.get_theme());
		l.setFont(new Font("Times New Roman", 1, 24));
		l.setBorder(new EmptyBorder(10, 20, 10, 0) );
		sub_panel.add(l);	
		
	 	Box box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); 
		sub_panel.add(box);
		
		b= new JButton("Add New Thread");
		b.addActionListener(new add_thread(designWindow , subForum));
		sub_panel.add(b);			 

	 	box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); 	
	 	sub_panel.add(box);

		b= new JButton("Add New Reaplay Post");
		b.addActionListener(new add_replayPost(designWindow , tree));
		sub_panel.add(b);	
		
	 	box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); 	
	 	sub_panel.add(box);

		b= new JButton("Delete thread/post");
		b.addActionListener(new remove_Post(designWindow , tree));
		sub_panel.add(b);
		
	 	box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); 	
	 	sub_panel.add(box);
	 	
	 	
	 	b= new JButton("Moderators Actions");
		b.addActionListener(new Moderators_Actions(designWindow , subForum));
		sub_panel.add(b);
		
	 	box=Box.createHorizontalBox() ; 
	 	box.setBorder(new EmptyBorder(10, 10, 10, 10) ); 	
	 	sub_panel.add(box);
		
	 	String is_moderator = "Status in subForum: Member" ;
	 	if(Status.ADMINISTRATOR.has_permission(this.designWindow.getClientHandler().get_status())
	 			|| subForum.isModerator(this.designWindow.getClientHandler().get_username()))
	 		is_moderator ="Status in subForum: Moderator";
		l= new JLabel(is_moderator);
		l.setFont(new Font("Times New Roman", 1, 16));
		l.setBorder(new EmptyBorder(10, 20, 10, 0) );
		sub_panel.add(l);	
		
		sub_panel.setAlignmentX(LEFT_ALIGNMENT);
		this.add(sub_panel);		
		

		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBackground(Color.LIGHT_GRAY);
		treeView.setBorder(new EmptyBorder(20, 20, 20,20));
		treeView.setAlignmentX(LEFT_ALIGNMENT);
		this.add(treeView);	
		
	}
	/**
	 * overriding the function Paint to paints all the components of this slide 
	 */
	public void paint(Graphics g){				
		super.paint(g);		
	}		
	
	private JTree init_tree(PostView postView) {
	    DefaultMutableTreeNode top =   new DefaultMutableTreeNode("SubForum Threads:");
	    for(IPost thread : this.subForum.get_threads())
	    	top.add(init_node(null,thread));    
	    JTree tree = new JTree(top);
	    tree.setRootVisible(false); 
	    
	    
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

        tree.addTreeSelectionListener(new PostSelection(tree  , postView ));

	    return tree;
	}

	private MutableTreeNode init_node(NodePost parent ,IPost post ) {
		NodePost top = new NodePost(parent,post);
		    for(IPost son : post.get_replies())
		    	top.add(init_node(top ,son));   
		return top;
	}
	
	
	




}
