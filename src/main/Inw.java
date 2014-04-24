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
import java.awt.BorderLayout;

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
	int user_ID;
	public int[] all_IC;
	public int[] deck;
	private JLabel data;
	
	
	public Inw(String fname,String lname,int LP,int MP,int maxDeck,String fb_id,int user_ID){
		this.fname = fname;
		this.lname = lname;
		this.LP_full = LP;
		this.MP_full = MP;
		LP_current = LP_full;
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
		Inw a = new Inw("{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}");
	//	for(Inw b:a){
			System.out.println(a.toString());
	//	}
		frame.getContentPane().add(a);
	}
	/**	create Inw with String representing the individual Inw JSON data from http://128.199.235.83/icw/?q=icw/service/opponent
	 * Example String value: 
	 * "{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"
	 * @param JSONString
	 */
	public Inw(String JSONString){
		JsonObject j = new Gson().fromJson(JSONString, JsonObject.class);
		this.fb_id = j.get("fb_id").getAsString();
		this.user_ID = j.get("cv_uid").getAsInt();
		this.fname = j.get("firstname_en").getAsString();
		this.lname = j.get("lastname_en").getAsString();
		this.LP_full = j.get("full_lp").getAsInt();
		this.MP_full = j.get("full_mp").getAsInt();
		this.maxDeck = j.get("max_deck_size").getAsInt();
		LP_current = LP_full;
		MP_current = MP_full;
		initGUI();
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
		LP_current = LP_full;
		MP_current = MP_full;
		initGUI();
	}
	private void initGUI() {
		try {
			profile = new ImageIcon(ImageIO.read(new URL("https://graph.facebook.com/"+fb_id+"/picture")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout(0, 0));
		
		data = new JLabel(profile);
		data.setText(fname+" "+lname+" LP: "+LP_current+" MP: "+MP_current);
		add(data);
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
	public void updateGUI(){
		data.setText(fname+" "+lname+" LP: "+LP_current+" MP: "+MP_current);
	}
	/**
	 * Fetch this Inw's data from the server
	 */
	public void addDeck(){
		Gson gs;
		InputStream is;	
		String url ="http://128.199.235.83/icw/?q=icw/service/get_deck&user="+user_ID;	//INTERT YOUR ID HERE
		JsonObject job = null;
		while(true){
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Cannot connect, trying again...");
				continue;
			}
	//		Type listType = new TypeToken<List<Integer>>(){}.getType();
			System.out.println(job);
			JsonArray ja = job.get("data").getAsJsonArray();
			deck = new int[ja.size()];
			int count = 0;
			for(JsonElement je:ja){
				deck[count] = je.getAsInt();
				count++;
			}
			break;
		}
	}
}
