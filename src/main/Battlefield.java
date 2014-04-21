package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Battlefield extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Battlefield frame = new Battlefield();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Battlefield() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		setLayout(new BorderLayout());

		contentPane = new JPanel();

		contentPane.setLayout(new GridLayout(4, 1, 0, 0));
		JPanel LcontentPane = new JPanel();
		JPanel RcontentPane = new JPanel();
		RcontentPane.setLayout(new GridLayout(3, 1, 0, 0));
		add(contentPane, BorderLayout.CENTER);
		add(LcontentPane, BorderLayout.WEST);
		add(RcontentPane, BorderLayout.EAST);
		

		JPanel opponent = new JPanel();
		//opponent.setBackground(Color.BLACK);
		contentPane.add(opponent);

		JPanel TheirBF = new JPanel();
		TheirBF.setLayout(new GridLayout(1, 4));
		JPanel Theirlane1 = new JPanel();
		Theirlane1.setBackground(Color.RED);
		JPanel Theirlane2= new JPanel();
		Theirlane2.setBackground(Color.BLUE);
		JPanel Theirlane3 = new JPanel();
		Theirlane3.setBackground(Color.YELLOW);
		JPanel Theirlane4= new JPanel();
		Theirlane4.setBackground(Color.GREEN);
		TheirBF.add(Theirlane1);
		TheirBF.add(Theirlane2);
		TheirBF.add(Theirlane3);
		TheirBF.add(Theirlane4);
		
		contentPane.add(TheirBF);

		JPanel MyBF = new JPanel();
		MyBF.setLayout(new GridLayout(1, 4));	
		JPanel Mylane1 = new JPanel();
		 Mylane1.setBackground(Color.GREEN);
		JPanel  Mylane2= new JPanel();
		 Mylane2.setBackground(Color.YELLOW);
		JPanel  Mylane3 = new JPanel();
		 Mylane3.setBackground(Color.BLUE);
		JPanel  Mylane4= new JPanel();
		 Mylane4.setBackground(Color.RED);
		MyBF.add(Mylane1);
		MyBF.add(Mylane2);
		MyBF.add(Mylane3);
		MyBF.add(Mylane4);

		contentPane.add(MyBF);

		JPanel CardsOnHand = new JPanel();
		CardsOnHand.setLayout(new GridLayout(1, 8));	
		contentPane.add(CardsOnHand);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

	

		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Battlefield.this.dispose();
			}
		});
		buttonPanel.add(quitButton);
		RcontentPane.add(buttonPanel);
	}
}
