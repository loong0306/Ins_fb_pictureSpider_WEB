<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>G.E.M.Gallery.</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
    <meta name="apple-mobile-web-app-status-bar-style" content="yes" />
    <meta name="sharecontent" data-msg-img='${ctx}/img/logo300.jpg' data-msg-title="G.E.M.Gallery." data-msg-content="G.E.M.Gallery." data-msg-callBack="" data-line-img='${ctx}/img/logo300.jpg' data-line-title="G.E.M.Gallery." data-line-callBack=""/>
    <link rel="stylesheet" href="${ctx}/css/mobile-angular-ui-hover.min.css" />
    <link rel="stylesheet" href="${ctx}/css/mobile-angular-ui-base.min.css" />
    <link rel="stylesheet" href="${ctx}/css/mobile-angular-ui-desktop.min.css" />
    <link rel="stylesheet" href="${ctx}/css/demo.css" />
    <script src="${ctx}/js/angular.min.js"></script>
    <script src="${ctx}/js/angular-route.min.js"></script>
    <script src="${ctx}/js/mobile-angular-ui.min.js"></script>
    <script src="${ctx}/js/mobile-angular-ui.gestures.min.js"></script>
    <script src="${ctx}/js/app.js"></script>
    <script src="${ctx}/js/comm.js"></script>
    <!--弹窗-->
    <script src="${ctx}/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <script type="text/javascript" src="${ctx}/js/alertPopShow.js"></script>
    <!--懒加载-->
    <script src="${ctx}/js/lazysizes.min.js" async=""></script>
    <!--弹窗-->
    <script type="text/javascript">
        $(function(){
            $(".myself").on('click', function(){
                webToast("亲爱的渔民.尽请期待","middle",1000);
            });
        });
    </script>
</head>
<body
        ng-app="MobileAngular"
        ng-controller="MainController"
        ui-prevent-touchmove-defaults
>
<!-- 微信图标 -->
<div id="wx_pic" style='display:none;'>
    <img src='${ctx}/img/logo300.jpg' />
</div>
<!-- 统计 -->
<%@ include file="cs.jsp" %>
<%CS cs = new CS(1261850199);cs.setHttpServlet(request,response);
    String imgurl = cs.trackPageView();%>
<img src="<%= imgurl %>" width="0" height="0" style="display:none;"/>

<!-- Navbars -->
<input type="hidden" id="ctxVal" value="${ctx}">
<div class="navbar navbar-app navbar-absolute-top">
    <div class="navbar-brand navbar-brand-center" ui-yield-to="title">
        G.E.M. Gallery.
    </div>
</div>

<div class="navbar navbar-app navbar-absolute-bottom">
    <div class="btn-group justified">
        <a href="#/" class="btn btn-navbar"><i class="fa fa-facebook-square fa-navbar"></i> Fb</a>
        <a href="#/ins" class="btn btn-navbar"><i class="fa fa-instagram fa-navbar"></i> Ins</a>
        <%--<div class="btn btn-navbar myself"><i class="fa fa-heartbeat fa-navbar"></i> Me</div>--%>
        <a href="#/about" class="btn btn-navbar"><i class="fa fa-heartbeat fa-navbar"></i> APP</a>
    </div>
</div>

<!-- App Body -->
<div class="app-body" ng-class="{loading: loading}">
    <div ng-show="loading" class="app-content-loading">
        <i class="fa fa-spinner fa-spin loading-spinner"></i>
    </div>
    <div class="app-content">
        <ng-view></ng-view>
    </div>
</div>
</div><!-- ~ .app -->
<div ui-yield-to="modals"></div>
</body>
</html>
