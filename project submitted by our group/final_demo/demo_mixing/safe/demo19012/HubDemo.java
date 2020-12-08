package demo19012;

import base.*;

import java.util.ArrayList;

import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;

class HubDemo extends Hub {

	public HubDemo(Location loc) {
		super(loc);
		trucks = new ArrayList<>();
	}

	// adds truck to Hub given if it has capacity
	@Override
	public synchronized boolean add(Truck truck) {
		
		if(trucks.size()>=super.getCapacity())
			return false;
	
		trucks.add(truck);
		return true;
    }

	// removes truck from Hub
	@Override
	protected synchronized void remove(Truck truck) {
        this.trucks.remove(truck);
    }

	// BFS used to calculate the next Hub
	// the routing algorithm used here is BFS
	// This code was taught to us by Prof. V.N. Muralidhara
	// The code which was taught is just modified a little so as to be compatable in the program
	@Override
	public Highway getNextHighway(Hub from, Hub dest) {

		Queue<Hub> queue = new LinkedList<Hub>();
		HashMap<Hub, Hub> parent = new HashMap<Hub, Hub>();
		HashMap<Hub, Boolean> visited = new HashMap<Hub, Boolean>();

		queue.add(this);
		visited.put(this, true);
		parent.put(this, null);

		Hub temp = null;

		// parent corresponds to phi array
		// visited corresponds to visited array
		while(queue.size() > 0)
		{
			temp = queue.poll();
			ArrayList<Highway> Highways = temp.getHighways();
			if(!Highways.isEmpty())
			{
				for(Highway hiways: Highways)
				{
					Hub end = hiways.getEnd();
					if(!visited.containsKey(end) || !visited.get(end))
					{
						visited.put(end, true);
						parent.put(end, temp);
						queue.add(end);
					}
					if(!visited.containsKey(end))
						visited.put(end, false);
				}
			} 
			else
				queue.remove();
		}

		// calculating the answer
		// The parent HashMap is like a dictionary which has key as children and value as parent
		Hub curr = dest;
		Hub prev = null;

		while(parent.get(curr) != null)
		{
			prev = curr;
			curr = parent.get(curr);
		}
		
		// returns Highway correspoding to next Hub which was calculated
		ArrayList<Highway> Highways = this.getHighways();
		for(Highway highway: Highways)
		{
			temp = highway.getEnd();
			if(temp.equals(prev))
				return highway;
		}
		return null;
	} 



	// executed multiple times so that truck can move
	@Override
	protected synchronized void processQ(int deltaT)
	{
		// deltaT was never used
		// this array list is used to avoid the error which I was getting "Cocurrent Modification Error"
		ArrayList<Truck> remove_trucks = new ArrayList<>();
		for(Truck truck : trucks)
		{
			// next Highway is calculated and truck is passed on to that
			Highway tentativeHighway = getNextHighway(truck.getLastHub(), NetworkDemo.getNearestHub(truck.getDest()));
			if(tentativeHighway.add(truck))
			{
				remove_trucks.add(truck);
				truck.enter(tentativeHighway);
			}
		}
		for(Truck truck: remove_trucks)
			this.remove(truck);
	}

    private ArrayList<Truck> trucks;
}
