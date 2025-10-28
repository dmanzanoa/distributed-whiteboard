import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Rectangle.java
 * Class used to generate the rectangles objects. Mode to draw rectangles over the white board.
 * 
 * 
 */
class Rectangle implements Shapes {
    private Color color;
    private int x;
    private int y;
    private int width;
    private int height;
    private Point RectPoints;

    public Rectangle(Point start, Point end, Color color) {
        this.color = color;
        Point p1 = null;
        Point p2 = null;
        p1 = start;
        p2 = end;
        this.x = Math.min(p1.getX(), p2.getX()); 
        this.y = Math.min(p1.getY(), p2.getY());
        this.width = Math.abs(start.getX() - end.getX());
        this.height = Math.abs(start.getY() - end.getY());
        this.RectPoints = new Point(this.x, this.y);
        
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawRect(RectPoints.getX(), RectPoints.getY(), this.width, this.height);
    }
    

}