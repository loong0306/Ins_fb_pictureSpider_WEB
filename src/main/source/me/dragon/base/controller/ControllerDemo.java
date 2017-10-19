package me.dragon.base.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by dragon on 4/1/2017.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(value = "demo")
public class ControllerDemo {
    Logger log = Logger.getLogger(ControllerDemo.class);

    @RequestMapping("/cd")
    public ModelAndView testView() {
        return new ModelAndView("demo");
    }
}
