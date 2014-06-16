package Network_layer.reactorServer.protocol;

import java.io.PrintWriter;

import Network_layer.reactorServer.reactor.ReactorData;
import Service_Layer.IUserHandler;

/**
 * A protocol that describes the behabiour of the server.
 */
public interface ServerProtocol<T> {

    /**
     * processes a message
     * @param msg the message to process
     * @return the reply that should be sent to the client, or null if no reply needed
     */
    T processMessage(T msg);

    /**
     * detetmine whether the given message is the termination message
     * @param msg the message to examine
     * @return true if the message is the termination message, false otherwise
     */
    boolean isEnd(T msg);
    
	public void InitUserHandler(IUserHandler userHandler) ;
	public void InitReactorData(ReactorData<T> reactor_data ) ;
	//public void InitHandler(ConnectionHandler<T> conHandler) ;

	PrintWriter getOut();


}
