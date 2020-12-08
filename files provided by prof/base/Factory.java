package base;

public abstract class Factory {

	abstract public Network createNetwork();
	abstract public Highway createHighway();
	abstract public Hub createHub(Location location);
	abstract public Truck createTruck();
	
}
