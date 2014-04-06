package edu.uw.stefant.ribbongame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

public class WorldReader {

	public static void parse(String string, HashMap<String, Ribbon> set) throws FileNotFoundException {
		File f = new File(string);
		
		Scanner scan = new Scanner(f);
		Scanner line = null;
		
		String name = "";
		while (scan.hasNext()) {
			try {
				line = new Scanner(scan.nextLine());
				if (!line.hasNext()) continue;
				
				name = line.next();
				if (name.charAt(0) == ';') continue;
				
				int points = line.nextInt();
				double width = line.nextDouble();
				double pitch = line.nextDouble();
				int red = line.nextInt(16);
				int green = line.nextInt(16);
				int blue = line.nextInt(16);
				int alpha = line.nextInt(16);
				ReadableColor color = new Color(red, green, blue, alpha);
				
				ArrayList<Coord> coords = new ArrayList<Coord>(points);
				
				for (int i = 0; i < points; i++) {
					line = new Scanner(scan.nextLine());
					
					double x = line.nextDouble();
					double y = line.nextDouble();
					double z = line.nextDouble();
					double r = line.nextDouble();
					
					coords.add(new Coord(x, y, z, r));
				}
				
				Ribbon r = new Ribbon(coords, width, pitch, color);
				set.put(name, r);
			} catch (NumberFormatException e) {
				System.err.println("Number format error in " + string + " under item " + name + ":");
				System.err.println(e.toString());
				continue;
			}
		}
	}

}
