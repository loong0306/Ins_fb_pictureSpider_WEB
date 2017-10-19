package me.dragon.facebook.entity;

import javax.persistence.*;

/**
 * Created by dragon on 4/18/2017.
 */
@Entity
@Table(name = "fb_feed")
public class FbFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name="created_time", length=100, nullable=true)
    private String created_time;
    @Column(name="message", length=255, nullable=true)
    private String message;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FbFeed{" +
                "id='" + id + '\'' +
                ", created_time='" + created_time + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
