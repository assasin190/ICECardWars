package misc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Dimension;

public class Splash2 extends JFrame {

	private SplashPanel2 splashPane;
	private JPanel contentPane;
	//public static JLabel progress = new JLabel("");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Splash2 frame = new Splash2();
		frame.setLocationRelativeTo(null);
	//	frame.setUndecorated(true);
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	
	public Splash2() {
		initGUI();
	}
	private void initGUI() {
	//	setSize(new Dimension(1920, 1080));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		splashPane = new SplashPanel2();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		getContentPane().add(splashPane);
		
	}
	public void stopThread(){
		splashPane.stillOn = false;
	}
	@Override
	public void dispose(){
		stopThread();
		super.dispose();
	}

}
