package com.chatappclient;

import com.chatappclient.model.MessagePrivate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MessagePrivateDAO {

    public int getIDUser(String username) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        String sql = "SELECT user_id from user WHERE username = '" + username + "';";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getInt("user_id");

        } catch (Exception ec) {
            ec.printStackTrace();
        }
        return 0;
    }

    public void insertMessage(String usersend, String userreceive, String message) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        int idUserSend = getIDUser(usersend);
        int idUserReceive = getIDUser(userreceive);
        String sql = "INSERT INTO chatapp.message_private (message, user_send, user_receive) VALUES ('" + message + "', " + idUserSend + "," + idUserReceive + ");";
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }

    public ArrayList<MessagePrivate> getAllMessagePrivate(String usersend, String userreceive) {
        ArrayList<MessagePrivate> listMessagePrivates = new ArrayList<>();
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        int idUserSend = getIDUser(usersend);
        int idUserReceive = getIDUser(userreceive);
        String sql = "select * from message_private where user_send = " + idUserSend + " and user_receive = " + idUserReceive + " OR user_send = " + idUserReceive + " and user_receive = " + idUserSend;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                listMessagePrivates.add(new MessagePrivate(rs.getInt("id"), rs.getString("message"), rs.getString("user_send"), rs.getString("user_receive")));
                
            }

        } catch (Exception ec) {
            ec.printStackTrace();
        }
        return listMessagePrivates;
    }
}
