package com.aj.particle.entity;

import com.aj.particle.graphics.Screen;

public class GravityHole {

	private double posX;
	private double posY;
	
	private Screen screen;
	
	public GravityHole(int posX, int posY, Screen screen) {
		this.posX = posX;
		this.posY = posY;
		
		this.screen = screen;
	}
	
	public void render() {
		for (int y = (int) (posY - 10); y < posY + 10; y++) {
			if (y < 0 || y >= screen.getHeight()) break;
			for (int x = (int) (posX - 10); x < posX + 10; x++) {
				if (x < 0 || x >= screen.getWidth()) break;		
				int cx = (int) (y - posY);
				int cy = (int) (x - posX);
				float r = 10 * 10;
				if ((cx*cx + cy*cy) <= r) {
					screen.pixels[x + y * screen.getWidth()] = 0x222222;
				} 
			}
		}
	}
	
	public double getX() {
		return posX;
	}
	
	public double getY() {
		return posY;
	}
	
}
