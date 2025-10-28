import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * WhiteboardPanel.java
 * Class responsible of retrieve shapes from the WhiteboardManager and 
 * draw them into the canvas/panel
 *
 * 
 * 
 */
    public class WhiteBoardPanel extends JPanel {
        private WhiteboardManager whiteBoardManager;
        private Color color;
        private Color background = new Color(255,255,255);
        
    	public WhiteBoardPanel(WhiteboardManager whiteBoardManager) {
    		super();
    		this.whiteBoardManager = whiteBoardManager;
    		this.setBackground(this.background);
    	}
    	
    	@Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
        	Graphics2D g2d = (Graphics2D) graphics;
    		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
    		for (Shapes shapes : whiteBoardManager.getPaintStoring()) {
    			shapes.draw(g2d);
    		}
        }
        
        public Color getbackgGroundColor() {
        	return this.background;
        }
        
        
       
}