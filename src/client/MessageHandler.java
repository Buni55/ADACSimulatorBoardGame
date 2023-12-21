package client;

import server.GameServerInterface;

import java.rmi.RemoteException;

public class MessageHandler {
    private GameServerInterface server;
    private String playerName;

    private GameClient client;

    public MessageHandler(GameClient client, GameServerInterface server, String playerName, GameClientGUI gui) {
        this.server = server;
        this.playerName = playerName;
    }

    public void sendMessage(String message) {
        String formattedMessage = playerName + ": " + message;
        try {
            server.sendMessage(formattedMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Failed to send message.");
        }
    }

    public void disconnectionMessage(GameClient client, String playerName) {
        try {
            server.handleClientDisconnection(client, playerName);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Failed to handle client disconnection.");
        }
    }
}
