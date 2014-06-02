package Network_layer.FourmMail;

import java.util.Date;
import java.util.Properties;
import java.util.Vector;
 
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

@SuppressWarnings("serial")
public class Mail extends javax.mail.Authenticator implements IMail  , java.io.Serializable  {
	private String _user;
	private String _pass;
 
	private String _from;
 
	private String _port;
	private String _sport;
 
	private String _host;
	private boolean _auth;
 
	private boolean _debuggable;
 
	private Multipart _multipart;
	
	private Session _session;
	private IMAPStore   _imapStore ;
	private IMAPFolder  _folder;
	private Thread _listening_emails;
	private MailHandler _mailHandler;
	private boolean _isclose;
	public Mail()
	 {
		 _host = "smtp.gmail.com"; // default smtp server
		 _port = "465"; // default smtp port
		 _sport = "465"; // default socketfactory port
		 
		_user = ""; // username
		 _pass = ""; // password
		 _from = ""; // email sent from
		 
		_debuggable = false; // debug mode on or off - default off
		 _auth = true; // smtp authentication - default on
		 
		_multipart = new MimeMultipart();
		_imapStore=null;
		_session=null;
		_folder = null;
		
		_listening_emails = null ;
		// There is something wrong with MailCap, javamail can not find a
		 // handler for the multipart/mixed part, so this bit needs to be added.
		 MailcapCommandMap mc = (MailcapCommandMap) CommandMap
		 .getDefaultCommandMap();
		 mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		 mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		 mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		 mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		 mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		 CommandMap.setDefaultCommandMap(mc);
	 }

 
	public Mail(String user, String pass ,  MailHandler mailHandler)
	 {
		 this();		 
		_mailHandler=mailHandler;
		_user = user;
		_pass = pass;
		_from = user;
		this._isclose = false;
	 }

	public void open_Store(){
		try {
			 // open email and get inbox
			 Properties props =_setProperties();
			 props.put("mail.smtp.host", _host);
			 
			 props.setProperty("mail.store.protocol", "imaps");
			 props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 props.setProperty("mail.imap.socketFactory.fallback", "false");
			
			 
			// open session
			_session = Session.getInstance(props, this);
			_imapStore =(IMAPStore) _session.getStore("imaps");			
			_imapStore.connect("imap.gmail.com", _user, _pass);
			
			 // get inbox folder
			_folder = (IMAPFolder) _imapStore.getFolder("inbox");
			_folder.open(Folder.READ_WRITE);
			if(_mailHandler!=null){
				_folder.addMessageCountListener(this._mailHandler); 
			}
			this._isclose = true;
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void close_Store(){
			this._isclose = false;
			try {
				if(_listening_emails!=null){
					_listening_emails.interrupt();
					_listening_emails=null;
				}
				if(_folder!=null){
					_folder.close(true);
					_folder=null;
				}

				if(_imapStore!=null){
					_imapStore.close();
					_imapStore=null;
				}

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}
 
	public boolean send(String[] _to,String _subject,String _body) throws Exception
	 {		 
		if(_folder==null || _session == null || _imapStore==null)
			return false;
		if (!_user.equals("") && !_pass.equals("") && _to.length > 0
		 && !_from.equals("") && !_subject.equals("")
		 && !_body.equals(""))
		 {			
			MimeMessage msg = new MimeMessage(_session);
			msg.setFrom(new InternetAddress(_from));
			InternetAddress[] addressTo = new InternetAddress[_to.length];
			 for (int i = 0; i < _to.length; i++)			 
				 addressTo[i] = new InternetAddress(_to[i]);
			 
			 msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);			 
			 msg.setSubject(_subject);
			 msg.setSentDate(new Date());			 
			// setup message body
			 BodyPart messageBodyPart = new MimeBodyPart();
			 messageBodyPart.setText(_body);
			 _multipart.addBodyPart(messageBodyPart);			 
			// Put parts in message
			 msg.setContent(_multipart);			 
			// send email
			 Transport.send(msg);
			 
			return true;
		 } else
		 {
			 return false;
		 }
	 } 


	public Vector<String> pop_MsgsfromInbox_SendersString()
	 {
		 Vector<String> senders = new  Vector<String>();
		 if(_folder==null || _session == null || _imapStore==null)
			 return null;
		 
		 try { 		 
			 
			 if (_folder.isOpen())
			 {
				 // get messages
				 
				 Message[] msg = _folder.getMessages();
				 
			    for (int i = 0; i < msg.length; i++){
			    	 // senders
					 Address[] senders_add = msg[i].getFrom();
					 for(Address addr:senders_add)
						 senders.add((new InternetAddress(addr.toString())).getAddress());			
			    }
				 Flags deleted = new Flags(Flags.Flag.DELETED);
				 _folder.setFlags(msg, deleted, true);	
				 _folder.expunge();				 
								
			 }
		 } catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		 return senders;		 
	 }
 

	@Override
	 public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(_user, _pass);
	 }
	 public void set_from(String _from) {
		 this._from = _from;
	 }
	 private Properties _setProperties() {
		 Properties props = new Properties(); 
		 props.put("mail.smtp.host", _host);
	
		 if (_debuggable) {
			 props.put("mail.debug", "true");
		 }
	 
		 if (_auth)
		 {
			 props.put("mail.smtp.auth", "true");
		 }
	 
		 props.put("mail.smtp.port", _port);
		 props.put("mail.smtp.socketFactory.port", _sport);
		 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 props.put("mail.smtp.socketFactory.fallback", "false");	 
		 return props;
 	}

	public void listen_to_Store() {
		
		 if(_folder==null || _session == null || _imapStore==null)
			 return;
		 _listening_emails = new Thread(new Runnable() {

	            public void run() {
	                try {
	                    while (_isclose) {
	                        _folder.idle(false);
	                    }
	                } catch (MessagingException ex) {
	                    //Handling exception goes here
	                }
	            }
	        });
		 _listening_emails.start();
	}
}