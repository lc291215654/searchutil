<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%--<base href="<%=basePath%>">--%>

    <link rel="stylesheet" type="text/css" href="./content/css/bootstrap.css">
    <script type="application/javascript" src="./content/js/jquery-3.1.1.min.js"></script>

    <title>全文搜索</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>

<body>


<div style="margin-left: 25px">
    <div>
        <div style="float: left">
            <a href="test.jsp"><img src="./content/img/cetc32.png" alt="" style="width: 70px"></a>

        </div>
        <div style="margin-left: 20px">
            <div style="margin-top: 15px">
                <form action="search.do" method="get" class="form-horizontal">
                    <input type="text" name="keyWords" class="form-control" id="keyWords"
                           style="height: 30px;margin-left: 20px"/>
                    <input type="submit" value="搜索" class="btn btn-info" style="height: 30px">
                    <input type="hidden" value="1" name="pageNum">
                </form>
            </div>
        </div>
    </div>

    <div style="margin-top: 20px">
        <c:if test="${! empty page.list }">
        <div style="width: 90%;margin-left: 5%">
            <h4>为您找到相关结果约${total}个</h4>
            <br>
            <c:forEach items="${page.list}" var="bean">
                <a href="detailDocById/${bean.id}.do">${bean.title}</a>
                <br/>
                <br/>
                <span>${bean.describe}</span>
                <br/>
                <hr style="height:2px;border:none;border-top:3px solid #117f7f;width: 100%;">
                <br/>
            </c:forEach>

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
        </div>
    </div>
</div>
<script type="application/javascript">
    // alert("${kw}");
    var keyword = "${kw}";
    if (keyword != null) {
        //   alert(keyword);
        //   $("#keyWords").val(keyword);
        document.getElementById("keyWords").value = keyword;
    }
</script>
</body>
</html>