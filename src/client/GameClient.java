package client;

import server.GameServerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameClient extends JFrame implements ClientInterface {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private GameServerInterface server;
    private String playerName;

    public GameClient() {
        // Create a login dialog
        playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Login", JOptionPane.PLAIN_MESSAGE);

        if (playerName == null) {
            // User canceled the login, so exit the application
            System.exit(0);
        }

        setTitle("Chat Client: " + playerName);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendButton = new JButton("Send");

        setLayout(new BorderLayout());
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 5555);
            server = (GameServerInterface) registry.lookup("GameServer");
            System.out.println("Connected to the server.");
            ClientInterface clientStub = (ClientInterface) UnicastRemoteObject.exportObject(this, 0);
            server.handleClientConnection(clientStub, playerName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to the server.");
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            try {
                server.sendMessage(playerName + ": " + message);
                messageField.setText("");
            } catch (RemoteException e) {
                e.printStackTrace();
                System.out.println("Failed to send message.");
            }
        }
    }

    @Override
    public void updateLabelOnClient(String newText) throws RemoteException {
        // Handle label updates if needed
    }

    public void appendToChatArea(String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        chatArea.append("[" + timestamp + "] " + message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameClient();
        });
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        appendToChatArea(message);
    }
}
