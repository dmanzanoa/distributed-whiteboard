import java.awt.Graphics2D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * InputText.java
 * Generate the InputText object for writing over whiteboard
 * 
 */

public class InputText implements Shapes {
	
	private Point p;
	private String t;
	private Color color;
	public InputText(String t, Point startP, Color color){
		this.t = t;
		this.p = startP;
		this.color = color;
	}
	
	public InputText(Point startP){
		this.t = "";
		this.p = startP;
	}
	
	public void setText(String text){
		this.t = text;
	}
	
	public Point getPoints() {
		return this.p;
	}
    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.drawString(this.t, this.p.getX(), this.p.getY());
    }
    
    public String printing() {
    	return "hello";
    }

}
