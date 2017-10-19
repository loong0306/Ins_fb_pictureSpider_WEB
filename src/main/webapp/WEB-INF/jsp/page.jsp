<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Page</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <script type="text/javascript" src="${ctx}/js/comm.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap.min.js"></script>
    <style type="text/css">
        body,table{
            font-size:12px;
        }
        table{
            table-layout:fixed;
            empty-cells:show;
            border-collapse: collapse;
            margin:0 auto;
        }
        td{
            height:30px;
        }
        h1,h2,h3{
            font-size:12px;
            margin:0;
            padding:0;
        }
        .table{
            border:1px solid #cad9ea;
            color:#666;
        }
        .table th {
            background-repeat:repeat-x;
            height:30px;
        }
        .table td,.table th{
            border:1px solid #cad9ea;
            padding:0 1em 0;
        }
        .table tr.alter{
            background-color:#f5fafe;
        }
    </style>
</head>
<body>
<table width="90%" class="table">
    <tr>
        <th>标识</th>
        <th>姓名</th>
        <th>性别</th>
    </tr>
    <c:forEach items="${personPage.result}" var="person">
        <tr>
            <td>${person.id}</td>
            <td>${person.name}</td>
            <td>${person.sex}</td>
        </tr>
    </c:forEach>
</table>
<div class="page">
    共有<span>${personPage.totalCount}</span>条，每页显示：1条
    <div class="bor">
        <a class="dif" href="javascript:goPage('first');" class="" title="首页">«</a>
        <a href="javascript:goPage('${personPage.pageNo - 1}');" class="glyphicon glyphicon-menu-left" title="上一页"></a>
        <a class="text-blue" href="javascript:void(0);">${personPage.pageNo}</a>
        <a href="javascript:goPage('${personPage.pageNo + 1}');" class="glyphicon glyphicon-menu-right" title="下一页"></a>
        <a class="dif last" href="javascript:goPage('last');" title="尾页">»</a>
    </div>
    <input type="text" class="form-control wid" id="toPage" />
    <a class="btn" href="javascript:goPage('go');">GO</a>
</div>



<div class="modal fade" id="myModalHint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body modal-body-info text-center" id="hintMessage">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="tsqr();">确认</button>
            </div>
        </div>
    </div>
</div>
</body>

<script>
    function goPage(pageNo) {
        var totalCount = '${personPage.totalCount}';
        var pageSize = '${personPage.pageSize}';
        var pages = totalCount%pageSize==0?totalCount/pageSize:Math.ceil(totalCount/pageSize);
        if("first" == pageNo){
            pageNo = 1;
        }else if("last" == pageNo){
            pageNo = pages;
        }else if("go" == pageNo){
            var pageStr = $.trim($("#toPage").val());
            if("" == pageStr){
                $("#hintMessage").text("请输入页数");
                $("#myModalHint").modal('show');
                return;
            }
            var reg = /^[1-9]\d*$/;
            if(!reg.test(pageStr)){
                $("#hintMessage").text("请输入有效页数");
                $('#myModalHint').modal('show');
                return;
            }
            if(pageStr > pages){
                $("#hintMessage").text("已超过最大页数");
                $('#myModalHint').modal('show');
                return;
            }
            pageNo = pageStr;
        }else if(pageNo > pages){
            pageNo = pages;
        }else if(pageNo < 1){
            pageNo = 1;
        }
        href('${ctx}/page.do?pageNum=' + pageNo);
    }
</script>
</html>
