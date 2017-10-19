package me.dragon.facebook.worker;

import com.alibaba.fastjson.JSON;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.internal.org.json.JSONObject;
import me.dragon.base.utils.FileUtil;
import me.dragon.facebook.entity.FbFeed;
import me.dragon.facebook.entity.FbFeedPicture;
import me.dragon.facebook.utils.SaveFacebookPicByURL;
import me.dragon.facebook.worker.impl.GemFacebookFeedImpl;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GemFacebookFeed {

    private final Logger logger = Logger.getLogger(this.getClass());
    private GemFacebookFeedImpl gemFacebookFeedImpl = new GemFacebookFeedImpl();
    private SaveFacebookPicByURL saveFacebookPicByURL = new SaveFacebookPicByURL();

    public void flushFacebook(){
        try {
            // 获取数据库数据
            List<FbFeed> localFeedList = gemFacebookFeedImpl.getFeedListByLocal();
            // 连接Facebook - 获取动态
            Facebook facebook = new FacebookFactory().getInstance();
            RawAPIResponse res = facebook.callGetAPI("/13062516037/feed");
            JSONObject object = res.asJSONObject();
            Object result = object.get("data");
            String resultJSON = result.toString();
            List<FbFeed> fbFeedList = JSON.parseArray(resultJSON,FbFeed.class);
            // 获取动态绑定图片
            RawAPIResponse resPic = facebook.callGetAPI("13062516037/feed?fields=full_picture&limit=50");
            JSONObject objectPic = resPic.asJSONObject();
            Object resultPic = objectPic.get("data");
            String resultJSONPic = resultPic.toString();
            List<FbFeedPicture> fbNeedPic_Flag_List = JSON.parseArray(resultJSONPic,FbFeedPicture.class);
            List<FbFeedPicture> fbFeedPictureList = new ArrayList<FbFeedPicture>();
            for (FbFeed fbFeed : fbFeedList) {
                String fbNeedId = fbFeed.getId();
                for (FbFeedPicture fbFeedPicture : fbNeedPic_Flag_List) {
                    String fbNeedPictureId = fbFeedPicture.getId();
                    if (fbNeedId.equals(fbNeedPictureId)){
                        // 拷贝图片
                        if (0 == localFeedList.size()){
                            String picRealPath = saveFacebookPicByURL.saveToFile(fbFeedPicture.getFull_picture());
                            // 另存为本地项目图库
                           /* String fileName = picRealPath.substring(19,picRealPath.length());
                            File srcFile = new File(picRealPath);
                            System.out.println(srcFile.getName());
                            File destFile = new File("GemFeeds/src/main/webapp/img/facebook_gem_pics/" + fileName);
                            FileUtils.copyFile(srcFile,destFile);*/

                            fbFeedPicture.setPicRealPath(picRealPath);
                            fbFeedPictureList.add(fbFeedPicture);
                        }else{
                            // 需要更新的图片
                            fbFeedPicture.setPicRealPath("new");
                            fbFeedPictureList.add(fbFeedPicture);
                        }
                    }
                }
            }
            /**
             * 获取数据库数据
             * 1.判断是否没有数据
             * 2.是否需要更新数据
             */
            if(0 == localFeedList.size()){
                // 新增动态
                gemFacebookFeedImpl.saveFeedList(fbFeedList,fbFeedPictureList);
            }else{
                // 本地最后一条动态ID
                FbFeed localFbFeed = localFeedList.get(0);
                String localLastId = localFbFeed.getId();
                // 线上最后一条动态ID
                FbFeed lineFbFeed = fbFeedList.get(0);
                String lineLastId = lineFbFeed.getId();
                // 对比动态
                if (localLastId.equals(lineLastId)){
                    // 不更新动态
                    logger.info("*连接Facebook结束，本时间节点没有可用更新");
                    List<FbFeedPicture> localFeedPicList = gemFacebookFeedImpl.getFeedPicListByLocal();
                    for (FbFeedPicture localPic : localFeedPicList) {
                        if (null == localPic.getPicRealPath()){
                            String picRealPath = saveFacebookPicByURL.saveToFile(localPic.getFull_picture());
                            // 另存为本地项目图库

                            gemFacebookFeedImpl.updateLocalFeedPic(localPic.getId(),picRealPath);
                            logger.info("*更新了id为：" + localPic.getId() + "的历史动态绑定的图片库");
                        }
                    }
                }else{
                    // 筛选新动态
                    for (FbFeed local : localFeedList) {
                        Iterator<FbFeed> itFeed = fbFeedList.iterator();
                        while(itFeed.hasNext()){
                            FbFeed line = itFeed.next();
                            if((local.getId()).equals(line.getId())){
                                itFeed.remove();
                            }
                        }
                        Iterator<FbFeedPicture> itFeedPic = fbFeedPictureList.iterator();
                        while(itFeedPic.hasNext()){
                            FbFeedPicture lineFeedPic = itFeedPic.next();
                            if((local.getId()).equals(lineFeedPic.getId())){
                                itFeedPic.remove();
                            }
                        }
                    }
                    // 拷贝需要更新的图片，并保存数据
                    for (FbFeedPicture lineFeedPic : fbFeedPictureList) {
                        if ((lineFeedPic.getPicRealPath()).equals("new")){
                            String picRealPath = saveFacebookPicByURL.saveToFile(lineFeedPic.getFull_picture());
                            // 另存为本地项目图库

                            lineFeedPic.setPicRealPath(picRealPath);
                        }
                    }
                    if(fbFeedList.size() > 0){
                        for (FbFeed fbFeed : fbFeedList){
                            logger.info("*新增了id为：" + fbFeed.getId() + "的新动态");
                        }
                        // 更新动态到本地
                        gemFacebookFeedImpl.saveFeedList(fbFeedList,fbFeedPictureList);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}