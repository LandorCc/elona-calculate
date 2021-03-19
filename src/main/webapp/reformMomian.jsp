<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Insert title here</title>
</head>

<body>
<script language="javascript">
    iIndex = 1;
    var annex = 1;
    var i = 1;

    function getIndex() {
        iIndex = event.srcElement.parentElement.parentElement.rowIndex;
        return iIndex;
    }

    function insertRow(iPos) {
        var otr = myTable.insertRow(annex);//插入一个tr
        var ocell = otr.insertCell(0);//插入一个td
        ocell.innerHTML = "<input type=text size=20 name='name(" + i + ")' id=name(" + i + ") >";
        var ocell = otr.insertCell(1);//插入一个td
        ocell.innerHTML = "<input type=text size=20 name='value(" + i + ")' id=value(" + i + ") >";
        var ocell = otr.insertCell(2);//插入一个td
        ocell.innerHTML = "<td><input type=button onclick='deleteRow(getIndex())' value='删除'></td>";
        annex++;
        document.getElementById("zjLength").value = annex;
        i++;
    }

    function deleteRow(iPos) {
        document.all.myTable.deleteRow(iPos);
        --annex;
        i--;
        document.getElementById("zjLength").value = annex;
    }

</script>

<form name=myForm action="${pageContext.request.contextPath}/reform/calculateMomian" method="post">
    改造次数：<input type="text" name="reformNum" ><br>
    获得最初垫子改造次数：<input type="text" name="sonDeep" >  ：建议输入1或2，数字越高计算量越大，程指数型增长<br>
    获得每次改造的本体的改造次数：<input type="text" name="dadyDeep"> ：同上
    <h3>---------------------------</h3>
    <a href="#" onClick="insertRow(0)">增加装备</a>
    <tr>
        <td colspan="4" name='提交行数'>
            <label for="zjLength"></label><input type="text" name="zjLength" id="zjLength"/>
        </td>
    </tr>
    <table id="myTable">
        <tr>
            <td>名称</td>
            <td>值</td>
            <td>删除</td>
        </tr>
    </table>
    <input type="submit" name="提交">
</form>
</body>
</html>