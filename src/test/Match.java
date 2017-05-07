package test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Match {
	private static Hashtable<Character, Hashtable> h;
	static{
		HashTable d = new HashTable();
		h = d.GetHash();
	}
	public static boolean go(String str){
		ArrayList <String> list;
		Hashtable<Integer, ArrayList<String>> hash2;
		try{
		hash2 =  h.get(str.charAt(0));
		list = hash2.get(str.length());
		Iterator ite;
		ite = list.iterator();
		while(ite.hasNext()){
			String t = ite.next().toString();
			if(t.equals(str))
				return true;
			}
		}
		catch(NullPointerException e)
		{
			return false;
		}
		return false;
		
	
	}
	}
