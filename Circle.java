import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Circle implements Shapes{
	private Point begp;
	private int diameter;
	private Color color;
	

	public Circle(Point startP, Point endP, Color color) {
		Point temp = null;
		if (startP.getX() > endP.getX()) {
			if (startP.getY() > endP.getY()) {
				temp = endP;
			} else {
				temp = new Point(endP.getX(), startP.getY());
			}
		} else {
			if (startP.getY() < endP.getY()) {
				temp = startP;
			} else {
				temp = new Point(startP.getX(), endP.getY());
			}
		}
		this.diameter = (int) Math.sqrt(Math.pow(startP.getX() - endP.getX(), 2) + Math.pow(startP.getY() - endP.getY(), 2));
		int centerX = temp.getX() + Math.abs(startP.getX() - endP.getX()) / 2;
		int centerY = temp.getY() + Math.abs(startP.getY() - endP.getY()) / 2;
		this.begp = new Point(centerX - this.diameter / 2, centerY - this.diameter / 2);
		this.color = color;
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawArc(begp.getX(), begp.getY(), diameter, diameter, 0, 360);
	}
	
	public int getDiamater() {
		return this.diameter;
	}
	
	public Color getColor() {
		return this.color;
	}
	

}
