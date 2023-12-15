package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

    private static final int SQUARE_SIZE = 20;
    private static final int SPACING = 5;

    private List<Point2D> points = new ArrayList<>();
    private List<Point2D> selectedPoints = new ArrayList<>();
    private List<Path2D.Float> paths = new ArrayList<>();

    public DrawingPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
/*

                if(points.size() % 4 == 0 && selectedPoints.size() >= 4){
                    selectedPoints.add(e.getPoint());
                    Path2D.Float path = new Path2D.Float();
                    for (int i = points.size() - 4; i < points.size(); i++) {
                        Point2D point = points.get(i);
                        if (i == points.size() - 4) {
                            path.moveTo(point.getX(), point.getY());
                        } else {
                            path.lineTo(point.getX(), point.getY());
                        }
                    }
                }
/*
 */
                if (points.size() % 4 == 0 && selectedPoints.size() < 4) {
                    if(selectedPoints.size() != 0){
                    selectedPoints.add(points.get(points.size()-1));
                    }
                    points.add(e.getPoint());
                    selectedPoints.add(e.getPoint());
                }

                if (selectedPoints.size() == 4) {
                    selectedPoints.clear();
                    Path2D.Float path = new Path2D.Float();

                    for (int i = 0; i < 4; i++) {
                        Point2D point = points.get(points.size() - 4 + i);
                        if (i == 0) {
                            path.moveTo(point.getX(), point.getY());
                        } else {
                            path.lineTo(point.getX(), point.getY());
                        }
                    }
                    paths.add(path);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int rows = getHeight() / (SQUARE_SIZE + SPACING);
        int cols = getWidth() / (SQUARE_SIZE + SPACING);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * (SQUARE_SIZE + SPACING);
                int y = row * (SQUARE_SIZE + SPACING);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        g.setColor(Color.BLUE);
        for (Point2D point : points) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            g.fillOval(x - SQUARE_SIZE / 2, y - SQUARE_SIZE / 2, SQUARE_SIZE, SQUARE_SIZE);
        }

        g.setColor(Color.BLUE);
        for (Point2D point : selectedPoints) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            g.fillOval(x - SQUARE_SIZE / 2, y - SQUARE_SIZE / 2, SQUARE_SIZE, SQUARE_SIZE);
        }

        g.setColor(Color.CYAN);
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(5.0f);
        g2d.setStroke(stroke);

        for (Path2D path : paths) {
            g2d.draw(path);
        }
    }
}


