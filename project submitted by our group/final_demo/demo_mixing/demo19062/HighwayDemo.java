package demo19062;

import base.*;
import java.util.*;
/*
    Methods: 
        1. hasCapacity() 
        2. add(Truck)
        3. remove(Truck)
*/

public class HighwayDemo extends Highway{

    private ArrayList<Truck> trucksAtHighway = new ArrayList<>();
    public ArrayList<Truck> getTrucksAtHighway(){
        return this.trucksAtHighway;
    }


    // returns true if Highway is not full, i.e. number of trucks is below capacity
	public boolean hasCapacity(){
        if(super.getCapacity() > this.trucksAtHighway.size()){
            return true;
        }
        else{
            return false;
        }
    }
	
	// fails if already at full highway capacity
    public synchronized boolean add(Truck truck){
        if(super.getCapacity() > this.trucksAtHighway.size()){
            this.trucksAtHighway.add(truck);
            return true;
        }
        else{
            return false;
        }
    }
    
    // remove from the highway
	public synchronized void remove(Truck truck){
        this.trucksAtHighway.remove(truck);
    }
}