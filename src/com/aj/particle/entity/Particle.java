package com.aj.particle.entity;

import java.util.Random;

import com.aj.particle.graphics.Screen;

public class Particle {

	private double posX;
	private double posY;
	private Screen screen;
	private Random rand = new Random();
	private int color;
	private int time = 0;
	private int life;
	private double dx;
	private double dy;
	private double accX;
	private double accY;
	
	private float radius;
	private boolean delete = false;
	
	private int split;
	private int split_radius;

	public Particle(int x, int y, Screen screen, int split, int split_radius) {
		this.posX = x;
		this.posY = y;
		this.screen = screen;
		color = rand.nextInt();
		
		life = Math.abs(rand.nextInt() % 7000);
		life += 3000;
		dx = 1.0 - ((rand.nextDouble()*10) % 2.0);
		dy = 1.0 - ((rand.nextDouble()*10) % 2.0);

		this.split_radius = split_radius;
		radius = 2 + (rand.nextInt() % 5) / split_radius;
		
		accX = 0;
		accY = 0;
		
		this.split = split;
	}
	

	public void update() {
		time++;
		if (time > life) {
			delete = true;
		}
		posX += dx * 6;
		posY += dy * 6;

	}
	
	// if the particles life runs out then return true
	public boolean checkLife() {
		return delete;
	}
	
	// when a new particle is created set that pixel to blue
	public void render() {
		for (int y = (int) (posY - radius); y < posY + radius; y++) {
			if (y < 0 || y >= screen.getHeight()) {
//				delete = true;
				break;
			}
			for (int x = (int) (posX - radius); x < posX + radius; x++) {
				if (x < 0 || x >= screen.getWidth()) {
//					delete = true;
					break;		
				}

				int cx = (int) (y - posY);
				int cy = (int) (x - posX);
				float r = radius * radius;
				if ((cx*cx + cy*cy) <= r) {
					screen.pixels[x + y * screen.getWidth()] = color;
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
	
	public double getAccX() {
		return accX;
	}
	
	public double getAccY() {
		return accY;
	}
	
	public void setdX(double x) {
		accX = x;
		dx = x;
	}
	
	public void setdY(double y) {
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
