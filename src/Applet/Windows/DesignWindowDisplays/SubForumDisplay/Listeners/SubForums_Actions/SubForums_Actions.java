package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Listeners.SubForums_Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Applet.Listeners.ChangeSlides;
import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.LeftToolBar;

public class SubForums_Actions implements ActionListener {

	private DesignWindow designWindow ;
	public SubForums_Actions(DesignWindow designWindow) {
		this.designWindow = designWindow ;
	}

	
	public void actionPerformed(ActionEvent e) {		
		//dialog for adding Component
		Object[] possibilities ={ "Search SubForum", "Add SubForum","Remove SubForum"};		
		String s = (String)JOptionPane.showInputDialog(this.designWindow.getCurSlide(),"Choose SubForums Action to execution:\n","SubForums Action",
		                    JOptionPane.PLAIN_MESSAGE, null,possibilities,"text");
		if(s!=null){
			if(s=="Add SubForum"){	 // adding image	
				
				DialogAddSubForum dialog=new DialogAddSubForum();
				if(dialog.isRecived()){
					if(this.designWindow.getClientHandler().createSubForum(dialog.get_theme(), dialog.get_moderators_names())){
						//this.designWindow.getLeft_toolbar().setNeedToRefresh();
						this.designWindow.Refresh();
						JOptionPane.showMessageDialog(this.designWindow, "The Adding SubForum process was successful ");
					}
					else 
						JOptionPane.showMessageDialog(this.designWindow, "The Adding SubForum process failed, Possible Causes:\n"
								+ "1. You have no authority to perform this action.\n"
								+ "2. SubForum theme is allready used.\n"
								+ "3. One of the names of the moderators does not exist."
								);
				}				
			}
			if(s=="Remove SubForum"){ // adding text
				String[] subforum_list = this.designWindow.getClientHandler().show_sub_forum_names(); 
				DialogRemoveSubForum dialog=new DialogRemoveSubForum(subforum_list);
				if(dialog.isRecived()){
					String sub = dialog.get_Search_result();
					if(this.designWindow.getClientHandler().deleteSubForum(sub)){
						//this.designWindow.getLeft_toolbar().setNeedToRefresh();
						this.designWindow.Refresh();
						JOptionPane.showMessageDialog(this.designWindow, "The Search SubForum process was successful ");						
					}
					else{
						JOptionPane.showMessageDialog(this.designWindow, "The Search SubForum process failed, Possible Causes:\n"
								+ "1. You have no authority to perform this action.\n"
								+ "2. SubForum you have selected have been allready remove.\n"
								+ "3. No  SubForum to remove.\n"
								);
					}
				}
			}
			if(s=="Search SubForum"){ //adding Drawing	
				String[] subforum_list = this.designWindow.getClientHandler().show_sub_forum_names(); 
				DialogSearchSubForum dialog=new DialogSearchSubForum(subforum_list);
				if(dialog.isRecived()){
					String sub = dialog.get_Search_result();
					LeftToolBar  left= this.designWindow.getLeft_toolbar(); 
					//left.setNeedToRefresh();
					this.designWindow.Refresh();
					int i = left.searchSlide(sub) ; 
					if(i>=0){
						(new ChangeSlides(i,left)).actionPerformed(null);
						JOptionPane.showMessageDialog(this.designWindow, "The Search SubForum process was successful ");
					}
					else{
						JOptionPane.showMessageDialog(this.designWindow, "The Search SubForum process failed, Possible Causes:\n"
								+ "1. SubForum you have Searched have been remove.\n"
								+ "2. No  SubForum to remove.\n"
								);
					}
				}
			}
		} 
	}

}
