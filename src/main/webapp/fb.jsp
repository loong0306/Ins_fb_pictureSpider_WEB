<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- 微信图标 -->
<div style='display:none;'>
    <img src='${ctx}/img/logo300.jpg' />
</div>
<div class="scrollable">
    <div class="scrollable-content">
        <div class="list-group text-center">

            <div id="fb-group">
                <div class="list-group-item list-group-item-home">
                    <div>
                        <input type="hidden" value="" id="fbFeedId"/>
                        <img data-src="img/1.jpg" class="lazyload" />
                        <code style="display: inline-block;margin-top: 8px;word-wrap:break-word;width:100%;">Time out</code>
                    </div>
                </div>
            </div>

            <div class="list-group-item list-group-item-home">
                <h1 style="font-family: 幼圆">G.E.M. Facebook 每小时自动更新
                </h1>
            </div>
        </div>
    </div>
</div>
