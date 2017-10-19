package me.dragon.instagram.entity;

import javax.persistence.*;

/**
 * Created by dragon on 4/24/2017.
 */
@Entity
@Table(name = "instagram_feed_picture")
public class InstagramFeedPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String date;
    @Column(name="thumbnail_src", length=255, nullable=true)
    private String thumbnail_src;
    @Column(name="pic_real_path", length=255, nullable=true)
    private String pic_real_path;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail_src() {
        return thumbnail_src;
    }

    public void setThumbnail_src(String thumbnail_src) {
        this.thumbnail_src = thumbnail_src;
    }

    public String getPic_real_path() {
        return pic_real_path;
    }

    public void setPic_real_path(String pic_real_path) {
        this.pic_real_path = pic_real_path;
    }

    @Override
    public String toString() {
        return "InstagramFeedPicture{" +
                "date='" + date + '\'' +
                ", thumbnail_src='" + thumbnail_src + '\'' +
                ", pic_real_path='" + pic_real_path + '\'' +
                '}';
    }
}
