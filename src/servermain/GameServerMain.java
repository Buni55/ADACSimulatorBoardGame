package servermain;

import server.GameServer;
import server.GameServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameServerMain {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(5555);
            GameServerInterface gameServer = new GameServer();
            registry.rebind("GameServer", gameServer);

            System.out.println("GameServer is running.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
