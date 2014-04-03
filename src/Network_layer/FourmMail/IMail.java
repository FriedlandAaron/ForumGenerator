package Network_layer.FourmMail;

import java.util.Vector;

import javax.mail.PasswordAuthentication;

public interface IMail {
	public void open_Store();
	public void close_Store();
	public boolean send(String[] _to,String _subject,String _body) throws Exception;
	public Vector<String> pop_MsgsfromInbox_SendersString();
	public PasswordAuthentication getPasswordAuthentication();
	public void set_from(String _from);
	public void listen_to_Store();
}
