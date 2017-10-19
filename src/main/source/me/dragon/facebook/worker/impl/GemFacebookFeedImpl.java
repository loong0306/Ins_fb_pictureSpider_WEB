package me.dragon.facebook.worker.impl;

import me.dragon.facebook.entity.FbFeed;
import me.dragon.facebook.entity.FbFeedPicture;
import me.dragon.facebook.service.FeedService;
import me.dragon.facebook.service.impl.FeedServiceImpl;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by dragon on 4/18/2017.
 */
public class GemFacebookFeedImpl {
    // 获取本地动态
    public List<FbFeed> getFeedListByLocal() throws SQLException {
        FeedService feedService = new FeedServiceImpl();
        List<FbFeed> feedList = feedService.getFeedList();
        return feedList;
    }

    // 获取本地图片
    public List<FbFeedPicture> getFeedPicListByLocal() throws SQLException {
        FeedService feedService = new FeedServiceImpl();
        List<FbFeedPicture> fbFeedPictureList = feedService.getFeedPictureList();
        return fbFeedPictureList;
    }

    // 新增动态
    public void saveFeedList(List<FbFeed> fbFeedList, List<FbFeedPicture> fbFeedPictureList){
        FeedService feedService = new FeedServiceImpl();
        feedService.saveFeedList(fbFeedList,fbFeedPictureList);
    }

    // 是否需要更新上次没有获取到的图片
    public boolean needFbFeedPicUpdate(String id) {
        FeedService feedService = new FeedServiceImpl();
        FbFeedPicture fbFeedPicture = feedService.needFbFeedPicUpdate(id);
        if(null != fbFeedPicture.getPicRealPath()){
            return true;
        }else{
            return false;
        }
    }

    // 更新历史图片库
    public void updateLocalFeedPic(String id, String picRealPath) {
        FeedService feedService = new FeedServiceImpl();
        feedService.updateLocalFeedPic(id,picRealPath);
    }
}
