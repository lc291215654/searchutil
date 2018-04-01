<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>全文搜索</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
</head>

<body>
<div style="width: 90%;margin-left: 5%">
  <h1>${Doc.title}</h1>
  <br>
  <hr style="height:2px;border:none;border-top:3px solid #117f7f;width: 100%;">
  <br>
  <div>
    ${Doc.author}
  </div>
  <div>
    ${Doc.describe}
  </div>
  <div>
    ${Doc.content}
  </div>

</div>

</body>
</html>
