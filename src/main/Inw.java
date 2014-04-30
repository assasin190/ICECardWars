package main;

import java.awt.Color;
import java.awt.Graphics;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Inw extends JPanel {

	String fname;
	String lname;
	String fbname;
	int LP_full;
	int LP_current;
	int MP_full;
	int MP_current;
	int maxDeck;
	String fb_id;
	ImageIcon profile;
	BufferedImage image;
	int user_ID;
	public int[] all_IC;
	public int[] deck;
	private JLabel data;

	public Inw(String fname, String lname, int LP, int MP, int maxDeck,
			String fb_id, int user_ID, BufferedImage image, String fbname) {

		this.fname = fname;
		this.lname = lname;
		this.LP_full = LP;
		this.MP_full = MP;
		LP_current = LP_full;
		MP_current = MP_full;
		this.maxDeck = maxDeck;
		this.fb_id = fb_id;
		this.user_ID = user_ID;
		this.image = image;
		this.fbname = fbname;
		// initGUI();
	}

	public static void main(String[] args) {
		// TESTING : getting all cards from user;
		// CardData.saveAllCardsToLocal();
		JFrame frame = new JFrame();
		frame.setSize(700, 700);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(4, 5));
		Inw a = new Inw(
				"{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}");
		Card c = new Card(1, true, a);
		frame.getContentPane().add(a);
		System.out.println(a.useMP(3));
		JButton j = new JButton("ADD");

		frame.add(j);
	}

	public Inw(String fname, String lname, int LP, int MP, int maxDeck,
			String fb_id, int user_ID) {

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
	 * create Inw with String representing the individual Inw JSON data from
	 * http://128.199.235.83/icw/?q=icw/service/opponent Example String value:
	 * "{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"
	 * 
	 * @param JSONString
	 */
	public Inw(String JSONString) {
		JsonObject j = (JsonObject) new JsonParser().parse(JSONString);
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
		// System.out.println("create inw from JSONString successful");
	}

	/**
	 * get Inw data from only JsonObject from
	 * http://128.199.235.83/icw/?q=icw/service/opponent
	 * 
	 * @param j
	 *            - Individual JsonObject extracted from the "data" JsonObject
	 * @wbp.parser.constructor
	 * 
	 */
	public Inw(JsonObject j) {
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
			// image = ImageIO.read(new
			// URL("https://graph.facebook.com/"+fb_id+"/picture"));
			profile = new ImageIcon(ImageIO.read(new URL(
					"https://graph.facebook.com/" + fb_id + "/picture")));

		} catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout(0, 0));

		data = new JLabel(profile);
		data.setText(fname + " " + lname + " LP: " + LP_current + " MP: "
				+ MP_current);
		// data.setText("LP: "+LP_current+" MP: "+MP_current);
		add(data);

	}

	/**
	 * @return An array of all opponent from
	 *         http://128.199.235.83/icw/?q=icw/service/opponent
	 */
	public static Inw[] getOpponent() {
		JsonArray opponent;
		while (true) {
			Gson gs;
			InputStream is;
			String url = "http://128.199.235.83/icw/?q=icw/service/opponent"; // INTERT
																				// YOUR
																				// ID
																				// HERE
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.err.print("can't connect, retrying");
				continue;
			}
			opponent = new Gson().fromJson(job.get("data"), JsonArray.class);
			// System.out.println(deck.toString());
			break;
		}
		Inw[] all = new Inw[opponent.size()];
		int count = 0;
		for (JsonElement j : opponent) {
			all[count] = new Inw(j.getAsJsonObject());
			count++;
		}
		return all;
	}

	public void getAllIC() {

	}

	public String toString() {
		return "[FIRSTNAME: " + fname + ", LASTNAME: " + lname + ", LP: "
				+ LP_full + ", MP: " + MP_full + ", MAXDECK: " + ",IMAGE: "
				+ profile.toString() + "]";
	}

	public int getID() {
		return user_ID;
	}

	public String getName() {
		return fname;
	}
	
	/*
	public static String getAll() {
		return fname + lname +

		"MP" + "LP_full" + "MP_full" + "maxDeck" + fb_id + "user_ID";
	}
	*/
	/**
	 * Check if the character has enough MP to perform action, if yes then this
	 * method will automatically decrease the MP of character by that amount
	 * 
	 * @param mp_cost
	 *            - the MP cost of the SA or Spell
	 * @return whether or not the character have enough MP to cover the MP cost
	 */
	public boolean useMP(int mp_cost) {
		if (MP_current >= mp_cost) {
			MP_current = MP_current - mp_cost;
			// System.out.println("MP used: "+mp_cost+" MP left"+MP_current);
			updateGUI();
			return true;
		} else
			return false;
	}

	public void updateGUI() {
		data.setText("LP: " + LP_current + " MP: " + MP_current);
		validate();
		// data.setText(fname+" "+lname+" LP: "+LP_current+" MP: "+MP_current);
	}

	/**
	 * Fetch this Inw's deck data from the server
	 * 
	 * @return true if there is deck AND more than 10 cards in the deck
	 */
	public boolean addDeck() {
		Gson gs;
		InputStream is;
		String url = "http://128.199.235.83/icw/?q=icw/service/get_deck&user="
				+ user_ID; // INTERT YOUR ID HERE
		JsonObject job = null;
		while (true) {
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Cannot connect, trying again...");
				continue;
			}
			// Type listType = new TypeToken<List<Integer>>(){}.getType();
			System.out.println(job);
			if (job.get("data").isJsonNull()) {
				System.err.println("User hasn't save his/her deck yet!");
				return false;
			}
			JsonArray ja = job.get("data").getAsJsonArray();
			if (ja.size() < 10)
				return false;
			deck = new int[ja.size()];
			int count = 0;
			for (JsonElement je : ja) {
				deck[count] = je.getAsInt();
				count++;
			}
			break;
		}
		return true;
	}

	public boolean attack(int DMG) {
		if (DMG < 0) {
			effectColor(Color.GREEN);
		} else
			effectColor(Color.RED);
		this.LP_current -= DMG;
		updateGUI();
		System.out.println("Inw received " + DMG + " damages LP is now: "
				+ LP_current);
		if (DMG > 0)
			Battlefield.notify.append("Inw received " + DMG
					+ " damages. LP is now: " + LP_current);
		else
			Battlefield.notify.append("Inw received " + (-DMG)
					+ " health. LP is now: " + LP_current);
		return LP_current <= 0;
	}

	public void restoreMP() {
		MP_current = MP_full;
		updateGUI();
	}

	public void effectColor(final Color c) {
		// Executors.newSingleThreadExecutor().execute
		// SwingUtilities.invokeLater
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int alpha = 0;
				Graphics gr = Inw.this.getGraphics();
				if (gr == null)
					return;
				alpha = 255;
				while (alpha > 0) {
					gr.setColor(new Color(r, g, b, alpha));
					gr.fillRect(0, 0, getWidth(), getHeight());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					alpha -= 26;
					// repaint();
				}
				// repaint();
			}
		});
	}
}
