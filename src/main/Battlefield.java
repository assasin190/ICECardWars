package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Battlefield extends JFrame {

	private JPanel contentPane;
	Inw player;
	Inw opponent;
	List<Integer> playerDeck ;
	List<Integer> opponentDeck;
	CardHolder[] Mylane_ref;
	CardHolder[] Theirlane_ref;
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
	private JButton useButton;
	private JPanel p_dumpster;
	private JPanel o_dumpster;
	private JButton cancelButton;
	private boolean selected = false;	//will be true if a card is selected and you can use its SA/Spell
	private Card caster = null;			//Caster of the SA/Spell
	//TODO: create more efficient method converting from ArrayList to String (label)
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
		this.player.getDeck();
		this.opponent.getDeck();
		this.playerDeck = new ArrayList<Integer>(player.deck.length);
		for(int a:player.deck){
			playerDeck.add(a);
		}
		this.opponentDeck = new ArrayList<Integer>(opponent.deck.length);
		for(int a:opponent.deck){
			opponentDeck.add(a);
		}
		TheirBF.add(opponent);
		MyBF.add(player);
		initGUI();
	}
	private void initGUI() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(!Main.Turn)return;//Do nothing if not your turn
				if(!selected){		//CARD NOT SELECTED
					Component c = arg0.getComponent();
					if(c instanceof Card){
						checkCastable((CardHolder)c.getParent());
					}else if(c instanceof CardHolder){
						if(!((CardHolder) c).isEmpty()){
							checkCastable((CardHolder) c);
						}
					}
				}else{				//CARD SELECTED AND READY TO USE THE SA/SPELL
					//TODO;	//lots of switch cases!
					//use mana
					//execute spell (checkCastable should already check if there's enough mana)
					//usebutton/cancelbutton disable
					//selected false and caster = nul/
				}
				
			}
		});
		this.player.addDeck();
		this.opponent.addDeck();
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
		opponentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
		opponentPanel.add(o_deck);
		
		
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
		TheirBF.add(Theirlane1);
		TheirBF.add(Theirlane2);
		TheirBF.add(Theirlane3);
		TheirBF.add(Theirlane4);
		
		Theirlane_ref = new CardHolder[4];
		Theirlane_ref[0] = Theirlane1;
		Theirlane_ref[1] = Theirlane2;
		Theirlane_ref[2] = Theirlane3;
		Theirlane_ref[3] = Theirlane4;
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
		MyBF.add(Mylane1);
		MyBF.add(Mylane2);
		MyBF.add(Mylane3);
		MyBF.add(Mylane4);
		Mylane_ref = new CardHolder[4];
		Mylane_ref[0] = Mylane1;
		Mylane_ref[1] = Mylane2;
		Mylane_ref[2] = Mylane3;
		Mylane_ref[3] = Mylane4;
		contentPane.add(MyBF);
		
		Theirlane1.setOpposingCH(Mylane1);
		Mylane1.setOpposingCH(Theirlane1);
		Theirlane2.setOpposingCH(Mylane2);
		Mylane2.setOpposingCH(Theirlane2);
		Theirlane3.setOpposingCH(Mylane3);
		Mylane3.setOpposingCH(Theirlane3);
		Theirlane3.setOpposingCH(Mylane4);
		Mylane4.setOpposingCH(Theirlane4);
		JPanel playerPanel = new JPanel();
		contentPane.add(playerPanel);
		playerPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));
		playerPanel.add(p_deck);
		
		o_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		o_hand.setBackground(new Color(255, 204, 255));
		opponentPanel.add(o_hand);
		o_hand.setLayout(new BoxLayout(o_hand, BoxLayout.X_AXIS));
		
		o_dumpster = new JPanel();
		o_dumpster.setBackground(Color.LIGHT_GRAY);
		opponentPanel.add(o_dumpster);
		o_dumpster.setLayout(new BoxLayout(o_dumpster, BoxLayout.X_AXIS));
		p_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		p_hand.setBackground(new Color(255, 204, 255));
		playerPanel.add(p_hand);
		p_hand.setLayout(new BoxLayout(p_hand, BoxLayout.X_AXIS));
		
		p_dumpster = new JPanel();
		p_dumpster.setBackground(Color.LIGHT_GRAY);
		playerPanel.add(p_dumpster);
		p_dumpster.setLayout(new BoxLayout(p_dumpster, BoxLayout.X_AXIS));
		
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
		endButton.setEnabled(false);
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//---------------------------------- END PLAYER PP TURN ----------------------------------
				Main.Turn = false;
				endButton.setEnabled(false);
				//FP of player turn
				for(CardHolder ch:Mylane_ref){
					Card c = ch.getCard();
					Card co = ch.getOpposingCardHolder().getCard();
					int dmg = c.generateNetAtk();
					System.out.println(c.title+" generate attack with "+dmg+" damage");
				
					if(c.directInw){	//attack the inw directly
						tempOpponentAtk(dmg);
					}
					else if(co.attack(dmg)){	//if the attack kill the monster
						o_dumpster.add(ch.getCard());
						ch.getOpposingCardHolder().removeCard();
					}else if(Math.random()<co.car){				//else check CAR (only check CAR once)
						System.out.println(co.title+" counterattacked!");
						if(c.attack(dmg)){
							p_dumpster.add(c);
							ch.removeCard();
						}
					}		
					try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				}
				//END FP of player turn		
				for(CardHolder ch:Theirlane_ref){		//disable protection buff for opponent
					if(!ch.isEmpty()){
						ch.getCard().Protected = false;
					}
				}
				for(CardHolder ch:Mylane_ref){		//kill sacrifice card, and disable directInw
					if(!ch.isEmpty()){
						Card c = ch.getCard();
						if(c.sacrifice){
							System.out.println(c.title+" is killed by SA (sacrifice)");
							p_dumpster.add(c);
							ch.removeCard();
						}else c.directInw = false;
					}
				}
				AIturn();		//end
			}
		});
		buttonPanel.add(endButton);
		useButton = new JButton("Use SA/Spell");
		useButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: basically two cases, SA/Spell that get used immediately and one that need further selection
			}
		});
		useButton.setEnabled(false);
		buttonPanel.add(useButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useButton.setEnabled(false);
				cancelButton.setEnabled(false);
				selected = false;
				caster = null;
			}
		});
		cancelButton.setEnabled(false);
		buttonPanel.add(cancelButton);
	}
	/**
	 * Run the game
	 */
	public void run(){
		
		// DO WTF ACTION
		
		// IF PLAYER GET TO START, call PlayerTurn();
		// else call AITurn();
		
		
	}
	public void playerTurn(){
		Main.Turn = true;
		endButton.setEnabled(true);
		player.restoreMP();		
		for(CardHolder ch:Mylane_ref){		//disable protection buff for player
			if(!ch.isEmpty()){
				ch.getCard().Protected = false;
			}
		}
		//PP
		if(playerDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
			if(player.attack(opponentDeck.size())){
				stop();return;
			}
		}else{
			p_hand.add(new Card(playerDeck.get(0)));	playerDeck.remove(0);
			p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));	
		}

	
		
	}
	public void AIturn(){
		Main.Turn = false;
		endButton.setEnabled(false);
		opponent.restoreMP();
		for(CardHolder ch:Theirlane_ref){		//disable protection buff for opponent
			if(!ch.isEmpty()){
				ch.getCard().Protected = false;
			}
		}
		//AI
		//CURRENT AI: if possible, add 3 cards to lane 1,2,3
		if(opponentDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
			if(opponent.attack(playerDeck.size())){
				stop();return;
			}
		}else{//TODO: not done
			o_hand.add(new Card(opponentDeck.get(0)));	opponent.remove(0);
			o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
		}
		
		//END AI
		//FP
		for(CardHolder ch:Theirlane_ref){
			Card c = ch.getCard();
			Card co = ch.getOpposingCardHolder().getCard();
			int dmg = c.generateNetAtk();
			System.out.println(c.title+" generate attack with "+dmg+" damage");
		
			if(c.directInw){	//attack the inw directly
				tempOpponentAtk(dmg);
			}
			else if(co.attack(dmg)){	//if the attack kill the monster
				p_dumpster.add(ch.getCard());
				ch.getOpposingCardHolder().removeCard();
			}else if(Math.random()<co.car){				//else check CAR (only check CAR once)
				System.out.println(co.title+" counterattacked!");
				if(c.attack(dmg)){
					o_dumpster.add(c);
					ch.removeCard();
				}
			}		
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		//END FP
		for(CardHolder ch:Theirlane_ref){		//kill sacrifice card, and disable direct Inw attack
			if(!ch.isEmpty()){
				Card c = ch.getCard();
				if(c.sacrifice){
					System.out.println(c.title+" is killed by SA (sacrifice)");
					o_dumpster.add(c);
					ch.removeCard();
				}else c.directInw = false;
			}
		}
		playerTurn();
	}
	/**
	 * End the game
	 */
	public void stop(){
		this.removeAll();
		JOptionPane.showMessageDialog(null, "GAME ENDED!!", "",JOptionPane.DEFAULT_OPTION);
	}
	/**only used to bypass calling method from nested class
	 * @param DMG
	 */
	public void tempOpponentAtk(int DMG){
		opponent.attack(DMG);
	}
	/**
	 * check if the selected Card can be casted by the user by checking the mana cost and card location (on your lane for monster or on your hand for spell)
	 * if yes, then the button to cast / canel is enabled
	 * ONLY APPLICABLE WITH PLAYER
	 */
	public void checkCastable(CardHolder ch){
		//check if it's your turn AND check if cardHolder is empty should be done at mouselistener
		Card c = ch.getCard();
		if(c.isMonster()){	//monster
			if(ch.type==CardHolder.PLAYER&&player.MP_current>=c.sa_mc){
				useButton.setEnabled(true);
				cancelButton.setEnabled(true);
				selected = true;
				caster = c;
			}	
		}else{				//spell
			if(ch.type==CardHolder.PLAYER_HAND&&player.MP_current>=c.mc){	//TODO: check if mc or not
				useButton.setEnabled(true);
				cancelButton.setEnabled(true);
				selected = true;
				caster = c;
			}
		}
		//Do nothing if selected card can't be casted sa/spell

	}
}
