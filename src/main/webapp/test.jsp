<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
    String path = request.getScheme()+"://" + request.getServerName()+":"+request.getServerPort()+request.getContextPath();

%>

<link rel="stylesheet" type="text/css" href="<%=path%>/content/css/bootstrap.css" />
   <link rel="stylesheet" type="text/css" href="<%=path%>/content/css/bootstrap-responsive.css"/>

   <style type="text/css">
   .bg{
   	background-image: url(<%=path%>/content/img/search1.jpg);
  background-size: 100% 100%;  
  -moz-background-size: 100% 100%;  
  -webkit-background-size: 100% 100%;  
   
   }
   </style>
   
<title> 搜 索 </title>
</head>
<body>
<div style="width:100%;height: 960px;" class="bg">

<div style="text-align: center;padding-top: 15%">
<form action="search.do" method="get" class="form-horizontal">
  <input type="text" name="keyWords" class="form-control" style="width:500px;height:40px"/>
  <input type="submit" class="btn btn-success" value="搜索" style="height:40px">
  <input type="hidden" value="1" name="pageNum">
</form>

</div>
	

</div>

</body>
</html>