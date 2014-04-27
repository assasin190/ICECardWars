package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import misc.AudioPlayer;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

public class Battlefield extends JFrame {
	//WARNING!! DO NOT ADD COMPONENT TO CARDHOLDER CLASS
	private JPanel middlePane;
	private JPanel contentPane;
	public static Inw player;
	public static Inw opponent;
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
	private CardHolder p_dumpster;
	private CardHolder o_dumpster;
	private JButton cancelButton;
	private static AudioPlayer bgMusic;
	private boolean firstTurn = true;
	private boolean selected = false;	//will be true if a card is selected and you can use its SA/Spell
	// AND YOU CONFIRM THE SA/spell use by pressing the useButton
	private Card caster = null;
	public static CardHolder selectedCard;
	//TODO: create more efficient method converting from ArrayList to String (label)
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					Battlefield frame = new Battlefield(new Inw("{\"cv_uid\":\"595\",\"fb_id\":\"100003770583869\",\"firstname_en\":\"Pasin\",\"lastname_en\":\"Boonsermsuwong\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}")
					, new Inw("{\"cv_uid\":\"663\",\"fb_id\":\"100003681922761\",\"firstname_en\":\"Ultra\",\"lastname_en\":\"7\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"));
					frame.setVisible(true);
					frame.run();
					bgMusic = new AudioPlayer("Mahou Battle.wav");
					bgMusic.playLoop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Battlefield(Inw player_,Inw opponent_) {
		Battlefield.player = player_;
		Battlefield.opponent = opponent_;
		Battlefield.player.addDeck();
		Battlefield.opponent.addDeck();
		this.playerDeck = new ArrayList<Integer>(player.deck.length);
		for(int a:player.deck){
			playerDeck.add(a);
		}
		this.opponentDeck = new ArrayList<Integer>(opponent.deck.length);
		for(int a:opponent.deck){
			opponentDeck.add(a);
		}

		initGUI();
	}
	/**
	 * 
	 */
	private void initGUI() {
		contentPane = new JPanel();
		this.setContentPane(contentPane);
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println(contentPane.findComponentAt(arg0.getPoint()));
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 800, 700);
		contentPane.setLayout(new BorderLayout());

		middlePane = new JPanel();

		middlePane.setLayout(new GridLayout(4, 1, 0, 0));
		JPanel LcontentPane = new JPanel();
		JPanel RcontentPane = new JPanel();
		RcontentPane.setLayout(new GridLayout(3, 1, 0, 0));
		contentPane.add(middlePane, BorderLayout.CENTER);
		contentPane.add(LcontentPane, BorderLayout.WEST);
		contentPane.add(RcontentPane, BorderLayout.EAST);


		JPanel opponentPanel = new JPanel();
		//opponent.setBackground(Color.BLACK);
		//	contentPane.add(opponent);
		middlePane.add(opponentPanel);
		opponentPanel.setLayout(new GridLayout(0, 1, 0, 0));

		o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
		opponentPanel.add(o_deck);


