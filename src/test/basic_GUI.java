package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import main.Card;
import java.awt.GridLayout;

public class basic_GUI extends JFrame {

	private JPanel contentPane;
	public JLabel cardShow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		basic_GUI frame = new basic_GUI();
		frame.setVisible(true);
		Card c = new Card(40,true);
		frame.getContentPane().add(c,BorderLayout.WEST);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sleep done");
		Image i = c.createImage(c);
		ImageIcon ic = new ImageIcon(i);
		System.out.println(ic);
		frame.cardShow = new JLabel(ic);
		frame.revalidate();
	}

	/**
	 * Create the frame.
	 */
	public basic_GUI() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		cardShow = new JLabel();
		contentPane.add(cardShow);
	}

}
