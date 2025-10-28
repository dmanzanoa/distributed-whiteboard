import javax.swing.*;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Erase.java
 * Generate the FreeDraw object for the free draw functionality in the whtieboard.
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

class FreeHandDraw implements Shapes {
	private Vector<Point> pointsfree;
    private Color color;


    public FreeHandDraw(Point startL, Point endL, Color color) {
    	this.color = color;
    	this.pointsfree =  new Vector <Point>();
    	this.pointsfree.add(startL);
    	this.pointsfree.add(endL);
    	this.color = color;
    	}
    
    public void PointAdd(Point endL) {
    	this.pointsfree.add(endL);
    	
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
		for (int i = 0; i < this.pointsfree.size() - 1; i++) {
			g.drawLine(this.pointsfree.get(i).getX(), this.pointsfree.get(i).getY(), this.pointsfree.get(i + 1).getX(), this.pointsfree.get(i + 1).getY());
		}
    }
    

    public Color getColor() {
    	return this.color;
    }
}