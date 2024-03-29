package com.chatappclient;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainServer extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainServer frame = new MainServer();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainServer() throws UnknownHostException {
        setTitle("Chat server");

        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 308, 211);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JButton start = new JButton("Start server");
        start.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JPanel panel = new JPanel();
        JTextField jTextField = new JTextField();
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(90)
                                .addComponent(start, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addGap(81))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(43)
                                .addComponent(start, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                .addContainerGap())
        );

        JLabel text = new JLabel("");
        text.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        jTextField.setEditable(false);
        Inet4Address ip = (Inet4Address) Inet4Address.getLocalHost();
        jTextField.setText("IP Server: " + ip.getHostAddress());
        
        panel.add(text);
        panel.add(jTextField);
        contentPane.setLayout(gl_contentPane);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread t = new Thread() {
                    public void run() {
                        try {
                            new Server();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                };
                t.start();

                start.setEnabled(false);
                text.setText("Start server successful on port " + 8082);
                text.setForeground(Color.green);
            }
        });
    }

}
