package demo19523;

import base.*;
public class FactoryDemo extends Factory
{  
    @Override
    public Network createNetwork() //creating the instance of all the demo classes
    {
        return new NetworkDemo();
    }

    @Override
    public Highway createHighway()
    {
        return new HighwayDemo();
    }

    @Override
    public Hub createHub(Location loc)
    {
        return new HubDemo(loc);
    }

    @Override
    public Truck createTruck()
    {
        return new TruckDemo();
    }
}