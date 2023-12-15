package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private Point2D draggingPoint = null;
    private boolean isCurveClosed = false;
    public DrawingPanel() {
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!points.isEmpty()) {
                        points.remove(points.size() - 1);
                        isCurveClosed = false;
                        System.out.println("Point removed");
                        recalculatePaths();
                        repaint();
                    }
                }
            }
        });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    requestFocusInWindow();
                    Point2D clickedPoint = e.getPoint();
                    draggingPoint = findPoint(clickedPoint);

                    if (draggingPoint != null) {
                        if (draggingPoint.equals(points.get(0)) && points.size() % 3 == 0 && points.size() >= 3 && isCurveClosed == false) {
                            closeCurve();
                            draggingPoint = null;
                            repaint();
                        }
                    } else {
                        System.out.println("Point added: " + clickedPoint.getX() + " " +  clickedPoint.getY());
                        points.add(clickedPoint);
                        recalculatePaths();
                        repaint();
                    }
                }


                @Override
                public void mouseReleased(MouseEvent e) {
                    if (draggingPoint != null) {
                        draggingPoint.setLocation(e.getX(), e.getY());
                        System.out.println("MouseReleased: " + e.getX() + " " + getY());
                        draggingPoint = null;
                        repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (draggingPoint != null) {
                        System.out.println("MouseDragged");
                        draggingPoint.setLocation(e.getX(), e.getY());
                        recalculatePaths();
                        repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            });

    }

    private void closeCurve() {
        System.out.println("Im in");
        if (points.size() < 3) return;

        Point2D startingPoint = points.get(points.size() -3);
        Point2D controlPoint1 = points.get(points.size() - 2);
        Point2D controlPoint2 = points.get(points.size() - 1);

        Path2D.Float path = new Path2D.Float();
        path.moveTo(startingPoint.getX(), startingPoint.getY());
        path.curveTo(controlPoint1.getX(), controlPoint1.getY(),
                controlPoint2.getX(), controlPoint2.getY(),
                points.get(0).getX(), points.get(0).getY());
        paths.add(path);
        isCurveClosed = true;
    }


    private Point2D findPoint(Point2D p) {
        for (Point2D point : points) {
            if (point.distance(p) < SQUARE_SIZE) {
                return point;
            }
        }
        return null;
    }

    private void recalculatePaths() {
        paths.clear(); // Clear existing paths
        setPaths();    // Recalculate paths based on current points

        if (isCurveClosed) {
            // Add the closing curve again
            closeCurve();
        }
    }


    private void setPaths() {
        if (points.size() >= 4) {
            paths.clear();

            for (int i = 0; i <= points.size() - 4; i += 3) {
                Path2D.Float path = new Path2D.Float();

                if (i == 0) {
                    path.moveTo(points.get(0).getX(), points.get(0).getY());
                } else {
                    Point2D lastEndPoint = points.get(i);
                    path.moveTo(lastEndPoint.getX(), lastEndPoint.getY());
                }
                Point2D controlPoint1 = points.get(i + 1);
                Point2D controlPoint2 = points.get(i + 2);
                Point2D endPoint = points.get(i + 3);

                path.curveTo(controlPoint1.getX(), controlPoint1.getY(),
                        controlPoint2.getX(), controlPoint2.getY(),
                        endPoint.getX(), endPoint.getY());

                paths.add(path);
            }
        }
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


