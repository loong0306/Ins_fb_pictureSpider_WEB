package me.dragon.instagram.service;

import me.dragon.instagram.entity.InstagramFeed;
import me.dragon.instagram.entity.InstagramFeedPicture;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dragon on 4/24/2017.
 */
@Service
@Transactional
public interface InsFeedService {
    List<InstagramFeed> getFeedList();
    void saveFeedList(List<InstagramFeed> instagramFeedList,List<InstagramFeedPicture> instagramFeedPictureList);
    void updateLocalFeedPic(String date, String picRealPath);
    List<InstagramFeedPicture> getFeedPictureList();
}
