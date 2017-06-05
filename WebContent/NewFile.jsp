<%@ page import="test.*" %>
<%
	String str = new String(request.getParameter("question").getBytes("ISO-8859-1"),"UTF-8");
	System.out.println(str);
	GetAQ.Get();
	System.out.println(Answer.go(str));
	String an = Answer.go(str);
%>
<Answer>
<%=an %> 
</Answer>