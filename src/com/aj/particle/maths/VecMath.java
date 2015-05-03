package com.aj.particle.maths;

public class VecMath {

	// calculate the dot product
	public static int dot(Vector3i vec1, Vector3i vec2) {
		
		int normal = vec1.getX() * vec2.getX() + vec1.getY() * vec2.getY() + vec1.getZ() * vec2.getZ();		
		return normal;
	}
	
	public static int calcLength(Vector3i vec) {
		
		int length;
		int x = vec.getX();
		int y = vec.getY();
		int z = vec.getZ();
		
		length = x*x + y*y + z*z;
		length = (int) Math.sqrt(length);
		
		return length;
		
	}
	
	public static double calcLength(double x, double y) {
		
		double result = (x*x) + (y*y);
		result = Math.sqrt(result);
		return result;
	}
	
	public static Vector3i calcDifference(Vector3i vec1, Vector3i vec2) {

		int x = vec1.getX() - vec2.getX();
		int y = vec1.getY() - vec2.getY();
		int z = vec1.getZ() - vec2.getZ();
		
		Vector3i result = new Vector3i(x, y, z);
		return result;
		
	}
	
}
