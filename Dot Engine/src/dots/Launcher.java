package dots;

import javax.swing.JFrame;
import java.awt.Toolkit;

import java.awt.GraphicsEnvironment;

public class Launcher extends JFrame{
	public Launcher() {
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		Frame r = new Frame();
		setTitle("Dots");
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(r);
	}
	static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static void main(String[] args) {
		Launcher l = new Launcher();
	}
}