package client;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    void handleClientDisconnection(GameClient client) throws RemoteException;

    void sendDrawing(List<Point2D> points, List<Path2D.Float> paths) throws RemoteException;
}
