import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Circle {
	
	public int radius;
	public Point center;
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
		
		//generate target point
		
		int speed = 10;
		
		//cos and sin
		// dx = speed * cos of angle
		// dy = ||      sin
		
		dx = speed * Math.cos(angle);
		dy = speed * Math.sin(angle);
	}
	
	void draw(Graphics2D g2) {
		
		Rectangle bounds = new Rectangle(0, 0, Game.PREF_W, Game.PREF_H);
		
		center.x += dx;
		center.y += dy;
		if ((center.x - radius + dx < 0 - radius) || (center.x + radius + dx > bounds.width))
		dx = -dx;
		if ((center.y - radius + dy < 0 - radius) || (center.y + radius + dy > bounds.height))
		dy = -dy;
		g2.setColor(color);
		g2.fillOval(center.x, center.y, radius, radius);
	}
	
}
