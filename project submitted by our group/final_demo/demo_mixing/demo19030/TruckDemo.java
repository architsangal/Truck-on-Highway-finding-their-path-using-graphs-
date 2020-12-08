package demo19030;

import base.Highway;
import base.Hub;
import base.Location;
import base.Truck;
import base.Network;


class TruckDemo extends Truck {

	private static int uniqueCodeCounter=0;
	private int uniqueCode=0;
	private Hub previousHub,currentHub,nextHub;
	private Highway currentHighway;
	private boolean onHighway,inHub;
	private int time;

	TruckDemo(){
		this.previousHub=null;
		this.currentHub=null;
		this.currentHighway=null;
		this.nextHub=null;
		this.onHighway=false;
		this.inHub=false;
		this.time=0;
		TruckDemo.uniqueCodeCounter++;
		this.uniqueCode=TruckDemo.uniqueCodeCounter;
	}

	void resetUniqueCodeCounter(){
		TruckDemo.uniqueCodeCounter=0;
	}

	void resetTime(){
		this.time=0;
	}

	public String getUniqueTruckCode(){
		return Integer.toString(this.uniqueCode);
	}

	@Override
	public Hub getLastHub() {
		return this.previousHub;
	}

	void setLastHub(Hub lastHub){
		this.previousHub=lastHub;
	}

	void setNextHub(Hub nextHub){
		this.nextHub=nextHub;
	}

	public Hub getCurrentHub() {
		if(this.inHub){
			return this.currentHub;
		}
		return null;
	}
	void setCurrentHub(Hub currentHub) {
		this.currentHub = currentHub;
	}

	boolean inHub(){
		return this.inHub;
	}
	boolean onHyw(){
		return this.onHighway;
	}

	@Override
	public String getTruckName() {
		String defaultTruckString="Truck19030";
		String uniqueTruckString=Integer.toString(this.uniqueCode);
		return defaultTruckString+uniqueTruckString;
	}

	Highway getCurrentHighway() {
		if(this.onHighway){
			return this.currentHighway;
		}
		return null;
	}

	@Override
	public synchronized void enter(Highway hwy) {
		if(hwy==null){
			this.setLoc(this.getDest());
			this.previousHub=this.currentHub;
			this.currentHub=null;
			this.nextHub=null;
			this.inHub=false;
			this.onHighway=false;
		}
		else{
			this.onHighway=true;
			hwy.add(this);
			this.currentHighway=hwy;
			this.setLastHub(this.currentHighway.getStart());
			this.setNextHub(this.currentHighway.getEnd());
			this.setCurrentHub(null);
			this.inHub=false;
		}
	}

	@Override
	protected synchronized void update(int deltaT) {
		this.time+=deltaT;
		System.out.println("Called the update from IMT2019030\n");
		if(this.time>=this.getStartTime()){
			if(this.getLoc().getX()==this.getSource().getX() && this.getLoc().getY()==this.getSource().getY()){
				Hub sourceNearestHub=Network.getNearestHub(this.getSource());
				if(sourceNearestHub.add(this)){
					this.setLoc(sourceNearestHub.getLoc());	
					this.currentHub=sourceNearestHub;
					this.previousHub=null;
					this.nextHub=null;
					this.inHub=true;
					this.onHighway=false;
				}
			}
			else if(this.getLoc().getX()==this.getDest().getX() && this.getLoc().getY()==this.getDest().getY()){
				this.setLoc(this.getLoc());
			}
			else if(this.onHighway){
				Location nextHubLoc=this.currentHighway.getEnd().getLoc();
				int x1=this.getLoc().getX();
				int y1=this.getLoc().getY();
				double distTravels=(this.currentHighway.getMaxSpeed()*deltaT*1.0)/1000.0;
				double currToDestDist=Math.sqrt(Math.pow((x1-nextHubLoc.getX()),2)+Math.pow((y1-nextHubLoc.getY()),2));
				double cosT=(1.0*(nextHubLoc.getX()-x1))/(currToDestDist);
				double sinT=(1.0*(nextHubLoc.getY()-y1))/(currToDestDist);

				if(distTravels<currToDestDist){
					int newX=x1+(int)(Math.round((distTravels*cosT)));
					int newY=y1+(int)(Math.round(distTravels*sinT));
					this.setLoc(new Location(newX,newY));
				}
				else{
					if(this.onHighway && this.currentHighway.getEnd().add(this)){
						this.currentHighway.remove(this);
						this.inHub=true;
						this.setLoc(new Location(nextHubLoc));
						this.onHighway=false;
						this.currentHub=this.nextHub;
						this.nextHub=null;
					}
					else{
						this.inHub=false;
						this.onHighway=true;
						int newX=nextHubLoc.getX()-1;
						int newY=nextHubLoc.getY()-1;
						this.setLoc(new Location(newX,newY));
					}
				}
			}
		}
	}
}