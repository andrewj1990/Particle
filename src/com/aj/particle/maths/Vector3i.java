package com.aj.particle.maths;

public class Vector3i {

	private int x;
	private int y;
	private int z;
	
	public Vector3i() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// calculate the dot product
	public int dot(Vector3i vec) {
		
		int normal = x * vec.getX() + y * vec.getY() + z * vec.getZ();		
		return normal;
	}
	
	public void setVector(Vector3i vec) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
}
