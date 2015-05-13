package com.aj.particle.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keyPresses = new boolean[256];
	public boolean space, up, down, left, right;
	
	public void update() {
		space = keyPresses[KeyEvent.VK_SPACE];
		up = keyPresses[KeyEvent.VK_UP];
		down = keyPresses[KeyEvent.VK_DOWN];
		left = keyPresses[KeyEvent.VK_LEFT];
		right = keyPresses[KeyEvent.VK_RIGHT];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// if a key is pressed then set the key pressed index to true
		keyPresses[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// set the key pressed to false
		keyPresses[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
