package assignment7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MultiThreadServer extends Application { // Text area for displaying
														// contents
	private TextArea ta = new TextArea();

	/*
	 * System.out.println("Hello welcome to the local massaging app");
	 * System.out.println("There are currently "+ ActiveChat.numberofchats()+
	 * " active chats avalable"); System.out.println(
	 * "Their respective names are "); ActiveChat.printkeys();
	 * System.out.println("You can enter a chat or create your own"); //let the
	 * user choose
	 */
	// Number a client

	private int clientNo = 0;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("MultiThreadServer"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		new Thread(() -> {
			try { // Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000);
				ta.appendText("MultiThreadServer started at " + new Date() + '\n');

				while (true) {
					// Listen for a new connection request
					Socket socket = serverSocket.accept();

					// Increment clientNo
					clientNo++;

					Platform.runLater(() -> {
						// Display the client number
						ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

						// Find the client's host name, and IP address
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
						ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
					});

					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket)).start();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}

	// Define the thread class for handling
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		/** Construct a thread */
		// int clienid=clientNo;
		InputStreamReader inputclient;
		PrintStream outputclient;
		ServerSocket server;
		BufferedReader reader;

		public HandleAClient(Socket socket) {
			this.socket = socket;
			// Observer temp = (Observer) socket;
			// if (socket!=null)
			ClientData.registerObserver(socket);
			// ClientData.registerObserver(socket, Observer);
		}

		/** Run a thread */
		public void run() {
			try {
				// Create data input and output streams
				inputclient = new InputStreamReader(socket.getInputStream());
				outputclient = new PrintStream(socket.getOutputStream());
				reader = new BufferedReader(inputclient);

				// Continuously serve the client
				while (true) {
					// Receiving message
					String msg = reader.readLine();
					ClientData.setclientsendingsocket(socket);

					if (!checkforkeyword(msg))
						ClientData.updater(msg);

					// Send area back to the client
					// outputclient.println(msg);
					Platform.runLater(() -> {
						ta.appendText("Message received from client" + clientNo + ": " + msg + '\n');
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private boolean checkforkeyword(String msg) {
			if (msg.length() < 8)
				return false;
			String temp = msg.substring(0, 6);
			String temp3 = msg.substring(7, 8);

			if (msg.equals("join chat")) {
				// ta.appendText("There are a total of "+
				// ActiveChat.numberofchats() + " chats available\n");
				// ta.appendText("Would you like to join one or create one
				// keywords join/create");
			} else if (temp.equals("client")) {

				if (msg.length() > 6) {
					msg = msg.substring(8);
					try {
						Integer clientnumber = Integer.parseInt(temp3);
						System.out.println("Number gotten: " + clientnumber);
						ClientData.senddirectmessage(clientnumber, msg);
					} catch (NumberFormatException e) {
						return false;
					}

				}
				return true;
			}
			return false;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
