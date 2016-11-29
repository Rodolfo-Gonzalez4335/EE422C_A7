package assignment7;

import java.util.*;

public class ActiveChat implements Observer {
	private static Map<String,ArrayList<Observer>> group = new HashMap<String,ArrayList<Observer>>();
	private static ArrayList<String> keys  = new ArrayList<String>();
	
	public void newmember(Observer c,String name){
		if (!group.containsKey(name))
			{
				ArrayList<Observer> temp =  new ArrayList<Observer>();
				temp.add(c);
				group.put(name, temp);
				keys.add(name);
			}
		else 
			group.get(name).add(c);
		
	}
	@Override
	public void update(Observer client) {
		for (String i : keys)
		{
			ArrayList<Observer> temp = group.get(i);
			temp.remove(client);
		}
	}
	
	
	public static int numberofchats(){
		return keys.size();
	}
	
	public static void printkeys(){
		for (String i :  keys)
			System.out.println(i);
	}

}
