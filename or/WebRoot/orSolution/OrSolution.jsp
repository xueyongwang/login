<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <base href="<%=basePath%>">
    
    <title>两阶段法解决方案</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="description" content="两阶段法解决方案">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>orSolution/css/styles.css">
	<script type="text/javascript" src="<%=basePath%>orSolution/js/main.js"></script>
</head>
<body onload="bgbs()"> 
<H2 align="center">两阶段法解决方案</H2>
<div id="div2">
	<table width="680"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFF00" style="text-align:center" align="center">
	  <tr>
	    <td rowspan="2"><input type="button" name="buttob1" id="button1" class="button" value="开始" onclick="yemian(j=13,i=10,null,null,null)"></input>	 </td>
	    <td style="width:200px">
	    	<input type="button" name="buttob2" id="buttob2" class="button" value="增加一行" onclick="Add_Row()"/>&nbsp;
	    	<input type="button" name="buttob3" id="buttob3" class="button" value="删除一行" onclick="Delete_Row()"/></td>
	    <td rowspan="2"><input type="button" name="buttob7" id="button7" class="button" value="继续" onclick="Go_on()"/></td>
	    <td rowspan="2"><input type="button" name="buttob6" id="button6" class="button" value="结果" onclick="Result()"/></td>
	  </tr>
	  <tr>
	    <td style="width:200px">
	    	<input type="button" name="button" id="button2" class="button" value="增加一列" onclick="Add_Column()"/>&nbsp;
	    	<input type="button" name="button2" id="button3" class="button" value="删除一列" onclick="Delete_Column()"/></td>
      </tr>
	</table>
	<br/>
</div>

<div id="div1">
</div>

<div>
	 <input type="hidden" value="" id="hidden1" name="hidden1"/>
	 <input type="hidden" value="" id="hidden2" name="hidden2"/>
	 <input type="hidden" value="" id="basic_standard" name="basic_standard"/>
	 <input type="hidden" value="" id="first_bl" name="first_bl"/>
	 <input type="hidden" value="" id="mid_value" name="mid_value"/>
</div>
</body>
</html>
<!--//prompt("please input base variable quantity divideby '$':","");//输入框-->
