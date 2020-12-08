package demo19523;

import base.Highway;
import base.Truck;

import java.util.*;

public class HighwayDemo extends Highway
{   
    private ArrayList<Truck> highwayTruck = new ArrayList<Truck>();

    public boolean hasCapacity() //utility function to check if there is capacity on the highway
    {
        if(super.getCapacity() > this.highwayTruck.size())
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public synchronized boolean add(Truck truck) //the usual add function
    {
        if(super.getCapacity() > this.highwayTruck.size())
        {
            this.highwayTruck.add(truck);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public synchronized void remove(Truck truck) //and the usual remove
    {
        if(this.highwayTruck.size() > 0)
        {
            this.highwayTruck.remove(truck);
        }
    }
}
