package edu.uw.stefant.ribbongame.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Error {
	
	public static void p() {
		int error = GL11.glGetError();
		if (error == GL11.GL_NO_ERROR) return;
		System.out.println(GLU.gluErrorString(error));
	}

}