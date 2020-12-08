package demo19062;
import base.*;

/*
	Name: Pranjal Walia, IMT2019062

	factorydemo derived class from factory.
	has 4 method to create highways,hubs,networks and trucks

	-- createHighway() => creates new HighwayDemo object
	-- createHub() => creates new HubDemo object 
	-- createTruck() => creates new TruckDemo object
	-- createNetwork() => creates new NetworkDemo object
*/



public class FactoryDemo extends Factory{
    @Override
    public Network createNetwork() {
        return new NetworkDemo();
	}
	
    @Override
	public Highway createHighway() {
		return new HighwayDemo();
	}

	@Override
	public Hub createHub(Location loc) {
		return new HubDemo(loc);
	}
	
	@Override
	public Truck createTruck() {
		return new TruckDemo();
	}
}
