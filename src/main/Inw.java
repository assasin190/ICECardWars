package main;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.awt.FlowLayout;

public class Inw extends JPanel{
	
	String fname;
	String lname;
	int LP_full;
	int LP_current;
	int MP_full;
	int MP_current;
	int maxDeck;
	String fb_id;
	ImageIcon profile;
<<<<<<< HEAD
	int user_ID;
	private int user_pw;
	public Inw(String fname,String lname,int LP,int MP,int maxDeck,String fb_id,ImageIcon profile,int user_ID,int user_PW){
=======
	private int user_ID;
	public int[] all_IC;
	public int[] deck;
	
	
	public Inw(String fname,String lname,int LP,int MP,int maxDeck,String fb_id,int user_ID){
>>>>>>> 0a00e18cf2f2ed2cd13f66433a730611f0f4174e
		this.fname = fname;
		this.lname = lname;
		this.LP_full = LP;
		LP_current = LP_full;
		this.MP_full = MP;
		MP_current = MP_full;
		this.maxDeck = maxDeck;
		this.fb_id = fb_id;
		this.user_ID = user_ID;
		initGUI();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TESTING : getting all cards from user;
//		CardData.saveAllCardsToLocal();
		JFrame frame = new JFrame();
		frame.setSize(700, 700);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(4,5));
		Inw[] a = Inw.getOpponent();
		for(Inw b:a){
			System.out.println(b.toString());
		}
	}
	/**
	 *  get Inw data from only JsonObject from http://128.199.235.83/icw/?q=icw/service/opponent
	 *  @param	j - Individual JsonObject extracted from the "data" JsonObject
	 * @wbp.parser.constructor
	 *  
	 */
	public Inw(JsonObject j){			
		this.fb_id = j.get("fb_id").getAsString();
		this.user_ID = j.get("cv_uid").getAsInt();
		this.fname = j.get("firstname_en").getAsString();
		this.lname = j.get("lastname_en").getAsString();
		this.LP_full = j.get("full_lp").getAsInt();
		this.MP_full = j.get("full_mp").getAsInt();
		this.maxDeck = j.get("max_deck_size").getAsInt();
		initGUI();
	}
	private void initGUI() {
		try {
			profile = new ImageIcon(ImageIO.read(new URL("https://graph.facebook.com/"+fb_id+"/picture")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	/**
	 * @return	An array of all opponent from http://128.199.235.83/icw/?q=icw/service/opponent
	 */
	public static Inw[] getOpponent(){
		JsonArray opponent;
		while(true){
			Gson gs;
			InputStream is;	
			String url ="http://128.199.235.83/icw/?q=icw/service/opponent";	//INTERT YOUR ID HERE
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.print("can't connect, retrying");
				continue;
			}
			opponent = new Gson().fromJson(job.get("data"), JsonArray.class);
		//	System.out.println(deck.toString());
			break;
		}
		Inw[] all = new Inw[opponent.size()];
		int count = 0;
		for(JsonElement j:opponent){
			all[count] = new Inw(j.getAsJsonObject());
			count++;
		}
		return all;
	}
	public void getAllIC(){
		
	}
	public void getDeck(){	//
		
	}
	public String toString(){
		return "[FIRSTNAME: "+fname+", LASTNAME: "+lname+", LP: "+LP_full+", MP: "+MP_full+", MAXDECK: "+",IMAGE: "+profile.toString()+"]"; 
	}
	public int getID(){
		return user_ID;
	}
	/**
	 * Check if the character has enough MP to perform action, if yes then this method will automatically decrease the MP of character by that amount
	 * @param mp_cost - the MP cost of the SA or Spell
	 * @return whether or not the character have enough MP to cover the MP cost
	 */
	public boolean useMP(int mp_cost){
		if(MP_current>=mp_cost){
			MP_current = MP_current-mp_cost;
			return true;
		}else return false;
	}
}
