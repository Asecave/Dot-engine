package dots;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.MouseInfo;

@SuppressWarnings("serial")
public class Frame extends JPanel implements ActionListener, MouseListener, MouseWheelListener{
	
	int timer = 20;
	Timer tm = new Timer(timer, this);
	
	static byte size = 50;
	
	int dot_size = 50;
	
	int hsb_color = 0;
	
	int mode = 1;
	
	Cursor[] cursors;
	
	boolean mouse_pressed;
	
	int mouse_x = MouseInfo.getPointerInfo().getLocation().x, mouse_y = MouseInfo.getPointerInfo().getLocation().y;
	
	Dot[][] dots;
	
	public Frame() {
		tm.start();
		addMouseWheelListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		while (Launcher.width % size != 0 || Launcher.height % size != 0) {
			size--;
		}
		cursors = new Cursor[Launcher.width/size];
		dots = new Dot[Launcher.width/size][Launcher.height/size];
		for (int init1 = 0; init1 < (Launcher.width/size); init1++) {
			for (int init2 = 0; init2 < (Launcher.height/size); init2++) {
				dots[init1][init2] = new Dot();
			}
		}
		mouse_x = MouseInfo.getPointerInfo().getLocation().x / size;
		mouse_y = MouseInfo.getPointerInfo().getLocation().y / size;
		for (int init3 = 0; init3 < cursors.length; init3++) {
			cursors[init3] = new Cursor();
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//Loop
		mouse_x = MouseInfo.getPointerInfo().getLocation().x / size;
		mouse_y = MouseInfo.getPointerInfo().getLocation().y / size;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Launcher.width, Launcher.height);
		for (int draw_x = 0; draw_x < dots.length; draw_x++) {
			for (int draw_y = 0; draw_y < dots[1].length; draw_y++) {
				if (dots[draw_x][draw_y].opacity > 0f) {
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dots[draw_x][draw_y].opacity));
					g2d.setColor(Color.BLACK);
					g2d.fillOval((draw_x * size - ((dots[draw_x][draw_y].size - size) / 2)) - 5, 
							(draw_y * size - ((dots[draw_x][draw_y].size - size) / 2)) - 5, 
							dots[draw_x][draw_y].size + 10, dots[draw_x][draw_y].size + 10);
					
					g2d.setColor(dots[draw_x][draw_y].color);
					g2d.fillOval(draw_x * size - ((dots[draw_x][draw_y].size - size) / 2), 
							draw_y * size - ((dots[draw_x][draw_y].size - size) / 2), 
							dots[draw_x][draw_y].size, dots[draw_x][draw_y].size);
					if (dots[draw_x][draw_y].opacity > 0f) {
						dots[draw_x][draw_y].opacity -= 0.004f;
					}
				}
				dots[draw_x][draw_y].size = dot_size;
			}
		}
		switch (mode) {
		case 1:
			if (mouse_pressed) {
				hsb_color += 2;
				dots[mouse_x][mouse_y].opacity = 1f;
				dots[mouse_x][mouse_y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
			}
			break;
		case 2:
			if (mouse_pressed) {
				hsb_color += 50;
				dots[mouse_x][mouse_y].opacity = 1f;
				dots[mouse_x][mouse_y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
			}
			break;
		case 3:
			hsb_color += 5;
			if (mouse_pressed) {
				summon_cursor(mouse_x, mouse_y);
				mouse_pressed = false;
			}
			for (int findCursors = 0; findCursors < cursors.length; findCursors++) {
				if (cursors[findCursors].active) {
					dots[cursors[findCursors].x][cursors[findCursors].y].opacity = 1f;
					dots[cursors[findCursors].x][cursors[findCursors].y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
					cursors[findCursors].move();
				}
			}
			break;
		case 4:
			hsb_color += 5;
			summon_cursor((int)(Math.random()*dots.length), 0);
			for (int findCursors = 0; findCursors < cursors.length; findCursors++) {
				if (cursors[findCursors].active) {
					dots[cursors[findCursors].x][cursors[findCursors].y].opacity = 1f;
					dots[cursors[findCursors].x][cursors[findCursors].y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
					cursors[findCursors].move_down();
				}
			}
			break;
		case 5:
			hsb_color += 5;
			if (mouse_pressed) {
				summon_cursor(mouse_x, (Launcher.height / size) - 1, false);
				mouse_pressed = false;
			}
			for (int findCursors = 0; findCursors < cursors.length; findCursors++) {
				if (cursors[findCursors].active) {
					dots[cursors[findCursors].x][cursors[findCursors].y].opacity = 1f;
					dots[cursors[findCursors].x][cursors[findCursors].y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
					if (cursors[findCursors].grow()) {
						summon_cursor(cursors[findCursors].x, cursors[findCursors].y, true);
					}
				}
			}
			break;
		case 6:
			hsb_color += 5;
			if ((int)(Math.random() * 10) == 0) {
				summon_cursor((int)(Math.random()*dots.length), (Launcher.height / size) - 1, false);
			}
			for (int findCursors = 0; findCursors < cursors.length; findCursors++) {
				if (cursors[findCursors].active) {
					dots[cursors[findCursors].x][cursors[findCursors].y].opacity = 1f;
					dots[cursors[findCursors].x][cursors[findCursors].y].color = Color.getHSBColor((float) hsb_color / Launcher.height, 1.0f, 1.0f);
					if (cursors[findCursors].grow()) {
						summon_cursor(cursors[findCursors].x, cursors[findCursors].y, true);
					}
				}
			}
			break;
		}
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouse_pressed = true;
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			if (size != 1) {
				size--;
			}else {
				size = 100;
			}
			while (Launcher.width % size != 0 || Launcher.height % size != 0) {
				size--;
			}
			cursors = new Cursor[Launcher.width/size];
			dots = new Dot[Launcher.width/size][Launcher.height/size];
			for (int init1 = 0; init1 < (Launcher.width/size); init1++) {
				for (int init2 = 0; init2 < (Launcher.height/size); init2++) {
					dots[init1][init2] = new Dot();
				}
			}
			mouse_x = MouseInfo.getPointerInfo().getLocation().x / size;
			mouse_y = MouseInfo.getPointerInfo().getLocation().y / size;
			for (int init3 = 0; init3 < cursors.length; init3++) {
				cursors[init3] = new Cursor();
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (mode < 6)
				mode++;
			else
				mode = 1;
			for (int i = 0; i < cursors.length; i++) {
				cursors[i].active = false;
			}
			if (mode == 5 || mode == 6) {
				timer = 200;
				tm.setDelay(timer);
				cursors = new Cursor[Launcher.width/size];
				dots = new Dot[Launcher.width/size][Launcher.height/size];
				for (int init1 = 0; init1 < (Launcher.width/size); init1++) {
					for (int init2 = 0; init2 < (Launcher.height/size); init2++) {
						dots[init1][init2] = new Dot();
					}
				}
				mouse_x = MouseInfo.getPointerInfo().getLocation().x / size;
				mouse_y = MouseInfo.getPointerInfo().getLocation().y / size;
				for (int init3 = 0; init3 < cursors.length; init3++) {
					cursors[init3] = new Cursor();
				}
			}else {
				timer = 20;
				tm.setDelay(timer);
			}
		}
	}
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mouse_pressed = false;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			dot_size++;
		}else {
			dot_size--;
		}
	}
	
	public void summon_cursor(int x, int y) {
		if (mode == 3) {
			if (x == 0)
				x++;
			if (y == 0)
				y++;
			if (x == (Launcher.width / size) - 1)
				x--;
			if (y == (Launcher.height / size) - 1)
				y--;
		}
		for (int i = 0; i < cursors.length; i++) {
			if (!cursors[i].active) {
				cursors[i].active = true;
				cursors[i].x = x;
				cursors[i].y = y;
				break;
			}
		}
	}
	public void summon_cursor(int x, int y, boolean right) {
		if (mode == 3) {
			if (x == 0)
				x++;
			if (y == 0)
				y++;
			if (x == (Launcher.width / size) - 1)
				x--;
			if (y == (Launcher.height / size) - 1)
				y--;
		}
		for (int i = 0; i < cursors.length; i++) {
			if (!cursors[i].active) {
				cursors[i].active = true;
				cursors[i].x = x;
				cursors[i].y = y;
				cursors[i].right = right;
				break;
			}
		}
	}
}