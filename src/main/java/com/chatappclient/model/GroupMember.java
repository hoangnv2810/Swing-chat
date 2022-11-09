package com.chatappclient.model;

import java.util.Date;


public class GroupMember {
    private int id;
    private User user;
    private Group group;
    private Date joinedDate;
    private Date leftDate;

    public GroupMember() {
    }

    public GroupMember(int id, User user, Group group, Date joinedDate, Date leftDate) {
        this.id = id;
        this.user = user;
        this.group = group;
        this.joinedDate = joinedDate;
        this.leftDate = leftDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Date getLeftDate() {
        return leftDate;
    }

    public void setLeftDate(Date leftDate) {
        this.leftDate = leftDate;
    }
    
    
}
