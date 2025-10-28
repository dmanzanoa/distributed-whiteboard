import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/***
 * Student Name: Daniel Manzano Aguayo
 * Student ID: 1095549
 * 
 * Shapes.java
 * Interface class with just one function for drawing. Is the parent of all the figure classes.
 * 
 * 
 */
interface Shapes  extends Serializable{
    void draw(Graphics2D g);
}