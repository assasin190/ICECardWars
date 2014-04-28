package misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class SplashPanel extends JPanel{

	/**
	 * Create the panel.
	 */
	
	int alpha;
	boolean increasing;
	Image i;
	boolean stillOn;
	public SplashPanel() {
		initGUI();
	}
	private void initGUI() {
		stillOn = true;
		alpha = 0;
		increasing = true;
		try {
			i = ImageIO.read(new File("icw_splash.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setVisible(true);
		
		Executor e = Executors.newSingleThreadExecutor();
		e.execute(new Runnable(){
			@Override
			public void run() {
				while(stillOn){
					SplashPanel.this.repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			//		System.out.println(alpha);
					if(increasing){
						alpha++;
						if(alpha>254){
							increasing = !increasing;
						}
					}
					else alpha--;
					if(alpha==0)increasing = !increasing;	
				}
				System.out.println("Splash executor stopped");
			}	
		});
		setLayout(new BorderLayout(0, 0));
	
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(i, 0, 0, null);
		Color c = new Color(255, 255, 255, alpha);
		g.setColor(c);
		g.fillRect(0, 0, 300, 300);
	}

}
