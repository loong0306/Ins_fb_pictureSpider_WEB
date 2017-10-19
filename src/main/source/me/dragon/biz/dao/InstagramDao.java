package me.dragon.biz.dao;

import me.dragon.base.dao.BaseDao;
import me.dragon.biz.entity.InstagramFeed;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dragon on 4/27/2017.
 */
@Repository
public interface InstagramDao extends BaseDao {
    List<InstagramFeed> getInstagramFeedList();
}
