package assignment7;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientData {
	//private static Map<Socket,Observer> numberofclients;
	private static ArrayList<Socket> keyset =  new ArrayList<Socket>();
	private static Map<Socket,Integer> ids = new HashMap<Socket,Integer>();
	private static Socket clientsendingmessage;
	private static int counter=0;
	private static ArrayList<Integer> clients = new ArrayList<Integer>();
	private static PrintStream outputclient;
	private static BufferedReader reader;
	
	public static void registerObserver(Socket id) {
	//	numberofclients.put(id, o);
		keyset.add(id);
		counter++;
		ids.put(id,counter);
		clients.add(counter);
	//	children.add(o);
	}

	public static void removeObserver(Socket id) {
		keyset.remove(id);
	//	numberofclients.remove(id);
		//children.remove(id);
//		numberofclients.get(id).update(null, numberofclients.get(id));
	}
	public static void updater(String msg){
		for (Socket socket : keyset)
		{
			try {
				outputclient = new PrintStream( socket.getOutputStream());
				outputclient.println(String.valueOf(ids.get(clientsendingmessage))+" "+ msg );
				outputclient.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public static void setclientsendingsocket(Socket x)
	{
		clientsendingmessage = x;
	}
	
	public static boolean clientexists(Integer x)
	{
		if (clients.contains(x))
			return true;
		return false;
					
	}
	
	public static void senddirectmessage(Integer client, String msg)
	{
		if (client.intValue()>0 && client.intValue()<= keyset.size())
		{
			try {
				
			Socket socket = keyset.get(client-1);
			outputclient = new PrintStream( socket.getOutputStream());
			outputclient.println(String.valueOf(ids.get(clientsendingmessage))+" "+ msg );
			outputclient.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
