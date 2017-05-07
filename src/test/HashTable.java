package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class HashTable {
	private Hashtable<Character, Hashtable> Dictionary =
			new Hashtable<Character, Hashtable>();
	HashTable()
	{
		Hashtable<Integer, ArrayList<String>> hash2 ;
		ArrayList<String> list;
		File f = new File("D:/test.txt");
		String str;
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(f));
			while((str = reader.readLine()) != null)
			{
				//System.out.println(str);
				hash2 = Dictionary.get(str.charAt(0));
				if(hash2 == null){
					hash2 = new Hashtable<Integer,ArrayList<String>>();
				}
				list = hash2.get(str.length());
				if(list == null){
				
					list = new ArrayList<String>();
				}
				list.add(str);
				hash2.put(str.length(), list);
				Dictionary.put(str.charAt(0), hash2);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	            }
	        }
	    }
	}
	public Hashtable<Character, Hashtable> GetHash()
	{
		return Dictionary;
	}
}
