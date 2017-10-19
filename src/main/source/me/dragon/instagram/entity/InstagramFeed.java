package me.dragon.instagram.entity;

import javax.persistence.*;

/**
 * Created by dragon on 4/18/2017.
 */
@Entity
@Table(name = "instagram_feed")
public class InstagramFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String date;
    @Column(name="caption", length=1000, nullable=true)
    private String caption;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "InstagramFeed{" +
                "date='" + date + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
