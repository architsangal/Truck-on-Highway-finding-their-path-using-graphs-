package base;

// We assume only one instance of Network will be created for a program
public abstract class Network {
	public Network() {
		theNet = this;
	}
	
	// keep track of the following entities
	abstract public void add(Hub hub);
	abstract public void add(Highway hwy);
	abstract public void add(Truck truck);
	

	// finds the nearest Hub to a given location
	// Made static so that other classes don't need to keep track of Network instance
	public static Hub getNearestHub(Location loc) {
		return theNet.findNearestHubForLoc(loc);
	}
	
	// start the simulation
	// derived class calls start on each of the Hubs and Trucks
	abstract public void start();

	// derived class calls draw on each hub, highway, and truck
	// passing in display
	abstract public void redisplay(Display disp);
	
	protected abstract Hub findNearestHubForLoc(Location loc);
	
	static Network theNet;
}
