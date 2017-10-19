package me.dragon.biz.controller;

import me.dragon.base.controller.GenericController;
import me.dragon.biz.entity.FacebookFeed;
import me.dragon.biz.entity.InstagramFeed;
import me.dragon.biz.service.InstagramService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dragon on 4/27/2017.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "api")
public class getInstagramContent extends GenericController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private InstagramService instagramService;

    @RequestMapping("/getInstagram.do")
    public String word(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        String timeStr = sdf.format(new Date());
        System.out.println("时间：[" + timeStr + "] 用户访问：" + response);
        List<InstagramFeed> instagramFeedList = new ArrayList<InstagramFeed>();
        try {
            instagramFeedList = instagramService.getInstagramFeedList();
            ajaxParam("T","",instagramFeedList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
