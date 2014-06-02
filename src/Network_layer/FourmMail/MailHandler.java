package Network_layer.FourmMail;

import java.util.Vector;

import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import Domain_layer.ForumComponent.Forum;

@SuppressWarnings("serial")
public class MailHandler implements MessageCountListener , IMailHandler  , java.io.Serializable {
	private Mail _mail;
	private Forum forum;
	public MailHandler(String mail,String pass){
		_mail = new Mail(mail, pass , this);  	
	}

    public void init_Mail_Handler(){    	 
    	_mail.open_Store();
    	//_mail.listen_to_Store();
    }
    public void close_Mail_Handler(){
    	_mail.close_Store();    	
    }
    
        
    public static void main(String args[]) throws Exception{
    	MailHandler my_mailHandler = new MailHandler("ForumGeneratorWSEP142@gmail.com", "MiraBalaban");
    	my_mailHandler.init_Mail_Handler();    	 
    }


	public void messagesAdded(MessageCountEvent arg0) {
//		System.out.println("messagesAdded");
		
//    	Vector<String> msg =_mail.pop_MsgsfromInbox_SendersString();
//    	forum.checkValidationEmails(msg);
//    	System.out.println(msg);
	}

	public void messagesRemoved(MessageCountEvent arg0) {
//		System.out.println("Removed_All_masseges");		
	}

	public boolean send_massage(String[] to, String subject, String body) {
		try {
			
			return this._mail.send(to, subject, body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Vector<String> getEmailsSenders() {
		return _mail.pop_MsgsfromInbox_SendersString();
	}

	public Mail get_mail() {
		return _mail;
	}

	public Forum getForum() {
		return forum;
	}
	
	
}









