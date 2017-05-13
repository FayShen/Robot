package test;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;  

public class Link {
		private String url = null;// = "jdbc:mysql://localhost:3306/mysql?useSSL=true";  
	    private String name = "com.mysql.jdbc.Driver";  
	  //  public static final String user = "root";  
	   // public static final String password = "123456";  
	  
	    public Connection conn = null;  
	  //  public PreparedStatement pst = null;  
	  
	    public Link(String database, String user, String password) {  
	        try {  
	        	url = "jdbc:mysql://localhost:3306/"+database+"?useSSL=true";
	        	//System.out.println(url);
	            Class.forName(name);//ָ����������  
	            conn = DriverManager.getConnection(url, user, password);//��ȡ����  
	           // pst = conn.prepareStatement(sql);//׼��ִ����� 
	            if(conn != null)
	            {
	            	System.out.println("���ӳɹ�");
	            }
	            else{
	            	System.out.println("����ʧ��");
	            }
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	
	    }  
	  public ArrayList<String> select()
	  {
		  ArrayList<String> list = new ArrayList<String>();
		  try{
	    		Statement stmt =  conn.createStatement();
	    		String sql ="select * from s;";
	    		ResultSet rs = stmt.executeQuery(sql);
  		
	    		while(rs.next())
	    		{
	    			String str = "";
	    			str = rs.getString("que") + "/"+rs.getString("ans");
	    			list.add(str);
	    		}
	    		rs.close();
	    		stmt.close();
	    		}
	    	
	    	catch(Exception e)
	    		{
	    		e.printStackTrace();
	    		}
		  return list;
		  
	  }
	  public String insert(String q, String a)
	  {
		  try{
	    		Statement stmt =  conn.createStatement();
	    		String sql ="insert into s values('"+q+"','"+a+"');";
	    		stmt.executeUpdate(sql);
	    		stmt.close();
	    		return "Insert OK!";
	    		}
	    	
	    	catch(SQLException e)
	    		{
	    		System.out.println(e);
	    		return e.toString();
	    		}
		  
	  }
	    public void close() {  
	        try {  
	            this.conn.close();  
	          //  this.pst.close();  
	            System.out.println("���ݿ��ѹر�");
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    
}

