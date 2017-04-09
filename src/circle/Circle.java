package circle;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Circle extends Ellipse2D.Double {

	final int WIDTH = 50;

	final int HEIGHT = 50;

	private int factorX = 1;

	private int factorY = 1;

	private JPanel panel;

	private boolean move = true;

	private Color color;

	public Circle(JPanel panel, double x, double y) {
		setFrame(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		factorX = Math.random() > 0.5 ? 1 : -1;
		factorY = Math.random() > 0.5 ? 1 : -1;
		this.panel = panel;
		Color[] colors = { Color.YELLOW, Color.BLUE, Color.BLACK };
		color = colors[new Random().nextInt(colors.length)];
	}

	public void move(ArrayList<Circle> circles) {
		if (!move) {
			return;
		}
		if (panel.getWidth() < getX() + WIDTH || getX() <= 0) {
			factorX *= -1;
		}
		if (panel.getHeight() < getY() + HEIGHT || getY() <= 0) {
			factorY *= -1;
		}
		for (Circle circle : circles) {
			if (circle != this && circle.intersects(getX(), getY(), getWidth(), getHeight())) {
				double rand = Math.random();
				if (rand > 0.5) {
					factorX *= -1;
				} else {
					factorY *= -1;
				}
			}
		}
		setFrame(getX() + 1 * factorX, getY() + 1 * factorY, WIDTH, HEIGHT);
	}

	public void toggleMove() {
		move = move ? false : true;
	}

	public Color getColor() {
		return color;
	}
}
