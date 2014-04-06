package edu.uw.stefant.ribbongame.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import edu.uw.stefant.ribbongame.Coord;
import edu.uw.stefant.ribbongame.Ribbon;

public class Camera {

	public Camera() {
		aspect = ((float) Display.getWidth()) / Display.getHeight();
	}

	private static final float FOV = 45f;
	private static final double RATE = 0.1;
	
	private final float aspect;
	public double x;
	public double y;

	public void lookAt(Coord point) {
		x = point.x * RATE + x * (1 - RATE);
		y = point.y * RATE + y * (1 - RATE);
	}

	public void transform() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(FOV, aspect, 0.1f, 100.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(-x, y, 0);
	}

	public void lookAt(Ribbon r) {
		lookAt(r.getPoints().get(0));
	}

}
