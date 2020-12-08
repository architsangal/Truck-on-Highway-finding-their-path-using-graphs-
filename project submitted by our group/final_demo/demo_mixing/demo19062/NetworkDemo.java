package demo19062;

import base.Location;
import base.Highway;
import base.Truck;
import base.Hub;
import base.Network;
import base.Display;

import java.util.ArrayList;

public class NetworkDemo extends Network {
    private ArrayList<Highway> highways = new ArrayList<>();
    private ArrayList<Truck> trucks = new ArrayList<>();
    private ArrayList<Hub> hubs = new ArrayList<>();

    //! define getters for the private members
    public ArrayList<Highway> getHighways() {
        return this.highways;
    }

    public ArrayList<Truck> getTrucks() {
        return this.trucks;
    }

    public ArrayList<Hub> getHubs() {
        return this.hubs;
    }

    // Add a highway to the current network of highways
    @Override
    public void add(Highway hwy) {
        this.highways.add(hwy);
    }

    // Add a hub to the network of hubs
    @Override
    public void add(Hub hub) {
        this.hubs.add(hub);
    } 

    // Add a truck to the network of trucks 
    @Override
    public void add(Truck truck) {
        this.trucks.add(truck);
    }

    // call start on all the hubs and trucks 
    @Override
    public void start() {
        for(Hub H : this.hubs){
            H.start();
        }
        for(Truck t : this.trucks){
            System.out.println("The last hub for the truck: " + t.getLastHub());
            t.start();
        }
    }

    // calls draw() on every member in the network
    @Override
    public void redisplay(Display disp) {
        for(Hub H : this.hubs){
            H.draw(disp);
        }
        for(Truck t : this.trucks){
            t.draw(disp);
        }
        for(Highway highway : this.highways){
            highway.draw(disp);
        }
    }    

    @Override
    protected Hub findNearestHubForLoc(Location loc) {
        Hub nearest = this.hubs.get(0);
        for(Hub h : this.hubs){
            if(nearest.getLoc().distSqrd(loc) > h.getLoc().distSqrd(loc)){
                nearest = h;
            }
        }
        return nearest;
    }
}
