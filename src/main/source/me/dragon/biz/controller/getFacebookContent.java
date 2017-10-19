package me.dragon.biz.controller;

import me.dragon.base.controller.GenericController;
import me.dragon.base.controller.RestControllerDemo;
import me.dragon.biz.entity.FacebookFeed;
import me.dragon.biz.service.FacebookService;
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
 * Created by dragon on 4/26/2017.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "api")
public class getFacebookContent extends GenericController {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private FacebookService facebookService;

    @RequestMapping("/getFacebook.do")
    public String word(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        String timeStr = sdf.format(new Date());
        System.out.println("时间：[" + timeStr + "] 用户访问：" + response);
        List<FacebookFeed> facebookFeedList = new ArrayList<FacebookFeed>();
        try {
            facebookFeedList = facebookService.getFacebookFeedList();
            ajaxParam("T","",facebookFeedList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
