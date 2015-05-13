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

	private List<Particle> particle_list = new ArrayList<Particle>();
	private List<GravityHole> gravity_list = new ArrayList<GravityHole>();
	
	public Screen(int w, int h, Mouse mouse, Keyboard keys) {
		
		this.width = w;
		this.height= h;
		
		pixels = new int[width * height];
		
		// initial value for emitted particles and splits
		// splits cause the particles to split into more particles when each particles lifespan is over
		emitted_particles = 400;
		particle_splits = 0;
		
		this.mouse = mouse;
		this.keys = keys;
	}
	
	// update screen
	public void update() {
		keys.update();
		
		// adjust number of particles emitted
		if (keys.up) emitted_particles++;
		if (keys.down && emitted_particles > 0) emitted_particles--; 
//		if (keys.right) particle_splits++;
//		if (keys.left && particle_splits > 0) particle_splits--;
		
		// space bar -- clear screen
		if (keys.space) {
			particle_list.clear(); 
			gravity_list.clear();
		}
		
		// determine each particles new position after every update
		for (int i = 0; i < particle_list.size(); i++) {
			// if there's gravity wells then calculate where the particle position will be
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
//		removeParticles();
		
		// left click -- add particles
		if (mouse.getButton() == 1) {
			addParticle(mouse.getX(), mouse.getY(), particle_splits, 1, emitted_particles);
		}
		
		// right click -- add gravity well
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

	}
	
	// set the particles next position based on the gravity wells
	public void setParticlePos(Particle particle) {
		
		// amount each particle will travel -- increases with every pull towards gravity well
		float accX = particle.getAccX();
		float accY = particle.getAccY();
		
		// particle position
		float px = particle.getX();
		float py = particle.getY();
		
		// find the amount of pull each gravity well has on a particle
		for (int j = 0; j < gravity_list.size(); j++) {	
			GravityHole grav = gravity_list.get(j);
			
			// gravity well position
			float gx = grav.getX();
			float gy = grav.getY();
			
			// calculate the direction between the particle and gravity hole
			float dx = gx - px;
			float dy = gy - py;
			
			// normalize the direction and divide by a factor to mimic the strength of pull		
			float length = (float) VecMath.calcLength(dx, dy);
			float ux = dx / (length * 7);
			float uy = dy / (length * 7);
			
			// accumulate the pull of each gravity hole
			accX += ux;
			accY += uy;
			
		}
		
		// set the particles change in position
		particle.setdX(accX);
		particle.setdY(accY);
	}
	
	// add gravity well
	public void addGravity(int x, int y) {
		gravity_list.add(new GravityHole(x, y, this));
	}
	
	// add particles at x and y location 
	public void addParticle(int x, int y, int split, int split_radius, int num_particles) {
		int particles_spawned = num_particles;
		
		// randomly choose the particle to spawn in an area around the location
		Random rand = new Random();
		for (int i = 0; i < particles_spawned; i++) {
			int xx = 40 - (rand.nextInt(80));
			int yy = 40 - (rand.nextInt(80));
			particle_list.add(new Particle(x + xx, y + yy, this, split, split_radius));			
		}
	}
	
	// remove particle and cause it to split (if any splits)
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
	
	// clear screen
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x090E0D;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	// get the number of total particles
	public int getParticlesSize() {
		return particle_list.size();
	}
	
	// get the number of particles emitted per click
	public int getEmittedParticles() {
		return emitted_particles;
	}
	
	// get the number of particles split after each particles death
	public int getParticleSplits() {
		return particle_splits;
	}
	
}
