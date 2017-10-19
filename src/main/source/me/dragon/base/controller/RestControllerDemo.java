package me.dragon.base.controller;

import me.dragon.base.utils.UrlUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dragon on 4/1/2017.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "demo")
public class RestControllerDemo {
    Logger log = Logger.getLogger(RestControllerDemo.class);

    @RequestMapping("/rcd")
    public String word(){
        return "RestController";
    }
}
