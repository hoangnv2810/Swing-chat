package com.chatappclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
    public String getUsername(int id) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        String sql = "SELECT username FROM user WHERE user_id = " + id + " ;";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            return rs.getString("username");

        } catch (Exception ec) {
            ec.printStackTrace();
        }
        return "";
    }
}
