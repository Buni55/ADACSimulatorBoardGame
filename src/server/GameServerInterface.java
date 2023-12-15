package server;

import client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServerInterface extends Remote {

    void handleClientConnection(ClientInterface client, String playerName) throws RemoteException;
    void handleClientDisconnection(ClientInterface client, String playerName) throws RemoteException;
    void sendMessage(String msg) throws RemoteException;
}


