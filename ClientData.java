package assignment7;

import java.util.*;

public class ClientData {
	private static Map<Integer,Observer> numberofclients;
	private static ArrayList<Integer> keyset;
	
	public static void registerObserver(Integer id,Observer o) {
		numberofclients.put(id, o);
		keyset.add(id);
	}

	public static void removeObserver(Integer id) {
		keyset.remove(id);
		numberofclients.remove(id);
		numberofclients.get(id).update(numberofclients.get(id));
	}

}