		TheirBF = new JPanel();
		TheirBF.setLayout(new GridLayout(0, 5));
		Theirlane1 = new CardHolder(CardHolder.OPPONENT,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		Theirlane2 = new CardHolder(CardHolder.OPPONENT,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		Theirlane3 = new CardHolder(CardHolder.OPPONENT,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		Theirlane4 = new CardHolder(CardHolder.OPPONENT,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};		TheirBF.add(opponent);
		TheirBF.add(Theirlane1);
		TheirBF.add(Theirlane2);
		TheirBF.add(Theirlane3);
		TheirBF.add(Theirlane4);
		Theirlane_ref = new CardHolder[4];
		Theirlane_ref[0] = Theirlane1;
		Theirlane_ref[1] = Theirlane2;
		Theirlane_ref[2] = Theirlane3;
		Theirlane_ref[3] = Theirlane4;
		middlePane.add(TheirBF);

		MyBF = new JPanel();
		MyBF.setLayout(new GridLayout(0, 5));	
		Mylane1 = new CardHolder(CardHolder.PLAYER,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		Mylane2 = new CardHolder(CardHolder.PLAYER,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};		Mylane3 = new CardHolder(CardHolder.PLAYER,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		Mylane4 = new CardHolder(CardHolder.PLAYER,false){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			try {
				g.drawImage(ImageIO.read(new File("Lane.jpg")), 0 , 0 ,this.getWidth(), this.getHeight(), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		};
		MyBF.add(player);
		MyBF.add(Mylane1);
		MyBF.add(Mylane2);
		MyBF.add(Mylane3);
		MyBF.add(Mylane4);
		Mylane_ref = new CardHolder[4];
		Mylane_ref[0] = Mylane1;
		Mylane_ref[1] = Mylane2;
		Mylane_ref[2] = Mylane3;
		Mylane_ref[3] = Mylane4;
		middlePane.add(MyBF);

		Theirlane1.setOpposingCH(Mylane1);
		Mylane1.setOpposingCH(Theirlane1);
		Theirlane2.setOpposingCH(Mylane2);
		Mylane2.setOpposingCH(Theirlane2);
		Theirlane3.setOpposingCH(Mylane3);
		Mylane3.setOpposingCH(Theirlane3);
		Theirlane3.setOpposingCH(Mylane4);
		Mylane4.setOpposingCH(Theirlane4);
		JPanel playerPanel = new JPanel();
		middlePane.add(playerPanel);
		playerPanel.setLayout(new GridLayout(0, 1, 0, 0));

		p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));
		playerPanel.add(p_deck);

		o_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		o_hand.setBackground(new Color(255, 204, 255));
		opponentPanel.add(o_hand);
		o_hand.setLayout(new BoxLayout(o_hand, BoxLayout.X_AXIS));

		o_dumpster = new CardHolder(CardHolder.DUMPSTER,false);
		o_dumpster.setBackground(Color.LIGHT_GRAY);
		opponentPanel.add(o_dumpster);
		o_dumpster.setLayout(new BoxLayout(o_dumpster, BoxLayout.X_AXIS));
		p_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		p_hand.setBackground(new Color(255, 204, 255));
		playerPanel.add(p_hand);
		p_hand.setLayout(new BoxLayout(p_hand, BoxLayout.X_AXIS));

		p_dumpster = new CardHolder(CardHolder.DUMPSTER,false);
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
				if(firstTurn){
					firstTurn = false;
					AIturn();
					return;
				}

				endButton.setEnabled(false);
				System.out.println("MP LEFT: "+player.MP_current);
				Main.Turn = false;
				endButton.setEnabled(false);
				//FP of player turn ?FPPL
				for(CardHolder ch:Mylane_ref){
					if(ch.isEmpty())continue;
					Card c = ch.getCard();
					//			if(c==null)continue;
					int dmg = c.generateNetAtk();
					System.out.println(c.title+" generate attack with "+dmg+" damage");

					if(c.directInw){	//attack the inw directly
						if(opponent.attack(dmg)){
							stop();return;
						}
					}
					else{
						CardHolder cho = ch.getOpposingCardHolder();
						if(!cho.isEmpty()){
							Card co = cho.getCard();
							if(co.attack(dmg,false)){	//if the attack kill the monster
								o_dumpster.add(cho.getCard());
								cho.repaint();
					//			cho.removeCard();
								if(Math.random()<co.car){
									System.out.println(co.title+" counterattacked!");
									if(c.attack(dmg,true)){
										p_dumpster.add(c);
										ch.repaint();
							//			ch.removeCard();
									}
								}
						//		ch.repaint();
							}
					//		cho.repaint();
						}else{
							if(opponent.attack(dmg)){//attack the inw directly if there's no opposing card
								stop();return;
							}
						}
					}
					/*			else {
				CardHolder cho = ch.getOpposingCardHolder();
				if(!cho.isEmpty()){
					Card co = cho.getCard();
					if(co.attack(dmg,false)){	//if the attack kill the monster
						p_dumpster.add(cho.getCard());
						cho.removeCard();
						if(Math.random()<co.car){
							System.out.println(co.title+" counterattacked!");
							if(c.attack(dmg,true)){
								o_dumpster.add(c);
								ch.removeCard();
							}
						}
					}	
				}
			}
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 */
					try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				}
				//END FP of player turn		
				for(CardHolder ch:Theirlane_ref){		//disable protection buff for opponent
					if(!ch.isEmpty()){
						ch.getCard().Protected = false;
					}
				}
				for(CardHolder ch:Mylane_ref){		//kill sacrifice card, and disable directInw
					if(!ch.isEmpty()){				//disable direct inw attack buff, SAactivated false
						Card c = ch.getCard();
						if(c.sacrifice){
							System.out.println(c.title+" is killed by SA (sacrifice)");
							p_dumpster.add(c);
							ch.removeCard();
						}else c.directInw = false;	c.SAactivated = false;
					}
				}
				AIturn();		//end
			}
		});
		buttonPanel.add(endButton);
		useButton = new JButton("Use SA/Spell");
		useButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				useButton.setEnabled(false);
				Card c = Main.getSelectedCard();

				if(c.isMonster()){
					switch(c.sa_code){
					case 1: 
						c.apply(c);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					case 2:
						((CardHolder)c.getParent()).getOpposingCardHolder().getCard().apply(c);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					case 3:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 4:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 5:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 6:
						c.apply(c);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					case 7:
						c.apply(c);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					}	
				}else{
					switch(c.spell_code){
					case 1: 
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 2:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 3:		//TODO: TEST  ////  REMOVE CARDS FOR USED SPELLS 
						//ALL CARDS WILL BE RESET (which is probably not a problem)
						System.out.println("SPELL REDRAW USED!");
						while(p_dumpster.getComponentCount()>0){
							Card t = (Card) p_dumpster.getComponent(0);
							playerDeck.add(t.ic_id);
						}
						int drawAmount = (int) Math.min(c.param_value, playerDeck.size());
						Collections.shuffle(playerDeck, new Random(System.currentTimeMillis()));
						//can't draw more than deck size!!
						for(int i = 0;i<drawAmount;i++){
							p_hand.add(new Card(i));
						}
						player.useMP(c.sa_mc);
						((CardHolder)c.getParent()).removeCard();
						break;
					case 4:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						break;
					case 5:
						player.attack((int) -c.param_value);
						((CardHolder)c.getParent()).removeCard();
						break;
					case 6:		//WILL RETURN RANDOMLY FROM DUMPSTER
						if(p_dumpster.getComponentCount()==0){
							System.out.println("Your Dumpster is empty!");
							return;
						}
						int random = 0 + (int)((Math.random() * p_dumpster.getComponentCount()));
						Card temp = (Card) p_dumpster.getComponent(random);
						p_dumpster.remove(random);
						p_hand.add(new Card(temp.ic_id));//the Card should be reset to initial status
						player.useMP(c.sa_mc);
						((CardHolder)c.getParent()).removeCard();
						break;
					case 7:	//return all card with > param rarity to hand
						for(int i = p_dumpster.getComponentCount()-1;i>=0;i--){
							Card t = (Card) p_dumpster.getComponent(i);
							if(t.rr>c.param_value){
								p_dumpster.remove(i);
								p_hand.add(new Card(t.ic_id));
							}
						}
						for(int i = o_dumpster.getComponentCount()-1;i>=0;i--){
							Card t = (Card) o_dumpster.getComponent(i);
							if(t.rr>c.param_value){
								o_dumpster.remove(i);
								o_hand.add(new Card(t.ic_id));
							}
						}
						player.useMP(c.sa_mc);
						((CardHolder)c.getParent()).removeCard();
						break;
					}
				}
				//selected true/cancel set true
				//basically two cases, SA/Spell that get used immediately and one that need further selection
			//	processNotify(Main.getSelectedCard());
			}
		});
		useButton.setEnabled(false);
		buttonPanel.add(useButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){// CANCEL THE SPELL USAGE AFTER YOU PRESSED THE USEBUTTON
			public void actionPerformed(ActionEvent e) {
				useButton.setEnabled(false);
				cancelButton.setEnabled(false);
				selected = false;
				caster = null;
			}
		});
		cancelButton.setEnabled(false);
		buttonPanel.add(cancelButton);

		selectedCard = new CardHolder(CardHolder.DISPLAY,false);
		selectedCard.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
				if(Main.getSelectedCard().getParent() != null)
				processNotify(Main.getSelectedCard());
			}
		});
		RcontentPane.add(selectedCard);
	}
	/**
	 * Run the game
	 */
	public void run(){

		// DO WTF ACTION

		// IF PLAYER GET TO START, call PlayerTurn();
		// else call AITurn();

		//TEST TEST TEST
		playerTurn();
		p_hand.add(new Card(17,true,player));
		p_hand.add(new Card(60,true,player));
		p_hand.add(new Card(55,true,player));
	}
	public void playerTurn(){
		System.out.println("PLAYER TURN");
		Main.Turn = true;
		endButton.setEnabled(true);
		player.restoreMP();		
		for(CardHolder ch:Mylane_ref){		//disable protection buff for player
			if(!ch.isEmpty()){
				ch.getCard().Protected = false;
			}
		}
		//PP ?PPPL
		if(playerDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
			if(player.attack(opponentDeck.size())){
				stop();return;
			}
		}else{
			p_hand.add(new Card(playerDeck.get(0),player));	playerDeck.remove(0);
			p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));	
		}
		System.out.println("PLAYER TURN INITIALS DONE!");
	}
	public void AIturn(){
		System.out.println("OPPONENT TURN");
		Main.Turn = false;
		endButton.setEnabled(false);
		opponent.restoreMP();
		for(CardHolder ch:Theirlane_ref){		//disable protection buff for opponent
			if(!ch.isEmpty()){
				ch.getCard().Protected = false;
			}
		}
		//AI	?PPAI
		//CURRENT AI: if possible, add 3 cards to lane 1,2,3
		if(opponentDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
			if(opponent.attack(playerDeck.size())){
				stop();return;
			}
		}else{
			o_hand.add(new Card(opponentDeck.get(0),opponent));	opponentDeck.remove(0);		
			o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
			//TODO: insert AI here
			Card c;
			if(o_hand.getComponentCount()!=0){
				c = (Card) o_hand.getComponent(0);
				if(Theirlane1.isEmpty()&&opponent.useMP(c.mc)&&c.isMonster())Theirlane1.add(o_hand.getComponent(0));
			}
	//		o_hand.remove(0);
			if(o_hand.getComponentCount()!=0){
				c = (Card) o_hand.getComponent(0);
				if(c!=null&&Theirlane2.isEmpty()&&opponent.useMP(c.mc)&&c.isMonster())Theirlane2.add(o_hand.getComponent(0));
			}
			if(o_hand.getComponentCount()!=0){
				c = (Card) o_hand.getComponent(0);
				if(c!=null&&Theirlane3.isEmpty()&&opponent.useMP(c.mc)&&c.isMonster())Theirlane3.add(o_hand.getComponent(0));
			}
			if(o_hand.getComponentCount()!=0){
				c = (Card) o_hand.getComponent(0);
				if(c!=null&&Theirlane4.isEmpty()&&opponent.useMP(c.mc)&&c.isMonster())Theirlane4.add(o_hand.getComponent(0));
			}
		}

		//END AI
		if(firstTurn){
			firstTurn = false;
			endButton.setEnabled(true);
			playerTurn();
			return;
		}
		//FP ?FPAI
		for(CardHolder ch:Theirlane_ref){
			if(ch.isEmpty())continue;
			Card c = ch.getCard();
			int dmg = c.generateNetAtk();
			System.out.println(c.title+" generate attack with "+dmg+" damage");
			if(c.directInw){	//attack the inw directly
				if(player.attack(dmg)){
					stop();return;
				}
			}
			else {
				CardHolder cho = ch.getOpposingCardHolder();
				if(!cho.isEmpty()){
					Card co = cho.getCard();
					if(co.attack(dmg,false)){	//if the attack kill the monster
						p_dumpster.add(cho.getCard());
						cho.repaint();
			//			cho.removeCard();
						if(Math.random()<co.car){
							System.out.println(co.title+" counterattacked!");
							if(c.attack(dmg,true)){
								o_dumpster.add(c);
								ch.repaint();
						//		ch.removeCard();
							}
						}
			//			ch.repaint();
					}	
		//			cho.repaint();
				}else{		//atk inw directly
					if(player.attack(dmg)){
						stop();return;
					}
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
				}else c.directInw = false;	c.SAactivated = false;
			}
		}
		endButton.setEnabled(true);
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
	/*
	public void tempOpponentAtk(int DMG){
		opponent.attack(DMG);
	}*/
	/**
	 * Card will notify this class when there is a mouse click
	 */
	public void processNotify(Card c){
	//	System.out.println("PROCESS NOTIFY");
		if(!Main.Turn)return;//Do nothing if not your turn
		if(!selected){		//CARD NOT SELECTED
			/**
			 * check if the selected Card can be casted by the user by checking the mana cost and card location (on your lane for monster or on your hand for spell)
			 * if yes, then the button to cast / canel is enabled
			 * ONLY APPLICABLE WITH PLAYER
			 */
			if(c.isMonster()){	//monster
				if(((CardHolder)c.getParent()).type==CardHolder.PLAYER&&player.MP_current>=c.sa_mc&&!c.SAactivated){
					useButton.setEnabled(true);
			//		cancelButton.setEnabled(true);
				}	
			}else if(((CardHolder)c.getParent()).type==CardHolder.PLAYER_HAND&&player.MP_current>=c.mc){
				useButton.setEnabled(true);	
			//	cancelButton.setEnabled(true);
			}else {
				useButton.setEnabled(false);
		//		cancelButton.setEnabled(false);
			}

			//Do nothing if selected card can't be casted sa/spell
		}else if(selected){				//TARGET CARD SELECTED AND READY TO USE THE SA/SPELL
			cancelButton.setEnabled(false);
			System.out.println("CASTER: "+caster.title+" APPLYING SA/SPELL ON: "+c.title);
			if(c.isMonster()){
				switch(caster.sa_code){
				case 3:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);c.SAactivated = true;
						System.out.println("TEST C:"+c.lp);
						player.useMP(caster.sa_mc);
					}else System.out.println("Invalid target type!");
					break;
				case 4:
					if(((CardHolder)c.getParent()).type==CardHolder.OPPONENT){
						c.apply(caster);c.SAactivated = true;
						player.useMP(caster.sa_mc);
					}else System.out.println("Invalid target type!");
					break;
				case 5:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);c.SAactivated = true;
						player.useMP(caster.sa_mc);
					}else System.out.println("Invalid target type!");
					break;
				default:
					System.err.println("ERR: Card doesn't need selection!");
					break;
				}	
			}else{
				switch(caster.spell_code){
				case 1: 
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);
						player.useMP(caster.mc);
					}else System.out.println("Invalid target type!");
					break;
				case 2:
					if(((CardHolder)c.getParent()).type==CardHolder.OPPONENT){
						c.apply(caster);
						player.useMP(caster.mc);
					}else System.out.println("Invalid target type!");
					break;
				case 4:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);
						player.useMP(caster.mc);
					}else System.out.println("Invalid target type!");
					break;
				default:
					System.err.println("ERR: Card doesn't need selection!");
					break;
				}

			}
			useButton.setEnabled(false);
			cancelButton.setEnabled(false);
			selected = false;
			caster = null;

			//lots of switch cases!
			//use mana
			//execute spell (checkCastable should already check if there's enough mana)
			//usebutton/cancelbutton disable
			//selected false and caster = nul/
		}
		//Choose a monster on your side and increase A of
		//the monster by X
		//TODO: can you select yourself!?


	}

}
