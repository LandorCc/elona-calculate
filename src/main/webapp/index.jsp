<%--
  Created by IntelliJ IDEA.
  User: shaojiea
  Date: 2020/10/22
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    var selector;

    //返回被选择radio的value属性值
    function whichRadioValueChecked(selector) {
        var rtn = "";
        selector.each(function () {
            if ($(this).prop("checked")) {
                rtn = $(this).attr("value");
            }
        });
        return rtn;
    }

    selector = $('input[type="radio"][name="checkType"]');
    var value = whichRadioValueChecked(selector);

</script>
<form action="${pageContext.request.contextPath}/reform/route" method="post">
    <a><label>
        <input type="radio" name="checkType" value="momian">
    </label>魔免垫子改造工具</a>
    <a><label>
        <input type="radio" name="checkType" value="momian2">
    </label>魔免主材改造工具</a>
<%--    <a><label>--%>
<%--        <input type="radio" name="checkType" value="fushang">--%>
<%--    </label>附伤词条</a>--%>
<%--    <a><label>--%>
<%--        <input type="radio" name="checkType" value="naixing">--%>
<%--    </label>耐性词条</a>--%>
    <a><input type="submit"></a>
</form>

</body>
</html>
