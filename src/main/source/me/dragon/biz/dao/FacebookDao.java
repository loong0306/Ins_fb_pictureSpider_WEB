package me.dragon.biz.dao;

import me.dragon.base.dao.BaseDao;
import me.dragon.biz.entity.FacebookFeed;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dragon on 4/26/2017.
 */
@Repository
public interface FacebookDao extends BaseDao{
    List<FacebookFeed> getFacebookFeedList();
}
