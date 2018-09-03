package dots;

public class Cursor {
	
	public Cursor() {
		if (Math.random() > 0.5)
			motion_x = 1;
		else motion_x = -1;
		if (Math.random() > 0.5)
			motion_y = 1;
		else
			motion_y = -1;
	}
	
	boolean active;
	int x; 
	int y;
	
	int rnd = (int)(Math.random() * 30);
	
	private int motion_x;
	private int motion_y;
	private int despawn = 0;
	
	boolean right;

	public void move() {
		if (x == 0 || x == (Launcher.width / Frame.size) - 1)
			motion_x *= -1;
		if (y == 0 || y == (Launcher.height / Frame.size) - 1)
			motion_y *= -1;
		x += motion_x;
		y += motion_y;
		
		if (despawn == 100) {
			active = false;
			despawn = 0;
			if (Math.random() > 0.5)
				motion_x = 1;
			else motion_x = -1;
			if (Math.random() > 0.5)
				motion_y = 1;
			else
				motion_y = -1;
		}else {
			despawn++;
		}
	}
	public void move_down() {
		if (y == (Launcher.height / Frame.size) - 1) {
			active = false;
		}else {
			y += 1;
		}
	}
	public boolean grow() {
		switch (rnd) {
		case 0:
			if (right) {
				if (x < (Launcher.height / Frame.size) - 1) {
					x++;
				}else {
					active = false;
				}
			}else {
				if (x > 0)
					x--;
				else
					active = false;
			}
			break;
		case 1:
			if (x > 0) {
				x--;
				rnd = (int)(Math.random() * 30);
				return true;
			}
			else
				active = false;
		case 2:
			active = false;
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			if (y > 0) {
				y--;
			}else {
				active = false;
			}
			break;
		default:
			break;
		}
		rnd = (int)(Math.random() * 30);
		return false;
	}
}