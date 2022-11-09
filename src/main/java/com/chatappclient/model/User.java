package com.chatappclient.model;

public class User {
    private int id;
    private User username;
    private User password;
    private int friendCount;
    private User picture;

    public User() {
    }

    public User(int id, User username, User password, int friendCount, User picture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.friendCount = friendCount;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public User getPassword() {
        return password;
    }

    public void setPassword(User password) {
        this.password = password;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public User getPicture() {
        return picture;
    }

    public void setPicture(User picture) {
        this.picture = picture;
    }
    
    
    
    
}
