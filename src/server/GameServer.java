package server;

import client.ClientInterface;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {
    private List<ClientInterface> connectedClients = new ArrayList<>();
    private int connectedClientCount = 0;

    public GameServer() throws RemoteException {
    }


    public void handleClientConnection(ClientInterface client, String playerName) throws RemoteException {
        connectedClients.add(client);
        connectedClientCount++;
        System.out.println("Client connected. Total clients: " + getConnectedClientCount());
        for (ClientInterface clients : connectedClients) {
            clients.receiveMessage(playerName + " has joined!");
        }
    }

     public void handleClientDisconnection(ClientInterface client, String playerName) throws RemoteException {
        connectedClients.remove(client);
        connectedClientCount--;
        System.out.println("Client disconnected. Total clients: " + getConnectedClientCount());
         for (ClientInterface clients : connectedClients) {
             clients.receiveMessage(playerName + " has left!");
         }
    }

    @Override
    public void sendMessage(String msg) throws RemoteException {
        for (ClientInterface client : connectedClients) {
            client.receiveMessage(msg);
        }
    }
    @Override
    public void sendDrawing(List<Point2D> points, List<Path2D.Float> paths) throws RemoteException {
        for (ClientInterface client : connectedClients) {
            client.sendDrawing(points, paths);
        }
    }

    public int getConnectedClientCount() {
        return connectedClientCount;
    }
}

