package main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Test extends JFrame {
	public static void main(String [] args) throws MalformedURLException, IOException {
		
		for(int j = 0;; j++) {
			System.out.println(j);
			if(j == 9) break;
		}
		
		BufferedImage img = ImageIO.read(new URL("https://graph.facebook.com/100000038984537/picture?type=large&type=square"));
		
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					//			Object[] o = CardData.allCardData();
					JFrame frame = new JFrame();

					frame.setSize(700, 700);
					frame.setVisible(true);
					frame.setLayout(new GridLayout(2,2));
					for(int i = 1;i<4;i++){
						frame.add(new Card(i));
					}
					Card c = new Card(6);
					Card c1 = new Card(6);
					c.atk = 100000;
					System.out.println(c);
					System.out.println(c1);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		
	}
	
	public Test(Card card) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(card);
		this.setSize(221, 324);
		this.setVisible(true);
	}
}