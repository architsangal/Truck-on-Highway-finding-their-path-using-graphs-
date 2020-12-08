package demo19062;

import base.*;
// import java.util.*;

class TruckDemo extends Truck {

    //! state variables
    private int currTime = 0;
    private Boolean onHighway = false;
    private Highway currentHighway;

    //! debug utility
    public Highway getCurrentHighway(){
        return this.currentHighway;
    }

	@Override
    public String getTruckName(){
        return "Truck 19062";
    }

    //! return prevHub according to the current highway
	@Override
    public Hub getLastHub() {
        Hub prev;
        //! start hub of a valid highway, i.e not at source
        if (this.currentHighway != null) {
            prev=currentHighway.getStart();
		}
		else{
            //! null if at source
			prev=null;
		}
		return prev;
    }

    @Override   //!place a truck onto the highway
    public void enter(Highway hwy){
        hwy.add(this);
        this.currentHighway = hwy;
        this.onHighway = true;
    }


    @Override
    protected synchronized void update(int deltaT){
        //! increment timestamp
        currTime += deltaT;
        System.out.println("Called update from IMT2019062\n");

        //! do not move until the start-time 
        if(currTime < this.getStartTime()){
            this.setLoc(this.getLoc());
        }
        else{

            //! entery, i.e jump to the first hub
            if (this.getLoc().getX() == this.getSource().getX() && this.getLoc().getY() == this.getSource().getY()) {

                Hub nearestHub = Network.getNearestHub(super.getSource());

                if (nearestHub.add(this)) {
                    System.out.println("Jumped to the firstHub for truck: " + this.getName());
                    this.setLoc(nearestHub.getLoc());
                }

            } 
        }


        //! stay put at the final location if reached
        if (super.getLoc().getX() == super.getDest().getX() && super.getLoc().getY() == super.getDest().getY()) {
            this.setLoc(super.getDest());
        } 

        else if (this.onHighway) {   //! check if we in highway and not in hub i.e we have to now route

            //! if we reach on the next time-step
            if (this.getLoc().distSqrd(currentHighway.getEnd().getLoc()) < Math.pow((currentHighway.getMaxSpeed() * deltaT * 1.00) / 1000, 2)) {
                Hub nearestHub = Network.getNearestHub(super.getLoc());
                Hub destinationHub = Network.getNearestHub(super.getDest());

                //! if we at final hub, set this location again and it will be changed(i.e popped off to destination) in next cycle by processQ
                if (nearestHub.getLoc().getX() == destinationHub.getLoc().getX() && nearestHub.getLoc().getY() == destinationHub.getLoc().getY()) {
                    this.setLoc(nearestHub.getLoc());

                    //! delay to show popoff
                    try {
                        Thread.sleep(deltaT);
                    } catch (InterruptedException e) {}

                    //! remove from highway and jump to destination
                    this.currentHighway.remove(this);
                    this.setLoc(super.getDest());

                } 
                else if (Network.getNearestHub(this.getLoc()).add(this)) {
                    this.currentHighway.remove(this);
                    this.onHighway = false;
                    this.setLoc(nearestHub.getLoc());
                } 
                else {//! if Hub is full, remain near the hub and wait for getting added 
                    this.setLoc(new Location(nearestHub.getLoc().getX() - 2, nearestHub.getLoc().getY() - 2));
                }
            } 
            else {
                //! if we cannot jump in the next time-step, then show the change in direction along with the highways
                Hub highwayEnd = currentHighway.getEnd();

                double distanceRemaining = 1.0 * Math.sqrt(super.getLoc().distSqrd(highwayEnd.getLoc()));

                double sin_current = (1.0 * (highwayEnd.getLoc().getY() - super.getLoc().getY())) / distanceRemaining; 
                double cos_current = (1.0 * (highwayEnd.getLoc().getX() - super.getLoc().getX())) / distanceRemaining; 

                double deltaX = cos_current * this.currentHighway.getMaxSpeed() * deltaT/1000.0;
                double deltaY = sin_current * this.currentHighway.getMaxSpeed() * deltaT/1000.0;

                super.setLoc(new Location(super.getLoc().getX()+(int)Math.round(deltaX), super.getLoc().getY()+(int)Math.round(deltaY)));
            }
        }
    }
}