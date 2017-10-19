package me.dragon.biz.service.impl;

import me.dragon.biz.dao.FacebookDao;
import me.dragon.biz.entity.FacebookFeed;
import me.dragon.biz.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 4/26/2017.
 */
@Service
@Transactional
public class FacebookServiceImpl implements FacebookService{

    @Autowired
    private FacebookDao facebookDao;

    @Override
    public List<FacebookFeed> getFacebookFeedList() {
        List<FacebookFeed> facebookFeedList = new ArrayList<FacebookFeed>();
        facebookFeedList = facebookDao.getFacebookFeedList();
        return facebookFeedList;
    }
}
