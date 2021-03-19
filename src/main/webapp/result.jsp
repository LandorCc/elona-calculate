<%--
  Created by IntelliJ IDEA.
  User: shaojiea
  Date: 2020/10/21
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>———————————当前最优方案————————————</h2>
<h3>******初始垫子方案******</h3>
<table>
    <tr>
        <td>名称</td>
        <td>值</td>
    </tr>
    <c:forEach items = "${sonEquipment}" var = "sonList" >
        <c:forEach items="${sonList}" var="entity">
            <tr>
                <td>${entity.name}</td>
                <td>${entity.momian}</td>
            </tr>
        </c:forEach>
    </c:forEach>
</table>
<h3>******主材方案（使用顺序从上往下）******</h3>
<h4>（第一个的值是最终结果）</h4>
<table>
    <tr>
        <td>名称</td>
        <td>值</td>
    </tr>
    <c:forEach items = "${dadyEquipment}" var = "dadyList" >

        <tr>
            <td>-------</td>
            <td>-------</td>
        </tr>
        <c:forEach items="${dadyList}" var="entity">
            <tr>
                <td>${entity.name}</td>
                <td>${entity.momian}</td>
            </tr>
        </c:forEach>
    </c:forEach>
</table>
</body>
</html>
