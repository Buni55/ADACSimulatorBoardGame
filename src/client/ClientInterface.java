package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    void handleClientDisconnection(GameClient client) throws RemoteException;
}
