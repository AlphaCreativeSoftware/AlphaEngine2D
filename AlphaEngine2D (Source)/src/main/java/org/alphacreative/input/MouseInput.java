package org.alphacreative.input;


import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.alphacreative.math.Vector2D;

public class MouseInput extends MouseAdapter{
	
	public static int X, Y;
	public static boolean LEFT;
	public static boolean RIGHT;
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {LEFT = true;}
		if(e.getButton() == MouseEvent.BUTTON3) {RIGHT = true;}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {LEFT = false;}
		if(e.getButton() == MouseEvent.BUTTON3) {RIGHT = false;}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}

    public static Vector2D GetMousePosition() {
        return new Vector2D(X,Y);
    }
	
}