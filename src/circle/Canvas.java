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

	ArrayList<CircleThread> threads = new ArrayList<CircleThread>();

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
		for (CircleThread thread : threads) {
			synchronized (thread) {
				if (detectCollision(thread.circle())) {
					thread.circle().changeDirection();
				}
				thread.circle().move();
				g2d.setColor(thread.circle().getColor());
				g2d.fill(thread.circle());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (CircleThread thread : threads) {
			if (thread.circle().contains(e.getX(), e.getY())) {
				if (e.getClickCount() == 2) {
					threads.remove(thread);
					repaint();
					return;
				}
				synchronized (thread) {
					thread.notify();
					return;
				}
			}
		}
		threads.add(createCircle(e.getX(), e.getY()));
		repaint();
	}

	private CircleThread createCircle(double x, double y) {
		CircleThread thread = new CircleThread().setPanel(this).setPosX(x).setPosY(y);
		thread.start();
		return thread;
	}

	private boolean detectCollision(Circle circle) {
		for (CircleThread otherThread : threads) {
			synchronized (otherThread) {
				if (otherThread.circle() != circle
						&& circle.getBounds2D().intersects(otherThread.circle().getBounds2D())) {
					System.out.println(
							circle.x + " " + circle.y + " " + otherThread.circle().x + " " + otherThread.circle().y);
					return true;
				}
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

class CircleThread extends Thread {

	private Circle circle;

	private JPanel panel;

	private double posX;

	private double posY;

	@Override
	public void run() {
		synchronized (this) {
			circle = new Circle(panel, posX, posY);
		}

		while (true) {
			synchronized (this) {
				try {
					circle.toggleMove();
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public CircleThread setPanel(JPanel panel) {
		this.panel = panel;
		return this;
	}

	public CircleThread setPosX(double posX) {
		this.posX = posX;
		return this;
	}

	public CircleThread setPosY(double posY) {
		this.posY = posY;
		return this;
	}

	public Circle circle() {
		return circle;
	}
}
