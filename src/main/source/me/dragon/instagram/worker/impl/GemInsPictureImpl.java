package me.dragon.instagram.worker.impl;

import me.dragon.instagram.entity.InstagramFeed;
import me.dragon.instagram.entity.InstagramFeedPicture;
import me.dragon.instagram.service.InsFeedService;
import me.dragon.instagram.service.impl.InsFeedServiceImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dragon on 4/24/2017.
 */
public class GemInsPictureImpl {
    // 获取本地动态
    public List<InstagramFeed> getFeedListByLocal() throws SQLException {
        InsFeedService feedService = new InsFeedServiceImpl();
        List<InstagramFeed> feedList = feedService.getFeedList();
        return feedList;
    }

    // 新增动态
    public void saveFeedList(List<InstagramFeed> instagramFeedList, List<InstagramFeedPicture> instagramFeedPictureList) {
        InsFeedService feedService = new InsFeedServiceImpl();
        feedService.saveFeedList(instagramFeedList,instagramFeedPictureList);
    }

    // 更新历史图片库
    public void updateLocalFeedPic(String date, String picRealPath) {
        InsFeedService feedService = new InsFeedServiceImpl();
        feedService.updateLocalFeedPic(date,picRealPath);
    }

    // 获取本地图片
    public List<InstagramFeedPicture> getFeedPicListByLocal() {
        InsFeedService feedService = new InsFeedServiceImpl();
        List<InstagramFeedPicture> instagramFeedPictureList = feedService.getFeedPictureList();
        return instagramFeedPictureList;
    }
}
