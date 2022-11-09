package com.chatappclient;

import com.chatappclient.model.MessagePrivate;
import com.chatappclient.view.HomeTest;
import com.chatappclient.view.Widget;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ReadThread extends Thread {

    private Socket socket;
    private HomeTest home;
//    private Obj

    public ReadThread(Socket socket, HomeTest home) {
        try {
            this.socket = socket;
            this.home = home;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (socket.isConnected()) {
                String method = "";
                try {
                    method = home.getDis().readUTF();

                } catch (EOFException e) {
                    e.printStackTrace();
                }
                System.out.println(method);

                if (method.equals("Text")) {
                    String sender = home.getDis().readUTF();
                    String message = home.getDis().readUTF();
                    System.out.println("--------------Nhan tu Server------------");
                    System.out.println(sender);
                    System.out.println(message);
                    System.out.println("[" + sender + "]:" + message);
                    System.out.println("-----------------------------------------");
                    SimpleAttributeSet attributeSet = new SimpleAttributeSet();
                    StyleConstants.setBold(attributeSet, true);
                    StyleConstants.setForeground(attributeSet, Color.RED);
                    home.cleanChatBodyJTextPane(sender);
                    home.insertMessage(sender, message, attributeSet);
                } else if (method.equals("Online users")) {
                    System.out.println(home.getUsername() + ": chu phong");
                    String[] users = home.getDis().readUTF().split(",");
                    boolean isChattingOnline = false;
                    System.out.println("Online: " + users.length);
                    home.getSidebarPanel().removeAll();
                    home.getSidebarPanel().setLayout(new BoxLayout(home.getSidebarPanel(), BoxLayout.Y_AXIS));
                    home.getOnlineUserComboBox().removeAllItems();
                    String listOneline = "ListUser:";
                    String listDuyet = "ListDuyet:";
                    for (String user : users) {
                        listOneline += user;
                        if (user.equals(home.getUsername()) == false) {
                            home.getSidebarPanel().add(new Widget(user, home.getUsername(), home.getDos(), home).getContentPane(), "wrap");
                            home.getOnlineUserComboBox().addItem(user);
                            listDuyet += user;
                        }
                    }
                    System.out.println(listDuyet + ".");
                    System.out.println(listOneline + ".");
                    System.out.println("------------------");

                } else if (method.equals("Message private between two user")) {
                    System.out.println("-------------Receive from server message private-------------");
//                    String usersend = home.getDis().readUTF();
//                    String userreceived = home.getDis().readUTF();
//                    ArrayList<MessagePrivate> listMessagePrivates = new ArrayList<>();

                    System.out.println("------------------------------------------------");
                } else if (method.equals("Safe to leave")) {
                    socket.close();
                    home.getDis().close();
                    home.getDos().close();
                    break;
                }
            }
        } catch (Exception e) {
            try {
                socket.close();
                home.getDis().close();
                home.getDos().close();
            } catch (IOException ex) {
                Logger.getLogger(ReadThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            e.printStackTrace();
        }
    }
}
