package com.aj.particle.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	private String path;
	private int SIZE = 50;
	public int[] pixels;
	
	public Sprite(String path) {
		pixels = new int[SIZE * SIZE];
		this.path = path;
		load();
	}
	
	public void load() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(path));
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
