package com.aj.particle.input;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener {

	private int x = -1;
	private int y = -1;
	private int mouseButton = -1;
	public int oneClick = -1;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getButton() {
		return mouseButton;
	}
	
	public void mouseClicked(MouseEvent e) {
		oneClick = e.getButton();
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mouseButton = -1;
		oneClick = -1;
	}

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	
	
}
