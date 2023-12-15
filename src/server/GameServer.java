package server;

import client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {
    private List<ClientInterface> connectedClients = new ArrayList<>();
    private int connectedClientCount = 0;

    public GameServer() throws RemoteException {
    }

    @Override
    public void updateLabel(String newText) throws RemoteException {
        for (ClientInterface client : connectedClients) {
            client.updateLabelOnClient(newText);
        }
    }

    public void handleClientConnection(ClientInterface client, String playerName) throws RemoteException {
        addConnectedClient(client);
        System.out.println("Client connected. Total clients: " + getConnectedClientCount());
        for (ClientInterface clients : connectedClients) {
            clients.receiveMessage(playerName + " joined!");
        }
    }

    public void handleClientDisconnection(ClientInterface client) {
        removeConnectedClient(client);
        System.out.println("Client disconnected. Total clients: " + getConnectedClientCount());
    }

    @Override
    public void sendMessage(String msg) throws RemoteException {
        for (ClientInterface client : connectedClients) {
            client.receiveMessage(msg);
        }
    }

    public void addConnectedClient(ClientInterface client) {
        connectedClients.add(client);
        connectedClientCount++;
    }


    public void removeConnectedClient(ClientInterface client) {
        connectedClients.remove(client);
        connectedClientCount--;
    }
    public int getConnectedClientCount() {
        return connectedClientCount;
    }
}

