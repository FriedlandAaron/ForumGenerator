package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.SubForums_Actions;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
public class DialogRemoveSubForum {
	private String theme  ;
	private boolean isRecive =false;

	public DialogRemoveSubForum(String[] sub_forum_list){		
	    JComboBox<String> combo_list=new JComboBox<String>(sub_forum_list);		      
	    AutoCompleteDecorator.decorate(combo_list);
		 
		
		// the panel that holds all the Properties of a text.
	     JPanel myPanel = new JPanel();
	     myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     JLabel l = new JLabel("Choose and Remove SubForum");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     combo_list.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(combo_list);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
   
	     
	 	//the dialog itself
	     
	     int result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter Choose SubForum", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
	     if (result == JOptionPane.OK_OPTION) { 
	      this.theme = (String) combo_list.getSelectedItem();
	      this.isRecive = true;
	     }
		
	}
	
	public boolean isRecived() {
		return  this.isRecive;
	}
	public String get_Search_result(){
		if(this.theme==null)
			return "";
		return this.theme.trim();
	}
}
