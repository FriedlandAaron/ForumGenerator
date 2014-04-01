package FourmMail;

import java.util.Vector;

import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

public class MailHandler implements MessageCountListener {
	private Mail _mail;
	public MailHandler(String mail,String pass){
		_mail = new Mail(mail, pass , this);  	
	}

    public void init_Mail_Handler(){    	 
    	_mail.open_Store();
    	_mail.listen_to_Store();
    }
    public void close_Mail_Handler(){
    	_mail.close_Store();    	
    }
    
    
    
    public static void main(String args[]) throws Exception{
    	MailHandler my_mailHandler = new MailHandler("ForumGeneratorWSEP142@gmail.com", "MiraBalaban");
    	my_mailHandler.init_Mail_Handler();    	 
    }


	public void messagesAdded(MessageCountEvent arg0) {
    	Vector<String> msg =_mail.pop_MsgsfromInbox_SendersString();
    	System.out.println(msg);
	}

	public void messagesRemoved(MessageCountEvent arg0) {
		System.out.println("Removed_All_masseges");		
	}

	public void send_massage(String header, String body) {
		// TODO Auto-generated method stub
		
	}
}









