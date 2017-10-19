package me.dragon.facebook.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;

/**
 * Created by dragon on 4/18/2017.
 */
public class SaveFacebookPicByURL {
    public static String ROOT = File.separator + "facebook_gem_pics".toUpperCase() + File.separator;// 服务器文件根路径

    /**
     * 根据网络地址保存图片
     */
    public String saveToFile(String destUrl) {
        String filePath = ROOT;
        String picPath = null;
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080));
            url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection(proxy);
            httpUrl.setRequestProperty("http.proxyHost", "127.0.0.1");
            httpUrl.setRequestProperty("http.proxyPort", "1080");
            httpUrl.setConnectTimeout(60000);
            httpUrl.setReadTimeout(60000);
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            // 新建文件夹
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdir();
            }
            // 保存图片到本地路径
            picPath = filePath + getUUIDFileName() + ".jpg";
            fos = new FileOutputStream(picPath);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return picPath;
    }

    // UUID命名
    public static String getUUIDFileName() {
        return UUID.randomUUID().toString();
    }
}
