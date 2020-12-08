import java.util.ArrayList;
import java.util.Scanner;

import base.*;

public class DemoDriver implements AppControl {

	public static void main(String[] args) {

		DemoDriver driver = new DemoDriver();

		ArrayList<Factory> demoFactories = new ArrayList<>();

		// in the following, replace the "demo" with the package name of each member of
		// the group
		demoFactories.add(new demo.FactoryDemo());
		demoFactories.add(new demo.FactoryDemo());
		demoFactories.add(new demo.FactoryDemo());
		demoFactories.add(new demo.FactoryDemo());
		demoFactories.add(new demo.FactoryDemo());
		demoFactories.add(new demo.FactoryDemo());

		// set up simulation objects
		driver.initialize(demoFactories);

		// Set up the appropriate display
		Display disp = new SwingDisplay(driver);
		//Display disp = new TextDisplay(driver);
		
		driver.setDisplay(disp);

		// initialize the display (essentially for derived classes of Display)
		// Hands over control to the UI
		// which will use appropriate call-backs to run the app
		disp.init();

	}

	// call backs for Display
	@Override
	public void updateView() {
		theNet.redisplay(theDisp);
	}

	@Override
	public void start() {
		// start the simulation
		theNet.start();
		
		// call Network redisplay periodically.
		try {
			for (int i = 0; i < 2000; i++) {	
				theDisp.clear();
				theDisp.update();
				Thread.sleep(displayTimeStep);
			}
		} catch (Exception e) {
			System.out.println("Driver loop:" + e);
		}
	}

	private void initialize(ArrayList<Factory> factories2) {
		factories = factories2;

		theNet= factories.get(0).createNetwork();
		// create network elements and trucks based on input
		// Cycle through factories and create hubs/highways/trucks using
		// different factories
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		String[] tokens = s.split(" ");
		animateTimeStep = Integer.valueOf(tokens[0]); // millisecs
		displayTimeStep = Integer.valueOf(tokens[1]); // millisecs

		// read in hubs
		ArrayList<Hub> hubs = new ArrayList<>();
		int numHubs = Integer.valueOf(sc.nextLine());
		for (int i = 0; i < numHubs; i++) {
			s = sc.nextLine();
			tokens = s.split(" ");
			Hub hub = nextFactory().createHub(new Location(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1])));
			hub.setCapacity(Integer.valueOf(tokens[2]));
			hub.setDeltaT(animateTimeStep);
			hubs.add(hub);
			theNet.add(hub);
		}

		// read in highways
		int numHwys = Integer.valueOf(sc.nextLine());
		for (int i = 0; i < numHwys; i++) {
			s = sc.nextLine();
			tokens = s.split(" ");
			Highway hwy = nextFactory().createHighway();
			hwy.setHubs(hubs.get(Integer.valueOf(tokens[0])), hubs.get(Integer.valueOf(tokens[1])));
			hwy.setCapacity(Integer.valueOf(tokens[2]));
			hwy.setMaxSpeed(Integer.valueOf(tokens[3]));
			theNet.add(hwy);
		}

		// read in trucks - create 2 trucks for each input line
		int numTrucks = Integer.valueOf(sc.nextLine());
		for (int i = 0; i < numTrucks; i++) {
			s = sc.nextLine();
			tokens = s.split(" ");
			Truck truck = nextFactory().createTruck();
			truck.setSourceDest(new Location(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1])),
					new Location(Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3])));
			truck.setStartTime(Integer.valueOf(tokens[4])); // in millisecs
			truck.setDeltaT(animateTimeStep);
			theNet.add(truck);

			truck = nextFactory().createTruck();
			truck.setSourceDest(new Location(Integer.valueOf(tokens[0])+5, Integer.valueOf(tokens[1])+5),
					new Location(Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3])));
			truck.setStartTime(Integer.valueOf(tokens[4])); // in millisecs
			truck.setDeltaT(animateTimeStep);
			theNet.add(truck);
		}
		sc.close();
	}


	private void setDisplay(Display disp) {
		theDisp = disp;
	}

	private Factory nextFactory() {
		int numFactories = factories.size();
		int newIndex = fIndex % numFactories;
		fIndex++;
		return factories.get(newIndex);
	}

	private int fIndex = 0; // for cycling through the factories
	private ArrayList<Factory> factories = new ArrayList<>();
	private int animateTimeStep = 500;
	private int displayTimeStep = 400;

	private Network theNet;
	private Display theDisp;

}
