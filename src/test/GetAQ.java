package test;

import java.util.ArrayList;

public class GetAQ {
	private static ArrayList<String> An;
	public static void Get(){
		Link link = new Link("answer","root","123456");
		An = link.select();
		link.close();
		//System.out.println(An);
	}
	public static ArrayList<String> GetList()
	{
		return An;
	}

}
