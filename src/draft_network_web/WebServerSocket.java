package draft_network_web;


import java.io.*;
import java.net.*;

import com.google.gson.Gson;

public class WebServerSocket {

	
	public static void main(String[] args){
		int ackcounter = 0;
		Gson gson = new Gson();
		int portNumber=8887;
		PrintWriter out = null;
		BufferedReader in = null;
		try { 
			    ServerSocket serverSocket = new ServerSocket(portNumber);
			    Socket clientSocket = serverSocket.accept();
			     out =   new PrintWriter(clientSocket.getOutputStream(), true);
			    in = new BufferedReader(
			        new InputStreamReader(clientSocket.getInputStream()));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("OPEN SOKECT ON PORT : 8887");
		String inputLine, outputLine;
		outputLine = gson.toJson(new subforumjASON("Hello user"));
        out.println(outputLine);

	    try {
	        while ((inputLine = in.readLine()) != null) {
				System.out.print(">>>>>>> getmessage: ");
				System.out.println(gson.fromJson(inputLine, subforumjASON.class));
				outputLine = gson.toJson(new subforumjASON("ACK: "+ackcounter+" get: "+ inputLine) );
				
			    out.println(outputLine);
			    if(inputLine.equals("DONE"))
			    	break;
			    
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
