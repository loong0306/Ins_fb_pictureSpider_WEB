package me.dragon.biz.service;

import me.dragon.base.service.BaseService;
import me.dragon.biz.entity.InstagramFeed;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dragon on 4/27/2017.
 */
@Service
@Transactional
public interface InstagramService extends BaseService {
    List<InstagramFeed> getInstagramFeedList();
}
