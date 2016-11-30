package assignment7;

import java.io.*; 
import java.net.*; 
import javafx.application.Application; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 
import javafx.scene.Scene; 
import javafx.scene.control.Label; 
import javafx.scene.control.ScrollPane; 
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 


public class Client extends Application { 
	// IO streams 
	PrintStream toServer = null; 
	InputStreamReader fromServer = null;
	BufferedReader reader;
	boolean flag=false;

	@Override // Override the start method in the Application class 
	public void start(Stage primaryStage) { 
		// Panel p to hold the label and text field 
		BorderPane paneForTextField = new BorderPane(); 
		paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
		paneForTextField.setStyle("-fx-border-color: green"); 
		paneForTextField.setLeft(new Label("Enter Text: ")); 

		TextField tf = new TextField(); 
		tf.setAlignment(Pos.BOTTOM_RIGHT); 
		paneForTextField.setCenter(tf); 

		BorderPane mainPane = new BorderPane(); 
		// Text area to display contents 
		TextArea ta = new TextArea(); 
		mainPane.setCenter(new ScrollPane(ta)); 
		mainPane.setTop(paneForTextField); 


		// Create a scene and place it in the stage 
		Scene scene = new Scene(mainPane, 450, 200); 
		primaryStage.setTitle("Client"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 
		
		tf.setOnAction(e -> { 
			// Get the text from the text field 
			String outmsg = tf.getText().trim();
			
			// Send the text to the server 
			toServer.println(outmsg); 
			toServer.flush(); 

			//reader = new BufferedReader(fromServer);
			//String inmsg = reader.readLine();
			// Display to the text area 
			
			//ta.appendText("Message " + outmsg+ "\n"); 
			
			tf.clear(); 
		}); 
		try { 
			// Create a socket to connect to the server 
			@SuppressWarnings("resource")
			Socket socket = new Socket("localhost", 8000); 
			
			// Create an input stream to receive data from the server 
			fromServer = new InputStreamReader(socket.getInputStream()); 
			
			toServer = new PrintStream(socket.getOutputStream()); 
		} 
		catch (IOException ex) { 
			ta.appendText(ex.toString() + '\n');
		}
		
		new Thread( () ->{
			try {	
			reader = new BufferedReader(fromServer);
			String inmsg;
			while ((inmsg = reader.readLine())!= null)
			{	System.out.println(inmsg);
			// Display to the text area 
			ta.appendText("Message from client" + inmsg+ "\n"); }
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}).start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
