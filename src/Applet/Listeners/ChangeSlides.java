package Applet.Listeners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Applet.Windows.DesignWindowDisplays.LeftToolBar;

/**
 * This class implements the JButtons of each JButton in SSArea, for  Change  between Slides.
 * @author Hadar Amran & Hod Amran. 
 *
 */
public class ChangeSlides implements ActionListener {
	private LeftToolBar mySSArea;
	private int myKeyInVec;
	
	public ChangeSlides(int key ,LeftToolBar s){
		if(s==null)
			throw new RuntimeException("the SSArea that was given to Remove sllids is null");		
		this.mySSArea=s;
		this.myKeyInVec=key;
	}	
	public void actionPerformed(ActionEvent e) {			
		this.mySSArea.getDesignWindow().setCurSlide(this.myKeyInVec);		
	}

}
