 <%@ page import="test.*" %>
<%
String type = new String(request.getParameter("type").getBytes("ISO-8859-1"),"UTF-8");
System.out.println(type);
int t = Integer.parseInt(type);
out.println("type="+t);
if(t==0)
{
	System.out.print(type);
	String id = new String(request.getParameter("id").getBytes("ISO-8859-1"),"UTF-8");
	int i = Integer.parseInt(id);
	Link link = new Link("answer","root","123456");
	link.delete(i);
	link.close();
	out.println("Delete");
}
if(t==1)
{
	String que = new String(request.getParameter("que").getBytes("ISO-8859-1"),"UTF-8");
	String ans = new String(request.getParameter("ans").getBytes("ISO-8859-1"),"UTF-8");
	SetAQ.set(que, ans);
	out.println("que"+que+"ans"+ans);
}
if(t==2)
{
	String id = new String(request.getParameter("id").getBytes("ISO-8859-1"),"UTF-8");
	String que = new String(request.getParameter("que").getBytes("ISO-8859-1"),"UTF-8");
	String ans = new String(request.getParameter("ans").getBytes("ISO-8859-1"),"UTF-8");
	int i = Integer.parseInt(id);
	Link link = new Link("answer","root","123456");
	link.update(i, que, ans);
	link.close();
	
}

%>