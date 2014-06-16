package Network_layer.reactorServer.reactor;

import java.io.PrintWriter;
import java.nio.channels.Selector;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

import Domain_layer.ForumComponent.IForum;
import Network_layer.reactorServer.protocol.ServerProtocolFactory;
import Network_layer.reactorServer.tokenizer.TokenizerFactory;

/**
 * a simple data structure that hold information about the reactor, including getter methods
 */
public class ReactorData<T> {

    private final ExecutorService _executor;
    private final Selector _selector;
    private final ServerProtocolFactory<T> _protocolMaker;
    private final TokenizerFactory<T> _tokenizerMaker;
    private final IForum _forum;
	private  Vector<PrintWriter> observers; 
	
    public ExecutorService getExecutor() {
        return _executor;
    }

    public Selector getSelector() {
        return _selector;
    }

	public ReactorData(ExecutorService _executor, Selector _selector, ServerProtocolFactory<T> protocol, TokenizerFactory<T> tokenizer , IForum forum) {
		this._executor = _executor;
		this._selector = _selector;
		this._protocolMaker = protocol;
		this._tokenizerMaker = tokenizer;
		this._forum = forum;
		this.observers = new Vector<PrintWriter>();
	}

	public ServerProtocolFactory<T> getProtocolMaker() {
		return _protocolMaker;
	}

	public TokenizerFactory<T> getTokenizerMaker() {
		return _tokenizerMaker;
	}
	public IForum getIForum(){
		return this._forum;
	}

	public  void addObserver(PrintWriter out) {
		if(out ==null)
			return;
		synchronized(observers){ 
			this.observers.add(out);
			//System.out.println("add observer: "+this.observers.size());
		}
	}

	public synchronized void notify(PrintWriter out , String msg) {
		PrintWriter observer ;
		synchronized(observers){ 
			for(int i = 0 ; i<this.observers.size() ; i++){
				observer = this.observers.get(i);
				if(!observer.equals(out)){
				//	System.out.println(">>notify:>>"+i+">>>>"+msg+">>>>>>>>");
					observer.println(msg);
				}
			}			
		}	

	}

	public void removeObserver(PrintWriter out) {
		if(out ==null)
			return;
		PrintWriter observer ;
		synchronized(observers){ 
			for(int i = 0 ; i<this.observers.size() ; i++){
				observer = this.observers.get(i);
				if(observer.equals(out)){
					this.observers.remove(i);
					//System.out.println("remove observer: "+i);
				}
			}			
		}	
	}

}
