package me.dragon.facebook.service;

import me.dragon.base.service.BaseService;
import me.dragon.facebook.entity.FbFeed;
import me.dragon.facebook.entity.FbFeedPicture;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dragon on 4/18/2017.
 */
@Service
@Transactional
public interface FeedService extends BaseService {
    List<FbFeed> getFeedList();
    List<FbFeedPicture> getFeedPictureList();
    void saveFeedList(List<FbFeed> fbFeedList, List<FbFeedPicture> fbFeedPictureList);
    FbFeedPicture needFbFeedPicUpdate(String id);
    void updateLocalFeedPic(String id, String picRealPath);
}