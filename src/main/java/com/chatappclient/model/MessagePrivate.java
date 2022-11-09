package com.chatappclient.model;
import java.io.Serializable;
import java.sql.Timestamp;


public class MessagePrivate implements Serializable{
    private int id;
    private String message;
    private String userSend;
    private String userReceive;
    private Timestamp timeCreated;

    public MessagePrivate() {
    }

    public MessagePrivate(int id, String message, String userSend, String userReceive, Timestamp timeCreated) {
        this.id = id;
        this.message = message;
        this.userSend = userSend;
        this.userReceive = userReceive;
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

    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }

    public String getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(String userReceive) {
        this.userReceive = userReceive;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }
    
    
    
            
            
            
            
            
            
            
            
            
}
