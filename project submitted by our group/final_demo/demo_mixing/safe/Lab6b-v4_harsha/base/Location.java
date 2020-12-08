package base;

public class Location {

	// convenience class - encapsulates the pair x,y
	public Location(int x, int y) {
		px = x;
		py = y;
	}

	public Location(Location loc) {
		px = loc.px;
		py = loc.py;
	}
	
	public void setPos(int x, int y) {
		px = x;
		py = y;
	}

	public int getX() {
		return px;
	}

	public int getY() {
		return py;
	}

	public int distSqrd(Location next) {
		int dx = px-next.px;
		int dy = py - next.py;
		return (dx*dx + dy*dy);
	}
	
	@Override
	public String toString() {
		
		return ("< " + px + ", " + py + ">");
	}
	
	private int px, py;
}
