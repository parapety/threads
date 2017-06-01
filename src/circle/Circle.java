package circle;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Circle extends Ellipse2D.Double {

	final int WIDTH = 20;

	final int HEIGHT = 20;

	private Color color;

	public Circle(double x, double y) {
		setFrame(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		Color[] colors = { Color.YELLOW, Color.BLUE, Color.BLACK };
		color = colors[new Random().nextInt(colors.length)];
	}

	public Color getColor() {
		return color;
	}
}
