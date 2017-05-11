package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Answer {
	
	public static String go(String Que)
	{
		ArrayList<String> an= new ArrayList<String>();
		File f = new File("D:/Answer.txt");
		String str;
		BufferedReader reader = null;
		try {
			 reader = new BufferedReader(new FileReader(f));
			while((str = reader.readLine()) != null)
			{
				an.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	            	e1.printStackTrace();
	            }
	        }
	    }
		ArrayList<AQ> aq = new ArrayList<AQ>();
		for(String strt:an)
		{
			String[] t1 = strt.split("/");
			String[] Stander = fenci.go(t1[0]).split("/");
			String[] MyQue = fenci.go(Que).split("/");
			int flag = 0;
			for(String t:MyQue)
			{
				for(String q:Stander)
				{
					if(t.equals(q))
					{
						flag++;
					}
				}
			}
			AQ t = new AQ(Stander, t1[1], flag);
			aq.add(t);
		}
		int max = 0;
		String Answer = null;
		for(AQ t: aq)
		{
			if(t.flag > max)
			{
				max = t.flag;
				Answer = t.Answer;
			}
			
		}
		if(Answer == null)
			return "Sorry!";
		return Answer;
		
	}
	private static class AQ{
		String[] Question;
		String Answer;
		int flag = 0;
		AQ(String[] a, String b, int f)
		{
			Question = a;
			Answer = b;
			flag = f;
		}
		
	}

}
