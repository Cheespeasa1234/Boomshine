import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Circle {
	
	boolean deploded = false;
	
	public int radius;
	public Point center;
	public int x, y;
	public Color color;
	
	public double angle = 0;
	public double slope;
	
	public double dx = 0;
	public double dy = 0;
	
	Circle(int x, int y, int r, Color c, int angle) {
		this.radius = r;
		this.center = new Point(x,y);
		this.color = c;
		this.angle = Math.toRadians(angle);
		this.slope = Math.tan(angle);
		
		this.x = center.x;
		this.y = center.y;
		
		//generate target point
		int speed = 10;
		
		//cos and sin
		// dx = speed * cos of angle
		// dy = ||      sin
		
		dx = speed * Math.cos(angle);
		dy = speed * Math.sin(angle);
	}
	
	public static double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	public static double dist(Point a, Point b) {
		return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
	
	public static boolean colliding(int x1, int y1, int r1, int x2, int y2, int r2) {
		return dist(x1, y1, x2, y2) <= (r1 + r2) / 2;
	}
	
	public static boolean colliding(Point a, int r1, Point b, int r2) {
		return dist(a, b) < (r1 + r2) / 2;
	}
	
	void draw(Graphics2D g2) {
		
		Rectangle bounds = new Rectangle(0, 0, Game.PREF_W, Game.PREF_H);
		
		center.x += dx;
		center.y += dy;
		
		x = center.x;
		y = center.y;
		
		if(dx == 0 && dy == 0) {
			
			if(radius < Game.maxRadius && !deploded) {				
				radius++;
				x = center.x;
				y = center.y;
			}
			if(radius >= Game.maxRadius) {
				deploded = true;
			}
			if(radius > 0 && deploded) {
				radius--;
				x = center.x;
				y = center.y;
			}
		}
		
		if ((center.x - radius + dx < 0 - radius / 2) || (center.x + radius + dx > bounds.width + radius / 2))
		dx = -dx;
		if ((center.y - radius + dy < 0 - radius / 2) || (center.y + radius + dy > bounds.height + radius / 2))
		dy = -dy;
		g2.setColor(color);
		g2.fillOval(x - radius / 2,y - radius / 2,radius,radius);
	}
	
}
