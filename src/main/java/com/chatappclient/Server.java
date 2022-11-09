package com.chatappclient;

import com.chatappclient.model.MessagePrivate;
import com.chatappclient.model.User;
import com.chatappclient.view.Home;
import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Server {

    private Object lock;
    private ServerSocket serverSocket;
    private Socket socket;
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    public Server() throws IOException {
        try {
            lock = new Object();
            serverSocket = new ServerSocket(8082);
            System.out.println("Server start port 8080");
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                String request = dis.readUTF();
                String username = dis.readUTF();
                String password = dis.readUTF();
                System.out.println("Request from client: " + request);
                if (request.equals("Register")) {
                    try {
                        registerUser(username, password);
                        dos.writeUTF("Sign up successful");
                        dos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Register failed");
                    }
                } else if (request.equals("Log in")) {

                    if (checkLogin(username, password)) {
                        ClientHandler clientHander = new ClientHandler(socket, username, password, true, lock);
                        clientHandlers.add(clientHander);
                        dos.writeUTF("Log in successful");
                        System.out.println("Logi in success");
                        dos.flush();
                        Thread t = new Thread(clientHander);
                        t.start();

                        updateOnlineUsers();
                        System.out.println("finished");

                    } else {
                        System.out.println("Login failed");
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    public boolean isExisted(User username) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        String sql = "SELECT count(1) FROM user where username = '" + username + "''";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ec) {
            ec.printStackTrace();
        }
        return false;
    }

    public boolean checkLogin(String username, String password) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        String sql = "SELECT count(1) FROM user where username = '" + username + "' AND password = '" + password + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                } else {
                    System.out.println("Sai mat khau");
                    return false;
                }
            }
        } catch (Exception ec) {
            ec.printStackTrace();
        }
        return false;
    }

    public void registerUser(String username, String password) {
        DBConnect dbc = new DBConnect();
        Connection conn = dbc.getConnection();
        String sql = "insert into user (username, password) values ('" + username + "', '" + password + "')";
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println(username + "has register success");
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }

    public static void updateOnlineUsers() {
        String message = "";
        for (ClientHandler client : clientHandlers) {
            if (client.getIsLoggedIn() == true) {
                message += ",";
                message += client.getUsername();
            }
        }
        message = message.replaceFirst(",", "");
        System.out.println(message);

        for (ClientHandler client : clientHandlers) {
            if (client.getIsLoggedIn() == true) {
                try {
                    client.getDos().writeUTF("Online users");
                    client.getDos().writeUTF(message);
                    client.getDos().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ClientHandler implements Runnable {

    private Object lock;

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
//    private ObjectOutputStream oos;
    private String username;
    private String password;
    private boolean isLoggedIn;

    public ClientHandler(Socket socket, String username, String password, boolean isLoggedIn, Object lock) throws IOException {
        this.socket = socket;
        this.username = username;
        this.password = password;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
//        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.isLoggedIn = isLoggedIn;
        this.lock = lock;
    }

    public ClientHandler(String username, String password, boolean isLoggedIn, Object lock) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = isLoggedIn;
        this.lock = lock;
    }

    public void setIsLoggedIn(boolean IsLoggedIn) {
        this.isLoggedIn = IsLoggedIn;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
//            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public DataOutputStream getDos() {
        return this.dos;
    }

//    public ObjectOutputStream getOos() {
//        return oos;
//    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = null;

                message = dis.readUTF();
                System.out.println("Message request from client:" + message);
                if (message.equals("Log out")) {

                    dos.writeUTF("Safe to leave");
                    dos.flush();
                    socket.close();
                    this.isLoggedIn = false;
                    Server.updateOnlineUsers();
                    break;
                } else if (message.equals("Text")) {
                    String usersend = dis.readUTF();
                    String receiver = dis.readUTF();
                    String content = dis.readUTF();
                    System.out.println("------------Server nhan------------");
                    System.out.println("usersend:" + usersend);
                    System.out.println("receiver:" + receiver);
                    System.out.println("content:" + content);
                    System.out.println("-----------------------------------");
                    MessagePrivateDAO mpdao = new MessagePrivateDAO();
                    mpdao.insertMessage(usersend, receiver, content);

                    for (ClientHandler client : Server.clientHandlers) {
                        if (client.getUsername().equals(receiver)) {
                            synchronized (lock) {
                                System.out.println("dda synch");
                                System.out.println("--------Server gui di-----------");
                                client.getDos().writeUTF("Text");
                                client.getDos().writeUTF(this.username);
                                client.getDos().writeUTF(content);
                                client.getDos().flush();
                                System.out.println(usersend);
                                System.out.println(this.username);
                                System.out.println(content);
                                System.out.println("-------------------------------");
                                break;
                            }
                        }
                    }
                } else if (message.equals("Get all message private")) {
                    String usersend = dis.readUTF();
                    String userreceive = dis.readUTF();
                    for (ClientHandler client : Server.clientHandlers) {
                        if (client.getUsername().equals(userreceive)) {
                            synchronized (lock) {
                                MessagePrivateDAO messagePrivateDAO = new MessagePrivateDAO();
                                ArrayList<MessagePrivate> listMessagePrivates = messagePrivateDAO.getAllMessagePrivate(usersend, userreceive);
                                System.out.println("--------Server get all message private send to client-----------");

                                client.getDos().writeUTF("Message private between two user");
//                                for (MessagePrivate mp : listMessagePrivates) {
//                                    System.out.println(mp.getId() + " " + mp.getUserSend() + " " + mp.getUserReceive() + " " + mp.getMessage() + " " + mp.getTimeCreated());
//                                }
                                client.getDos().writeUTF(usersend);
                                client.getDos().writeUTF(userreceive);
//                                client.getOos().writeObject(listMessagePrivates);
                                client.getDos().flush();
//                                client.getOos().flush();
                                System.out.println("User send: " + usersend);
                                System.out.println("User receive: " + userreceive);
                                System.out.println("-------------------------------");
                                break;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
