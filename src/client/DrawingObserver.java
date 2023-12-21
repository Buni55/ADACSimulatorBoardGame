package client;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.rmi.RemoteException;
import java.util.List;

public interface DrawingObserver {
    void onDrawingChanged(List<Point2D> points, List<Path2D.Float> paths) throws RemoteException;
}
