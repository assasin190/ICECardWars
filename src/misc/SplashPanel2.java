package misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Font;

public class SplashPanel2 extends JPanel{

	/**
	 * Create the panel.
	 */
	AudioPlayer bgMusic;
	int alpha;
	boolean increasing;
	Image i;
	boolean stillOn;
	//public static JLabel progress = new JLabel();
	public SplashPanel2() {
		initGUI();
	}
	private void initGUI() {
	//	progress.setBounds(0, (int) (this.getHeight()/1.3), this.getWidth(), 35);
		//progress.setFont(new Font("Courier New", Font.PLAIN, 24));
		//progress.setForeground(Color.WHITE);
		//progress.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon icon = new ImageIcon("ajax-loader.gif");
		setLayout(null);
		//progress.setIcon(icon);
		//add(progress);
		stillOn = true;
		alpha = 0;
		increasing = true;
		try {
			i = ImageIO.read(new File("Loading2.png"));
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
					SplashPanel2.this.repaint();
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
	
	}
	
	@Override
	public void paintComponent(Graphics g){
		//progress.setBounds(0, (int) (this.getHeight()/1.3), this.getWidth(), 35);
		super.paintComponent(g);
		g.drawImage(i, 0, 0,this.getWidth(),this.getHeight(), null);
		Color c = new Color(0, 0, 0, alpha);
		g.setColor(c);
		g.fillRect(0, 0,this.getWidth(),this.getHeight());
	}
	public static void setProgress(String s){
		//.setText(s);
		//progress.revalidate();
		//progress.repaint();
	}
}
