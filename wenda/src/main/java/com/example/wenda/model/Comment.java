package com.example.wenda.model;


import java.util.Date;

/**
 * @author zyy
 * @date 2019/7/6 20:17
 */
public class Comment {
    private int id;
    private int userId;
    private Date createdDate;
    private int entityId;
    private int entityType;
    private String content;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdDate;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdDate = createdTime;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
