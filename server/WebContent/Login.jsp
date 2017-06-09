<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
			if(t.responseText.indexOf("0")>=0)
				{
			alert("登录失败！");
			
				}
			else
				{
				alert("登录成功！");
				window.location.href='Admin.jsp'; 
				}
			
			}
			
	}	
}
function check(username, userpwd)
{
	//alert(username.value);
	//window.location.href='Admin.jsp'; 
	createRequest('a.jsp?user='+username.value+'&pwd='+userpwd.value);
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link href="./Wopop_files/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="./Wopop_files/style.css">
<link rel="stylesheet" type="text/css" href="./Wopop_files/userpanel.css">
<link rel="stylesheet" type="text/css" href="./Wopop_files/jquery.ui.all.css">

</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="./Wopop_files/logo.png" width="196" height="46"></div>
<div class="login_boder">

<div class="login_padding" id="login_model">

  <h2>USERNAME</h2>
  <label>
    <input type="text" id="username" class="txt_input txt_input2" onfocus="if (value ==&#39;Your name&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;Your name&#39;}" value="Your name">
  </label>
  <h2>PASSWORD</h2>
  <label>
    <input type="password" name="textfield2" id="userpwd" class="txt_input" onfocus="if (value ==&#39;******&#39;){value =&#39;&#39;}" onblur="if (value ==&#39;&#39;){value=&#39;******&#39;}" value="******">
  </label>
 
 

 
  <p class="forgot"><a id="iforget" href="javascript:void(0);">Forgot your password?</a></p>
  <div class="rem_sub">
  <div class="rem_sub_l">
  <input type="checkbox" name="checkbox" id="save_me">
   <label for="checkbox">Remember me</label>
   </div>
    <label>
      <input type="submit" class="sub_button" name="button" id="button" value="SIGN-IN" style="opacity: 0.7;" onclick="check(username, userpwd)">
    </label>
  </div>
</div>

<div class="copyrights">Collect from <a href="http://www.cssmoban.com/" >企业</a></div>

<div id="forget_model" class="login_padding" style="display:none">
<br>

   <h1>Forgot password</h1>
   <br>
   <div class="forget_model_h2">(Please enter your registered email below and the system will automatically reset users’ password and send it to user’s registered email address.)</div>
    <label>
    <input type="text" id="usrmail" class="txt_input txt_input2">
   </label>

 
  <div class="rem_sub">
  <div class="rem_sub_l">
   </div>
    <label>
     <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now" style="opacity: 0.7;">
     　　　
     <input type="submit" class="sub_button" name="button" id="denglou" value="Return" style="opacity: 0.7;">　　
    
    </label>
  </div>
</div>



 


<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br> <br>




</body></html>