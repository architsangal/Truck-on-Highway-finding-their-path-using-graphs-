package demo19030;

import java.util.ArrayList;

import base.Highway;
import base.Truck;

class HighwayDemo extends Highway {

	private ArrayList<Truck>truckQueue;

	public HighwayDemo() {
		this.truckQueue=new ArrayList<Truck>();
	}

	ArrayList<Truck>getTruckList(){
		return this.truckQueue;
	}
	
	void resetTruckQueue(){
		this.truckQueue=new ArrayList<Truck>();
	}
	
	@Override
	public boolean hasCapacity() {
		if(this.truckQueue.size()<super.getCapacity()){
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
	public synchronized void remove(Truck truck){
		try{
			this.truckQueue.remove(truck);
		}
		catch(Exception e){
			System.out.println("Error while removing the "+truck.getName()+" from an Highway : "+e);
		}
	}

}
