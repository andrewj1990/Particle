package com.aj.particle;

import java.awt.Canvas;
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
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private Thread thread;
	private JFrame frame;
	
	private Screen screen;
	private Keyboard keys;
	private Mouse mouse;
	private Light light;
	
	public Main() {
		frame = new JFrame("Particle | fps : 0");
		thread = new Thread(this, "display");
		
		Dimension dimension = new Dimension(width, height);
		
		frame.setPreferredSize(dimension);
		frame.add(this);
		frame.pack();
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
		screen = new Screen(width, height, mouse);
		light = new Light();

		System.out.println("Screen size : " + getSize().width + " height : " + getSize().height);
		
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
		keys.update();
		if (keys.space) System.out.println("space has been pressed");
		if (keys.up) System.out.println("up has been pressed");

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
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				light.setPos(x, y);
				light.setMouse(mouse.getX(), mouse.getY());
				if (mouse.getX() < 0 || mouse.getX() > width || mouse.getY() < 0 || mouse.getY() > height) break;
				
				// separate colours into its components in order to calculate diffuse lighting
				int colour = screen.pixels[x + y * width];
				int r = (colour >> 16) & 0xff;
				int g = (colour >>  8) & 0xff;
				int b = (colour		 ) & 0xff;
				float diffuse = light.diffuseLight();
				
				r *= diffuse;
				g *= diffuse;
				b *= diffuse;
				
				r = (r << 16);
				g = (g << 8);
				
				colour = r | g | b;
				pixels[x + y * width] = colour;
				
			}
		}
		Graphics g = bs.getDrawGraphics();
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) {
		new Main().start();
		
	}
	
}
