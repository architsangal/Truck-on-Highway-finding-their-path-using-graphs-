package demo19030;

import java.util.ArrayList;
import base.*;

public class NetworkDemo extends Network {

    private ArrayList<Hub>hubsList;
    private ArrayList<Highway>highwaysList;
    private ArrayList<Truck>trucksList;

    public NetworkDemo(){
        this.hubsList=new ArrayList<Hub>();
        this.highwaysList=new ArrayList<Highway>();
        this.trucksList=new ArrayList<Truck>();
    }

    @Override
    public void add(Hub hub) {
        this.hubsList.add(hub);
    }

    @Override
    public void add(Highway hwy) {
        this.highwaysList.add(hwy);
    }

    @Override
    public void add(Truck truck) {
        this.trucksList.add(truck);
    }

    @Override
    public void start() {
        for(Hub hub:hubsList){
            hub.start();
        }
        for(Truck truck:trucksList){
            truck.start();
        }
    }

    @Override
    public void redisplay(Display disp) {
        for(Hub hub:hubsList){
            hub.draw(disp);
        }
        for(Highway highway:highwaysList){
            highway.draw(disp);
        }
        for(Truck truck:trucksList){
            truck.draw(disp);
        }
    }

    private double computeDist(Location loc1,Location loc2){
        return Math.sqrt(Math.pow((loc2.getX()-loc1.getX()), 2)+Math.pow((loc2.getY()-loc1.getY()), 2));
    }

    @Override
    protected Hub findNearestHubForLoc(Location loc) {
        Hub shortestHub=null;
        double shortestDist=1e7;
        for(Hub hub:hubsList){
            double tempDist=computeDist(loc, hub.getLoc());
            if(shortestDist>tempDist){
                shortestDist=tempDist;
                shortestHub=hub;
            }
        }
        return shortestHub;
    }
    
}
