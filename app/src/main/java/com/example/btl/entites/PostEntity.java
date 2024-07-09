package com.example.btl.entites;

public class PostEntity {
    String id;
    String title;
    String content;
    String userId;
    String restaurant_id;
    String image;
    String created_at;
    String updated_at;
    Boolean status;

    public PostEntity(String id, String title, String content, String userId, String restaurant_id, String image, String created_at, String updated_at, Boolean status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.restaurant_id = restaurant_id;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
    }

    public PostEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
