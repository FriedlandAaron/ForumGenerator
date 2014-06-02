package Network_layer.reactorServer.tokenizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;

public class FixedSeparatorForumMessageTokenizer implements MessageTokenizer<ForumMessage> {

   private final StringBuffer _stringBuf = new StringBuffer();
   /**
	 * the fifo queue, which holds data coming from the socket. Access to the
	 * queue is serialized, to ensure correct processing order.
	 */
   private final Vector<ByteBuffer> _buffers = new Vector<ByteBuffer>();

   private final CharsetDecoder _decoder;
   private final CharsetEncoder _encoder;

   public FixedSeparatorForumMessageTokenizer( Charset charset) {
      this._decoder = charset.newDecoder();
      this._encoder = charset.newEncoder();
   }

   /**
    * Add some bytes to the message.  
    * Bytes are converted to chars, and appended to the internal StringBuffer.
    * Complete messages can be retrieved using the nextMessage() method.
    *
    * @param bytes an array of bytes to be appended to the message.
    */
   public synchronized void addBytes(ByteBuffer bytes) {
	   _buffers.add(bytes);
      
   }

   /**
    * Is there a complete message ready?.
    * @return true the next call to nextMessage() will not return null, false otherwise.
    */
   public synchronized boolean hasMessage() {
	   while(_buffers.size() > 0) {
           ByteBuffer bytes = _buffers.remove(0);
           CharBuffer chars = CharBuffer.allocate(bytes.remaining());
 	      this._decoder.decode(bytes, chars, false); // false: more bytes may follow. Any unused bytes are kept in the decoder.
 	      chars.flip();
 	      this._stringBuf.append(chars);
	   }
	   return isStringBufHasMassege();
   }

   private boolean isStringBufHasMassege() {
	if(this._stringBuf.length()<8)
		return false;
	String size_string = this._stringBuf.substring(0, 8);
	int size = FixedSeparatorForumMessageTokenizer.EightHexaStrToint(size_string);
	if(this._stringBuf.length()<8+size)
		return false;
	return true;
   }

/**
    * Get the next complete message if it exists, advancing the tokenizer to the next message.
    * @return the next complete message, and null if no complete message exist.
    */
   public synchronized ForumMessage nextMessage() {
      String serializedObject  =null;
    	
      
      //get serializedObject:
      String size_string = this._stringBuf.substring(0, 8);
  	  int size = FixedSeparatorForumMessageTokenizer.EightHexaStrToint(size_string);
  	    
  	  serializedObject  = this._stringBuf.substring(8, 8+size);
      this._stringBuf.delete(0, 8+size);      
      
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
      //System.out.println("<GetMassage>\n\n"+forumMessage.toString());
      //System.out.println("<\\GetMassage>");

      return forumMessage;
   }




/**
    * Convert the String message into bytes representation, taking care of encoding and framing.
    *
    * @return a ByteBuffer with the message content converted to bytes, after framing information has been added.
    */
   public ByteBuffer getBytesForMessage(ForumMessage msg)  throws CharacterCodingException {
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
		String size_str =  FixedSeparatorForumMessageTokenizer.intToEightHexaStr(size);
		String mes_String =size_str+serializedForumMessage;
	    StringBuilder sb = new StringBuilder(mes_String);    
	    ByteBuffer bb = this._encoder.encode(CharBuffer.wrap(sb));
		return bb;
   }
   
   
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

}
