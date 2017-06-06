<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="test.*" %>
  <%@ page import="java.util.ArrayList" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function createRequest(url){
	t = false;
	if(window.XMLHttpRequest)
		{
			t = new XMLHttpRequest();
		}
	else if(window.ActiveXObject)
		{
			try{
				t = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch(e){
				try{
					t = new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e){}
			}
		
		}
	if(!t)
		{
		alert("def");
		return false;
		}
	t.onreadystatechange = getResult;
	t.open('GET',url,true);
	t.send(null);
	
}
function getResult()
{
	if(t.readyState==4 ){
		if(t.status == 200)
			{
			alert(t.responseText);
			window.location.reload();
			}
			
	}	
}
function check(username, userpwd)
{
	alert(username.value);
	createRequest('NewFile1.jsp?user='+username.value+'&pwd='+userpwd.value);
}
function deleteS(i)
{
	alert(i);
	createRequest('action.jsp?type=0&id='+i);
	
}
function Insert(que, ans)
{
	if(que=="" || ans=="")
	{
		alert("不能为空！");
	}
	else
	{
	createRequest('action.jsp?type=1&que='+que+'&ans='+ans);
	}
}
</script>
</head>
<body>
<%
	GetAQ.Get();
	ArrayList<String> list = GetAQ.GetList();
	System.out.println(list);
	for(String str:list)
	{
		String[] s = str.split("/");
		int i = -1;
		try{
		i = Integer.parseInt(s[2]);
		}
		catch(Exception e)
		{}
		%>
		<input id="que_<%=i %>" type = "text" value="<%=s[0] %>" disabled="disabled">
		<input id="ans_<%=i %>" type = "text" value="<%=s[1] %>" disabled="disabled">
		<input id="but_<%=i %>" type = "button" value="Delete" onclick="deleteS(<%=i %>)">
		<input id="but_<%=i %>" type = "button" value="Delete" onclick="deleteS(<%=i %>)">
		<br>
		<% 
		
	}
%>
<input id="que_insert" type = "text" value="" >
<input id="ans_insert" type = "text" value="" >
<input id="insert" type = "button" value="Insert" onclick="Insert(que_insert.value,ans_insert.value)">
</body>
</html>