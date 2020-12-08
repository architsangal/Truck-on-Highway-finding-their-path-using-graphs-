package demo19012;

import base.*;

public class TruckDemo extends Truck
{

	public TruckDemo()
	{
		hasStarted = false;
    }


    @Override
	protected void update(int deltaT)
	{
		// totalTime keeps track of the time passed away
		totalTime = totalTime + deltaT;

		// nothing should happen till the truck does not spends the StartTime
		if(totalTime < getStartTime())
            return;
		
		// if the control reached there that means that it has passed all the above constrains
		// And now we will check if the truck has started
		// it is starting for the first time set it to hasStarted variable will be false
		// we set it true if the truck is started for the first time
		// this is executed only first time
		if(hasStarted == false)
		{
			hasStarted = true;

			// finds the nearest hub from the starting Location
			Hub source = NetworkDemo.getNearestHub(this.getSource());

			// We the teleport or jump the truck is it can be added to the truck
			if (source.add(this))
                this.setLoc(source.getLoc());// setting the new location when added to Hub
		}
		else if(this.currentHighway != null)
		{
			// If we are on highway --> is clear if we are on these statements
			// then update the distance in the right direction
			// nextHub is being stored 
			Hub nextHub = this.currentHighway.getEnd();
			
			// calculate the distance travelled in deltaT time.
			// assuming that the truck is moving with max speed
            double distanceCoveredInDeltaT = (3.0/1000.0 * deltaT) * this.currentHighway.getMaxSpeed();
			
			// Finding destination hub like we did for finding the nearest hub to start location
			Hub destinationHub = NetworkDemo.getNearestHub(getDest());

			// my aim here is to calculate angle with the help of tan inverse
			// we can just take the modulus to avoid the confusion
			if (getLoc().getX() != nextHub.getLoc().getX())
			{
				// finding the angle
                double angle = Math.atan(Math.abs((double) (nextHub.getLoc().getY() - getLoc().getY()) / (nextHub.getLoc().getX() - getLoc().getX())));
				
				// finding the distance between the current location of the truck and the nextHub
				double nextHubDistance = Math.sqrt(getLoc().distSqrd(nextHub.getLoc()));

				// we can travel closer to the nextHub
				if (nextHubDistance > distanceCoveredInDeltaT)
				{
					// we find the component of the distanceCoveredInDeltaT along the x and y axises
					// they the the modulus of the projections
                    int distanceAlongX = (int) Math.abs(Math.round(distanceCoveredInDeltaT * Math.cos(angle)));
                    int distanceAlongY = (int) Math.abs(Math.round(distanceCoveredInDeltaT * Math.sin(angle))); 
					
					// finding the sign of the components
					if (distanceAlongX < 1)	distanceAlongX = 1;
                    if (distanceAlongY < 1) distanceAlongY = 1;

					// updating the distances along X anY axises
					if (getLoc().getX() > nextHub.getLoc().getX())	distanceAlongX = -distanceAlongX;
                    if (getLoc().getY() > nextHub.getLoc().getY())  distanceAlongY = -distanceAlongY;

					// updating the location of the truck
                    this.setLoc(new Location(getLoc().getX() + distanceAlongX, getLoc().getY() + distanceAlongY)); // Moving
				}
				else 
				{
					// this is when truck needs to be added to nextHub

					// As instructed we don't have to add the truck to last hub therefore we just jump or teleport the truck
                    if (nextHub.getLoc().getX() == destinationHub.getLoc().getX()
							&& nextHub.getLoc().getY() == destinationHub.getLoc().getY())
					{
                        this.setLoc(getDest());
                        this.currentHighway.remove(this);
						this.currentHighway = null;
                        return;
					}
					
					// when it is not the last Hub
					if (nextHub.add(this))
					{
                        this.setLoc(nextHub.getLoc());
                        this.currentHighway.remove(this);
						this.currentHighway = null;
                    }
                }

			}
			else// corner case problem if we are using tan inverse to calculate the angle we have to create a separete case when angle is 90 degree
			{
				// similar logic as above
				if(Math.abs(nextHub.getLoc().getY() - getLoc().getY()) <= distanceCoveredInDeltaT)
				{
                    if (nextHub.getLoc().getX() == destinationHub.getLoc().getX()
							&& nextHub.getLoc().getY() == destinationHub.getLoc().getY())
					{
                        this.setLoc(getDest());
                        this.currentHighway.remove(this);
        				this.currentHighway = null;
                        return;
                    }
					if (nextHub.add(this))
					{
                        this.setLoc(nextHub.getLoc());

                        this.currentHighway.remove(this);
        				this.currentHighway = null;
                    }

                }
				else
				{
					if (getLoc().getY() > nextHub.getLoc().getY())
                        this.setLoc(new Location(getLoc().getX(), getLoc().getY() - (int) Math.round(distanceCoveredInDeltaT)));
					else
                        this.setLoc(new Location(getLoc().getX(), getLoc().getY() + (int) Math.round(distanceCoveredInDeltaT)));
                }
                
            }
        }
    }

	// All the trucks will have the same name as mentioned in an mail
    @Override
	public String getTruckName()
	{
        return super.getTruckName()+"19012";
    }

	// stores the last hub from which the truck is coming from
	// though it is not used in my code my other students may want to use it
	@Override
	public Hub getLastHub()
	{
        return this.lastHub;
    }

	// put the truck on Highway --> copy of this fact in truck itself
    @Override
	public void enter(Highway hwy)
	{
        this.currentHighway = hwy;
		this.lastHub = hwy.getStart();
    }

	private boolean hasStarted;
    private Hub lastHub = null;
    private Highway currentHighway = null;
    private int totalTime = 0;
}