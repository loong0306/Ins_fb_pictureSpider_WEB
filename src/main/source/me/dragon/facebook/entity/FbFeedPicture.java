package me.dragon.facebook.entity;

import javax.persistence.*;

/**
 * Created by dragon on 4/18/2017.
 */
@Entity
@Table(name = "fb_feed_picture")
public class FbFeedPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name="full_picture", length=255, nullable=true)
    private String full_picture;
    @Column(name="pic_real_path", length=255, nullable=true)
    private String picRealPath;

    public String getPicRealPath() {
        return picRealPath;
    }

    public void setPicRealPath(String picRealPath) {
        this.picRealPath = picRealPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_picture) {
        this.full_picture = full_picture;
    }

    @Override
    public String toString() {
        return "FbFeedPicture{" +
                "id='" + id + '\'' +
                ", full_picture='" + full_picture + '\'' +
                ", picRealPath='" + picRealPath + '\'' +
                '}';
    }
}
