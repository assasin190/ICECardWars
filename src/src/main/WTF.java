package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import misc.AudioPlayer;
import sun.net.www.content.image.gif;
//import sun.net.www.content.image.gif;



import misc.AudioPlayer;
import sun.net.www.content.image.gif;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;


public class WTF extends JPanel{
	
	private static AudioPlayer bgMusic;

	public WTF() {
	
		//ImageIcon icon = new ImageIcon("coin2.gif");
		
		final JLabel gif = new JLabel();
		JButton tail = new JButton(new ImageIcon("head.gif"));
		tail.setBackground(Color.BLACK);
		tail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		gif.setVisible(false);
		setLayout(null);
		this.add(gif);
		
		JLabel label = new JLabel("Choose One Coin");
		label.setForeground(new Color(216, 191, 216));
		label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 60));
		label.setBounds(470,400,800,300);
		add(label);
		
		JLabel label_1 = new JLabel("");
		add(label_1);
		
		JLabel label_2 = new JLabel("");
		add(label_2);
		JButton head = new JButton(new ImageIcon("gold.jpg"));
		head.setBackground(Color.BLACK);
		head.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		head.setOpaque(true);
		head.setBounds(320,300, 200, 200);
		tail.setBounds(850,300, 200, 200);
		//tail.setBackground(Color.TRANSLUCENT);
		head.setForeground(null);
		this.add(tail);
		this.add(head);
		//bgMusic = new AudioPlayer("lilium.wav");
		//		bgMusic.play();
		
		
		head.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Thread t = new MyThread(gif);
				t.start();
				//Thread t2 = new MyThread2();
				//t2.start();
				/*
				EventQueue.invokeLater(new Runnable() {		

				@Override
				public void run() {
					try {
						Thread.currentThread().sleep(2000);
						int chance = 0;
						if (Math.random()==chance){
							
							JOptionPane.showMessageDialog(new Frame(), "You go First");
							
							
							
						} else{
							JOptionPane.showMessageDialog(new Frame(), "You go Second");
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					// TODO Auto-generated method stub
					
				});
				*/
				
				
			}
			
			
		});
		this.add(tail);
		
		tail.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//gif.setVisible(true);
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Thread t = new MyThread(gif);
				t.start();
				/*
				EventQueue.invokeLater(new Runnable() {		

				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						int chance = 0;
						if (Math.random()==chance){
							
							JOptionPane.showMessageDialog(new Frame(), "You go First");
							
							
							
						} else{
							JOptionPane.showMessageDialog(new Frame(), "You go Second");
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					// TODO Auto-generated method stub
					
				});
				*/
				
			}
			
			
		});
		
	}
	
	public void paintComponent(final Graphics g){
		super.paintComponent(g);
		
				ImageIcon c0 = new ImageIcon("c0.jpg");
				Image c_0 = c0.getImage();


				ImageIcon c1 = new ImageIcon("coin2-0.jpg");
				Image c_1 = c1.getImage();
				
				
				ImageIcon c2 = new ImageIcon("coin2-1.jpg");
				Image c_2 = c2.getImage();
				
				ImageIcon c3 = new ImageIcon("c3.jpg");
				Image c_3 = c3.getImage();
				
				ImageIcon c4 = new ImageIcon("c4.jpg");
				Image c_4 = c4.getImage();
				
				ImageIcon c5 = new ImageIcon("c5.jpg");
				Image c_5 = c5.getImage();
				
				
		ImageIcon bg = new ImageIcon("magic4.jpg");
		Image gg = bg.getImage();
		
	
		g.drawImage(gg, 0, 0,  this.getWidth(), this.getHeight(),null);
		

	}

	public static void main(String[] args){
		WTF a = new WTF();
		bgMusic = new AudioPlayer("WTF2.wav");
		bgMusic.playLoop();
		JFrame test = new JFrame(); 
		test.getContentPane().add(a);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setExtendedState(JFrame.MAXIMIZED_BOTH);
		test.setVisible(true);
		
	}
	
	class MyThread extends Thread {
		JLabel gif;
		
		public MyThread(JLabel gif) {
			super();
			this.gif = gif;

		}
		
		public void run() {
			gif.setVisible(true);
			
			try {
				sleep(2000);
				if (Math.random()>0.5){
					
					JOptionPane.showMessageDialog(new Frame(), "You go First");
					
					
					
				} else{
					JOptionPane.showMessageDialog(new Frame(), "You go Second");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}