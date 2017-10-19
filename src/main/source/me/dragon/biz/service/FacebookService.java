package me.dragon.biz.service;

import me.dragon.base.service.BaseService;
import me.dragon.biz.entity.FacebookFeed;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dragon on 4/26/2017.
 */
@Service
@Transactional
public interface FacebookService extends BaseService{
    List<FacebookFeed> getFacebookFeedList();
}
