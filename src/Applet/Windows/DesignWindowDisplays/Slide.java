package Applet.Windows.DesignWindowDisplays;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * This abstract class represents a general Slide in this software to Slide Show.
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public  class Slide extends JPanel {
		private  String name;
		private int index;
		public Slide(String name ,int index){
			this.name = name;
			this.index=index;
		}
		
		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		/**
		 * overriding the function Paint to paints all the components of this slide 
		 */
		public void paint(Graphics g){				
			super.paint(g);		
		}


		public int getIndex() {
			return index;
		}


		public void setIndex(int index) {
			this.index = index;
		}

	
}
