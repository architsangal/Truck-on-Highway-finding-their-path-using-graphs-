package demo19062;

import base.*;
import java.util.*;

public class HubDemo extends Hub{
    
    private ArrayList<Truck> trucksAtHub = new ArrayList<>();

    public ArrayList<Truck> gettrucksAtHub(){
        return this.trucksAtHub;
    }

    public HubDemo(Location loc){
        super(loc);
    }

    @Override //! add trucks to the current hub
	public synchronized boolean add(Truck truck){
        if(this.trucksAtHub.size() < this.getCapacity() && !this.trucksAtHub.contains(truck)){
            this.trucksAtHub.add(truck);
            return true;
        }
        else{
            return false;
        }
    }

    @Override   //! remove from hub
    protected synchronized void remove(Truck truck){
        this.trucksAtHub.remove(truck);
    }

    //! provides routing information
    //! brute forcing the graph
    @Override
	public Highway getNextHighway(Hub last, Hub dest){
        Highway nextHighway = super.getHighways().get(0);
        System.out.println("Called the getNextHighway of IMT2019062\n");
        for(Highway h : super.getHighways()){
            int distance_current_to_last = h.getEnd().getLoc().distSqrd(dest.getLoc());
            if(distance_current_to_last < nextHighway.getEnd().getLoc().distSqrd(dest.getLoc())){    
                nextHighway = h;
            }
        }
        return nextHighway;
    }
    
    // to be implemented in derived classes. Called at each time step
    @Override
	protected synchronized void processQ(int deltaT){
            //! iterate in reverse to avoid index overwriting
        for(int i=(this.trucksAtHub.size()-1) ; i>=0 ; --i){
            Truck t = this.trucksAtHub.get(i);

            Hub destinationHub = Network.getNearestHub(this.trucksAtHub.get(i).getDest());
            Highway nextHighway=this.getNextHighway(this.trucksAtHub.get(i).getLastHub() , destinationHub);

            //! enter highway if not at full capacity            
            if(nextHighway.hasCapacity()){
                t.enter(nextHighway);
                this.remove(t);
            }

        }
    }
}


