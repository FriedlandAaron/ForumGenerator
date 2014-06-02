package draft_network_web;

import java.io.*;
import java.net.*;

import com.google.gson.Gson;
public class WebServerClient {
	public static void main(String []  args){
		int portNumber = 8887;
		Gson gson = new Gson();
		String fromServer  , fromUser ; 
	
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((fromServer = in.readLine()) != null) {

			    System.out.println("Server: " +gson.fromJson(fromServer , subforumjASON.class));

		        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			    fromUser = reader.readLine();
			    if (fromUser != null) {
			        out.println(gson.toJson(new subforumjASON(fromUser)));
			    }
			    if (fromUser.equals("DONE"))
			        break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
