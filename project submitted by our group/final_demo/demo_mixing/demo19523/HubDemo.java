package demo19523;

import java.util.*;

import base.*;
public class HubDemo extends Hub
{
    private ArrayList<Truck> truckHubList = new ArrayList<Truck>(); //keeping a track of all trucks at the hub
    //listSize = truckHubList.size();

    public HubDemo(Location loc)
    {
        super(loc);
    }

    private int distanceHelper(Highway highTemp, Hub dest)
    {
        return highTemp.getEnd().getLoc().distSqrd(dest.getLoc()); //just a helper function to make it much easier to read
    }

    public synchronized boolean add(Truck truck) //the usual add
    {
        if(this.getCapacity() > truckHubList.size())
        {
            truckHubList.add(truck);
            return true;
        }
        else
        {
            return false;
        }
    }

    protected synchronized void remove(Truck truck)//and the usual remove
    {
        
            truckHubList.remove(truck);
        
    }

    public Highway getNextHighway(Hub last, Hub dest)
    {
        Highway highTemp = super.getHighways().get(0); //highTemp basically gets you the highway the truck is supposed to move into

        

        for(Highway highIterator : super.getHighways())
        {
            int distance = distanceHelper(highIterator, dest); //gets the nextHighway truck is supposed to move into
            if( distanceHelper(highTemp, dest) > distance) //the one which is the nearest to the dest is routed here
            {
                highTemp = highIterator;
            }
        }

        return highTemp;

    }

    protected synchronized void processQ(int deltaT) //checking if it can enter the highway or not
    {
        int i = truckHubList.size() - 1;

        while(i >= 0)
        {
            Truck truckTemp = truckHubList.get(i);
            Highway highTemp = this.getNextHighway(truckHubList.get(i).getLastHub(), Network.getNearestHub(truckHubList.get(i).getDest()));

            if(highTemp.hasCapacity())
            {
                truckTemp.enter(highTemp);
                this.remove(truckTemp);
            }

            i--;
        }



    }
}
