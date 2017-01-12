<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">
  <title>千度全文搜索</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
</head>

<body>
<form action="search.do" method="post">
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
    <%--<c:set var="describe" value="${bean.describe}"/>
    <c:choose>
      <c:when test="${fn:length(describe) > 255}">
        ${desc.substring(describe, 0, 255)}
      </c:when>
      <c:otherwise>
        <c:out value="${bean.describe}" />
      </c:otherwise>
    </c:choose>--%>
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

</body>
</html>
