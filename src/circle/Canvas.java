package circle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Canvas extends JPanel implements MouseListener, ActionListener {

	ArrayList<Circle> circles = new ArrayList();

	Timer timer;

	public Canvas() {
		addMouseListener(this);
		setPreferredSize(new Dimension(MainFrame.WINDOW_WIDTH, MainFrame.WINDOW_HEIGHT));

		timer = new Timer(20, this);
		timer.setInitialDelay(190);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Circle circle : circles) {
			if (detectCollision(circle)) {
				circle.changeDirection();
			}
			circle.move();
			g2d.setColor(circle.getColor());
			g2d.fill(circle);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (Circle circle : circles) {
			if (circle.contains(e.getX(), e.getY())) {
				circle.toggleMove();
				return;
			}
		}
		circles.add(createCircle(e.getX(), e.getY()));
		repaint();
	}

	private Circle createCircle(double x, double y) {
		Circle circle = new Circle(this, x, y);
		return circle;
	}

	private boolean detectCollision(Circle circle) {
		for (Circle otherCircle : circles) {
			if (otherCircle != circle && circle.getBounds2D().intersects(otherCircle.getBounds2D())) {
				System.out.println(circle.x + " " + circle.y + " " + otherCircle.x + " " + otherCircle.y);
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
