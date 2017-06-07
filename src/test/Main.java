package test;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	String str = "12";
		GetAQ.Get();
	System.out.println(Answer.go(str));*/
	//SetAQ.set("ÄÄ¼Ò¿ìµÝ","Ô²Í¨");
		//Link
		Link link = new Link("answer","root","123456");
		int i =link.Login("2", "123456");
		System.out.println(i);
		link.close();
		
	}

}
