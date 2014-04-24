package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import misc.AudioPlayer;
import misc.Splash;

public class Main {
	static Socket con;
	static PrintWriter out;
	static BufferedReader in;
	private static AudioPlayer bgMusic;
	private static boolean scSW = true;	//Selected card switch
	private static Card sc1 = null;	//Selected card 1
	private static Card sc2 = null;	//Selected card 2	
	public static boolean Turn;		//Universal boolean for turn , turn = true means it's player turn
	
	
	public static void main(String[] args) {

		MainMenu main = new MainMenu(con);
		Splash frame = new Splash();
		bgMusic = new AudioPlayer("Hishoku no sora.wav");
		bgMusic.play();
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		Splash.setProgress("Connecting to server");
		while(true){
	        try {
	    		con = new Socket("128.199.235.83",80);
	    		if(bgMusic.isPlay()==false) bgMusic.play();
	    		else ;
			} catch (IOException e1) {
				int a = JOptionPane.showConfirmDialog(null, "Could not connect to server\nTry again?", "",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if(a==JOptionPane.YES_OPTION)continue;
				else System.exit(0);
			}; 
	        break;
		}
	//	CardData.saveAllCardsToLocal();
	//	main.setUndecorated(true);
	//	main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Splash.setProgress("Starting game");
		main.setVisible(true);
		frame.dispose();
	}
	public static void setSelectedCard(Card c){
		if(scSW){
			if(sc2 == c)return;
			Main.sc1 = c;
			if(Main.sc2!=null)Main.sc2.deselect();
			scSW = !scSW;
		}else{
			if(sc1 == c)return;
			Main.sc2 = c;
			if(Main.sc1!=null)Main.sc1.deselect();
			scSW = !scSW;
		}
	}
	public static Card getSelectedCard(){
		if(scSW){
			return sc2;
		}
		return sc1;
	}
}
