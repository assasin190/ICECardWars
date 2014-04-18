package misc;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fade extends JPanel implements Runnable {

	static Image image;
	private float alpha = 0f;

	public static void main(String[] args) throws IOException {

		image = new ImageIcon(ImageIO.read(new File("temp.png")))
		.getImage();

		JFrame frame = new JFrame("fade frame");
		frame.add(new Fade());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(image.getWidth(frame), image.getHeight(frame));
		// set picture in the center of screen

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		ExecutorService executor = Executors.newFixedThreadPool(1);
		Runnable fade = new Fade();
		executor.execute(fade);

		executor.shutdown();

		// while (!executor.isTerminated()) {
		// }
		// System.out.println("Finished fade in / fade out threads");

	}

	public void run() {
		while (true) {

			while (alpha < 1) {

				try {
					System.out.println(alpha);
					alpha += 0.1;
					Fade.this.repaint();

					Thread.sleep(100);
				} catch (InterruptedException ex) {
			//		Logger.getLogger(Fader.class.getName()).log(Level.SEVERE,
			//				null, ex);
				}
			}

		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		System.out.println("repaint");

		g2d.drawImage(image, 0, 0, null);



		Color c = new Color(255, 255, 255, 0);



		g2d.setColor(c);



		g2d.fillRect(0, 0,this.getWidth(),this.getHeight());



		System.out.println("repaint");

	}

}