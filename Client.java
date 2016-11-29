package assignment7;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	int port = 8000;
	String host = "localhost";
	InputStreamReader in;
	PrintWriter out;
	Socket socket;
	BufferedReader reader;
	
	//have a message to specify on who is who.
	//tell who wants to receive what.
	//how do i update
	
	
	
	void runme () {
		Scanner sc = new Scanner(System.in);
		
		try {
			// Define client socket, and initialize in and out streams.
			socket = new Socket(host, port);
			in = new InputStreamReader(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(in);
			
			ClientData.registerObserver(port);
			String msg;
			
			while (true) {
				try {
					// ask user to enter a double
					System.out.print("Send a message to server: ");
					msg = sc.nextLine();
				} catch (Exception e) {
					System.out.println("Try again.");
					continue;
				}
				
				// send the double to the server
				out.println(msg);
				out.flush();
				//
				
				// read the server's response, and print it out.
				System.out.println("Clint ID says: " + 
						msg);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
