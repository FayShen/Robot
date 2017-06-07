package test;

public class fenci {
	public  static String go(String str){
		String[] fen= new String[100];
		String newstr = "";
		int maxmatch =4 ;
		String s1;
		boolean b = false;
		int length = str.length();
		int tu = 1;
		int feni = 0;
		while(length >0 && tu == 1){
			if(str.length() > maxmatch)
				s1 = str.substring(str.length() - maxmatch);
			else
				s1 = str;
		int i = s1.length();
		b =false;
		while(!b && i > 0)
		{
			s1 = s1.substring(s1.length()-i);
			b = Match.go(s1);
			i--;
		}
		str = str.substring(0, str.length() - s1.length());
		//System.out.println(s1);
		fen[feni++] = s1;
		if(newstr.length()==0)
			newstr+= s1;
		else
			newstr = newstr +"/"+ s1;
		length = str.length();
		}
		return newstr;
	}
}
