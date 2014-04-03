package Network_layer.FourmMail;

import java.util.Vector;

import Domain_layer.ForumComponent.Forum;

public interface IMailHandler {
	public void init_Mail_Handler();
	public void close_Mail_Handler();
	public boolean send_massage(String[] to, String subject, String body);
	public Vector<String> getEmailsSenders();
	
	public Mail get_mail();
	public Forum getForum();
}
