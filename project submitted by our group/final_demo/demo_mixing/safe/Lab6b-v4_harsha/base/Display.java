package base;

public abstract class Display {
	public Display(AppControl app) {
		myApp = app;
	}
	
	public abstract void drawRect(Location p, int w, int h);
	public abstract void drawCircle(Location c, int rad);
	public abstract void drawText(Location p, String text);
	public abstract void drawLine(Location start, Location end);
	
	public void init() {
		myApp.start();
	}
	
	public void clear() {}
	public void update() {
		myApp.updateView();
	}
	
	protected AppControl getApp() {
		return myApp;
	}
	
	private AppControl myApp;
}
