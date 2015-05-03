package com.aj.particle.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aj.particle.entity.GravityHole;
import com.aj.particle.entity.Particle;
import com.aj.particle.input.Keyboard;
import com.aj.particle.input.Mouse;
import com.aj.particle.maths.VecMath;

public class Screen {

	private int width;
	private int height;
	private int emitted_particles;
	private int particle_splits;
	
	public int[] pixels;
	public int[] map;
	
	private Mouse mouse;
	private Keyboard keys;
	private Sprite player;
	
	private List<Particle> particle_list = new ArrayList<Particle>();
	private List<GravityHole> gravity_list = new ArrayList<GravityHole>();
	
	public Screen(int w, int h, Mouse mouse, Keyboard keys) {
		
		this.width = w;
		this.height= h;
		
		pixels = new int[width * height];
		clear();
		
		Random rand = new Random();
		map = new int[64 * 64];
		for (int i = 0; i < map.length; i++) {
			map[i] = rand.nextInt();
		}
		
		player = new Sprite("/res/player.png");
		
		emitted_particles = 20;
		particle_splits = 2;
		
		this.mouse = mouse;
		this.keys = keys;
	}
	
	// update screen
	public void update() {
		keys.update();
		if (keys.up) emitted_particles++;
		if (keys.down && emitted_particles > 0) emitted_particles--; 
		if (keys.right) particle_splits++;
		if (keys.left && particle_splits > 0) particle_splits--;
		// space bar -- clear screen
		if (keys.space) {
			particle_list.clear(); 
			gravity_list.clear();
		}
		
		for (int i = 0; i < particle_list.size(); i++) {
			if (gravity_list.size() > 0) {
				Particle particle = particle_list.get(i);
				setParticlePos(particle);
			}
			particle_list.get(i).update();				
		}
		

	}
	
	// draw on screen
	public void render() {
		
		// kill dead particles -- particle life is over limit
		removeParticles();
		
		// left click -- add particles
		if (mouse.getButton() == 1) {
			addParticle(mouse.getX(), mouse.getY(), particle_splits, 1, emitted_particles);
		}
		
		// right click -- add gravity hole
		if (mouse.oneClick == 3) {
			addGravity(mouse.getX(), mouse.getY());
			mouse.oneClick = -1;
		}
		
		// draw the gravity holes and particles
		for (int i = 0; i < gravity_list.size(); i++) {
			gravity_list.get(i).render();
		}
		for (int i = 0; i < particle_list.size(); i++) {
			particle_list.get(i).render();
		}

		
//		int i = 0;
//		for (int y = 0; y < 50; y++) {
//			for (int x = 0; x < 50; x++) {
//				int col = player.pixels[i++];
//				if (col != 0xffff00ff) pixels[y + x * width] = col;
//			}
//		}

	}
	
	// set the particles next position based on the gravity holes
	public void setParticlePos(Particle particle) {
		double accX = particle.getAccX();
		double accY = particle.getAccY();
		double px = particle.getX();
		double py = particle.getY();
		for (int j = 0; j < gravity_list.size(); j++) {	
			GravityHole grav = gravity_list.get(j);
			
			double gx = grav.getX();
			double gy = grav.getY();
			
			// calculate the direction between the particle and gravity hole
			double dx = gx - px;
			double dy = gy - py;
			
			// normalize the direction and divide by a factor to mimic the strength of pull		
			double length = VecMath.calcLength(dx, dy);
			double ux = dx / (length * 7);
			double uy = dy / (length * 7);
			
			// accumulate the pull of each gravity hole
			accX += ux;
			accY += uy;
			
		}
		
		// set the particles change in position
		particle.setdX(accX);
		particle.setdY(accY);
	}
	
	public void addGravity(int x, int y) {
		gravity_list.add(new GravityHole(x, y, this));
	}
	
	public void addParticle(int x, int y, int split, int split_radius, int num_particles) {
		int particles_spawned = num_particles;
		
		Random rand = new Random();
		for (int i = 0; i < particles_spawned; i++) {
			int xx = 40 - (rand.nextInt(80));
			int yy = 40 - (rand.nextInt(80));
			particle_list.add(new Particle(x + xx, y + yy, this, split, split_radius));			
		}
	}
	
	public void removeParticles() {
		for (int i = 0; i < particle_list.size(); i++) {
			if (particle_list.get(i).checkLife()) {
				int new_split = particle_list.get(i).getSplit() - 1;
				int new_split_radius = particle_list.get(i).getSplitRadius() * 2;
				if (new_split >= 0) {
					addParticle((int) particle_list.get(i).getX(), (int) particle_list.get(i).getY(), new_split, new_split_radius, emitted_particles/2);
				}
				particle_list.remove(i);
			}
		}
	}
	
	// clear screen, set all pixels to black
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x44;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getParticlesSize() {
		return particle_list.size();
	}
	
	public int getEmittedParticles() {
		return emitted_particles;
	}
	
	public int getParticleSplits() {
		return particle_splits;
	}
	
}
