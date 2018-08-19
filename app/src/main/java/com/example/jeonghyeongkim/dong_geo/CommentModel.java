package com.example.jeonghyeongkim.dong_geo;

import java.io.Serializable;

/**
 * Created by User on 8/22/2017.
 */

public class CommentModel implements Serializable {
    private String useruid;
    private String commentId;
    private long timeCreated;
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


    public CommentModel(String username, long timeCreated, String comment) {

        this.username = username;
        this.timeCreated = timeCreated;
        this.comment = comment;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
