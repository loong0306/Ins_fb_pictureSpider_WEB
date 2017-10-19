package me.dragon.biz.service.impl;

import me.dragon.biz.dao.InstagramDao;
import me.dragon.biz.entity.InstagramFeed;
import me.dragon.biz.service.InstagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 4/27/2017.
 */
@Service
@Transactional
public class InstagramServiceImpl implements InstagramService {

    @Autowired
    private InstagramDao instagramDao;

    @Override
    public List<InstagramFeed> getInstagramFeedList() {
        List<InstagramFeed> instagramFeedList = new ArrayList<InstagramFeed>();
        instagramFeedList = instagramDao.getInstagramFeedList();
        return instagramFeedList;
    }
}
