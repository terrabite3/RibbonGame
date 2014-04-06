package edu.uw.stefant.ribbongame;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.ReadableColor;

public class Ribbon {
	
	private final ArrayList<Coord> points;
	private double width;
	private final double pitch;
	private ReadableColor color;
	
	public Ribbon(Ribbon r) {
		this(r.points, r.width, r.pitch, r.color);
	}
	
	public Ribbon(List<Coord> points2, double width2, double pitch2,
			ReadableColor color2) {
		points = new ArrayList<Coord>(points2.size());
		width = width2;
		pitch = pitch2;
		color = color2;
		
		points.addAll(points2);
	}

	public int getLength() {
		return points.size() - 1;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double w) {
		width = w;
	}
	
	public double getPitch() {
		return pitch;
	}
	
	public ArrayList<Coord> getPoints() {
		return points;
	}
	
	public ReadableColor getColor() {
		return color;
	}
	
	public void setColor(ReadableColor c) {
		color = c;
	}
	
	public static Ribbon createStraightRibbon(int l, double x0, double y0, double dx, double dy, double width, ReadableColor color) {
		double pitch = Math.sqrt(dx * dx + dy * dy);
		ArrayList<Coord> points = new ArrayList<Coord>();
		
		for (int i = 0; i < l; i++) {
			points.add(new Coord(x0 + dx * i, y0 + dy * i, -5, 0));
		}
		
		return new Ribbon(points, width, pitch, color);
	}

}
