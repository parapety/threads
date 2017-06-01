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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Canvas extends JPanel implements MouseListener, ActionListener {

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
		for (CircleThread thread : getThreads()) {
			synchronized (thread) {
				if (detectCollision(thread)) {
					thread.changeDirection();
				}
				g2d.setColor(thread.circle().getColor());
				g2d.fill(thread.circle());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isMyEvent(e)) {
			createCircle(e.getX(), e.getY());
			repaint();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static ArrayList<CircleThread> getThreads() {
		return (ArrayList) new ArrayList(Thread.getAllStackTraces().keySet()).stream()
				.filter(thread -> thread instanceof CircleThread).collect(Collectors.toList());
	}

	private CircleThread createCircle(double x, double y) {
		CircleThread thread = new CircleThread(this, x, y);
		thread.start();
		if (detectCollision(thread)) {
			thread.shouldContinue = false;
		}
		addMouseListener(thread);
		return thread;
	}

	private boolean detectCollision(CircleThread thread) {
		for (CircleThread otherThread : getThreads()) {
			synchronized (otherThread) {
				if (otherThread.circle() != thread.circle()) {
					if (thread.circle().getBounds().intersects(otherThread.circle().getBounds())
							&& !otherThread.shouldWait
					// ||
					// otherThread.circle().getBounds().intersects(thread.circle().getBounds())
					) {
						/*
						 * if (thread.circle().isEscaping) { continue; }
						 */
						// thread.circle().isEscaping = true;
						return true;
						// thread.move(10, 10);
					}
				}
			}
		}
		return false;
	}

	private boolean isMyEvent(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			return false;
		}
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = image.createGraphics();
		paint(g2);
		int color = image.getRGB(e.getX(), e.getY());
		g2.dispose();

		return color == getBackground().getRGB();
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
	public void mouseClicked(MouseEvent e) {
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
