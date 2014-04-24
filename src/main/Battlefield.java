package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Battlefield extends JFrame {

	private JPanel contentPane;
	Inw player;
	Inw opponent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Battlefield frame = new Battlefield(new Inw("{\"cv_uid\":\"516\",\"fb_id\":\"557757076\",\"firstname_en\":\"Disakorn\",\"lastname_en\":\"Suebsanguan Galassi\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}")
					, new Inw("{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"));
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
	public Battlefield(Inw player,Inw opponent) {
		this.player = player;
		this.opponent = opponent;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		getContentPane().setLayout(new BorderLayout());

		contentPane = new JPanel();

		contentPane.setLayout(new GridLayout(4, 1, 0, 0));
		JPanel LcontentPane = new JPanel();
		JPanel RcontentPane = new JPanel();
		RcontentPane.setLayout(new GridLayout(3, 1, 0, 0));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		getContentPane().add(LcontentPane, BorderLayout.WEST);
		getContentPane().add(RcontentPane, BorderLayout.EAST);


		JPanel opponentPanel = new JPanel();
		//opponent.setBackground(Color.BLACK);
		//	contentPane.add(opponent);
		contentPane.add(opponentPanel);
		JPanel TheirBF = new JPanel();
		TheirBF.setLayout(new GridLayout(0, 5));
		JPanel Theirlane1 = new JPanel();
		Theirlane1.setBackground(Color.RED);
		JPanel Theirlane2= new JPanel();
		Theirlane2.setBackground(Color.BLUE);
		JPanel Theirlane3 = new JPanel();
		Theirlane3.setBackground(Color.YELLOW);
		JPanel Theirlane4= new JPanel();
		Theirlane4.setBackground(Color.GREEN);
		TheirBF.add(opponent);
		TheirBF.add(Theirlane1);
		TheirBF.add(Theirlane2);
		TheirBF.add(Theirlane3);
		TheirBF.add(Theirlane4);

		contentPane.add(TheirBF);

		JPanel MyBF = new JPanel();
		MyBF.setLayout(new GridLayout(0, 5));	
		JPanel Mylane1 = new JPanel();
		Mylane1.setBackground(Color.GREEN);
		JPanel  Mylane2= new JPanel();
		Mylane2.setBackground(Color.YELLOW);
		JPanel  Mylane3 = new JPanel();
		Mylane3.setBackground(Color.BLUE);
		JPanel  Mylane4= new JPanel();
		Mylane4.setBackground(Color.RED);
		MyBF.add(player);
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
