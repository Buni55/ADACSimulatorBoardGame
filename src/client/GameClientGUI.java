package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameClientGUI {
    private GameClient gameClient;
    private JTextArea chatArea;
    private JTextField messageField;
    private DrawingPanel drawingPanel;

    public GameClientGUI(GameClient gameClient) {
        this.gameClient = gameClient;
        createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Chat Client: ");

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });


        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel chatInputPanel = new JPanel(new BorderLayout());
        chatInputPanel.add(messageField, BorderLayout.CENTER);
        chatInputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(chatInputPanel, BorderLayout.SOUTH);

        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatPanel, drawingPanel);
        splitPane.setResizeWeight(0.3);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameClient.handleClientDisconnection(gameClient);
                System.out.println("Disconnected");
                System.exit(0);
            }
        });
    }



    public void appendToChatArea(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        chatArea.append("[" + timestamp + "] " + message + "\n");
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            gameClient.sendMessage(message);
            messageField.setText("");
        }
    }
}

