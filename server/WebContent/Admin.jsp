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
			alert("成功");
			window.location.reload();
			}
			
	}	
}
function check(username, userpwd)
{
	createRequest('NewFile1.jsp?user='+username.value+'&pwd='+userpwd.value);
}
function deleteS(i)
{
	
	createRequest('action.jsp?type=0&id='+i);
	
}
function Insert(que, ans)
{
	que=que.trim();
	ans = ans.trim();
	if(que=="" || ans=="")
	{
		alert("不能为空！");
	}
	else
	{
	createRequest('action.jsp?type=1&que='+que+'&ans='+ans);
	}
}
function update(i,que,ans,up)
{
	if(up.value=="修改")
	{
	up.value="提交";
	que.removeAttribute("disabled");
	ans.removeAttribute("disabled");
	}
	else{
		up.value="修改";
		que.setAttribute("disabled","disabled");
		ans.setAttribute("disabled","disabled");
		q = que.value.trim();
		a = ans.value.trim();
		createRequest('action.jsp?type=2&id='+i+'&que='+q+'&ans='+a);
	}
				
}
function clean(q, a)
{
	q.value="";
	a.value="";
}
</script>

</head>
<body background="Wopop_files/login_bgx.gif">
<div style="background-color: #CCCCCC; font-size: 30px">管理界面</div>
<div align="center" style="margin-top: 20px;">
<table bgcolor="#CCCCCC">
<tr>
<td style="font-family:宋体; font-size: 20px" align="center">问题</td>
<td style="font-family:宋体; font-size: 20px " align="center">答案</td>
</tr>
<tr>
<td><textarea rows="2" cols="30" id="que_insert" ></textarea></td>
<td><textarea rows="2" cols="30" id="ans_insert"></textarea></td>

<td><input id="insert" type = "button" value="添加 " onclick="Insert(que_insert.value,ans_insert.value)"></td>
</tr>
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
		
	
		<tr>
		<td><textarea id="que_<%=i %>" rows="2" cols="30" disabled="disabled"><%=s[0] %></textarea></td>
		<td><textarea id="ans_<%=i %>" rows="2" cols="30" disabled="disabled"><%=s[1] %></textarea></td>
		
		<td><input id="del_<%=i %>" type = "button" value="删除" onclick="deleteS(<%=i %>)"></td>
		<td><input id="up_<%=i %>" type = "button" value="修改" onclick="update(<%=i %>,que_<%=i %>,ans_<%=i %>,up_<%=i %>)"></td>
		</tr>
		
		<% 
		
	}
%>
</table>
		
</div>
</body>
</html>