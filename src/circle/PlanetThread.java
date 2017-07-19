package circle;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class PlanetThread extends CircleThread {

	private double radius = 0;

	private double factor = 0;

	private double initRad = 0;

	private double orbitSpeed = Math.PI / 32;

	public PlanetThread(JPanel panel, double x, double y) throws Exception {
		super(panel, x, y);
		factorY = 1;
		factorX = 1;
		double distX = Math.abs(MainFrame.WINDOW_WIDTH / 2 - x);
		double distY = Math.abs(MainFrame.WINDOW_HEIGHT / 2 - y);
		radius = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		if (radius > MainFrame.WINDOW_HEIGHT / 2) {
			throw new Exception("Planet beyond horizon");
		}
		initRad = Math.asin(distY / radius);
		if (x <= MainFrame.WINDOW_WIDTH / 2 && y >= MainFrame.WINDOW_HEIGHT / 2) {
			initRad = Math.asin(distX / radius);
			initRad += Math.PI * 0.5;
		} else if (x < MainFrame.WINDOW_WIDTH / 2 && y < MainFrame.WINDOW_HEIGHT / 2) {
			initRad += Math.PI;
		} else if (x >= MainFrame.WINDOW_WIDTH / 2 && y <= MainFrame.WINDOW_HEIGHT / 2) {
			initRad = Math.asin(distX / radius);
			initRad += Math.PI * 1.5;
		}

		System.out.println("distY " + distY + " radius " + radius + " factor " + initRad);
	}

	@Override
	public void move(int bumpX, int bumpY) {

		double radian = initRad + factor++ * orbitSpeed;
		double drawX = radius * Math.cos(radian);
		double drawY = radius * Math.sin(radian);

		circle.setFrame(MainFrame.WINDOW_WIDTH / 2 + drawX, MainFrame.WINDOW_HEIGHT / 2 + drawY, circle.WIDTH,
				circle.HEIGHT);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.draw(circle);
	}
}