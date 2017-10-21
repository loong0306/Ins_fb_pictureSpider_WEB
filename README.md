# Ins_fb_pictureSpider
✈让无法科学上网的粉丝关注爱豆的脸书与照片墙动态

#### 关于项目：
```html
1.Ins_fb_pictureSpider项目是我为了追偶像（G.E.M）开发的小应用程序，方便查看爱豆在FB和INS上的图文动态。
2.同时有一些粉丝由于无法科学上网，所以通过此项目可以帮助了解资讯动态。
3.您可以换成所希望关注博主的FB和INS，具体请参考使用第四条。
```


#### 使用：
```html
1.resources文件夹:
    -> application.properties
        spring.datasource.username
        spring.datasource.password
        
    -> application.yml
        username
        password
        
    -> facebook4j.properties
        oauth.appId
        oauth.appSecret
        oauth.accessToken
        http.proxyHost
        http.proxyPort
        (此处注意，oauth为Facebook开发者验权信息，可在官方注册应用；proxy为服务器代理，项目并非黑科技，可自行使用SSR)
        
2.source.me.dragon.facebook/instagram.utils
    -> SaveInstagramPicByURL.java
        (此处注意，httpUrl.setRequestProperty需进行代理设置)
        
3.source.me.dragon.base.utils
    -> TimeSchedulerUtil.java
        (此处用到了定时器，每隔一小时爬取新动态)
        
4.source.me.dragon.facebook/instagram.worker
    -> GemFacebookFeed
        (此处修改flushFacebook方法中的RawAPIResponse方法参数即可)
    -> GemInsPicture
        (此处修改flushInstagram方法中的Spider方法参数即可)
```

#### 关于代码：
```html
1.项目开发于2017.04使用了[AprilDragonSpring](https://github.com/dragon-yuan/AprilDragonSpring)
虽然整合的框架没有优美，但也是本人在Git上的起步项目，所以有些情怀。
2.在项目中使用的druid，service层使用JDBC，为了查看下JDBC的效率，也可以换成其他技术使用。
```


#### 感谢：
```html
1.[facebook4j](https://github.com/roundrop/facebook4j)
    -> 感谢此项目，并使用到了项目中，很好的依赖
2.[instagram4j](https://github.com/lithiumtech/instagram4j)
    -> 此项目无法使用代理，所以只能使用爬虫自行爬取
3.[weibo-spider](https://github.com/yuki-lau/weibo-spider)
    -> 感谢此项目提供了学习的机会，但由于新浪微博API禁用，目前不适用由于新浪微博
4.感谢前端技术
    -> alertPopShow.js
    -> mobile-angular-ui.js
```