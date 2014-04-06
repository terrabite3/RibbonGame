package edu.uw.stefant.ribbongame.render;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import edu.uw.stefant.ribbongame.Coord;
import edu.uw.stefant.ribbongame.Ribbon;

public class Render {

	private HashMap<String, Ribbon> ribbons;
	private Camera camera;

	public Render(HashMap<String,Ribbon> set) {
		ribbons = set;
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		camera = new Camera();
		camera.lookAt(Coord.ORIGIN);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// TODO Setup Shaders
		// TODO Setup Textures
		Error.p();
	}

	public void render() {
		
		GL11.glClearColor(0f, 0f, 0f, 1f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		Ribbon player = ribbons.get("hello");
		if (player != null) camera.lookAt(player);
		
		for (Ribbon r : ribbons.values()) {
			drawRibbon(r);
		}
		Error.p();
	}

	private void drawRibbon(Ribbon r) {
		GL11.glPushMatrix();
		camera.transform();
		drawRibbonQuad(r);
		drawRibbonLine(r);
		GL11.glPopMatrix();
	}

	private void drawRibbonLine(Ribbon r) {
		float red = r.getColor().getRed() / 256f;
		float green = r.getColor().getGreen() / 256f;
		float blue = r.getColor().getBlue() / 256f;
		float alpha = r.getColor().getAlpha() / 256f;
		
		for (int i = 0; i < r.getLength(); i++) {
			Coord p1 = r.getPoints().get(i);
			Coord p2 = r.getPoints().get(i + 1);		
			
			GL11.glBegin(GL11.GL_LINES);
			
			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p1.x, -p1.y, p1.z);

			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p2.x, -p2.y, p2.z);
			
			GL11.glEnd();
			
		}
	}
	
	private void drawRibbonQuad(Ribbon r) {
		float red = r.getColor().getRed() / 256f;
		float green = r.getColor().getGreen() / 256f;
		float blue = r.getColor().getBlue() / 256f;
		float alpha = r.getColor().getAlpha() / 256f;
		
		// temp to show up
		alpha /= 2;
		
		for (int i = 0; i < r.getLength(); i++) {
			Coord[] p1 = positionPoints(i, r.getPoints(), r.getWidth() / 2);
			Coord[] p2 = positionPoints(i + 1, r.getPoints(), r.getWidth() / 2);
			
			
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p1[0].x, -p1[0].y, p1[0].z);
			
			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p1[1].x, -p1[1].y, p1[1].z);
			
			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p2[1].x, -p2[1].y, p2[1].z);
			
			GL11.glColor4f(red, green, blue, alpha);
			GL11.glVertex3d(p2[0].x, -p2[0].y, p2[0].z);
			
			GL11.glEnd();
		}
		
	}
	
	
	private Coord[] positionPoints(int i, ArrayList<Coord> points, double width) {
		Coord[] result = new Coord[2];
		
		if (i == 0) {	// First point
			Coord pThis = points.get(i);
			Coord pNext = points.get(i + 1);
			
			double dx = pNext.x - pThis.x;
			double dy = pNext.y - pThis.y;
			double thetaNextPoint = Math.atan2(dy, dx);
			
			double thetaP0 = thetaNextPoint + Math.PI / 2;
			
			double xP0 = Math.cos(thetaP0) * width;
			double yP0 = Math.sin(thetaP0) * width;
			
			
			if (xP0 < 0) {
				xP0 = -xP0;
				yP0 = -yP0;
			}

			result[0] = new Coord(pThis.x + xP0, pThis.y + yP0, pThis.z, pThis.r);
			result[1] = new Coord(pThis.x - xP0, pThis.y - yP0, pThis.z, pThis.r);
		} else if (i + 2 >= points.size()) {	// Last point
			Coord pThis = points.get(i);
			Coord pPrev = points.get(i - 1);
			
			double dx = pPrev.x - pThis.x;
			double dy = pPrev.y - pThis.y;
			double thetaPrevPoint = Math.atan2(dy, dx);
			
			double thetaP0 = thetaPrevPoint + Math.PI / 2;
			
			double xP0 = Math.cos(thetaP0) * width;
			double yP0 = Math.sin(thetaP0) * width;
			
			
			if (xP0 < 0) {
				xP0 = -xP0;
				yP0 = -yP0;
			}

			result[0] = new Coord(pThis.x + xP0, pThis.y + yP0, pThis.z, pThis.r);
			result[1] = new Coord(pThis.x - xP0, pThis.y - yP0, pThis.z, pThis.r);
		} else {	// Any other point
			Coord pThis = points.get(i);
			Coord pNext = points.get(i + 1);
			Coord pPrev = points.get(i - 1);
		
			double dx0 = pPrev.x - pThis.x;
			double dy0 = pPrev.y - pThis.y;
			double thetaPrevPoint = Math.atan2(dy0, dx0);

			double dx1 = pNext.x - pThis.x;
			double dy1 = pNext.y - pThis.y;
			double thetaNextPoint = Math.atan2(dy1, dx1);
			
			if (thetaPrevPoint < 0) thetaPrevPoint = thetaPrevPoint + 2 * Math.PI;
			if (thetaNextPoint < 0) thetaNextPoint = thetaNextPoint + 2 * Math.PI;
			
			double thetaP0 = (thetaPrevPoint + thetaNextPoint) / 2;
			
			double xP0 = Math.cos(thetaP0) * width;
			double yP0 = Math.sin(thetaP0) * width;

			
			if (xP0 < 0) {
				xP0 = -xP0;
				yP0 = -yP0;
			}

			result[0] = new Coord(pThis.x + xP0, pThis.y + yP0, pThis.z, pThis.r);
			result[1] = new Coord(pThis.x - xP0, pThis.y - yP0, pThis.z, pThis.r);
		}
		
		return result;
	}

}
