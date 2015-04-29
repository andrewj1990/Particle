package com.aj.particle.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aj.particle.entity.Particle;
import com.aj.particle.input.Mouse;

public class Screen {

	private int width;
	private int height;
	
	public int[] pixels;
	public int[] map;
	
	private Mouse mouse;
	private Sprite player;
	
	private List<Particle> particle_list = new ArrayList<Particle>();
	
	public Screen(int w, int h, Mouse mouse) {
		
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
		
		this.mouse = mouse;
	}
	
	// update screen
	public void update() {
		for (int i = 0; i < particle_list.size(); i++) {
			particle_list.get(i).update();
		}
	}
	
	// draw on screen
	public void render() {
		
		int number_of_splits = 2;
		int number_of_particles = 20;
		
		// kill dead particles -- particle life is over limit
		removeParticles();
		
		// left click - add particles
		if (mouse.getButton() == 1) {
			addParticle(mouse.getX(), mouse.getY(), number_of_splits, 1, number_of_particles);
		}
		
		// right click - clear screen
		if (mouse.getButton() == 3) {
			particle_list.clear();
			clear();
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
	
	public void addParticle(int x, int y, int split, int split_radius, int num_particles) {
		int particles_spawned = num_particles;
		
		for (int i = 0; i < particles_spawned; i++) {
			particle_list.add(new Particle(x, y, this, split, split_radius));			
		}
	}
	
	public void removeParticles() {
		for (int i = 0; i < particle_list.size(); i++) {
			if (particle_list.get(i).checkLife()) {
				int new_split = particle_list.get(i).getSplit() - 1;
				int new_split_radius = particle_list.get(i).getSplitRadius() * 2;
				if (new_split >= 0) {
					addParticle((int) particle_list.get(i).getX(), (int) particle_list.get(i).getY(), new_split, new_split_radius, 10);
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
	
}
