package com.aj.particle.graphics;

import com.aj.particle.maths.VecMath;
import com.aj.particle.maths.Vector3i;

public class Light {

	private Vector3i mousePos;
	private Vector3i coordPos;
	private Vector3i surfaceToLight = new Vector3i();
	private Vector3i normal = new Vector3i(0, 0, -1);
	
	private final int depth = -500;
	
	public Light() {
		mousePos = new Vector3i(0, 0, depth);
		coordPos = new Vector3i();
	}
	
	public Light(int mouseX, int mouseY, int posX, int poxY) {		
		mousePos = new Vector3i(mouseX, mouseY, depth);
		coordPos = new Vector3i(posX, poxY, 0);
		
	}
	
	// calculate diffuse lighting
	public float diffuseLight() {
		float brightness = 0;
		
		// calculate the distance between surface and light
		surfaceToLight.setVector(VecMath.calcDifference(mousePos, coordPos));
		float top = VecMath.dot(normal, surfaceToLight);		
		float bot = VecMath.calcLength(surfaceToLight) * VecMath.calcLength(normal);

		if (bot != 0)
			brightness = top / bot;
		
		if (brightness < 0)
			brightness = 0;
		
		return brightness;
	}
	
	// location of light source
	public void setMouse(int mouseX, int mouseY) {
		mousePos.setX(mouseX);
		mousePos.setY(mouseY);
	}
	
	// coordinate to calculate light at
	public void setPos(int posX, int posY) {
		coordPos.setX(posX);
		coordPos.setY(posY);
		
	}
	
	
}
