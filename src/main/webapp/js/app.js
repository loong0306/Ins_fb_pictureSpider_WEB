/* eslint no-alert: 0 */

'use strict';


var app = angular.module('MobileAngular', [
    'ngRoute',
    'mobile-angular-ui',
    'mobile-angular-ui.gestures'
]);


app.run(function($transform) {
    window.$transform = $transform;
});


app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'fb.jsp',
            controller: 'facebookController'
        })
        .when('/ins', {
            templateUrl: 'ins.jsp',
            controller: 'instagramController'
        })
        .when('/about', {
            templateUrl: 'about.jsp',
            reloadOnSearch: false
        })
        .otherwise({
            redirectTo: '/'
        })
});


app.controller('MainController', function($rootScope, $scope) {
    $scope.userAgent = navigator.userAgent;

    $rootScope.$on('$routeChangeStart', function() {
        $rootScope.loading = true;
    });

    $rootScope.$on('$routeChangeSuccess', function() {
        $rootScope.loading = false;
    });
});

app.controller('facebookController', function($rootScope, $scope) {
    var ctx = $("#ctxVal").val();
    var result = postData(ctx + '/api/getFacebook.do','');
    if("T" == result.flag){
        var objs = result.param;
        var str = '';
        $("#fb-group").empty();
        for (var i = 0; i < objs.length; i++){
            var picRealPath = ctx + '/comm/showImg.do?imgUrl=' + objs[i].pic;
            str += '<div class="list-group-item list-group-item-home">';
            str += '<div>';
            str += '<input type="hidden" value="'+ objs[i].id +'" id="fbFeedId"/>';
            str += '<img data-src="'+ picRealPath +'" class="lazyload" />';
            str += '<code style="display: inline-block;margin-top: 8px;word-wrap:break-word;width:100%;">' + objs[i].message + '</code>';
            str += '</div></div>';
        }
        $("#fb-group").append(str);
    }
});

app.controller('instagramController', function($rootScope, $scope) {
    var ctx = $("#ctxVal").val();
    var result = postData(ctx + '/api/getInstagram.do','');
    if("T" == result.flag){
        var objs = result.param;
        var str = '';
        $("#ins-group").empty();
        for (var i = 0; i < objs.length; i++){
            var picRealPath = ctx + '/comm/showImg.do?imgUrl=' + objs[i].pic;
            str += '<div class="list-group-item list-group-item-home">';
            str += '<div>';
            str += '<input type="hidden" value="'+ objs[i].date +'" id="insFeedId"/>';
            str += '<img data-src="'+ picRealPath +'" class="lazyload" />';
            str += '<code style="display: inline-block;margin-top: 8px;word-wrap:break-word;width:100%;">' + objs[i].caption + '</code>';
            str += '</div></div>';
        }
        $("#ins-group").append(str);
    }
});
