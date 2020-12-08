package demo19012;

import java.util.ArrayList;

import base.*;

public class NetworkDemo extends Network {

    public NetworkDemo()
    {
        super();

        highways = new ArrayList<>();
        hubs = new ArrayList<>();
        trucks = new ArrayList<>();
    }

    // adds objects of hub to a local arraylist
    public void add(Hub hub)
    {
        hubs.add(hub);
    }

    // adds objects of highway to a local arraylist
    public void add(Highway hwy)
    {
        highways.add(hwy);
    }

    // adds objects of truck to a local arraylist
    public void add(Truck truck)
    {
        trucks.add(truck);
    }

    @Override
    public void start()
    {

        for(Hub hub : hubs)
            hub.start();

        for(Truck truck : trucks)
            truck.start();

    }    

    // returns nearest Huc when provided by a location
    @Override
    protected Hub findNearestHubForLoc(Location loc)
    {
        Hub nearest = hubs.get(0);
        double distance = loc.distSqrd(nearest.getLoc());

        for(Hub hub: hubs)
        {
            if(distance > loc.distSqrd(hub.getLoc()))
            {
                nearest = hub;
                distance = loc.distSqrd(hub.getLoc());
            }
        }

        return nearest;
    }

    @Override
    public void redisplay(Display disp)
    {
        for(Highway highway : highways)
            highway.draw(disp);

        for(Hub hub : hubs)
            hub.draw(disp);

        for(Truck truck : trucks)
            truck.draw(disp);
    }

    private ArrayList<Highway> highways;
    private ArrayList<Hub> hubs;
    private ArrayList<Truck> trucks;

}