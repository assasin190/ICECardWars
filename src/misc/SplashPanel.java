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

public class SplashPanel extends JPanel{

	/**
	 * Create the panel.
	 */
	
	int alpha;
	boolean increasing;
	Image i;
	public SplashPanel() {
		alpha = 0;
		increasing = true;
		try {
			i = ImageIO.read(new File("temp.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setVisible(true);
		Executor e = Executors.newSingleThreadExecutor();
		e.execute(new Runnable(){
			@Override
			public void run() {
				while(true){
					SplashPanel.this.repaint();
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		//			System.out.println(alpha);
					if(increasing){
						alpha++;
						if(alpha>254){
							increasing = !increasing;
						}
					}
					else alpha--;
					if(alpha==0)increasing = !increasing;	
				}
			}	
		});
		
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
