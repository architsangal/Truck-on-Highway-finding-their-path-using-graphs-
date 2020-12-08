package base;

public abstract class Truck extends Thread {
	
	public void setSourceDest(Location start, Location end) {
		source =  new Location(start);
		loc = new Location(start);
		dest =  new Location(end);
	}
	
	public void setStartTime(int msecs) { // in millisecs
		startTime = msecs;
	}
	
	public void setDeltaT(int dt) {
		deltaT = dt;
	}
	
	public Location getLoc() {
		return  new Location(loc);
	}
	
	// derived classes should generate unique name for each instance
	public String getTruckName() {
		return "Truck";
	}
	
	public void setLoc(Location loc) {
		this.loc = new Location(loc);
	}
	
	public Location getDest() {
		return  new Location(dest);
	}
	
	// the Hub from which it last exited. 
	abstract public Hub getLastHub();

	abstract public void enter(Highway hwy); 
	
	public void run() {  // called when Thread is first ready to run
		// sets timer and invokes update forever
		for(int i=0; i < 1000; i++) {
			try {
				Thread.sleep(deltaT);
				update(deltaT);
			} catch (Exception e) {
				System.out.println("Truck:" + e);
			}
		}
	}

	public void draw(Display disp) {
		// draws a rectangle at its location, as well as its name 
		disp.drawRect(loc,  10,  10);
		disp.drawText(loc, getTruckName());
	}
	
	protected int getStartTime() {
		return startTime;
	}
	
	protected Location getSource() {
		return  new Location(source);
	}

	// called every deltaT time to update its status/position
	// If less than startTime, does nothing
	// If at a hub, tries to move on to next highway in its route
	// If on a road/highway, moves towards next Hub
	// If at dest Hub, moves towards dest
	abstract protected void update(int deltaT);
	
	private int deltaT = 500;
	private Location loc;
	private Location source, dest;
	private int startTime;
	
}
