package com.chatappclient.model;

import java.sql.Timestamp;

public class MessageGroup {
    private int id;
    private String message;
    private Group group;
    private User user;
    private Timestamp timeCreated;

    public MessageGroup() {
    }

    public MessageGroup(int id, String message, Group group, User user, Timestamp timeCreated) {
        this.id = id;
        this.message = message;
        this.group = group;
        this.user = user;
        this.timeCreated = timeCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }
    
    
    
}
