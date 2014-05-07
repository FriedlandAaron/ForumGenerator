package Network_layer.reactorClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;

import Network_layer.reactorServer.tokenizer.ForumMessage;

public class ConnectionHandler {
	private String _host;
	private short _port;
	private SocketChannel _socketChannel ;
	private final CharsetDecoder _decoder;
	private final CharsetEncoder _encoder; 
	protected Vector<ByteBuffer> _outData = new Vector<ByteBuffer>();
	
	public static String _outputString = "";

	
	private final static Charset charset = Charset.forName("UTF-8");

	public ConnectionHandler(String host, short port) {
		this._host = host;
		this._port = port;	
		this._decoder = ConnectionHandler.charset.newDecoder();
		this._encoder = ConnectionHandler.charset.newEncoder();
		
	}
	
	public boolean connect(){
		//Create a client socket.
        InetAddress address;

		try{
			_socketChannel = SocketChannel.open();
			address = InetAddress.getByName(this._host);
		    //Bind the client socket to the server socket.
			
			_socketChannel.connect(new InetSocketAddress(address, this._port));
			this._socketChannel.configureBlocking(true);
   		       
		}catch (UnknownHostException e1) {
			System.out.println("Error:ConnectionHandler  connect\n");
			return false;
		} catch (IOException e) {
			System.out.println("Error:ConnectionHandler  connect\n");
			return false;
		}           
           return true;
	}
	
	public void closeConnection() {
		try {
			this._socketChannel.close();
		} catch (IOException ignored) {
			ignored = null;
		}
	}
		
	
	public boolean sendForumMessage(ForumMessage forumMessage) {
		boolean result = false;
		try {
			result = sendFrameAscii(forumMessage , "\n");
		} catch (CharacterCodingException e) {
			return false;
		}
		return result;
	}
	
	public boolean sendFrameAscii(ForumMessage msg, String delimiter)  throws CharacterCodingException {
		String serializedForumMessage = "";

		// serialize the object
		try {
		    ByteArrayOutputStream bo = new ByteArrayOutputStream();
		    ObjectOutputStream so = new ObjectOutputStream(bo);
		    so.writeObject(msg);
		    so.flush();
		    //serializedForumMessage = bo.toString();
		    serializedForumMessage =new String(Base64.encodeBase64(bo.toByteArray()));
		} catch (Exception e) {
		    System.out.println(e);
		    System.exit(1);
		}
		  
		
		int size = serializedForumMessage.length();
		String size_str =  ConnectionHandler.intToEightHexaStr(size);
		String mes_String =size_str+serializedForumMessage;
	    StringBuilder sb = new StringBuilder(mes_String);    

	    
	    ByteBuffer bb = this._encoder.encode(CharBuffer.wrap(sb));
		return sendBytes(bb);
	}
	
	public boolean sendBytes( ByteBuffer buf) {
		try {
			this._socketChannel.write(buf);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	
	
	public ForumMessage getForumMessage() {
		ForumMessage result = null;
		try {
			result = getFrameAscii();
		} catch (CharacterCodingException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
		return result;
	}
	
	public ForumMessage getFrameAscii()  throws IOException {
		String serializedObject = "";

		//getting a  mssege
		//gets 8 char for size of string
		//-----------------------
		InputStream stream = this._socketChannel.socket().getInputStream();
		StringBuilder sb = new StringBuilder("") , sb_size =new StringBuilder("") ;
		int nextbyte , size;
        ByteBuffer bytes;
        CharBuffer chars;
        char nextchar;
        
        //get size of string
        for(int i=1 ; i<=8 ; i++){
        	nextbyte = stream.read();
			if(nextbyte == -1){
				System.out.println("Error getForumMessage : BufferedReader close ended\n");
				return null;
			}
			bytes = ByteBuffer.allocate(1);
			bytes.put((byte) nextbyte);
			chars = CharBuffer.allocate(1);
			bytes.flip();
			this._decoder.decode(bytes, chars, false);
			nextchar=chars.get(0);
			sb_size.append(nextchar);
        	
        }


        size = ConnectionHandler.EightHexaStrToint(sb_size.toString());

        //get string
		while(size>0){
			nextbyte = stream.read();
			if(nextbyte == -1){
				System.out.println("Error getForumMessage : BufferedReader close ended\n");
				return null;
			}
			bytes = ByteBuffer.allocate(1);
			bytes.put((byte) nextbyte);
			bytes.flip();
			chars = CharBuffer.allocate(1);			
			this._decoder.decode(bytes, chars, false);
			nextchar=chars.get(0);
			sb.append(nextchar);	
			size--;
		}
		serializedObject = sb.toString();		
		//-----------------------
		   
		 ForumMessage forumMessage = null;      
		 // deserialize the object
	      try {

	          byte b[] = Base64.decodeBase64(serializedObject);          
	          ByteArrayInputStream bi = new ByteArrayInputStream(b);
	          ObjectInputStream si = new ObjectInputStream(bi);
	          forumMessage = (ForumMessage) si.readObject();
	      } catch (Exception e) {
	          System.out.println(e);
	          System.exit(1);
	      }
	      return forumMessage;
	}	
	
	
	/*
	public static void main(String[] args){
		int size = 0;
		String s = intToEightHexaStr(size);
		System.out.println(s);
		System.out.println(EightHexaStrToint(s));		
	}
	*/
	public static String intToEightHexaStr(int size){
		String s = Integer.toHexString(size);
		while(s.length()<8){
			s="0"+s;
		}
		return s;		
	}
	
	public static int  EightHexaStrToint(String s){
		return  (int) Long.parseLong(s, 16);		
	}

	public static ConnectionHandler connect_server(String host, short port) {
        ConnectionHandler connectionHandler = new ConnectionHandler(host, port);
        if (!connectionHandler.connect()) {
            return null;
        }
        return connectionHandler;
	}
	
}
