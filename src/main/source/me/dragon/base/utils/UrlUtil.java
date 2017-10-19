package me.dragon.base.utils;

import me.dragon.base.core.BaseUtils;

/**
 * Created by dragon on 4/1/2017.
 */
public class UrlUtil {
    public static String getUrl(String suffix){
        String url = "";
        String scheme = BaseUtils.getRequest().getScheme();
        String serverName = BaseUtils.getRequest().getServerName();
        int serverPort = BaseUtils.getRequest().getServerPort();
        String contextPath = BaseUtils.getRequest().getContextPath();

        if(serverPort != 80){
            url = scheme + "://" + serverName + ":" + serverPort + contextPath + suffix;
        }else{
            url = scheme + "://" + serverName + contextPath + suffix;
        }

        return url;
    }
}
