<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>全文搜索</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta charset="UTF-8">




  <link rel="stylesheet" type="text/css" href="content/css/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="content/css/bootstrap-responsive.css">
  <link rel="stylesheet" type="text/css" href="content/css/bootmetro.css">
  <link rel="stylesheet" type="text/css" href="content/css/bootmetro-tiles.css">
  <link rel="stylesheet" type="text/css" href="content/css/bootmetro-charms.css">
  <link rel="stylesheet" type="text/css" href="content/css/metro-ui-light.css">
  <link rel="stylesheet" type="text/css" href="content/css/icomoon.css">

  <style type="text/css">
    .bg{
      background-image: url(content/img/search1.jpg);
      background-size: 100% 100%;
      -moz-background-size: 100% 100%;
      -webkit-background-size: 100% 100%;

    }
  </style>




</head>

<body>
<%--<div style="width:100%;height: 960px;" class="bg">

  <div style="text-align: center;padding-top: 15%">--%>
  <form action="search.do" method="get">
    <input type="text" name="keyWords" />
    <input type="submit" value="搜索">
    <input type="hidden" value="1" name="pageNum">
  </form>

    <c:if test="${! empty page.list }">
      <h3>为您找到相关结果约${total}个</h3>
      <c:forEach items="${page.list}" var="bean">
        <h3><a href="/detailDocById/${bean.id}.do">${bean.title}</a></h3>
        <span>
            ${bean.describe}
        </span>
        <br/>
        <br/>
      </c:forEach>

      <div></div><br/>
      <c:if test="${page.hasPrevious }">
        <a href="search.do?pageNum=${page.previousPageNum }&keyWords=${kw}"> 上一页</a>
      </c:if>
      <c:forEach begin="${page.everyPageStart }" end="${page.everyPageEnd }" var="n">
        <a href="search.do?pageNum=${n }&keyWords=${kw}"> ${n }</a> &nbsp;&nbsp;
      </c:forEach>

      <c:if test="${page.hasNext }">
        <a href="search.do?pageNum=${page.nextPageNum }&keyWords=${kw}"> 下一页</a>
      </c:if>
    </c:if>
<%--
</div>
</div>--%>



</body>
</html>
