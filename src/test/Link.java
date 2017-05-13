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
	            Class.forName(name);//指定连接类型  
	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	           // pst = conn.prepareStatement(sql);//准备执行语句 
	            if(conn != null)
	            {
	            	System.out.println("链接成功");
	            }
	            else{
	            	System.out.println("链接失败");
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
	            System.out.println("数据库已关闭");
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    
}

