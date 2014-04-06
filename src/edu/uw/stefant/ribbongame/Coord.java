package edu.uw.stefant.ribbongame;

public class Coord {
	
	public static final Coord ORIGIN = new Coord(0, 0, 0, 0);
	
	public Coord(double x, double y, double z, double r) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}
	
	public final double x;
	public final double y;
	public final double z;
	public final double r;

	public Coord translate(double dx, double dy, double dz) {
		return new Coord(x + dx, y + dy, z + dz, r);
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

}
