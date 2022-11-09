package com.chatappclient.model;


public class Friend {
    private int friendOne;
    private int friendTwo;
    private int status;

    public Friend() {
    }

    public Friend(int friendOne, int friendTwo, int status) {
        this.friendOne = friendOne;
        this.friendTwo = friendTwo;
        this.status = status;
    }

    public int getFriendOne() {
        return friendOne;
    }

    public void setFriendOne(int friendOne) {
        this.friendOne = friendOne;
    }

    public int getFriendTwo() {
        return friendTwo;
    }

    public void setFriendTwo(int friendTwo) {
        this.friendTwo = friendTwo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
    
}
