package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import misc.AudioPlayer;
import misc.BF_save;
import misc.Dialog;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class Battlefield extends JFrame {
	private static final long serialVersionUID = -3457162401020244642L;
	//WARNING!! DO NOT ADD COMPONENT TO CARDHOLDER CLASS
	private JPanel battlePane;
	private JPanel contentPane;
	public static Inw player;
	public static Inw opponent;
	public ArrayList<Integer> playerDeck ;
	public ArrayList<Integer> opponentDeck;
	CardHolder[] Mylane_ref = new CardHolder[4];
	CardHolder[] Theirlane_ref = new CardHolder[4];
	private CardHolder o_hand;
	private CardHolder p_hand;
	private JPanel MyBF;
	private JPanel TheirBF;
	public CardHolder Mylane4;
	public CardHolder Mylane3;
	public CardHolder Mylane2;
	public CardHolder Mylane1;
	public CardHolder Theirlane4;
	public CardHolder Theirlane3;
	public CardHolder Theirlane2;
	public CardHolder Theirlane1;
	private JButton endButton;
	private JButton useButton;
	public CardHolder p_dumpster;
	public CardHolder o_dumpster;
	private JButton cancelButton;
	private static AudioPlayer bgMusic;
	private boolean lowHealthMusic = false;	//true if player have <10% LP
	private int firstTurn = 2;
	private boolean selected = false;	//will be true if a card is selected and you can use its SA/Spell
	// AND YOU CONFIRM THE SA/spell use by pressing the useButton
	private Card caster = null;
	public static CardHolder selectedCard;
	private boolean isActive = true;	//battlefield is still running
	private JScrollPane p_hand_scr;
	private JScrollPane o_hand_scr;
	private JScrollPane notify_scr;
	public static JTextArea notify;
	private Image lane;
	private JPanel infoPane_2;
	private JButton surrenderButton;
	private JButton quitButton;
	private JTextArea selectedCard_desc;
	private JScrollPane p_dumpster_scr;
	private JScrollPane o_dumpster_scr;
	private JLabel turnLabel;
	private JPanel o_deck_num;
	private JPanel o_dumpster_num;
	private JPanel p_deck_num;
	private JPanel p_dumpster_num;
	private JLabel o_deck_l;
	private JLabel o_dumpster_l;
	private JLabel p_dumpster_l;
	private JLabel p_deck_l;
	private Image dumpsterImage = null;
	private Image deckImage = null;
	private JLabel lblNewLabel;
	private JPanel o_hand_cover;
	public static void main(final String[] args) {

		//final String s = args[0];



		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					Battlefield frame = new Battlefield(new Inw("{\"cv_uid\":\"595\",\"fb_id\":\"100003770583869\",\"firstname_en\":\"Pasin\",\"lastname_en\":\"Boonsermsuwong\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}")
					, new Inw("{\"cv_uid\":\"584\",\"fb_id\":\"1035721781\",\"firstname_en\":\"Min\",\"lastname_en\":\"Uswachoke\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"));

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public Battlefield(String sav){
		try {
			lane = ImageIO.read(new File("Lane.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BF_save savObject = null;
		try {
			FileInputStream fin = new FileInputStream("save.sav");
			ObjectInputStream ois = new ObjectInputStream(fin);
			savObject = (BF_save) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {e.printStackTrace();
		}
		Battlefield.player = savObject.player;
		Battlefield.opponent = savObject.opponent;
	//	playerDeck = savObject.playerDeck;
	//	opponentDeck = savObject.opponentDeck;
		initGUI();
		p_dumpster = savObject.pDumpster;
		o_dumpster = savObject.oDumpster;
		p_hand = savObject.pHand;
		o_hand = savObject.oHand;
		if(savObject.my1!=null)Mylane1.add(savObject.my1);
		if(savObject.my2!=null)Mylane2.add(savObject.my2);
		if(savObject.my3!=null)Mylane3.add(savObject.my3);
		if(savObject.my4!=null)Mylane4.add(savObject.my4);
		if(savObject.th1!=null)Theirlane1.add(savObject.th1);
		if(savObject.th2!=null)Theirlane2.add(savObject.th2);
		if(savObject.th3!=null)Theirlane3.add(savObject.th3);
		if(savObject.th4!=null)Theirlane4.add(savObject.th4);
		
		runBFchecker();
		Main.Turn = true;
		endButton.setEnabled(true);
		//	breakButton.setEnabled(true);
	}
	/**
	 * @wbp.parser.constructor
	 * 
	 */
	public Battlefield(Inw player_,Inw opponent_) {
		try {
			deckImage = ImageIO.read(new File("deck.png"));
			dumpsterImage = ImageIO.read(new File("dumpster.gif"));
			lane = ImageIO.read(new File("Lane.jpg"));
		} catch (IOException e1) {e1.printStackTrace();}
		//bgMusic = new AudioPlayer("Mahou Battle.wav");
		//bgMusic.playLoop();
		Battlefield.player = player_;
		Battlefield.opponent = opponent_;
		//System.out.println("FULLMP"+opponent.MP_full);
		//	System.out.println(player.deck.length);
		if(!Battlefield.player.addDeck()){
			System.out.println("PL DISPOSE");
			JOptionPane.showMessageDialog(this, "You have less than 10 cards in your deck\nThe game will now quit", "Error",JOptionPane.ERROR_MESSAGE);
			setVisible(false);
			dispose();
			return;
		}
		if(!Battlefield.opponent.addDeck()){
			System.out.println("OP DISPOSE");
			JOptionPane.showMessageDialog(this, "Your opponent have less than 10 cards in his/her deck\nThe game will now quit", "Error",JOptionPane.ERROR_MESSAGE);
			setVisible(false);
			dispose();
			return;
		}
		this.playerDeck = new ArrayList<Integer>(player.deck.length);
		for(int a:player.deck){
			playerDeck.add(a);
		}
		this.opponentDeck = new ArrayList<Integer>(opponent.deck.length);
		for(int a:opponent.deck){
			opponentDeck.add(a);
		}

		initGUI();
		setVisible(true);
		run();
	}
	/**
	 * 
	 */
	@SuppressWarnings("serial")
	private void initGUI() {
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JPanel infoPane_1 = new JPanel();
		contentPane.add(infoPane_1);
		infoPane_1.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel buttonPanel = new JPanel();
		infoPane_1.add(buttonPanel);

		endButton = new JButton("End Turn");
		endButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		endButton.setEnabled(false);

		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerFP();
			}
		});

		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(opponent);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));

		buttonPanel.add(endButton);
		useButton = new JButton("Use SA/Spell");
		useButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		useButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				useButton.setEnabled(false);
				useButton.setBackground(new Color(240,240,240));
				Card c = Main.getSelectedCard();

				if(c.isMonster()){
					switch(c.sa_code){
					case 1: 
						c.apply(c);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					case 2:
						Card t = ((CardHolder)c.getParent()).getOpposingCardHolder().getCard();//.apply(c);
						//	System.out.println(t.toString());
						if(t==null){
							JOptionPane.showMessageDialog(null, "Opposing lane is empty!", "Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						if(t.apply(c));o_dumpster.add(t);
						player.useMP(c.sa_mc);c.SAactivated = true;
						break;
					case 3:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						notify.append("Select a monster on your side\n");
						break;
					case 4:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						notify.append("Select a monster on opponent side\n");
						break;
					case 5:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						notify.append("Select a monster on your side\n");
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
						notify.append("Select a monster on your side\n");
						break;
					case 2:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						notify.append("Select a monster on opponent side\n");
						break;
					case 3:	
						//ALL CARDS WILL BE RESET (which is probably not a problem)

						System.out.println("SPELL REDRAW USED!");
						System.out.println("DECKBEFORE"+playerDeck);
						while(p_hand.getComponentCount()>0){
							Card t = (Card) p_hand.getComponent(0);
							playerDeck.add(t.ic_id);
							p_hand.remove(0);
						}
						System.out.println("DECKFITTED"+playerDeck);
						int drawAmount = (int) Math.min(c.param_value, playerDeck.size());
						System.out.println("DRAWAMOUNT: "+drawAmount);
						Collections.shuffle(playerDeck, new Random(System.currentTimeMillis()));
						System.out.println("DECKSHUFFLED"+playerDeck);
						//can't draw more than deck size!!
						for(int i = 0;i<drawAmount;i++){
							p_hand.add(new Card(playerDeck.get(i)));
							playerDeck.remove(i);
						}
						System.out.println("DECKDRAWED"+playerDeck);
						player.useMP(c.sa_mc);
						c.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(c);
						break;
					case 4:
						caster = c;
						selected = true;cancelButton.setEnabled(true);
						notify.append("Select a monster on your side\n");
						break;
					case 5:
						player.attack((int) -c.param_value);
						player.useMP(c.sa_mc);
						c.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(c);
						break;
					case 6:		//WILL RETURN RANDOMLY FROM DUMPSTER
						if(p_dumpster.getComponentCount()==0){
							JOptionPane.showMessageDialog(null, "Your dumpster is empty! Spell not used", "Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
						int random = 0 + (int)((Math.random() * p_dumpster.getComponentCount()));
						Card temp = (Card) p_dumpster.getComponent(random);
						p_dumpster.remove(random);
						p_hand.add(new Card(temp.ic_id));//the Card should be reset to initial status
						player.useMP(c.sa_mc);
						c.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(c);
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
						c.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(c);
						break;
					}
				}
				//selected true/cancel set true
				//basically two cases, SA/Spell that get used immediately and one that need further selection
				//	processNotify(Main.getSelectedCard());
			}
		});

		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		useButton.setEnabled(false);
		useButton.setBackground(new Color(240,240,240));
		buttonPanel.add(useButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancelButton.addActionListener(new ActionListener(){// CANCEL THE SPELL USAGE AFTER YOU PRESSED THE USEBUTTON
			public void actionPerformed(ActionEvent e) {
				useButton.setEnabled(false);useButton.setBackground(new Color(240,240,240));
				cancelButton.setEnabled(false);
				selected = false;
				caster = null;
				processNotify(Main.getSelectedCard());
			}
		});
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));


		cancelButton.setEnabled(false);
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 20)));
		buttonPanel.add(player);
		buttonPanel.add(Box.createVerticalGlue());

		battlePane = new JPanel();
		battlePane.setPreferredSize(new Dimension(656, 768));
		contentPane.add(battlePane);
		battlePane.setLayout(new GridLayout(0, 1, 0, 0));
		JPanel opponentPanel = new JPanel();
		battlePane.add(opponentPanel);
		opponentPanel.setLayout(new BorderLayout(0, 0));

		o_deck_num = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				g.drawImage(deckImage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		opponentPanel.add(o_deck_num, BorderLayout.WEST);
		o_deck_num.setLayout(new BoxLayout(o_deck_num, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(" DECK ");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 23));
		o_deck_num.add(label);
		o_deck_l = new JLabel("");
		o_deck_l.setForeground(Color.WHITE);
		o_deck_l.setHorizontalAlignment(SwingConstants.CENTER);
		o_deck_l.setAlignmentX(Component.CENTER_ALIGNMENT);
		o_deck_l.setFont(new Font("Tahoma", Font.PLAIN, 40));
		o_deck_num.add(o_deck_l);

		o_dumpster_num = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				g.drawImage(dumpsterImage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		opponentPanel.add(o_dumpster_num, BorderLayout.EAST);
		o_dumpster_num.setLayout(new BoxLayout(o_dumpster_num, BoxLayout.Y_AXIS));
		JLabel label_3 = new JLabel("DUMPSTER");
		label_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_3.setForeground(Color.WHITE);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		o_dumpster_num.add(label_3);
		o_dumpster_l = new JLabel("");
		o_dumpster_l.setForeground(Color.WHITE);
		o_dumpster_l.setHorizontalAlignment(SwingConstants.CENTER);
		o_dumpster_l.setAlignmentX(Component.CENTER_ALIGNMENT);
		o_dumpster_l.setFont(new Font("Tahoma", Font.PLAIN, 40));
		o_dumpster_num.add(o_dumpster_l);
		
		o_hand_cover = new JPanel();
		o_hand_cover.setBackground(Color.PINK);
		opponentPanel.add(o_hand_cover, BorderLayout.NORTH);



		o_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		o_hand.setBackground(new Color(255, 204, 255));
		o_hand_scr = new JScrollPane(o_hand);
		opponentPanel.add(o_hand_cover);
		//opponentPanel.add(o_hand);
		o_hand.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));

		o_dumpster = new CardHolder(CardHolder.DUMPSTER,false);
		o_dumpster.setBackground(Color.LIGHT_GRAY);
		o_dumpster.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		o_dumpster_scr = new JScrollPane(o_dumpster);
		//	opponentPanel.add(o_dumpster_scr);


		TheirBF = new JPanel();
		//	TheirBF.add(opponent);
		TheirBF.setLayout(new GridLayout(1, 0, 0, 0));
		Theirlane1 = new CardHolder(CardHolder.OPPONENT,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);

			}
		};
		TheirBF.add(Theirlane1);
		Theirlane_ref[0] = Theirlane1;
		Theirlane1.setLayout(new GridLayout(1, 0, 0, 0));
		Theirlane2 = new CardHolder(CardHolder.OPPONENT,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		TheirBF.add(Theirlane2);
		Theirlane_ref[1] = Theirlane2;
		Theirlane2.setLayout(new GridLayout(1, 0, 0, 0));
		Theirlane3 = new CardHolder(CardHolder.OPPONENT,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		TheirBF.add(Theirlane3);
		Theirlane_ref[2] = Theirlane3;
		Theirlane3.setLayout(new GridLayout(1, 0, 0, 0));
		Theirlane4 = new CardHolder(CardHolder.OPPONENT,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		TheirBF.add(Theirlane4);
		Theirlane_ref[3] = Theirlane4;
		Theirlane4.setLayout(new GridLayout(1, 0, 0, 0));
		battlePane.add(TheirBF);

		MyBF = new JPanel();
		//	MyBF.add(player);
		MyBF.setLayout(new GridLayout(0, 4, 0, 0));



		Mylane1 = new CardHolder(CardHolder.PLAYER,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		MyBF.add(Mylane1);
		Mylane_ref[0] = Mylane1;
		Mylane1.setLayout(new GridLayout(1, 0, 0, 0));


		Mylane2 = new CardHolder(CardHolder.PLAYER,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};		
		MyBF.add(Mylane2);
		Mylane_ref[1] = Mylane2;
		Mylane2.setLayout(new GridLayout(1, 0, 0, 0));
		battlePane.add(MyBF);


		Mylane3 = new CardHolder(CardHolder.PLAYER,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		MyBF.add(Mylane3);
		Mylane_ref[2] = Mylane3;
		Mylane3.setLayout(new GridLayout(1, 0, 0, 0));
		Mylane3.setOpposingCH(Theirlane3);
		Theirlane3.setOpposingCH(Mylane3);
		Mylane4 = new CardHolder(CardHolder.PLAYER,false){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(lane, 0 , 0 ,this.getWidth(), this.getHeight(), this);
			}
		};
		MyBF.add(Mylane4);
		Mylane_ref[3] = Mylane4;
		Mylane4.setLayout(new GridLayout(1, 0, 0, 0));
		JPanel playerPanel = new JPanel();
		battlePane.add(playerPanel);
		playerPanel.setLayout(new BorderLayout(0, 0));

		p_deck_num = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				g.drawImage(deckImage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		playerPanel.add(p_deck_num, BorderLayout.WEST);
		p_deck_num.setLayout(new BoxLayout(p_deck_num, BoxLayout.Y_AXIS));
		
		JLabel label_1 = new JLabel(" DECK ");
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		p_deck_num.add(label_1);

		p_deck_l = new JLabel("");
		p_deck_l.setForeground(Color.WHITE);
		p_deck_l.setHorizontalAlignment(SwingConstants.CENTER);
		p_deck_l.setAlignmentX(Component.CENTER_ALIGNMENT);
		p_deck_l.setFont(new Font("Tahoma", Font.PLAIN, 40));
		p_deck_num.add(p_deck_l);

		p_dumpster_num = new JPanel(){
		@Override
		public void paintComponent(Graphics g){
			super.paintComponents(g);
			g.drawImage(dumpsterImage, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		};
		playerPanel.add(p_dumpster_num, BorderLayout.EAST);
		p_dumpster_num.setLayout(new BoxLayout(p_dumpster_num, BoxLayout.Y_AXIS));
		JLabel label_2 = new JLabel("DUMPSTER");
		label_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		p_dumpster_num.add(label_2);
		p_dumpster_l = new JLabel("");
		p_dumpster_l.setForeground(Color.WHITE);
		p_dumpster_l.setHorizontalAlignment(SwingConstants.CENTER);
		p_dumpster_l.setFont(new Font("Tahoma", Font.PLAIN, 40));
		p_dumpster_l.setAlignmentX(Component.CENTER_ALIGNMENT);
		p_dumpster_num.add(p_dumpster_l);


		p_hand = new CardHolder(CardHolder.PLAYER_HAND,true);
		p_hand.setBackground(new Color(255, 204, 255));
		p_hand_scr = new JScrollPane(p_hand);
		playerPanel.add(p_hand_scr);


		p_hand.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));

		Mylane1.setOpposingCH(Theirlane1);
		Theirlane1.setOpposingCH(Mylane1);
		Mylane2.setOpposingCH(Theirlane2);
		Theirlane2.setOpposingCH(Mylane2);
		Mylane4.setOpposingCH(Theirlane4);
		Theirlane4.setOpposingCH(Mylane4);


		infoPane_2 = new JPanel();
		contentPane.add(infoPane_2);
		infoPane_2.setLayout(new BoxLayout(infoPane_2, BoxLayout.Y_AXIS));

		selectedCard = new CardHolder(CardHolder.DISPLAY,false);
		selectedCard.setPreferredSize(new Dimension(14, 350));
		infoPane_2.add(selectedCard);
		selectedCard.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent arg0) {
				if(Main.getSelectedCard().getParent() != null)
					processNotify(Main.getSelectedCard());
			}
		});
		selectedCard.setLayout(new GridLayout(1, 0, 0, 0));

		selectedCard_desc = new JTextArea();
		selectedCard_desc.setPreferredSize(new Dimension(150,24));
		infoPane_2.add(selectedCard_desc);
		selectedCard_desc.setWrapStyleWord(true);
		selectedCard_desc.setLineWrap(true);

		surrenderButton = new JButton("Surrender");
		surrenderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to surrender?");
				if(a == JOptionPane.YES_OPTION){
					player.LP_current=0;
					player.updateGUI();
					stop();
				}
			}
		});
		surrenderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPane_2.add(Box.createRigidArea(new Dimension(20, 20)));
		turnLabel = new JLabel();
		turnLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoPane_2.add(turnLabel);
		infoPane_2.add(Box.createRigidArea(new Dimension(20, 20)));
		infoPane_2.add(surrenderButton);
		infoPane_2.add(Box.createRigidArea(new Dimension(20, 20)));

		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
				if(a == JOptionPane.YES_OPTION){
					stop();
				}
			}
		});
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPane_2.add(quitButton);
		infoPane_2.add(Box.createRigidArea(new Dimension(20, 20)));
		//	infoPane_2.add(opponent);
		//	infoPane_2.add(player);
		notify = new JTextArea();
		notify.setFont(new Font("Monospaced", Font.PLAIN, 12));
		DefaultCaret caret = (DefaultCaret)notify.getCaret();
		notify_scr = new JScrollPane(notify);
		infoPane_2.add(notify_scr);
		notify_scr.setPreferredSize(new Dimension(150, 200));
		//TEST TEST TEST

		//	p_hand.add(new Card(18));
		//	p_hand.add(new Card(53));

		p_dumpster = new CardHolder(CardHolder.DUMPSTER,false);
		p_dumpster.setBackground(Color.LIGHT_GRAY);

		p_dumpster.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		//	p_hand.add(new Card(49));
		//	p_hand.add(new Card(50));
		//	p_dumpster.add(new Card(40));
		//	p_dumpster.add(new Card(45));
		p_dumpster_scr = new JScrollPane(p_dumpster);
		//	playerPanel.add(p_dumpster_scr);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//		playerDeck.
	}
	/**
	 * Run the game
	 */
	public void run(){	
		runBFchecker();
		bgMusic = new AudioPlayer("cgb1.wav");
		bgMusic.playLoop();
		setVisible(true);
		Collections.shuffle(playerDeck, new Random(System.currentTimeMillis()));
		Collections.shuffle(opponentDeck, new Random(System.currentTimeMillis()));
		for(int i = 0;i<5;i++){
			p_hand.add(new Card(playerDeck.get(0)));	playerDeck.remove(0);
			o_hand.add(new Card(opponentDeck.get(0)));	opponentDeck.remove(0);
		}
		
		WTF wtf = new WTF();
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				while(true){
					if(WTF.a!=null){
						if(WTF.a){
							playerPP();
							break;
						}else{
							AIturn();
							break;
						}
					}
				}
			}
		});
	}
	public void runBFchecker(){
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				System.out.println("Thread battlefield checker started");
				while(isActive){
					for(CardHolder ch:Mylane_ref){
						if(ch.getComponentCount()==2){
							p_dumpster.add(ch.getComponent(1));
							ch.getCard().addListeners();
						}
					}
					for(CardHolder ch:Theirlane_ref){
						if(ch.getComponentCount()==2){
							o_dumpster.add(ch.getComponent(1));
							ch.getCard().addListeners();
							ch.getCard().addListeners();
						}
					}
					if(!lowHealthMusic&&player.LP_current<10){
						bgMusic.close();
						bgMusic = new AudioPlayer("cgb2.wav");
						bgMusic.playLoop();
						lowHealthMusic = true;
						JOptionPane.showMessageDialog(null, "Your LP is low!");
						
					}
					if(!lowHealthMusic&&opponent.LP_current<10) {
						bgMusic.close();
						bgMusic = new AudioPlayer("cgb2.wav");
						bgMusic.playLoop();
						lowHealthMusic = true;
						JOptionPane.showMessageDialog(null, "Enemy LP is low!");
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {e.printStackTrace();
					}
					if(Main.Turn&&player.MP_current==0){
						Main.Turn = false;
						playerFP();
					}
					p_deck_l.setText(playerDeck.size()+"");
					p_dumpster_l.setText(p_dumpster.getComponentCount()+"");
					o_deck_l.setText(opponentDeck.size()+"");
					o_dumpster_l.setText(o_dumpster.getComponentCount()+"");
					Battlefield.this.repaint();
				}
				System.out.println("Thread battlefield checker ended");
			}

		});
	}
	public void playerPP(){
		Dialog dialog = new Dialog("PLAYER PREPARE PHRASE");
		turnLabel.setText("PLAYER PP");
		try {Thread.sleep(1200);} catch (InterruptedException e1) {}
		notify.append("-----------------------------\nPlayer preparation phrase\n-----------------------------\n");
		System.out.println("PLAYER PP TURN");
		Main.Turn = true;
		endButton.setEnabled(true);
		player.restoreMP();	
		//PP ?PPPL
		if(playerDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
			notify.append("Player deck is empty, receiving"+opponentDeck.size()+" damages\n");
			if(player.attack(opponentDeck.size())){
				stop();return;
			}
		}else{
			Card c = new Card(playerDeck.get(0));
			notify.append("Player drawn "+c.title+" from deck\n");
			p_hand.add(c);	playerDeck.remove(0);
			p_hand.revalidate();p_hand.repaint();
			c.effectColor(Color.WHITE);
			//		p_deck = new JLabel("DECK: "+Arrays.toString(playerDeck.toArray()));	
		}
		System.out.println("PLAYER TURN INITIALS DONE!");
	}
	public void playerFP(){
	
		turnLabel.setText("PLAYER FP");
		try {Thread.sleep(1200);} catch (InterruptedException e1) {}
		endButton.setEnabled(false);
		//---------------------------------- END PLAYER PP TURN ----------------------------------
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				if(firstTurn!=0){
					firstTurn--;
					AIturn();
					return;
				}
				Dialog dialog = new Dialog("PLAYER FIGHT PHRASE");
				notify.append("-----------------------------\nPlayer fighting phrase\n-----------------------------\n");
				System.out.println("PLAYER FP TURN");
				endButton.setEnabled(false);
				useButton.setEnabled(false);useButton.setBackground(new Color(240,240,240));
				//	breakButton.setEnabled(false);

				Main.Turn = false;
				//FP of player turn ?FPPL
				for(CardHolder ch:Mylane_ref){
					if(ch.isEmpty())continue;
					Card c = ch.getCard();
					AudioPlayer bgMusic2 = new AudioPlayer("Attack.wav");
					bgMusic2.play();
					int dmg = c.generateNetAtk();
					System.out.println(c.title+" generate attack with "+dmg+" damage");
					c.effectAttack();
					if(c.directInw){	//attack the inw directly
						if(opponent.attack(dmg)){
							stop();
							bgMusic.stop();
							return;
						}
					}
					else{
						CardHolder cho = ch.getOpposingCardHolder();
						if(!cho.isEmpty()){
							Card co = cho.getCard();
							if(co.attack(dmg,false)){	//if the attack kill the monster
								o_dumpster.add(cho.getCard());
								//			cho.repaint();
							}else{
								if(Math.random()<co.car){
									System.out.println(co.title+" counterattacked!");
									notify.append(co.title+" counterattacked!");
									try {Thread.sleep(1000);} catch (InterruptedException e) {}
									co.effectAttack();
									if(c.attack(dmg,true)){
										p_dumpster.add(c);
										//		ch.repaint();
									}
								}
							}
						}else{
							if(opponent.attack(dmg)){//attack the inw directly if there's no opposing card
								stop();return;
							}
						}
					}
					try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				}
				//END FP of player turn		
				for(CardHolder ch:Mylane_ref){		//kill sacrifice card, and disable directInw
					if(!ch.isEmpty()){				//disable direct inw attack buff, SAactivated false
						Card c = ch.getCard();
						if(c.sacrifice){
							System.out.println(c.title+" is killed by SA (sacrifice)");
							notify.append(c.title+" is killed by SA (sacrifice)\n");
							c.effectColor(Color.BLACK);
							p_dumpster.add(c);
						}else c.directInw = false;	c.SAactivated = false;
					}
				}
				for(CardHolder ch:Theirlane_ref){		//disable protection buff for opponent
					if(!ch.isEmpty()){
						ch.getCard().Protected = false;
					}
				}
				AIturn();		//end
			}
		});

	}
	public void AIturn(){
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				System.out.println("OPPONENT PP TURN");
				Dialog dialog = new Dialog("OPPONENT PREPARE PHRASE");
				turnLabel.setText("OPPONENT PP");
				try {Thread.sleep(1280);} catch (InterruptedException e1) {}
				notify.append("-----------------------------\nOpponent prepar" +
						"ation phrase\n-----------------------------\n");
				//AI	?PPAI

				if(opponentDeck.size()==0){		//DRAW 1 from deck, if cannot then receive penalty
					notify.append("Opponent deck is empty, receiving"+playerDeck.size()+" damages\n");
					if(opponent.attack(playerDeck.size())){
						stop();return;
					}
				}else{
					Card ct = new Card(opponentDeck.get(0));
					notify.append("Opponent drawn "+ct.title+" from deck\n");
					ct.effectColor(Color.WHITE);
					o_hand.add(ct);	opponentDeck.remove(0);	
					o_hand.revalidate();o_hand.repaint();
					//		o_deck = new JLabel("DECK: "+Arrays.toString(opponentDeck.toArray()));
					//AI here

					LinkedList<Integer> ranIndex = randomIndexArray(Theirlane_ref.length);
					System.out.println("AI PHRASE 1");
					//AI PHRASE 1: Try to summon card into nonempty lanes
					for(int index:ranIndex){
						CardHolder lane = Theirlane_ref[index];
						if(lane.isEmpty()){
							for(Component c:o_hand.getComponents()){
								Card temp = (Card)c;
								if(temp.isMonster()){
									if(opponent.useMP(temp.mc)){
										lane.add(temp);
										notify.append("Opponent put "+temp.title+" into lane\n");
										try {
											Thread.sleep(2000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										break;
									}
								}
							}
						}
					}
					System.out.println("AI PHRASE 2");
					//AI PHRASE 2: If there are mp left, try to randomly use spell card in hand
					if(opponent.MP_current>0&&o_hand.getComponentCount()>0){
						
							ranIndex = randomIndexArray(o_hand.getComponentCount());
							
							for(int index:ranIndex) {
								Card t = null;
								try{
									t = (Card)o_hand.getComponent(index);
								}catch(ArrayIndexOutOfBoundsException e){
									continue;
								}
								if(t!=null&&!t.isMonster()&&opponent.MP_current>=t.mc){
									AIuseCard(t);
								}
							}
						
						
					}
					System.out.println("AI PHRASE 3");
					//AI PHRASE 3: If there are mp left, try to randomly use SA of current cards in lane
					if(opponent.MP_current>0){
						ranIndex = randomIndexArray(Theirlane_ref.length);
						for(int index:ranIndex){
							CardHolder ch = Theirlane_ref[index];
							if(!ch.isEmpty()){
								Card t = ch.getCard();
								if(t!=null&&opponent.MP_current>=t.mc){
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									AIuseCard(t);
								}
							}

						}
					}
				}
				//END AI
				if(firstTurn!=0){
					firstTurn--;
					playerPP();
					return;
				}
				System.out.println("OPPONENT FP TURN");
				turnLabel.setText("OPPONENT FP");
				dialog = new Dialog("OPPONENT FIGHT PHRASE");
				try {Thread.sleep(1280);} catch (InterruptedException e1) {}
				notify.append("-----------------------------\nOpponent fighting phrase\n-----------------------------\n");
				//FP ?FPAI
				for(CardHolder ch:Theirlane_ref){
					if(ch.isEmpty())continue;
					Card c = ch.getCard();
					AudioPlayer bgMusic3 = new AudioPlayer("HurtSoMuch.wav");
					bgMusic3.play();
					int dmg = c.generateNetAtk();
					System.out.println(c.title+" generate attack with "+dmg+" damage");
					c.effectAttack();
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
							}else{
								if(Math.random()<co.car){
									System.out.println(co.title+" counterattacked!");
									try {Thread.sleep(1000);} catch (InterruptedException e) {}
									co.effectAttack();
									if(c.attack(dmg,true)){
										o_dumpster.add(c);
										//				ch.repaint();
										//		ch.removeCard();
									}
								}
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
							notify.append(c.title+" is killed by SA (sacrifice)\n");
							c.effectColor(Color.BLACK);
							o_dumpster.add(c);
						}else c.directInw = false;	c.SAactivated = false;
					}
				}
				for(CardHolder ch:Mylane_ref){		//disable protection buff for player
					if(!ch.isEmpty()){
						ch.getCard().Protected = false;
					}
				}
				//	endButton.setEnabled(true);
				opponent.restoreMP();
				playerPP();
				
			}

		});

	}
	/**Use SA/Spell for AI
	 * @param c
	 */
	public void AIuseCard(Card c){
		randomIndexArray(4);
		//c.effectSpell();
		if(c.isMonster()){
			switch(c.sa_code){
			case 1:
				c.apply(c);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 2:
				Card t = ((CardHolder)c.getParent()).getOpposingCardHolder().getCard();//.apply(c);
				if(t==null){
					//		JOptionPane.showMessageDialog(this, "msg", "Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(t.apply(c));p_dumpster.add(t);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 3:
				Card tt = Theirlane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(tt==null)break;
				tt.apply(c);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 4:
				Card tt2 = Mylane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(tt2==null)break;
				if(tt2.apply(c))p_dumpster.add(tt2);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 5:
				Card tt3 = Theirlane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(tt3 == null)break;
				tt3.apply(c);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 6:
				c.apply(c);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			case 7:
				c.apply(c);
				opponent.useMP(c.sa_mc);c.effectSpell();
				break;
			default:
				System.err.println("error AI");
				break;
			}	
		}else{
			switch(c.spell_code){
			case 1: 
				Card temp = Theirlane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(temp!=null){
					temp.apply(c);
					opponent.useMP(c.mc);
					c.effectSpell();
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
					o_dumpster.add(c);
				}
				break;
			case 2:
				Card temp2 = Mylane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(temp2!=null){
					if(temp2.apply(c))p_dumpster.add(temp2);
					opponent.useMP(c.mc);
					c.effectSpell();
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
					o_dumpster.add(c);
				}

				break;
			case 3:		//  REMOVE CARDS FOR USED SPELLS 
				//ALL CARDS WILL BE RESET (which is probably not a problem)
				//	System.out.println("SPELL REDRAW USED!");
				while(o_hand.getComponentCount()>0){
					Card t = (Card) o_hand.getComponent(0);
					opponentDeck.add(t.ic_id);
					o_hand.getComponent(0);
				}
				int drawAmount = (int) Math.min(c.param_value, opponentDeck.size());
				Collections.shuffle(opponentDeck, new Random(System.currentTimeMillis()));
				//can't draw more than deck size!!
				for(int i = 0;i<drawAmount;i++){
					o_hand.add(new Card(i));
				}
				opponent.useMP(c.sa_mc);
				c.effectSpell();
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				o_dumpster.add(c);
				break;
			case 4:
				Card temp3 = Theirlane_ref[(int)Math.round((Math.random()*3))].getCard();//.apply(c);
				if(temp3!=null){
					temp3.apply(c);
					opponent.useMP(c.mc);
					c.effectSpell();
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
					o_dumpster.add(c);
				}
				break;
			case 5:
				opponent.attack((int) -c.param_value);
				opponent.useMP(c.sa_mc);
				c.effectSpell();
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				o_dumpster.add(c);
				break;
			case 6:		//WILL RETURN RANDOMLY FROM DUMPSTER
				if(o_dumpster.getComponentCount()==0){
					JOptionPane.showMessageDialog(this, "Your dumpster is empty! Spell not used", "Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
				int random = 0 + (int)((Math.random() * o_dumpster.getComponentCount()));
				Card temp4 = (Card) o_dumpster.getComponent(random);
				o_dumpster.remove(random);
				o_hand.add(new Card(temp4.ic_id));//the Card should be reset to initial status
				opponent.useMP(c.sa_mc);
				c.effectSpell();
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				o_dumpster.add(c);
				c.effectColor(Color.WHITE);
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
				opponent.useMP(c.sa_mc);
				c.effectSpell();
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				o_dumpster.add(c);
				break;
			}

		}
	}
	public static LinkedList<Integer> randomIndexArray(int length){
		LinkedList<Integer> l = new LinkedList<Integer>();
		for(int i = 0;i<length;i++){
			l.add(i);
		}
		Collections.shuffle(l,new Random(System.currentTimeMillis()));
		return l;
	}
	/**
	 * End the game
	 */
	public void stop(){
		//(null,player.getName()+" "+" win against "+" "+opponent.getName(), "",JOptionPane.DEFAULT_OPTION);
		if(player.LP_current<=0)JOptionPane.showMessageDialog(null,opponent.getName()+" "+" win against "+" "+player.getName(), "",JOptionPane.DEFAULT_OPTION);
		else if(opponent.LP_current<=0)JOptionPane.showMessageDialog(null,player.getName()+" "+" win against "+" "+opponent.getName(), "",JOptionPane.DEFAULT_OPTION);
		else JOptionPane.showMessageDialog(null,"GAME ENDED", "",JOptionPane.DEFAULT_OPTION);
		isActive = false;
		this.setVisible(false);
		this.dispose();
		bgMusic.stop();
		Main.bgMusic.playLoop();
		Main.main.setVisible(true);
	}
	/**
	 * Card will notify this class when there is a mouse click
	 */
	public void processNotify(Card c){
		selectedCard_desc.setText(c.desc);
		useButton.setEnabled(false);useButton.setBackground(new Color(240,240,240));
		System.out.println("PROCESS NOTIFY");
		if(!Main.Turn){
			notify.append("This is not your turn!");
			return;//Do nothing if not your turn
		}
		if(!selected){		//CARD NOT SELECTED
			/**
			 * check if the selected Card can be casted by the user by checking the mana cost and card location (on your lane for monster or on your hand for spell)
			 * if yes, then the button to cast / canel is enabled
			 * ONLY APPLICABLE WITH PLAYER
			 */
			if(c.isMonster()){	//monster
				if(((CardHolder)c.getParent()).type==CardHolder.PLAYER&&player.MP_current>=c.sa_mc&&!c.SAactivated){
					useButton.setEnabled(true);useButton.setBackground(Color.CYAN);
				}	
			}else if(((CardHolder)c.getParent()).type==CardHolder.PLAYER_HAND&&player.MP_current>=c.mc){
				useButton.setEnabled(true);	useButton.setBackground(Color.CYAN);
			}else {

				System.out.println("USEBUTTON NOT ENABLED");
			}
			//Do nothing if selected card can't be casted sa/spell
		}else if(selected){				//TARGET CARD SELECTED AND READY TO USE THE SA/SPELL
			cancelButton.setEnabled(false);
			boolean spellSuccess = true;
			if(caster.isMonster()){
				switch(caster.sa_code){
				case 3:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);c.SAactivated = true;
						System.out.println("TEST C:"+c.lp);
						player.useMP(caster.sa_mc);caster.effectSpell();
					}else spellSuccess = false;
					break;
				case 4:
					if(((CardHolder)c.getParent()).type==CardHolder.OPPONENT){
						if(c.apply(caster))p_dumpster.add(c);c.SAactivated = true;
						player.useMP(caster.sa_mc);caster.effectSpell();
					}else spellSuccess = false;
					break;
				case 5:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);c.SAactivated = true;
						player.useMP(caster.sa_mc);caster.effectSpell();
					}else spellSuccess = false;
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
						caster.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(caster);
					}else spellSuccess = false;
					break;
				case 2:
					if(((CardHolder)c.getParent()).type==CardHolder.OPPONENT){
						caster.effectSpell();
						if(c.apply(caster))o_dumpster.add(c);
						player.useMP(caster.mc);
						caster.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(caster);
					}else spellSuccess = false;
					break;
				case 4:
					if(((CardHolder)c.getParent()).type==CardHolder.PLAYER){
						c.apply(caster);
						player.useMP(caster.mc);
						caster.effectSpell();
						try {Thread.sleep(1000);} catch (InterruptedException e) {}
						p_dumpster.add(caster);
					}else spellSuccess = false;
					break;
				default:
					System.err.println("ERR: Card doesn't need selection!");
					break;
				}
			}
			if(spellSuccess){
				if(caster.isMonster()){
					notify.append(caster.title+" spell used on "+c.title+"\n");
				}else notify.append(caster.title+" using special ability on "+c.title+"\n");
			}else{
				JOptionPane.showMessageDialog(this, "Invalid target!", "Error",JOptionPane.ERROR_MESSAGE);
			}

			useButton.setEnabled(false);
			cancelButton.setEnabled(false);
			selected = false;
			caster = null;
		}
	}
}
