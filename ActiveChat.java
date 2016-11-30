package assignment7;

import java.net.*;
import java.util.*;

public class ActiveChat {
	private static Map<Integer,ArrayList<Socket>> activechats = new HashMap<Integer,ArrayList<Socket>>();
	private static ArrayList<Integer> keyset = new ArrayList<Integer>();
	
	public static void addmember(Integer key,Socket client)
	{
		ArrayList<Socket> temp;
		if (!activechats.containsValue(key))
		{	
			temp =  new ArrayList<Socket>();
			temp.add(client);
			activechats.put(key, temp);
			keyset.add(key);
		}
		else 
		{
			activechats.get(key).add(client);//check if this works
		}
	}
	
	//public static void updateMap()
	
	public static int numberofchats(){
		return keyset.size();
	}
}
