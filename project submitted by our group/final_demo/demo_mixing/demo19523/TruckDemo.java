package demo19523;
import base.*;

public class TruckDemo extends Truck
{

    private boolean statusHighway = false;
    private int time = 0;
    private Highway theHighway;

    @Override
    public String getTruckName(){
        return "Truck 19523";
    }

    private boolean compareHelper(Location A, Location B) //justa helper function to simplify things
    {
        if(A.getX() == B.getX() && A.getY() == B.getY())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Hub getLastHub()
    {
        Hub previousHub;
        if(this.theHighway == null) //if its not on the highway its previous dest was null(station)
        {
            previousHub = null;
        }
        else
        {
            previousHub = theHighway.getStart(); //if its not, all aboard
        }

        return previousHub;
    }

    public void enter(Highway hwy)
    {
        hwy.add(this); //adding the truck onto the highway
        this.theHighway = hwy;
        this.statusHighway = true;//since its on the highway now
    }

    protected synchronized void update(int deltaT)
    {
        time += deltaT;

        if(time < this.getStartTime())
        {
            this.setLoc(this.getLoc()); 
        }
        else
        {
            if(compareHelper(this.getLoc(), this.getSource()) == true) //if its at the source we are gonna add it to the nearest hub
            {
                Hub hubTemp = Network.getNearestHub(super.getSource());

                if(hubTemp.add(this) == true)
                {
                    this.setLoc(hubTemp.getLoc());
                }
            }
        }

        if(compareHelper(super.getLoc(), super.getDest()) == true)
        {
            this.setLoc(super.getDest());
        }

        else if(this.statusHighway == true) //when the truck is on the highway
        {
            int distance;
            distance = (int)Math.pow(((theHighway.getMaxSpeed()*deltaT *1.0)/1000.0), 2); //calculating distance using maxspeed and deltaT

            if(this.getLoc().distSqrd(theHighway.getEnd().getLoc()) < distance) //moving the truck towards its destination
            {
                Hub hubTemp = Network.getNearestHub(super.getLoc()); 
                Hub destHub = Network.getNearestHub(super.getDest());

                if(compareHelper(hubTemp.getLoc(), destHub.getLoc()) == true) //checking if the hub and the destination hub are the same here to move the truck onto the destination
                {
                    this.setLoc(hubTemp.getLoc());
                    this.setLoc(super.getDest());
                    this.theHighway.remove(this);
                }
                else if(Network.getNearestHub(this.getLoc()).add(this) == true) //if its not the above case, we are gonna move it to the nearest hub and take it off the highway
                {
                    this.statusHighway = false;
                    this.theHighway.remove(this);
                    this.setLoc(hubTemp.getLoc());
                }

                else
                {
                    Location park = new Location(hubTemp.getLoc().getX(), hubTemp.getLoc().getY()); //this is when the hub capacity is full, so  we are going to park it here
                    this.setLoc(park); 
                }
            }
            else
            {
                Hub theEnd = theHighway.getEnd(); //the Hub you wanna reach
                double distanceLeft, sin, cos, X, Y;
                distanceLeft = Math.sqrt(super.getLoc().distSqrd(theEnd.getLoc())); //as it says, calculates distance left

                sin = (1.0*(theEnd.getLoc().getY() - super.getLoc().getY()))/distanceLeft;//calculating the cos and sin values to maintain the angle it is moving at
                cos = (1.0*(theEnd.getLoc().getX() - super.getLoc().getX()))/distanceLeft;

                X = this.theHighway.getMaxSpeed()*deltaT*cos/1000.0;
                Y = this.theHighway.getMaxSpeed()*deltaT*sin/1000.0;

                Location tempSet = new Location(super.getLoc().getX() + (int)Math.round(X), super.getLoc().getY() + (int)Math.round(Y)); //setting its location

                super.setLoc(tempSet);
                

            }
        }
    }
}
