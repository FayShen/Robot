package test;

import java.util.ArrayList;

public class SetAQ {
	public static void set(String q, String a)
	{
		//ArrayList<String> list = GetAQ.GetList();
		Link link = new Link("answer","root","123456");
		link.insert(q, a);
		link.close();
	}

}
