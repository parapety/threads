package circle;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	static final int WINDOW_HEIGHT = 480;
	static final int WINDOW_WIDTH = 640;

	public MainFrame() {
		super("Clik!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height - WINDOW_HEIGHT;
		int screenWidth = screenSize.width - WINDOW_WIDTH;
		setLocation(screenWidth / 2, screenHeight / 2);
		
		JPanel panel = new Canvas();
		add(panel);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}
}
