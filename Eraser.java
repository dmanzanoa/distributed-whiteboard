import javax.swing.*;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Erase.java
 * Generate the eraser object for the erase functionality in the whtieboard.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class Eraser implements Shapes {
	private Vector<Point> points;
    private int Size;
    private Color color;


    public Eraser(Point startL, Point endL, Color color, int Size) {
    	this.points =  new Vector <Point>();
    	this.points.add(startL);
    	this.points.add(endL);
    	this.Size = Size;
    	this.color = color;
    	}
    
    public void PointAdd(Point endL) {
    	this.points.add(endL);
    	
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(this.Size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		for (int i = 0; i < this.points.size() - 1; i++) {
			g.drawLine(this.points.get(i).getX(), this.points.get(i).getY(), this.points.get(i + 1).getX(), this.points.get(i + 1).getY());
		}
    }
    
}