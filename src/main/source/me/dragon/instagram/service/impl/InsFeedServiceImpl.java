package me.dragon.instagram.service.impl;

import me.dragon.biz.utils.DatabaseUtil;
import me.dragon.instagram.entity.InstagramFeed;
import me.dragon.instagram.entity.InstagramFeedPicture;
import me.dragon.instagram.service.InsFeedService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 4/24/2017.
 */
@Service
@Transactional
public class InsFeedServiceImpl implements InsFeedService{
    DatabaseUtil databaseUtil = new DatabaseUtil();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @Override
    public List<InstagramFeed> getFeedList() {
        List<InstagramFeed> insFeedList = new ArrayList<InstagramFeed>();
        try{
            conn = databaseUtil.getConnection();
            String sql = "select * from instagram_feed order by date desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InstagramFeed instagramFeed = new InstagramFeed();
                instagramFeed.setDate(rs.getString(1));
                instagramFeed.setCaption(rs.getString(2));
                insFeedList.add(instagramFeed);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,rs);
        }
        return insFeedList;
    }

    @Override
    public void saveFeedList(List<InstagramFeed> instagramFeedList,List<InstagramFeedPicture> instagramFeedPictureList) {
        try{
            conn = databaseUtil.getConnection();
            String sql = "insert into instagram_feed (date,caption) values (?,?)";
            ps = conn.prepareStatement(sql);
            for (InstagramFeed instagramFeed : instagramFeedList) {
                ps.setString(1, instagramFeed.getDate());
                ps.setString(2, instagramFeed.getCaption());
                ps.executeUpdate();
            }
            String sql2 = "insert into instagram_feed_picture (date,thumbnail_src,pic_real_path) values (?,?,?)";
            ps = conn.prepareStatement(sql2);
            for (InstagramFeedPicture instagramFeedPicture : instagramFeedPictureList) {
                ps.setString(1, instagramFeedPicture.getDate());
                ps.setString(2, instagramFeedPicture.getThumbnail_src());
                ps.setString(3, instagramFeedPicture.getPic_real_path());
                ps.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,null);
        }
    }


    @Override
    public void updateLocalFeedPic(String date, String picRealPath) {
        try{
            conn = databaseUtil.getConnection();
            String sql = "update instagram_feed_picture set pic_real_path = ? where date = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, picRealPath);
            ps.setString(2, date);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,null);
        }
    }

    @Override
    public List<InstagramFeedPicture> getFeedPictureList() {
        List<InstagramFeedPicture> fbFeedPictureList = new ArrayList<InstagramFeedPicture>();
        try{
            conn = databaseUtil.getConnection();
            String sql = "select * from instagram_feed_picture";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InstagramFeedPicture instagramFeedPicture = new InstagramFeedPicture();
                instagramFeedPicture.setDate(rs.getString(1));
                instagramFeedPicture.setThumbnail_src(rs.getString(2));
                instagramFeedPicture.setPic_real_path(rs.getString(3));
                fbFeedPictureList.add(instagramFeedPicture);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,rs);
        }
        return fbFeedPictureList;
    }
}
