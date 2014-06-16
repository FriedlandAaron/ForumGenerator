package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions.Moderators_Actions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.Icons.DiamondIcon;
import Domain_layer.ForumComponent.ISubForum;
import Domain_layer.FourmUser.Complaint;

public class Moderators_Actions implements ActionListener {

	private DesignWindow designWindow ;
	private ISubForum subForum; 

	public Moderators_Actions(DesignWindow designWindow, ISubForum subForum) {
		this.designWindow = designWindow ;
		this.subForum = subForum;
	}
	public void actionPerformed(ActionEvent e) {		
		//dialog for adding Component
		Object[] possibilities ={ "Add Complaint Moderator" , "Add Moderator","Remove Moderator" , "List Moderator complients"};		
		String s = (String)JOptionPane.showInputDialog(this.designWindow.getCurSlide(),"Choose Moderators Action to execution:\n","Moderators Actions",
		                    JOptionPane.PLAIN_MESSAGE, null,possibilities,"text");
		if(s!=null){
			if(s=="Add Complaint Moderator"){	 // adding Complaint Moderator
				addcomplaintModerator();
			}
			if(s=="Add Moderator"){ //Add Moderator
				addModerator(); 
			}
			if(s=="Remove Moderator"){ //Remove Moderator
				removeModerator(); 
			}
			if(s=="List Moderator complients"){//List Moderator complients
				get_userComplaint();				
			}
		} 
	}
	private void removeModerator() {
		 JComboBox<String> combo_list=new JComboBox<String>(this.designWindow.getClientHandler().Moderators_list(this.subForum.get_theme()).split(";"));		      
		 AutoCompleteDecorator.decorate(combo_list);
			 
			
		// the panel that holds all the Properties of a text.
		JPanel myPanel = new JPanel();
		   myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));				      	
		   myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		JLabel l = new JLabel("Choose and Remove moderator");
		l.setAlignmentX(Box.LEFT_ALIGNMENT);
		myPanel.add(l);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		combo_list.setAlignmentX(Box.LEFT_ALIGNMENT);
		myPanel.add(combo_list);				      	
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	   
		     
		//the dialog itself
		   
		int result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter Choose Moderator", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) { 
			String moderator =(String) combo_list.getSelectedItem();
			if(moderator!=null){
			moderator =moderator.trim();
			 if(this.designWindow.getClientHandler().removeModerator(subForum.get_theme(), moderator)){
					this.designWindow.Refresh();	    			 
					JOptionPane.showMessageDialog(this.designWindow, "The Remove of New Moderator process was successful ");
	    		 }
	    		 else
	    			 JOptionPane.showMessageDialog(this.designWindow, "The Remove of New  Moderator process failed, Possible Causes:\n"
							+ "1. You have no authority to perform this action.\n"
							+ "2. The Moderator submited is no longer a moderator in the sub forum .\n"
							);
			}
		}
	}
	private void addModerator() {
		 JTextField moderator = new JTextField(50);  
		
	     JPanel myPanel = new JPanel();
	     JLabel l;
	     myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));
	     
	     l =new JLabel("Moderator Name:");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     moderator.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(moderator);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer  
	     
	     	     
	 	//the dialog itself
	     int  result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter The Moderator Name", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
	      if (result == JOptionPane.OK_OPTION) {
	    	  if(!moderator.getText().trim().equals("")){	
	    		 if(this.designWindow.getClientHandler().addModerator(subForum.get_theme(), moderator.getText().trim())){
					this.designWindow.Refresh();	    			 
					JOptionPane.showMessageDialog(this.designWindow, "The Adding of New Moderator process was successful ");
	    		 }
	    		 else
	    			 JOptionPane.showMessageDialog(this.designWindow, "The Adding of New  Moderator process failed, Possible Causes:\n"
							+ "1. You have no authority to perform this action.\n"
							+ "2. The Moderator submited is no a member in the forum .\n"
							+ "3. The Moderator submited is allredy a moderator .\n"							
							);
	    	  }        
	    	  else{
	    		  JOptionPane.showMessageDialog(null, "Please Enter A Moderator Name ");
	    	  }
	      }			
	}
	private void addcomplaintModerator() {
		 JTextField moderator = new JTextField(50);  
		 JTextField theme = new JTextField(50);  
		 JTextArea body = new  JTextArea(20,50);  
		 body.setLineWrap(true);
		 body.setWrapStyleWord(true);
		 
		 JScrollPane areaScrollPane = new JScrollPane(body);
		 areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// the panel that holds all the Properties of a text.
	     JPanel myPanel = new JPanel();
	     JLabel l;
	     myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));
	     
	     l =new JLabel("Moderator Name:");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     moderator.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(moderator);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer  
	     
	     l =new JLabel("Subject:");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     theme.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(theme);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     
	     l =new JLabel("Body: ");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     areaScrollPane.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(areaScrollPane);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     
	     
	 	//the dialog itself
	     int  result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter The Complaint properties", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
	      if (result == JOptionPane.OK_OPTION) {
	    	  if(!(theme.getText().trim().equals("")||moderator.getText().trim().equals(""))){	
	    		 if(this.designWindow.getClientHandler().addcomplaintModerator(subForum, moderator.getText().trim(), theme.getText().trim() ,  body.getText().trim())){
					this.designWindow.Refresh();	    			 
					JOptionPane.showMessageDialog(this.designWindow, "The Adding of New Complaint Moderator process was successful ");
	    		 }
	    		 else
	    			 JOptionPane.showMessageDialog(this.designWindow, "The Adding of New Complaint Moderator process failed, Possible Causes:\n"
							+ "1. You have no authority to perform this action.\n"
							+ "2. The Moderator submited is no a modreator in the sub forum .\n"
							+ "3. You do not post a post in this subforum "
							);
	    	  }        
	    	  else{
	    		  JOptionPane.showMessageDialog(null, "Please Enter A Subject of Complient ,And Moderator Name ");
	    	  }
	      }		
	}
	
	private void get_userComplaint() {
		 JComboBox<String> combo_list=new JComboBox<String>(this.designWindow.getClientHandler().Moderators_list(this.subForum.get_theme()).split(";"));		      
		 AutoCompleteDecorator.decorate(combo_list);
			 
			
		// the panel that holds all the Properties of a text.
		JPanel myPanel = new JPanel();
		   myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));				      	
		   myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		JLabel l = new JLabel("Choose and Remove moderator");
		l.setAlignmentX(Box.LEFT_ALIGNMENT);
		myPanel.add(l);
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
		combo_list.setAlignmentX(Box.LEFT_ALIGNMENT);
		myPanel.add(combo_list);				      	
		myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	   
		     
		//the dialog itself
		   
		int result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter Choose Moderator", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) { 
			String moderator =(String) combo_list.getSelectedItem();
			if(moderator!=null){

			moderator =moderator.trim();
				Vector<Complaint> comp = this.designWindow.getClientHandler().get_userComplaint( moderator);
				 if(comp!= null){
						Dimension dimension = new Dimension(1000,600);
						Dimension B_dimension = new Dimension(250, 30);
						JTree tree = init_tree(comp) ; 
					 	JPanel jpanel = new JPanel();
						jpanel.setBackground(Color.white);
						jpanel.setLayout(new BoxLayout(jpanel , BoxLayout.Y_AXIS));
						
							JScrollPane treeView = new JScrollPane(tree);
							treeView.setAlignmentX(Box.CENTER_ALIGNMENT);		
							treeView.setMaximumSize(dimension);
							treeView.setMinimumSize(dimension);
							treeView.setPreferredSize(dimension);
						jpanel.add(treeView);
						
						 	Box box=Box.createHorizontalBox() ; 
						 	box.setBorder(new EmptyBorder(10, 10, 10,10) ); 	 	
					 	jpanel.add(box);
						
							JButton show_complient ;
							show_complient = new JButton("Show Complient" );
							show_complient.addActionListener(new show_complient(tree));
							show_complient.setAlignmentX(Box.CENTER_ALIGNMENT);		
							show_complient.setMaximumSize(B_dimension);
							show_complient.setMinimumSize(B_dimension);
							show_complient.setPreferredSize(B_dimension);
						jpanel.add(show_complient);					
				  	 	JOptionPane.showMessageDialog(this.designWindow, jpanel,"Complaints" ,JOptionPane.PLAIN_MESSAGE);  		    			 
		    		 }
		    		 else
		    			 JOptionPane.showMessageDialog(this.designWindow, "The List Moderator complients process failed, Possible Causes:\n"
								+ "1. You have no authority to perform this action.\n"
								+ "2. The Moderator submited is no longer a moderator in the sub forum .\n"
								);
			}
		}
	}
	private JTree init_tree(Vector<Complaint> comp) {
		 DefaultMutableTreeNode top =   new DefaultMutableTreeNode("Complaints:");
		    for(int index = 0 ; index<comp.size() ; index++)
		    	top.add(init_node(index ,  comp.get(index)));    
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
	private MutableTreeNode init_node(int index, Complaint complaint) {
		return new NodeComplaint(index , complaint);
	}

}
