package me.dragon.biz.dao.impl;

import me.dragon.base.dao.GenericDao;
import me.dragon.biz.dao.InstagramDao;
import me.dragon.biz.entity.InstagramFeed;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dragon on 4/27/2017.
 */
@Repository
public class InstagramDaoImpl extends GenericDao implements InstagramDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<InstagramFeed> getInstagramFeedList() {
        List<InstagramFeed> list = null;
        try {
            String sql = "select f.date,f.caption,p.pic_real_path as pic from instagram_feed as f,instagram_feed_picture as p where f.date = p.date order by f.date desc limit 20";
            list = findListWithSQL(sql, -1, -1, InstagramFeed.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
