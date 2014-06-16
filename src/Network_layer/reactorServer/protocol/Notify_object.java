package Network_layer.reactorServer.protocol;

import java.io.PrintWriter;

import Network_layer.reactorServer.reactor.ReactorData;
import Network_layer.reactorServer.tokenizer.ForumMessage;

public class Notify_object implements Runnable {
	private ReactorData<ForumMessage> reactor_data ; 
	private PrintWriter out ;
	private String notify_str ;
	public Notify_object(ReactorData<ForumMessage> reactor_data,PrintWriter out, String notify_str) {
		this.reactor_data = reactor_data;
		this.notify_str = notify_str;
		this.out = out;
	}
	public void run() {
		this.reactor_data.notify(out, notify_str);
	}

}
