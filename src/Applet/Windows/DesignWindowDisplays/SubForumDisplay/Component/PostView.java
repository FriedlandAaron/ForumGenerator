package Applet.Windows.DesignWindowDisplays.SubForumDisplay.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Applet.Windows.DesignWindowDisplays.SubForumDisplay.SubForumDispaly;
import Domain_layer.ForumComponent.IPost;
/**
 * This abstract class represents a general Slide in this software to Slide Show.
 * @author Hadar Amran & Hod Amran
 *
 */
@SuppressWarnings("serial")
public  class PostView extends JPanel {

		public PostView() {
			this.setBackground(Color.white);
			BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
			this.setLayout(box);
		}

		/**
		 * overriding the function Paint to paints all the components of this slide 
		 */
		public void paint(Graphics g){				
			super.paint(g);		
		}

		public void setPost(IPost post, String path) {
			this.removeAll();
			
			JLabel l;
			
			JPanel panel_mes = new JPanel();
			panel_mes.setLayout( new BoxLayout(panel_mes, BoxLayout.Y_AXIS));
			l= new JLabel(SubForumDispaly.string_to_html("Subject:"));
			l.setFont(new Font("Times New Roman", Font.BOLD |Font.HANGING_BASELINE, 22));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 10, 10, 10) );
			panel_mes.add(l);
			
			l= new JLabel(SubForumDispaly.string_to_html(post.get_header()));
			l.setFont(new Font("Times New Roman", 1, 18));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 50, 10, 10) );
			panel_mes.add(l);
			
			l= new JLabel(SubForumDispaly.string_to_html("Body:"));
			l.setFont(new Font("Times New Roman", 1, 22));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 10, 10, 10) );
			panel_mes.add(l);
			
			l= new JLabel(SubForumDispaly.string_to_html(post.get_body()));
			l.setFont(new Font("Times New Roman", 1, 18));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 50, 10, 10) );
			panel_mes.add(l);
			
			l= new JLabel(SubForumDispaly.string_to_html("Author: "+post.get_author().get_username()+"\n"+"Date: "+post.get_date()));
			l.setFont(new Font("Times New Roman", 1, 16));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 10, 10, 10) );
			panel_mes.add(l);
			
			this.add(panel_mes);
			
			l= new JLabel(SubForumDispaly.string_to_html("Path:\n"+path));
			l.setFont(new Font("Times New Roman", 1, 14));
			l.setAlignmentX(LEFT_ALIGNMENT);
			l.setBorder(new EmptyBorder(10, 10, 10, 10) );
			this.add(l);
			
			this.repaint();
			this.revalidate();
		}

		
		
}
