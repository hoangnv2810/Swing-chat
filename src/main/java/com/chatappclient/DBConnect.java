package com.chatappclient;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
    public Connection connection;
    public Connection getConnection(){
        String user = "root";
        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/chatapp";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
