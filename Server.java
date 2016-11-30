package assignment7;

import java.io.*;
import java.net.*;

public class Server {

	int port = 8000;
	InputStreamReader in;
	PrintWriter out;
	ServerSocket server;
	Socket socket;
	BufferedReader reader;

	void runme() {
		try {
			// Create a server socket, and define in and out streams for it
			server = new ServerSocket(port);
			socket = server.accept();
			in = new InputStreamReader(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(in);
			
			// loop keeps reading from client, squares it, and sends the result
			// back to the client.
			while (true) {
				String msg =reader.readLine();
				System.out.println("Server received " + msg);
				out.println(msg);
				out.flush();
				System.out.println("Server: I wrote " + msg + " to client.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	

}
