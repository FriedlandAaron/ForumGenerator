package Network_layer.FourmMail;

import java.io.*;

@SuppressWarnings("serial")
public class MailHandler   implements  java.io.Serializable {

    
        
	public static boolean send_massage(String mail_name ,String pass , String[] to, String subject, String body) {
		boolean b = false ; 
		try {
			Mail mail = new Mail(mail_name, pass );  	
			mail.open_Store();
			b = mail.send(to, subject, body);
			mail.close_Store();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}	
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
	    // default serialization 
	    oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
	    // default deserialization
	    ois.defaultReadObject();
	}
}









