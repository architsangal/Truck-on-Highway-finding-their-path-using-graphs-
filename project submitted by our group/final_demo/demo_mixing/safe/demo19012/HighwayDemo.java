package demo19012;

import java.util.ArrayList;

import base.Highway;
import base.Truck;

// Highway class
public class HighwayDemo extends Highway {

	public HighwayDemo()
	{
		super();

		trucks = new ArrayList<>();
	}

	// returns a boolean value if any truck can be added or not
	@Override
	public synchronized boolean hasCapacity() {
		
		if(trucks.size() >= super.getCapacity())
			return false;

		return true;
	}

	// adds the truck given that highway has capacity
	@Override
	public synchronized boolean add(Truck truck) {
		
		if(!hasCapacity())
			return false;
		
		trucks.add(truck);
		return true;
	}

	// removes the truck
	@Override
	public synchronized void remove(Truck truck) {
		trucks.remove(truck);
	}

	private ArrayList<Truck> trucks;
}
