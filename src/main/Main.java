package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import misc.AudioPlayer;
import misc.Splash;

public class Main {
	static Socket con;
	static PrintWriter out;
	static BufferedReader in;
	static AudioPlayer bgMusic;
	private static boolean scSW = true;	//Selected card switch
	private static Card sc1 = null;	//Selected card 1
	private static Card sc2 = null;	//Selected card 2
	public static boolean Turn;		//Universal boolean for turn , turn = true means it's player turn
	public static CardHolder orig;
	
	public static void main(String[] args) {

		MainMenu main = new MainMenu(con);
		Splash frame = new Splash();
		bgMusic = new AudioPlayer("Hishoku no sora.wav");
		bgMusic.playLoop();
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		Splash.setProgress("Connecting to server");
		while(true){
	        try {
	    		con = new Socket("128.199.235.83",80);
	    		//if(bgMusic.isPlay()==false) bgMusic.play();
	    		//else ;
			} catch (IOException e1) {
				int a = JOptionPane.showConfirmDialog(null, "Could not connect to server\nTry again?", "",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if(a==JOptionPane.YES_OPTION)continue;
				else System.exit(0);
			}; 
	        break;
		}
	CardData.saveAllCardsToLocal();
	main.setUndecorated(true);
	main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Splash.setProgress("Starting game");
		main.setVisible(true);
		frame.dispose();
	}
	public static void setSelectedCard(Card c){
		orig = (CardHolder) c.getParent();
		sc1 = c;
		/*

		if(scSW){
			if(sc2 == c)return;
			Main.sc1 = c;
			scSW = !scSW;
			
			if(sc2!=null){
				sc2.deselect();
			}
			
		}else{

			if(sc1 == c)return;
			Main.sc2 = c;
			scSW = !scSW;
			
			if(sc1!=null){
				sc1.deselect();
			}
		}
		*/
	}
	public static Card getSelectedCard(){
		return sc1;
		/*
		if(scSW){
			return sc2;
		}
		return sc1;
		*/
	}
	public static void updateDisplay(){
		if(Battlefield.selectedCard!=null){
		//	System.out.println("static CardHolder display changed");
			Battlefield.selectedCard.removeAll();
			Battlefield.selectedCard.add(new Card(getSelectedCard()));//TODO: only new card created with ID, not actual current stat!
			Battlefield.selectedCard.revalidate();
			Battlefield.selectedCard.repaint();
		}
	}
	
}
