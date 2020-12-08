package demo19523;

import java.util.*;
import base.*;

public class NetworkDemo extends Network
{

    private ArrayList<Hub> listHub = new ArrayList<Hub>();
    private ArrayList<Highway> listHighway = new ArrayList<Highway>();
    private ArrayList<Truck> listTruck = new ArrayList<Truck>();

    @Override
    public void add(Hub hub)//the usual add functions
    {
        this.listHub.add(hub);
    }

    @Override
    public void add(Highway highway)
    {
        this.listHighway.add(highway);
    }

    @Override
    public void add(Truck truck)
    {
        this.listTruck.add(truck);
    }

    @Override
    public void start() //chop-chop its time to get to work
    {
           for(Hub hubTemp : this.listHub)
           {
               hubTemp.start();
           }

           for(Truck truckTemp : this.listTruck)
           {
               truckTemp.start();
           }
    }

    @Override
    public void redisplay(Display disp) //for the GUI
    {
        for(Hub hubTemp : this.listHub)
           {
               hubTemp.draw(disp);
           }

           for(Highway highTemp : this.listHighway)
           {
                highTemp.draw(disp);
           }

           for(Truck truckTemp : this.listTruck)
           {
               truckTemp.draw(disp);
           }

    }

    @Override
    public Hub findNearestHubForLoc(Location loc) //uses distance to find the nearest hub
    {
        Hub minHub = this.listHub.get(0);
        for(Hub hubTemp : this.listHub)
        {
            if(hubTemp.getLoc().distSqrd(loc) <  minHub.getLoc().distSqrd(loc))
            {
                minHub = hubTemp;
            }
        }

        return minHub;
    }
}
