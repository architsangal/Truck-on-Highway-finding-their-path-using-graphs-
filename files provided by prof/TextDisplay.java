import base.AppControl;
import base.Display;
import base.Location;

public class TextDisplay extends Display {

	public TextDisplay(AppControl app) {
		super(app);
	}

	@Override
	public void drawRect(Location p, int w, int h) {
		System.out.println("Rectangle from " + p + " width " + w + " height " + h);
	}

	@Override
	public void drawCircle(Location c, int rad) {
		System.out.println("Circle at " + c + " radius " + rad);
	}

	@Override
	public void drawText(Location p, String text) {
		System.out.println("Text as " + p + ": " + text);
	}

	@Override
	public void drawLine(Location start, Location end) {
		System.out.println("Line from " + start + " to " + end);
	}

}
