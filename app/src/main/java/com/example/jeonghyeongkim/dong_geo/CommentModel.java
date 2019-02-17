package com.example.jeonghyeongkim.dong_geo;

import java.io.Serializable;

/**
 * Created by User on 8/22/2017.
 */

public class CommentModel implements Serializable {
    private String userid;
    private String commentId;
    private String timeCreated;
    private String state;
    private String comment;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public CommentModel() {
    }


    public CommentModel(String username, String timeCreated, String comment, String userid, String commentId, String state) {

        this.userid=userid;
        this.commentId=commentId;
        this.username = username;
        this.timeCreated = timeCreated;
        this.comment = comment;
        this.state = state;
    }

    public String getUseruid() {
        return userid;
    }

    public void setUseruid(String userid) {
        this.userid = userid;
    }


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}
