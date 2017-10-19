package me.dragon.biz.dao.impl;

import me.dragon.base.dao.GenericDao;
import me.dragon.biz.dao.FacebookDao;
import me.dragon.biz.entity.FacebookFeed;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dragon on 4/26/2017.
 */
@Repository
public class FacebookDaoImpl extends GenericDao implements FacebookDao{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<FacebookFeed> getFacebookFeedList() {
        List<FacebookFeed> list = null;
        try {
            // 长度等于28的为Facebook活动消息。大于28字节为动态
            String sql = "select f.id,f.message,p.pic_real_path as pic from fb_feed as f,fb_feed_picture as p where f.id = p.id and LENGTH(f.id) > 28 order by f.id desc limit 20";
            list = findListWithSQL(sql, -1, -1, FacebookFeed.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
