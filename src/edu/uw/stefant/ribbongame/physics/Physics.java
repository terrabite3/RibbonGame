package edu.uw.stefant.ribbongame.physics;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import edu.uw.stefant.ribbongame.Coord;
import edu.uw.stefant.ribbongame.Ribbon;

public class Physics {

	private HashMap<String, Ribbon> ribbons;

	public Physics(HashMap<String,Ribbon> set) {
		ribbons = set;
	}

	public void update() {
		// Get keyboard and mouse updates
		getInput();
	}

	/**
	 * Rudimentary method to move the first point of Ribbon "hello" with keyboard.
	 */
	private void getInput() {
		if (ribbons == null || !ribbons.containsKey("hello")) return;
		Ribbon hello = ribbons.get("hello");
		if (hello == null || hello.getPoints() == null || hello.getPoints().size() == 0) return;
		Coord point = hello.getPoints().get(0);

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) point = point.translate(0, -0.01, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) point = point.translate(0, 0.01, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) point = point.translate(-0.01, 0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) point = point.translate(0.01, 0, 0);
		
		hello.getPoints().set(0, point);
	}

}
