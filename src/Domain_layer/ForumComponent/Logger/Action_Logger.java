package Domain_layer.ForumComponent.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Action_Logger   implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<Action_Logger_Entry> entrys;
	public Action_Logger(){
		entrys = new ArrayList<Action_Logger_Entry>();
	}
	public void addentry(String username ,String commend , Date date) {
		Action_Logger_Entry a = new Action_Logger_Entry(username , commend ,date );
		entrys.add(a);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<Action_Logger>\n");
		Iterator<Action_Logger_Entry> i = entrys.iterator();
		while(i.hasNext())
			sb.append("> ").append(i.next().toString()).append("\n");
		sb.append("<Action_Logger>");
		return sb.toString();
	}
}
