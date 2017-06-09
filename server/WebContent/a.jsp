 <%@ page import="test.*" %>
<%
String user = new String(request.getParameter("user").getBytes("ISO-8859-1"),"UTF-8");
String pwd = new String(request.getParameter("pwd").getBytes("ISO-8859-1"),"UTF-8");
user = user.trim();
pwd = pwd.trim();
Link link = new Link("answer","root","123456");
int i =link.Login(user, pwd);
System.out.println(i);
link.close();
out.println(i);
%>
