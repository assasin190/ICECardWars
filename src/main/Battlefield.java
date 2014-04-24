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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

public class Battlefield extends JFrame {

	private JPanel contentPane;
	Inw player;
	Inw opponent;
	List<Integer> playerDeck ;
	List<Integer> opponentDeck;
	private CardHolder o_hand;
	private CardHolder p_hand;
	private JPanel MyBF;
	private JPanel TheirBF;
	private JLabel o_deck;
	private JLabel p_deck;
	private CardHolder Mylane4;
	private CardHolder Mylane3;
	private CardHolder Mylane2;
	private CardHolder Mylane1;
	private CardHolder Theirlane4;
	private CardHolder Theirlane3;
	private CardHolder Theirlane2;
	private CardHolder Theirlane1;
	private JButton endButton;
	private JButton quitButton;
	/**
	 * Launch the application.
	 */
	//TODO: create more efficient method converting from ArrayList to String
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Battlefield frame = new Battlefield(new Inw("{\"cv_uid\":\"516\",\"fb_id\":\"557757076\",\"firstname_en\":\"Disakorn\",\"lastname_en\":\"Suebsanguan Galassi\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}")
					, new Inw("{\"cv_uid\":\"663\",\"fb_id\":\"100003681922761\",\"firstname_en\":\"Ultra\",\"lastname_en\":\"7\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"));
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
		this.player.addDeck();
		this.opponent.addDeck();
		this.playerDeck = new ArrayList<Integer>(player.deck.length);
		for(int a:player.deck){
			playerDeck.add(a);
		}
		this.opponentDeck = new ArrayList<Integer>(opponent.deck.length);
		for(int a:opponent.deck){
			opponentDeck.add(a);
		}
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
		opponentPanel.setLayout(new BorderLayout(0, 0));
		
		o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
		opponentPanel.add(o_deck, BorderLayout.NORTH);
		
		
		TheirBF = new JPanel();
		TheirBF.setLayout(new GridLayout(0, 5));
		Theirlane1 = new CardHolder(CardHolder.OPPONENT,false);
		Theirlane1.setBackground(Color.RED);
		Theirlane2 = new CardHolder(CardHolder.OPPONENT,false);
		Theirlane2.setBackground(Color.BLUE);
		Theirlane3 = new CardHolder(CardHolder.OPPONENT,false);
		Theirlane3.setBackground(Color.YELLOW);
		Theirlane4 = new CardHolder(CardHolder.OPPONENT,false);
		Theirlane4.setBackground(Color.GREEN);
		TheirBF.add(opponent);
		TheirBF.add(Theirlane1);
		TheirBF.add(Theirlane2);
		TheirBF.add(Theirlane3);
		TheirBF.add(Theirlane4);

		contentPane.add(TheirBF);

		MyBF = new JPanel();
		MyBF.setLayout(new GridLayout(0, 5));	
		Mylane1 = new CardHolder(CardHolder.OPPONENT,false);
		Mylane1.setBackground(Color.GREEN);
		Mylane2 = new CardHolder(CardHolder.OPPONENT,false);
		Mylane2.setBackground(Color.YELLOW);
		Mylane3 = new CardHolder(CardHolder.OPPONENT,false);
		Mylane3.setBackground(Color.BLUE);
		Mylane4 = new CardHolder(CardHolder.OPPONENT,false);
		Mylane4.setBackground(Color.RED);
		MyBF.add(player);
		MyBF.add(Mylane1);
		MyBF.add(Mylane2);
		MyBF.add(Mylane3);
		MyBF.add(Mylane4);

		contentPane.add(MyBF);

		JPanel playerPanel = new JPanel();
		contentPane.add(playerPanel);
		playerPanel.setLayout(new BorderLayout(0, 0));
		
		p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));
		playerPanel.add(p_deck, BorderLayout.NORTH);
		
		o_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		o_hand.setBackground(new Color(255, 204, 255));
		opponentPanel.add(o_hand, BorderLayout.CENTER);
		p_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		p_hand.setBackground(new Color(255, 204, 255));
		playerPanel.add(p_hand, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));



		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Battlefield.this.dispose();
			}
		});
		buttonPanel.add(quitButton);
		RcontentPane.add(buttonPanel);
		
		endButton = new JButton("End Turn");
		buttonPanel.add(endButton);
	}
	/**
	 * Run the game
	 */
	public void run(){
		
		// DO WTF ACTION
		
		// IF PLAYER GET TO START, call PlayerTurn();
		// else call AITurn();
		
		
	}
	public void PlayerTurn(){
		if(playerDeck.size()==0){
			player.LP_current -= opponentDeck.size();
			checkIfDead();
		}else{
			
		}
	}
	public void AIturn(){
		if(opponentDeck.size()==0){
			opponent.LP_current -= playerDeck.size();
			checkIfDead();
		}else{
			
		}
		
	}
	public boolean checkIfDead(){
		if(player.LP_current<=0||opponent.LP_current<=0){
			System.err.println("END GAME OPERATION");
			this.removeAll();
			return true;
		}
		return false;
	}
}
