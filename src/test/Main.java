package test;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "ÄãÔÚ¸ÉÊ²Ã´";
		fenci f = new fenci();
		String[] fc = f.go(str).split("/");
		System.out.println(f.go(str));
	}

}
