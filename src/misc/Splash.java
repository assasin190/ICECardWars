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

public class Splash extends JFrame {

	private SplashPanel splashPane;
	private JPanel contentPane;
	public static JLabel progress = new JLabel("");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Splash frame = new Splash();
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	
	public Splash() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		splashPane = new SplashPanel();
		splashPane.setBackground(Color.WHITE);
		splashPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		splashPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	//	progress = new JLabel("");
		ImageIcon icon = new ImageIcon("aloader.gif");
		progress.setIcon(icon);
	//	icon.setImageObserver(progress);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		getContentPane().add(splashPane);
		getContentPane().add(progress);
	}
	public void stopThread(){
		splashPane.stillOn = false;
	}
	@Override
	public void dispose(){
		stopThread();
		super.dispose();
	}
	public static void setProgress(String s){
		progress.setText(s);
	}
}
