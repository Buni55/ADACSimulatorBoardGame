package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void updateLabelOnClient(String newText) throws RemoteException;
    void receiveMessage(String message) throws RemoteException;
}
