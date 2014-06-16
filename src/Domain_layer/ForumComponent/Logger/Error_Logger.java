package Domain_layer.ForumComponent.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Error_Logger  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<Error_Logger_Entry> entrys;
	public Error_Logger(){
		entrys = new ArrayList<Error_Logger_Entry>();
	}
	public void addentry(String username ,String commend , Date date , Exception e) {
		entrys.add(new Error_Logger_Entry(username , commend ,date  ,e));
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<Error_Logger>\n");
		Iterator<Error_Logger_Entry> i = entrys.iterator();
		while(i.hasNext())
			sb.append("> ").append(i.next().toString()).append("\n");
		sb.append("<Error_Logger>");
		return sb.toString();
	}
}
