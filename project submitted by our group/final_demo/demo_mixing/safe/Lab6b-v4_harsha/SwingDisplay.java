import base.AppControl;
import base.Display;
import base.Location;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class SwingDisplay extends Display {

	public SwingDisplay(AppControl app) {
		super(app);
	}

	@Override
	public void drawRect(Location p, int w, int h) {
		if (panelG != null)
			panelG.drawRect(p.getX(), p.getY(), w, h);
	}

	@Override
	public void drawCircle(Location c, int rad) {
		if (panelG != null) {
			//System.out.println("Circle at " + c + " radius " + rad);
			panelG.drawOval(c.getX() -rad/2, c.getY() - rad/2, rad, rad);
		}
	}

	@Override
	public void drawText(Location p, String text) {
		if (panelG != null)
			panelG.drawString(text, p.getX(), p.getY());
	}
	
	@Override
	public void drawLine(Location start, Location end) {
		if (panelG != null)
			panelG.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpGUI();
			}
		});
	}

	@Override
	public void clear() {
		imagePanel.removeAll();
	}

	@Override
	public void update() {
		if (imagePanel != null) {
			//System.out.println("Updating view");
			imagePanel.repaint();
		}
	}

	@SuppressWarnings("serial")
	private void setUpGUI() {
		System.out.println("Set up GUI");

		JFrame frame = new JFrame("Highway Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		topPanel = new JPanel();

		topPanel.setPreferredSize(new Dimension(Width + 200, Height + 10));
		topPanel.setLayout(new FlowLayout());
		Border bborder = BorderFactory.createLineBorder(Color.red);
		frame.add(topPanel);

		imagePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				panelG = g;
				//System.out.println("Repainting");
				getApp().updateView();
			}
		};
		imagePanel.setPreferredSize(new Dimension(Width, Height));
		imagePanel.setBorder(bborder);
		topPanel.add(imagePanel);
		

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
		sidePanel.setBorder(bborder);
		topPanel.add(sidePanel);

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start");
				new Thread() {
					public void run() {
						getApp().start();
					}
				}.start();
			}

		});
		sidePanel.add(startButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				System.exit(0);
			}

		});
		sidePanel.add(exitButton);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	private JPanel topPanel, imagePanel;
	private Graphics panelG;
	private final int Height = 600;
	private final int Width = 900;

}
