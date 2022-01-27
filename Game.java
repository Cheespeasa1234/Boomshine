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
import java.util.ArrayList;
import java.awt.event.MouseEvent;

//Honors Computer Science - Mr. Uhl
//Program description: A template class for creating graphical applications

public class Game extends JPanel implements MouseListener {
	// Variables for the class
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 1111;
	public static final int PREF_H = 696;

	public static ArrayList<Circle> circles = new ArrayList<Circle>();
	public static ArrayList<Circle> blownUpCircles = new ArrayList<Circle>();

	// player clicked circle
	public boolean clicked = false;
	public boolean deploded = false;
	public static int circleX = 100000;
	public static int circleY = 100000;
	public static int circleR = 0;
	
	Color circleColor = (new Color(rand(0, 255), rand(0, 255), rand(0, 255), 127));
	
	public static int maxRadius = 150;

	public Timer timer = new Timer(1000 / 60, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			repaint();
			if(clicked && circleR < maxRadius && !deploded) {
				circleR++;
			}
			
			if(circleR == maxRadius) {
				deploded = true;
			}
			
			if(deploded) {
				circleR--;
				if(circleR <= 0) {
					clicked = false;
					deploded = false;
				}
			}
			
			if(clicked) {
				for(int i = 0; i < circles.size(); i++) {
					if(Circle.colliding(new Point(circleX + circleR / 2, circleY + circleR / 2), circleR, new Point(circles.get(i).x + circles.get(i).radius / 2, circles.get(i).y + circles.get(i).radius / 2), circles.get(i).radius)) {
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

		for (int i = 0; i < 10; i++) {
			circles.add(new Circle(rand(100, PREF_W - 100), rand(100, PREF_H - 100), 100,
					new Color(rand(0, 255), rand(0, 255), rand(0, 255), 127), rand(-180, 180)));
			if (circles.get(i).dx == 0 && circles.get(i).dy == 0) {
				circles.get(i).dx = 1;
			}
		}
	}

	// The method used to add graphical images to the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (clicked) {
			g2.setColor(circleColor);
			g2.fillOval(circleX - circleR / 2,circleY - circleR / 2,circleR,circleR);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			g2.drawOval(circleX - maxRadius / 2, circleY - maxRadius / 2, maxRadius, maxRadius);
		}

		for (int i = 0; i < circles.size(); i++) {
			circles.get(i).draw(g2);
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
		if(circleR == 0) {
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