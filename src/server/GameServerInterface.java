package server;

import client.ClientInterface;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GameServerInterface extends Remote {

    void handleClientConnection(ClientInterface client, String playerName) throws RemoteException;
    void handleClientDisconnection(ClientInterface client, String playerName) throws RemoteException;
    void sendMessage(String msg) throws RemoteException;
    void sendDrawing(List<Point2D> points, List<Path2D.Float> paths) throws RemoteException;
}


