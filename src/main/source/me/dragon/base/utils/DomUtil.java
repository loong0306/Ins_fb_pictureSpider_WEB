package me.dragon.base.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by dragon on 4/1/2017.
 */
public class DomUtil {
    //通过Class抓取页面
    public static String getHtmlByClass(String url,String eleName){
        String ret = "";
        try {
            Document doc = Jsoup.connect(url).post();
            Elements els = doc.getElementsByClass(eleName);
            ret = els.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

