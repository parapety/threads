package circle;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class CircleThread extends Thread implements MouseListener {

	protected Circle circle;

	protected JPanel panel;

	protected double posX;

	protected double posY;

	protected static int iter = 0;

	protected int factorX = 1;

	protected int factorY = 1;

	public boolean shouldContinue = true;

	public boolean shouldWait = false;

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
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void changeDirection() {
		factorX *= -1;
		factorY *= -1;
	}

	public int getFactorX() {
		return factorX;
	}

	public int getFactorY() {
		return factorY;
	}

	private boolean isMyEvent(MouseEvent e) {
		if (circle.contains(e.getPoint())) {
			return true;
		}
		return false;
	}

	public void move(int bumpX, int bumpY) {
		if (panel.getWidth() < circle.getX() + circle.WIDTH || circle.getX() <= 0) {
			factorX *= -1;
		}
		if (panel.getHeight() < circle.getY() + circle.HEIGHT || circle.getY() <= 0) {
			factorY *= -1;
		}
		circle.setFrame(circle.getX() + bumpX * factorX, circle.getY() + bumpY * factorY, circle.WIDTH, circle.HEIGHT);
	}

	private void move() {
		move(1, 1);
	}

	public Circle circle() {
		return circle;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(circle.getColor());
		g2d.fill(circle);
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