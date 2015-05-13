package com.aj.particle.entity;

import java.util.Random;

import com.aj.particle.graphics.Screen;

public class Particle {

	private float posX;
	private float posY;
	private int color;
	private int time = 0;
	private int life;
	private float dx;
	private float dy;
	private float accX;
	private float accY;
	private float radius;
	private boolean delete = false;
	private int split;
	private int split_radius;
	
	private Screen screen;
	private Random rand = new Random();

	public Particle(int x, int y, Screen screen, int split, int split_radius) {
		this.posX = x;
		this.posY = y;
		this.screen = screen;
		
		// initialize colour
		color = 0x001133;
		
		// set lifespan of particle
		life = 3000 + Math.abs(rand.nextInt() % 7000);
		
		// set the direction of each particle to travel randomly
		dx = 1.0f - ((rand.nextFloat()*10) % 2.0f);
		dy = 1.0f - ((rand.nextFloat()*10) % 2.0f);

		this.split_radius = split_radius;
//		radius = 1 + (rand.nextInt() % 3) / split_radius;		
		
		radius = 1;
		accX = 0;
		accY = 0;
		
		this.split = split;
	}
	

	public void update() {
		
		// if the particle runs out of time then set delete to true
		time++;
		if (time > life) delete = true;
		
		posX += dx;
		posY += dy;

	}
	
	// if the particles life runs out then return true
	public boolean checkLife() {
		return delete;
	}
	
	// draw particle on screen
	public void render() {
		for (int y = (int) (posY - radius); y <= posY + radius; y++) {
			if (y < 0 || y >= screen.getHeight()) break;
			for (int x = (int) (posX - radius); x <= posX + radius; x++) {
				if (x < 0 || x >= screen.getWidth()) break;		
				int cx = (int) (y - posY);
				int cy = (int) (x - posX);
				float r = radius * radius;
				if ((cx*cx + cy*cy) <= r) {
					// change the colour of the particles based on the amount of particle density
					if (screen.pixels[x + y * screen.getWidth()] > 0xffffff) {
						screen.pixels[x + y * screen.getWidth()] = 0x00ffff;
					} else {
						// get rgb components
						int red = (color >> 16) & 0xff;
						int green = (color >> 8) & 0xff;
						int blue = (color) & 0xff;
						
						// increase each colour component
						red *= 1.001;
						green *= 1.001;
						blue *= 1.0001;
						
						color =  (red << 16) | (green << 8) | blue;
						
						screen.pixels[x + y * screen.getWidth()] += color;
					}
				} 
			}
		}
	}
	
	public float getX() {
		return posX;
	}
	
	public float getY() {
		return posY;
	}
	
	public float getAccX() {
		return accX;
	}
	
	public float getAccY() {
		return accY;
	}
	
	public void setdX(float x) {
		accX = x;
		dx = x;
	}
	
	public void setdY(float y) {
		accY = y;
		dy = y;
	}
	
	public int getSplit() {
		return split;
	}
	
	public int getSplitRadius() {
		return split_radius;
	}
}
