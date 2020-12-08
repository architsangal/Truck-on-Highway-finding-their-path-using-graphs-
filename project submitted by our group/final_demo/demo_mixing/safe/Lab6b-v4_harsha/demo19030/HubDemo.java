package demo19030;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import base.Highway;
import base.Hub;
import base.Location;
import base.Truck;

class HubDemo extends Hub {

	private ArrayList<Truck>truckQueue;

	public HubDemo(Location loc) {
		super(loc);
		this.truckQueue=new ArrayList<Truck>();
		
	}

	ArrayList<Truck>getTruckQueue(){
		return this.truckQueue;
	}
	
	void resetTruckQueue(){
		this.truckQueue=new ArrayList<Truck>();
	}

	public boolean hasCapacity(){
		if(this.truckQueue.size()<this.getCapacity()){
			return true;
		}
		return false;
	}

	@Override
	public synchronized boolean add(Truck truck) {
		if(this.hasCapacity()){
			this.truckQueue.add(truck);
			return true;
		}
		return false;
	}

	@Override
	public synchronized void remove(Truck truck) {
		try{
			this.truckQueue.remove(truck);
		}
		catch(Exception e){
			System.out.println("Error while removing the "+truck.getName()+" from a hub : "+e);
		}
	}

	private Highway getRandomElement(ArrayList<Highway> list) {
        Random rand = new Random(); 
        return list.get(rand.nextInt(list.size())); 
    } 

	private boolean visit(Hub hub,Hub destHub ,HashSet<Location>visited){
		if(visited.contains(hub.getLoc())){
			return false;
		}
		visited.add(hub.getLoc());
		if(hub.getLoc().getX()==destHub.getLoc().getX() && hub.getLoc().getY()==destHub.getLoc().getY()){
			return true;
		}
		ArrayList<Highway>highways=new ArrayList<>(hub.getHighways());
		while(highways.size()>0){
			Highway highway=getRandomElement(highways);
			if(visit(highway.getEnd(),destHub,visited)){
				return true;
			}
			highways.remove(highway);
		}
		
		return false;	
	}

	@Override
	public synchronized Highway getNextHighway(Hub from, Hub dest) {
		HashSet<Location>visited=new HashSet<>();
		visited.add(from.getLoc());
		for(Highway highway:from.getHighways()){
			if(visit(highway.getEnd(),dest,visited) && highway.hasCapacity()){
				return highway;
			}
		}
		return null;
	}

	@Override
	protected synchronized void processQ(int deltaT){
		if(this.truckQueue.isEmpty()){
			return;
		}
		for(int i=this.truckQueue.size()-1;i>=0;i--){
			Truck truck=this.truckQueue.get(i);
			Hub destinationHub=NetworkDemo.getNearestHub(truck.getDest());
			if(destinationHub.getLoc().getX()==truck.getLoc().getX() && destinationHub.getLoc().getY()==truck.getLoc().getY()){
				this.truckQueue.remove(truck);
				truck.enter(null);
				continue;
			}
			Highway nextHighway=this.getNextHighway(this,destinationHub);
			if(nextHighway!=null){
				this.truckQueue.remove(truck);
				truck.enter(nextHighway);
			}
		}
	}
	
}