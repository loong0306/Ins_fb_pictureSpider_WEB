<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <script type="text/javascript" src="${ctx}/js/comm.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <title>demo-decorators</title>
    <sitemesh:head />
</head>
<body>
<h1>decorators-template</h1>
<sitemesh:body />
</body>
</html>
