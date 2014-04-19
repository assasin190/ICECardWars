package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.swing.JOptionPane;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import misc.Splash;

public class Main {
	static Socket con;
	static PrintWriter out;
	static BufferedReader in;
	private static boolean scSW = true;	//Selected card switch
	private static Card sc1 = null;	//Selected card 1
	private static Card sc2 = null;	//Selected card 2
	
	static JsonParserFactory factory=JsonParserFactory.getInstance();
	static JSONParser parser=factory.newJsonParser();
	
	
	public static void main(String[] args) {
		CardData.saveAllCardsToLocal();
		MainMenu main = new MainMenu(con);
		Splash frame = new Splash();
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		while(true){
			frame.setVisible(true);	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			frame.setVisible(false);
	        try {
	    		con = new Socket("128.199.235.83",80);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
				int a = JOptionPane.showConfirmDialog(null, "Could not connect to server\nTry again?", "",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if(a==JOptionPane.YES_OPTION)continue;
				else System.exit(0);
			};
	        break;
		}
		frame.dispose();
//	System.out.println("Connect success");
	//	main.setUndecorated(true);
	//	main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		main.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
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
