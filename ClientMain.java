package assignment7;


public class ClientMain {
	public static void main (String [] args) {
		Client client = new Client();
		
		System.out.println("Hello welcome to the local massaging app");
		System.out.println("There are currently "+ ActiveChat.numberofchats()+ " active chats avalable");
		System.out.println("Their respective names are ");
		ActiveChat.printkeys();
		System.out.println("You can enter a chat or create your own");
		//let the user choose
		
		
		new Thread(new Runnable () {
			@Override
			public void run() {
				client.runme();
			}
		}).start();
		
	}
}