import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;


/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Drawer.java
 * Capture users clicks to generate the objects and shapes
 *
 */
public class Drawer extends MouseAdapter implements ActionListener {
	
	private Point startP, endP;
	private WhiteboardViewer wbv;
	private Color color;
	private String shapeMode = "FreeHand";
	private FreeHandDraw lastFreeHandDraw;
	private Eraser eraser;
	private InputText textElement;
	private int Size;
	
	public Drawer(WhiteboardViewer wbv) {
		lastFreeHandDraw = null;
		this.wbv = wbv;
		color = wbv.getWhiteboardManager().getDrawingColor();
		this.Size = wbv.getWhiteboardManager().getEraserSize();
		this.shapeMode = wbv.getShape();
	}
	
	public void actionPerformed(ActionEvent e) {
		this.wbv.getWhiteboardManager().refresh();
		color = wbv.getWhiteboardManager().getDrawingColor();
		Size = wbv.getWhiteboardManager().getEraserSize();
		shapeMode = wbv.getShape();
	}
	
	public void mousePressed(MouseEvent e) {
		startP = new Point(e.getX(), e.getY());
		color = wbv.getWhiteboardManager().getDrawingColor();
		shapeMode = wbv.getShape();
		Size = wbv.getWhiteboardManager().getEraserSize();
	}
	
	public void mouseReleased(MouseEvent e) {
		endP = new Point(e.getX(), e.getY());
		Shapes shapes = null;
		if(this.shapeMode.equalsIgnoreCase("Circle")) {
			shapes = new Circle(startP, endP, this.color);
		}else if(this.shapeMode.equalsIgnoreCase("Line")){
			shapes = new Line(startP, endP, this.color);
		}else if(this.shapeMode.equalsIgnoreCase("Rectangle")){
			shapes = new Rectangle(startP, endP, this.color);
		}else if(this.shapeMode.equalsIgnoreCase("Oval")){
			shapes = new Oval(startP, endP, this.color);
		}else if(this.shapeMode.equalsIgnoreCase("FreeHand")){
			//shapes = new Oval(startP, endP, color);
			if (lastFreeHandDraw == null) {
				lastFreeHandDraw = new FreeHandDraw(startP, endP, this.color);
			}else {
				lastFreeHandDraw.PointAdd(endP);
			}
		shapes = lastFreeHandDraw;
		lastFreeHandDraw = null;	
		}else if(this.shapeMode.equalsIgnoreCase("Eraser")){
		if (eraser == null) {
			eraser = new Eraser(startP, endP, wbv.getbackgGroundColor(), this.Size);
		}else {
			eraser.PointAdd(endP);
		}
		shapes = eraser;
		eraser = null;	
		}else if(this.shapeMode.equalsIgnoreCase("Text")) {
			JFrame frame = wbv.getFrame();
			String userInput = JOptionPane.showInputDialog(
					frame,
	                "Text:",
	                JOptionPane.INFORMATION_MESSAGE
	            );
			
			if((userInput != null))   
			{			
				InputText textElement = new InputText(userInput, endP, this.color);
				shapes = textElement;
			}

		}else{
			System.err.println("Not Correct Option");
		}
		if(shapes != null) {
				wbv.getWhiteboardManager().addShape(shapes);
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		endP = new Point(e.getX(), e.getY());
		if (this.shapeMode.equals("FreeHand")) {
			if (lastFreeHandDraw == null) {
				lastFreeHandDraw = new FreeHandDraw(startP, endP, this.color);
			}else {
				lastFreeHandDraw.PointAdd(endP);
			} 	
		}
		if (this.shapeMode.equals("Eraser")) {
			if (eraser == null) {
				eraser = new Eraser(startP, endP, wbv.getbackgGroundColor(), this.Size);
			}else {
				eraser.PointAdd(endP);
			} 	
		}
	}
}


