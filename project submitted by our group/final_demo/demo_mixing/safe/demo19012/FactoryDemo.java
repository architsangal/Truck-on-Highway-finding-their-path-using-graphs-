package demo19012;

import base.*;

// This is a factory class that returns objects when its functions are called
public class FactoryDemo extends Factory {

	public FactoryDemo()
	{
		super();
	}

	// creates instances of Highway
	@Override
	public Highway createHighway() {
		return new HighwayDemo();
	}

	// creates instances of Hub
	@Override
	public Hub createHub(Location loc) {
		return new HubDemo(loc);
	}

	// creates instances of Truck
	@Override
	public Truck createTruck() {
		
		return new TruckDemo();
	}

	// creates instances of Network
	@Override
	public Network createNetwork()
	{
		return new NetworkDemo();
	}
}
