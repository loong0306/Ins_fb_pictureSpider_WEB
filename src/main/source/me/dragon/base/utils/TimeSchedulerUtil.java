package me.dragon.base.utils;

import me.dragon.facebook.worker.GemFacebookFeed;
import me.dragon.instagram.worker.GemInsPicture;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by dragon on 4/6/2017.
 */
@Component
public class TimeSchedulerUtil {
    private final Logger logger = Logger.getLogger(this.getClass());

    // 生产
    /*@Scheduled(cron="0 0 0/1 * * ?")
    public void facebookTimeScheduler() {
        logger.info("正在连接Facebook获取最新Feed 。。。");
        GemFacebookFeed gemFacebookFeed = new GemFacebookFeed();
        gemFacebookFeed.flushFacebook();
        logger.info("断开连接Facebook 。。。");
    }

    @Scheduled(cron="0 0 0/1 * * ?")
    public void instagramTimeScheduler() {
        logger.info("正在连接Instagram获取最新Feed 。。。");
        GemInsPicture gemInsPicture = new GemInsPicture();
        gemInsPicture.flushInstagram();
        logger.info("断开连接Instagram 。。。");
    }*/

    // 开发
    @Scheduled(cron="0 0/1 * * * ?")
    public void facebookTimeScheduler() {
        logger.info("正在连接Facebook获取最新Feed 。。。");
        GemFacebookFeed gemFacebookFeed = new GemFacebookFeed();
        gemFacebookFeed.flushFacebook();
        logger.info("断开连接Facebook 。。。");
    }

    @Scheduled(cron="0 0/1 * * * ?")
    public void instagramTimeScheduler() {
        logger.info("正在连接Instagram获取最新Feed 。。。");
        GemInsPicture gemInsPicture = new GemInsPicture();
        gemInsPicture.flushInstagram();
        logger.info("断开连接Instagram 。。。");
    }

}