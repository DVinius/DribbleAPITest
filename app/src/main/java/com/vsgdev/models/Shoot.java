package com.vsgdev.models;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by franklin on 23/09/17.
 */

public class Shoot {
    private String title;
    private Integer viewCount;
    private String createdAt;
    private String description;
    private Integer commentsCount;
    private String teaserImageUrl;
    private String normalImageUrl;

    public Shoot(String title, String createdAt, Integer viewCount) {
        this.title = title;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

    public String getTeaserImageUrl() {
        return teaserImageUrl;
    }

    public void setTeaserImageUrl(String teaserImageUrl) {
        this.teaserImageUrl = teaserImageUrl;
    }

    public String getNormalImageUrl() {
        return normalImageUrl;
    }

    public void setNormalImageUrl(String normalImageUrl) {
        this.normalImageUrl = normalImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }
}
