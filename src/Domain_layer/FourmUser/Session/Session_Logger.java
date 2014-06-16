package Domain_layer.FourmUser.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Session_Logger   implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ArrayList<Session_Logger_Entry> entrys;
	public Session_Logger(){
		entrys = new ArrayList<Session_Logger_Entry>();
	}
	public void addentry(String commend , Date date) {
		Session_Logger_Entry a = new Session_Logger_Entry( commend ,date );
		entrys.add(a);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t<Sassion_Logger>\n");
		Iterator<Session_Logger_Entry> i = entrys.iterator();
		while(i.hasNext())
			sb.append("\t> ").append(i.next().toString()).append("\n");
		sb.append("\t<Sassion_Logger>");
		return sb.toString();
	}
	public ArrayList<Session_Logger_Entry> getEntrys() {
		return entrys;
	}
	
}
