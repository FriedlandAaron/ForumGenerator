package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.SubForums_Actions;

import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DialogAddSubForum {
	private JTextField theme ;
	private  JTextArea moderators_names ;
	private boolean isRecive =false;

	public DialogAddSubForum(){
		 JTextField theme = new JTextField(30);  
		 JTextArea moderators_names = new  JTextArea(5,30);  
		 moderators_names.setLineWrap(true);
		 moderators_names.setWrapStyleWord(true);
		 
		 JScrollPane areaScrollPane = new JScrollPane(moderators_names);
		 areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// the panel that holds all the Properties of a text.
	     JPanel myPanel = new JPanel();
	     JLabel l ;
	     myPanel.setLayout(new BoxLayout(myPanel , BoxLayout.Y_AXIS));
	     l= new JLabel("Subject:");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     theme.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(theme);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     l=new JLabel("Moderators names separated by ',' : ");
	     l.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(l);
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     areaScrollPane.setAlignmentX(Box.LEFT_ALIGNMENT);
	     myPanel.add(areaScrollPane);				      	
	     myPanel.add(Box.createHorizontalStrut(10)); // a spacer
	     
	     
	 	//the dialog itself
	     boolean insert_theme = false;
	     int result;
	     while(!insert_theme){
	    	 result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter The SubForum properties", JOptionPane.OK_CANCEL_OPTION  ,JOptionPane.PLAIN_MESSAGE);
	      if (result == JOptionPane.OK_OPTION) {
	    	  if(!theme.getText().trim().equals("")){	
	    		  insert_theme=true;
	    		  this.theme=theme;
	    		  this.moderators_names=moderators_names;
	    		  this.isRecive=true;	    		 
	    	  }        
	    	  else{
	    		  JOptionPane.showMessageDialog(null, "Please Enter A Aubject of SubForum");
	    	  }
	      }	
	      else
	    	  break;
	     }
		
	}
	
	public boolean isRecived() {
		return  this.isRecive;
	}
	public String get_theme(){
		if(this.theme==null)
			return "";
		return this.theme.getText().trim();
	}
	public String[] get_moderators_names(){
		if(this.theme==null)
			return new String[]{};
		String [] mod =  this.moderators_names.getText().trim().split(",");
		String temp;
		Vector<String> moderators = new Vector<String>();
		for( int i = 0  ; i<mod.length ; i++ ){
			temp= mod[i].trim();
			if(!temp.equals(""))
				moderators.add(temp);						
		}
		String [] result  = new String[moderators.size()]; 
		for(int i = 0  ; i < result.length ; i++)
			result[i] = moderators.get(i);
		return  result; 
	}
}
