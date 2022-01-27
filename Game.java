import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

	public ArrayList<Circle> circles = new ArrayList<Circle>();

	public Timer timer = new Timer(1000 / 60, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			repaint();
			
		}
	});
	
	// Class constructor BOB tHE BUILDER CAN hE FIX IT?????T?T??/
	public Game() {
		this.setFocusable(true);
		this.setBackground(Color.WHITE);
		this.addMouseListener(this);
		
		timer.start();
		
		for(int i = 0; i < 10; i++) {
			circles.add(new Circle(rand(100, PREF_W - 100),
					rand(100, PREF_H - 100),
					100,
					new Color(rand(0,255), rand(0,255), rand(0,255), 127), 1, 1));
		}
	}

	// The method used to add graphical images to the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		for(int i = 0; i < circles.size(); i++) {
			circles.get(i).draw(g2);
		}
	}
	
	public int rand(int min, int max) {
		return (int) Math.floor(Math.random()*(max-min+1)+min);
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
		// TODO Auto-generated method stub
		circles.clear();
		for(int i = 0; i < 10; i++) {
			circles.add(new Circle(rand(100, PREF_W - 100),
					rand(100, PREF_H - 100),
					100,
					new Color(rand(0,255), rand(0,255), rand(0,255), 127), 1, 1));
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