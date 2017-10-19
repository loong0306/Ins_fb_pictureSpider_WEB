package me.dragon.biz.controller;

import me.dragon.base.controller.GenericController;
import me.dragon.base.utils.PicUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dragon on 4/26/2017.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "comm")
public class PicController extends GenericController {
    private Logger log = Logger.getLogger(this.getClass());
    private String imgUrl;

    /*public void writeByte(byte[] b, int off, int len){
        try {
            response.getOutputStream().write(b, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @RequestMapping("/showImg.do")
    public String showImg() {
        imgUrl = request.getParameter("imgUrl");
        PicUtil thread = new PicUtil(imgUrl,response);
        thread.run();
        /*if (imgUrl != null && !"".equals(imgUrl)) {
            File f = new File(imgUrl);
            InputStream inputStream = null;
            try {
                if (f.exists()) {
                    inputStream = new FileInputStream(f);
                    byte[] b = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(b, 0, 1024)) != -1) {
                        writeByte(b, 0, len);
                    }
                }
            } catch (FileNotFoundException e) {
                log.error("图片文件不存在", e);
            } catch (IOException e) {
                log.error("图片IO错误", e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("图片IO错误", e);
                }
            }
        }*/
        return null;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
