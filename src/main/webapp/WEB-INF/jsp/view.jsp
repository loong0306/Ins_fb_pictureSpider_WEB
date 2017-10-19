<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/js/comm.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.0.js"></script>
</head>
<body>
ctx = ${ctx}
<br/>
nameStr = ${nameStr}
<br/>
<input type="text" value="${nameStr}" id="name" style="width: 1000px;">
<br/>
</body>
<script>
$(document).ready(function () {
    var param = postData("${ctx}/view-select.do","");
    $("#name").val(param.msg);
    alert(param.msg);
})
</script>
</html>
