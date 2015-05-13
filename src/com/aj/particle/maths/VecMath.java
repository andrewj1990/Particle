package com.aj.particle.maths;

public class VecMath {

	// calculate the dot product
	public static int dot(Vector3i vec1, Vector3i vec2) {
		int normal = vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ();		
		return normal;
	}
	
	// calculate the length given a Vector3i
	public static int calcLength(Vector3i vec) {
		int x = vec.getX();
		int y = vec.getY();
		int z = vec.getZ();
		
		int length = (int) Math.sqrt(x*x + y*y + z*z);
		
		return length;
		
	}
	
	// calculate the length of x and y
	public static double calcLength(double x, double y) {
		return Math.sqrt((x*x) + (y*y));
	}
	
	// calculate the distance between 2 vectors
	public static Vector3i calcDifference(Vector3i vec1, Vector3i vec2) {
		int x = vec1.getX() - vec2.getX();
		int y = vec1.getY() - vec2.getY();
		int z = vec1.getZ() - vec2.getZ();
		
		return new Vector3i(x, y, z);
		
	}
	
}
