package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.TreeView_Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component.NodePost;


public class add_replayPost implements ActionListener {
	private DesignWindow designWindow ;
	private  JTree tree ;

	public add_replayPost(DesignWindow designWindow , JTree tree) {
		this.designWindow =designWindow;
		this.tree = tree;
	}	
	public void actionPerformed(ActionEvent e) {	
		NodePost np = (NodePost)tree.getLastSelectedPathComponent() ;
		 if(np == null){
			JOptionPane.showMessageDialog(this.designWindow, "Please select Message first.");
			return;
		 }
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
	     int  result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter The ReplayPost properties", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
	      if (result == JOptionPane.OK_OPTION) {
	    	  
	    	  if(!theme.getText().trim().equals("")){	
	    		 if(this.designWindow.getClientHandler().createReplyPost(theme.getText().trim(), body.getText().trim(), np.get_Post())){
					//this.designWindow.getLeft_toolbar().setNeedToRefresh();
					this.designWindow.Refresh();	    			 
					JOptionPane.showMessageDialog(this.designWindow, "The Adding New ReplayPost process was successful ");
	    		 }
	    		 else
	    			 JOptionPane.showMessageDialog(this.designWindow, "The Adding New ReplayPost process failed, Possible Causes:\n"
							+ "1. You have no authority to perform this action.\n"
							+ "2. The Post has been removed.\n"
							);
	    	  }        
	    	  else{
	    		  JOptionPane.showMessageDialog(null, "Please Enter A Subject of thread");
	    	  }
	      }		
	}

}
