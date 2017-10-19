package me.dragon.facebook.service.impl;

import me.dragon.facebook.entity.FbFeed;
import me.dragon.facebook.entity.FbFeedPicture;
import me.dragon.facebook.service.FeedService;
import me.dragon.biz.utils.DatabaseUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dragon on 4/18/2017.
 */
@Service
@Transactional
public class FeedServiceImpl implements FeedService {

    DatabaseUtil databaseUtil = new DatabaseUtil();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // 获取本地动态
    @Override
    public List<FbFeed> getFeedList(){
        List<FbFeed> fbFeedList = new ArrayList<FbFeed>();
        try{
            conn = databaseUtil.getConnection();
            String sql = "select * from fb_feed order by created_time desc";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FbFeed fbFeed = new FbFeed();
                fbFeed.setId(rs.getString(1));
                fbFeed.setCreated_time(rs.getString(2));
                fbFeed.setMessage(rs.getString(3));
                fbFeedList.add(fbFeed);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,rs);
        }
        return fbFeedList;
    }

    @Override
    public List<FbFeedPicture> getFeedPictureList() {
        List<FbFeedPicture> fbFeedPictureList = new ArrayList<FbFeedPicture>();
        try{
            conn = databaseUtil.getConnection();
            String sql = "select * from fb_feed_picture";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                FbFeedPicture fbFeedPicture = new FbFeedPicture();
                fbFeedPicture.setId(rs.getString(1));
                fbFeedPicture.setFull_picture(rs.getString(2));
                fbFeedPicture.setPicRealPath(rs.getString(3));
                fbFeedPictureList.add(fbFeedPicture);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,rs);
        }
        return fbFeedPictureList;
    }

    // 新增动态
    @Override
    public void saveFeedList(List<FbFeed> fbFeedList, List<FbFeedPicture> fbFeedPictureList) {
        try{
            conn = databaseUtil.getConnection();
            String sql = "insert into fb_feed (id,created_time,message) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            for (FbFeed fbFeed : fbFeedList) {
                ps.setString(1, fbFeed.getId());
                ps.setString(2, fbFeed.getCreated_time());
                ps.setString(3, fbFeed.getMessage());
                ps.executeUpdate();
            }
            String sql2 = "insert into fb_feed_picture (id,full_picture,pic_real_path) values (?,?,?)";
            ps = conn.prepareStatement(sql2);
            for (FbFeedPicture fbFeedPicture : fbFeedPictureList) {
                ps.setString(1, fbFeedPicture.getId());
                ps.setString(2, fbFeedPicture.getFull_picture());
                ps.setString(3, fbFeedPicture.getPicRealPath());
                ps.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,null);
        }
    }

    @Override
    public FbFeedPicture needFbFeedPicUpdate(String id) {
        FbFeedPicture fbFeedPicture = new FbFeedPicture();
        try{
            conn = databaseUtil.getConnection();
            String sql = "select * from fb_feed_picture where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                fbFeedPicture.setId(rs.getString(1));
                fbFeedPicture.setFull_picture(rs.getString(2));
                fbFeedPicture.setPicRealPath(rs.getString(3));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,rs);
        }
        return fbFeedPicture;
    }

    @Override
    public void updateLocalFeedPic(String id, String picRealPath) {
        try{
            conn = databaseUtil.getConnection();
            String sql = "update fb_feed_picture set pic_real_path = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, picRealPath);
            ps.setString(2, id);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            databaseUtil.getFree(conn,ps,null);
        }
    }
}
