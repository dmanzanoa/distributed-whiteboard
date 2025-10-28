import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Line.java
 * Generate line objects for the Line mode in the whiteboard
 *  
 * 
 */

class Line implements Shapes {
    private Color color;
    private Point linePointsStart;
    private Point linePointsEnds;


    public Line(Point startL, Point endL, Color color) {
    	this.color = color;
    	Point temporal1 = null;
    	Point temporal2 = null; 
    	temporal1 = startL;
    	temporal2 = endL;
    	this.linePointsStart = new Point(temporal1.getX(), temporal1.getY());
    	this.linePointsEnds = new Point(temporal2.getX(), temporal2.getY());
    	}
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.drawLine(linePointsStart.getX(), linePointsStart.getY(), linePointsEnds.getX(), linePointsEnds.getY());
    }

}