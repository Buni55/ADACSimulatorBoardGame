package server;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve {
    private List<Point> controlPoints = new ArrayList<>();

    public void addControlPoint(Point point) {
        controlPoints.add(point);
    }

    public void draw(Graphics g) {
    }
}
