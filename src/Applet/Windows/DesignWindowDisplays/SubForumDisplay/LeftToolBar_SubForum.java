package Applet.Windows.DesignWindowDisplays.SubForumDisplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import Applet.Listeners.ChangeSlides;
import Applet.Windows.DesignWindow;
import Applet.Windows.DesignWindowDisplays.LeftToolBar;
import Applet.Windows.DesignWindowDisplays.Slide;
import Applet.Windows.DesignWindowDisplays.SubForumDisplay.Slide.SlideSubForum;
import Domain_layer.ForumComponent.ISubForum;
/**
 * This Class represent the Switch Slide Area in the design window. 
 * @author Hadar Amran & Hod Amran
 */
@SuppressWarnings("serial")
public class LeftToolBar_SubForum extends  LeftToolBar{
	private Vector<Slide> v;
	private DesignWindow myDWindow;
	public LeftToolBar_SubForum(DesignWindow d){
		super();
		GridBagLayout grid = new GridBagLayout() ;
		this.setLayout(grid);
		this.setBackground(Color.pink);
		this.myDWindow=d;
		this.init_paint();

	}	
	
	private void init_paint() {
		this.removeAll();
		
		GridBagConstraints c = new GridBagConstraints();		
		JLabel l= new JLabel("SubForums List:");
		l.setFont(new Font("Times New Roman", 1, 20));
		l.setBackground(Color.WHITE);
		c.anchor = GridBagConstraints.PAGE_START;
		c.weightx = 0;
		c.insets = new Insets(10,10,10,30);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth =1;
		c.gridx=0;
		c.gridy=0;
		
		this.add(l,c);
		
		JButton b;	
		//get sub_forum list 
		Vector<ISubForum>  subForums = myDWindow.getClientHandler().show_sub_forum();
		ISubForum sub;
		v=new Vector<Slide>();
		for(int i= 0 ; i< subForums.size() ; i++ ){
			sub = subForums.get(i);
			v.add(new SlideSubForum(sub.get_theme() , i, this.myDWindow , sub ));
			b= new JButton(sub.get_theme());
			b.addActionListener(new ChangeSlides(i,this));
			c.gridy=i+1;
			this.add(b,c);
			
		}
		if(v.size()==0)
			v.add(new Slide("", 0));
		
	}


	//getter
	public DesignWindow getDesignWindow(){return this.myDWindow;}
	public Slide getSlide(int i){
		Slide s ;
		try{
			if(i==v.size())
				s = v.lastElement();
			else
				s = v.get(i);
		}
		catch(Exception e){
			s=  new Slide("error", i);
		}
		return s;
	}	
	public int getSizeOfSlides(){return v.size();}
	

	public void Refresh(){		

		this.init_paint();			
		
		this.invalidate();
		this.validate();
		this.repaint();				
	}
	
	public int searchSlide(String sub) {
		for(int i=0 ; i<this.v.size() ; i++)
			if(this.v.get(i).getName().equals(sub))
				return i;
		return -1;
	}
}
