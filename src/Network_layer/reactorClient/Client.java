package Network_layer.reactorClient;
 
public class Client {
	
 
    public static void main(String[] args) {   	

    	//init
        ConnectionHandler connectionHandler = ConnectionHandler.connect_server("127.0.0.1" , (short) 6667); 
        ClientHandler cl_handler = new ClientHandler(connectionHandler);

        //main
        System.out.println(cl_handler.login("hadaramran", "12374567"));
        System.out.println(cl_handler.logout());
        System.out.println(cl_handler.logout());

        //closeConnection
        connectionHandler.closeConnection();
        return ;        
        
    }
}



/*

InputStream in = new InputStream(connectionHandler);
Thread INtheard =new  Thread(in , "INtheard");

OutputStream out = new OutputStream(connectionHandler);
Thread OUTtheard =new  Thread(out , "OUTtheard");

OUTtheard.run();
INtheard.run();


	try {
		INtheard.join();
        OUTtheard.join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
*/