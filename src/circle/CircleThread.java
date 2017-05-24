package circle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class CircleThread extends Thread implements MouseListener {

	private Circle circle;

	private JPanel panel;

	private double posX;

	private double posY;

	private static int iter = 0;

	private int factorX = 1;

	private int factorY = 1;

	private boolean shouldContinue = true;

	private boolean shouldWait = false;

	public CircleThread(JPanel panel, double x, double y) {
		setName("kropka " + iter++);

		this.panel = panel;

		factorX = Math.random() > 0.5 ? 1 : -1;
		factorY = Math.random() > 0.5 ? 1 : -1;

		circle = new Circle(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isMyEvent(e)) {
			return;
		}
		synchronized (this) {
			if (SwingUtilities.isRightMouseButton(e)) {
				shouldContinue = false;
			} else {
				shouldWait = !shouldWait;
			}
			notify();
		}
	}

	@Override
	public void run() {
		while (shouldContinue) {
			// System.out.println("Running " + getName());
			synchronized (this) {
				move();
				try {
					if (shouldWait) {
						wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void changeDirection() {
		factorX *= -1;
		factorY *= -1;
	}

	private boolean isMyEvent(MouseEvent e) {
		if (circle().contains(e.getPoint())) {
			return true;
		}
		return false;
	}

	private void move() {
		if (panel.getWidth() < circle.getX() + circle.WIDTH || circle.getX() <= 0) {
			factorX *= -1;
		}
		if (panel.getHeight() < circle.getY() + circle.HEIGHT || circle.getY() <= 0) {
			factorY *= -1;
		}
		circle.setFrame(circle.getX() + 0.000001 * factorX, circle.getY() + 0.000001 * factorY, circle.WIDTH,
				circle.HEIGHT);
	}

	public Circle circle() {
		return circle;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}