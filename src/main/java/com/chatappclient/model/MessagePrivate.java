package com.chatappclient.model;
import java.io.Serializable;
import java.sql.Timestamp;


public class MessagePrivate{
    private int id;
    private String message;
    private String userSend;
    private String userReceive;

    public MessagePrivate() {
    }

    public MessagePrivate(int id, String message, String userSend, String userReceive) {
        this.id = id;
        this.message = message;
        this.userSend = userSend;
        this.userReceive = userReceive;
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


    
    
    
            
            
            
            
            
            
            
            
            
}
