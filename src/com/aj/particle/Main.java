package com.aj.particle;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.aj.particle.graphics.Light;
import com.aj.particle.graphics.Screen;
import com.aj.particle.input.Keyboard;
import com.aj.particle.input.Mouse;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private int width = 1680;
	private int height = 720;
	private boolean running = false;
	
	private BufferedImage image;
	private int[] pixels;
	
	private Thread thread;
	private JFrame frame;
	
	private Screen screen;
	private Keyboard keys;
	private Mouse mouse;
	private Light light;
	
	public Main() {
		frame = new JFrame("Particle | fps : 0");
		thread = new Thread(this, "display");
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		this.width = screenSize.width;
//		this.height = screenSize.height;
		
		Dimension dimension = new Dimension(width, height);

		frame.setPreferredSize(dimension);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// add the keyboard and key listener
		keys = new Keyboard();
		addKeyListener(keys);
		// add mouse listener and motion listener
		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		screen = new Screen(getWidth(), getHeight(), mouse, keys);
		light = new Light();
		
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		System.out.println("Screen size : " + getWidth() + " height : " + getHeight());
	}
	
	public void start() {
		running = true;
		thread.start();
	}
	
	public void run() {
		long prevTime = System.nanoTime();
		int frames = 0;
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		long timer = System.currentTimeMillis();
		requestFocus();
		setCursor(new Cursor(1));
		while (running) {
			long now = System.nanoTime();
			delta += (now - prevTime) / ns;
			prevTime = now;
			while (delta >= 1.0) {
				update();
				delta--;
			}
			
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Particle | fps : " + frames);
				frames = 0;
			}

		}
	}
	
	public void update() {
		screen.update();
	}
	
	public void render() {
		// assign buffer strategy to bs
		// if null then create a buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		// clear screen
		screen.clear();
		// set the pixels colours to the screens pixels
		screen.render();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				light.setPos(x, y);
				light.setMouse(mouse.getX(), mouse.getY());
				if (mouse.getX() < 0 || mouse.getX() > width || mouse.getY() < 0 || mouse.getY() > height) break;
				
				// separate colours into its components in order to calculate diffuse lighting
				int colour = screen.pixels[x + y * getWidth()];
//				int r = (colour >> 16) & 0xff;
//				int g = (colour >>  8) & 0xff;
//				int b = (colour		 ) & 0xff;
//				float diffuse = light.diffuseLight();
//				
//				r *= diffuse;
//				g *= diffuse;
//				b *= diffuse;
//				
//				colour = (r << 16) | (g << 8) | b;
				pixels[x + y * getWidth()] = colour;
				
			}
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.drawString("Particles : " + screen.getParticlesSize(), 10, 20);
		g.drawString("Emitted Particles : " + screen.getEmittedParticles() + " (up, down)", 10, 35);
//		g.drawString("Particle Splits : " + screen.getParticleSplits(), 10, 50);
		g.drawString("Left click to add particles | Right click to add vacuum hole", 10, 50);	
		g.drawString("Spacebar to clear", 10, 65);	
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {
		new Main().start();
		
	}
	
}
