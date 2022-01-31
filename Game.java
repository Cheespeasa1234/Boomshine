import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseEvent;

//Honors Computer Science - Mr. Uhl
//Program description: A template class for creating graphical applications

public class Game extends JPanel implements MouseListener {
	// Variables for the class
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 1111;
	public static final int PREF_H = 696;

	public static ArrayList<Circle> circles = new ArrayList<Circle>();
	public static Map<Integer, Integer> levels = new HashMap<Integer, Integer>();
	public static ArrayList<TrajPoint> trajectories = new ArrayList<TrajPoint>();

	// player clicked circle
	public boolean clicked = false;
	public boolean deploded = false;
	public static int circleX = 100000;
	public static int circleY = 100000;
	public static int circleR = 0;
	public static int maxRadius = 150;
	Color circleColor = (new Color(rand(0, 255), rand(0, 255), rand(0, 255), 127));

	public static int score = 0;
	public static int visibleCircles = 0;
	public static int circlesExploded = 0;
	public static int timesClicked = 0;
	public static int level = 1;

	public Timer timer = new Timer(1000 / 60, new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			if (visibleCircles == levels.get(level) && circleR == 0) {

				visibleCircles = 0;
				circles.clear();
				level++;
				for (int i = 0; i < levels.get(level); i++) {
					circles.add(new Circle(rand(100, PREF_W - 100), rand(100, PREF_H - 100), 100,
							new Color(rand(0, 255), rand(0, 255), rand(0, 255), 127), 10 + drand(0, 2)));
				}
			}

			repaint();

			if (clicked && circleR < maxRadius && !deploded) {
				circleR++;
			}

			if (circleR == maxRadius) {
				deploded = true;
			}

			if (deploded) {
				circleR--;
				if (circleR <= 0) {
					clicked = false;
					deploded = false;
				}
			}

			if (clicked) {
				for (int i = 0; i < circles.size(); i++) {
					if (Circle.colliding(new Point(circleX + circleR / 2, circleY + circleR / 2), circleR,
							new Point(circles.get(i).x + circles.get(i).radius / 2,
									circles.get(i).y + circles.get(i).radius / 2),
							circles.get(i).radius)) {
						circles.get(i).dx = 0;
						circles.get(i).dy = 0;
					}

				}
			}
		}
	});

	// Class constructor BOB tHE BUILDER CAN hE FIX IT?????T?T??/
	public Game() {

		this.setFocusable(true);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);

		timer.start();

		levels.put(1, 100);
		levels.put(2, 15);
		levels.put(3, 23);
		levels.put(4, 40);
		levels.put(5, 58);
		levels.put(6, 70);
		levels.put(7, 80);
		levels.put(8, 95);
		levels.put(9, 100);
		levels.put(10, 120);

		for (int i = 0; i < levels.get(level); i++) {
			circles.add(new Circle(rand(100, PREF_W - 100), rand(100, PREF_H - 100), 100,
					new Color(rand(0, 255), rand(0, 255), rand(0, 255), 127), rand(-180, 180)));
			if (circles.get(i).dx == 0 && circles.get(i).dy == 0) {
				circlesExploded++;
				circles.get(i).dx = 1;
				circles.get(i).dy = 1;
			}
		}

	}

	// The method used to add graphical images to the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		String scoreMessage = "Score: " + (circlesExploded * 10 - timesClicked * 10);
		g2.setFont(new Font("Monato", Font.PLAIN, 30));
		g2.drawString(scoreMessage, PREF_W / 2 - g2.getFontMetrics().stringWidth(scoreMessage) / 2, 100);

		if (clicked) {
			g2.setColor(circleColor);
			g2.fillOval(circleX - circleR / 2, circleY - circleR / 2, circleR, circleR);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			g2.drawOval(circleX - maxRadius / 2, circleY - maxRadius / 2, maxRadius, maxRadius);
		}

		for (int i = 0; i < circles.size(); i++) {
			circles.get(i).draw(g2);
		}
		for (int i = 0; i < trajectories.size(); i++) {
			TrajPoint p = trajectories.get(i);
			g2.setColor(new Color(100, 100, 100, p.alpha));
			g2.fillOval(p.p.x, p.p.y, 10, 10);
			p.alpha -= 5;
			if(p.alpha == 0) trajectories.remove(i);
		}
	}

	public int rand(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1) + min);
	}

	public double drand(int min, int max) {
		return Math.random() * (max - min + 1) + min;
	}

	/** ******* METHODS FOR INITIALLY CREATING THE JFRAME AND JPANEL *********/

	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame("My First Panel!");
		JPanel gamePanel = new Game();

		frame.getContentPane().add(gamePanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("OMG YOU JUST CLICKED WTF");
		if (circleR == 0) {
			timesClicked++;
			circleX = e.getX();
			circleY = e.getY();
			circleR = 0;
			clicked = true;
		}
		repaint();
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}