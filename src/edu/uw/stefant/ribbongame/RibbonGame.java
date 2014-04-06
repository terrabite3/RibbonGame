package edu.uw.stefant.ribbongame;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.ReadableColor;

import edu.uw.stefant.ribbongame.physics.Physics;
import edu.uw.stefant.ribbongame.render.Render;

public class RibbonGame {
	

	private void start() {
		setupDisplay();
		
		HashMap<String, Ribbon> set = new HashMap<String, Ribbon>();
		try {
			WorldReader.parse("assets/ribbons.txt", set);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
//		set.put("hello", sampleRibbon());
//		set.put("hello", Ribbon.createStraightRibbon(31, 0, 0, 0, 0.05, 0.05, ReadableColor.RED));
		
		Render r = new Render(set);
		
		Physics p = new Physics(set);
		
		while (!Display.isCloseRequested()) {
			//Game loop
			Display.update();
			p.update();
			r.render();
		}
	}
	

	
	private void setupDisplay() {
		try {
			Display.setVSyncEnabled(true);
			Display.setTitle("Test");
			Display.setFullscreen(false);
			
			Display.create();
			
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String... args) {
		new RibbonGame().start();
	}

}
