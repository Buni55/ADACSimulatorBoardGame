package client;

import server.GameServerInterface;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class GameClient extends JFrame implements ClientInterface {
    private GameServerInterface server;
    private String playerName;
    private MessageHandler messageHandler;
    private GameClientGUI gameClientGUI;

    public GameClient() {

        //playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Login", JOptionPane.PLAIN_MESSAGE);
        playerName = "Buni";

        if (playerName == null) {
            System.exit(0);
        }

        gameClientGUI = new GameClientGUI(this);

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

        messageHandler = new MessageHandler(this, server, playerName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameClient();
        });
    }
    @Override
    public void receiveMessage(String message) throws RemoteException {
        gameClientGUI.appendToChatArea(message);
    }
    @Override
    public void sendMessage(String message) {
        messageHandler.sendMessage(message);
    }
    @Override
    public void handleClientDisconnection(GameClient client){
        messageHandler.disconnectionMessage(client, playerName);
    }
}
