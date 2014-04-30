package main;

import javax.swing.*;

import misc.DropHandler;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SetDeck extends JPanel {
	
	static Inw me;
	ArrayList<Card> card;
	ArrayList<Card> deck;
	CardHolder deckHolder;
	CardHolder cardHolder;
	
	
	public SetDeck() {
		try {
			Gson g = new Gson();
			URL url = new URL("http://128.199.235.83/icw/?q=icw/service/all_ic_of&user=624");
			try {
				HashMap<String, ArrayList<Double>> temp = g.fromJson(new InputStreamReader(url.openStream()), HashMap.class );
				ArrayList<Double> cardString = temp.get("data");
				cardHolder = new CardHolder(CardHolder.DECK, false);
				deckHolder = new CardHolder(CardHolder.DECK, false);
				for(int i = 0; i < cardString.size(); i++) {
					cardHolder.add(new Card(cardString.get(i).intValue()));
				}
				url = new URL("http://128.199.235.83/icw/?q=icw/service/get_deck&user=624");
				temp = g.fromJson(new InputStreamReader(url.openStream()), HashMap.class);
				ArrayList<Double> deckString = temp.get("data");
				if(deckString != null) {
					for(int i = 0; i < deckString.size(); i++) {
						deckHolder.add(new Card(deckString.get(i).intValue()));
					}
				}
				DropHandler handler = new DropHandler();
				DropTarget dropTarget = new DropTarget(cardHolder, DnDConstants.ACTION_MOVE, handler, true);
				cardHolder.setPreferredSize(new Dimension(328, 768));
				cardHolder.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
				deckHolder.setPreferredSize(new Dimension(328, 768));
				deckHolder.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
				this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
				this.add(new JScrollPane(cardHolder));
				this.add(new JScrollPane(deckHolder));
				JButton b = new JButton("Submit");
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						JButton b = (JButton) arg0.getSource();
						JPanel panel = (JPanel) b.getParent();
						SetDeck s = (SetDeck) panel.getParent();
						Component [] c = s.deckHolder.getComponents();
						int [] card = new int[c.length];
						for(int i = 0; i < card.length; i++) {
							Card cd = (Card) c[i];
							System.out.println(cd.getSize());
							card[i] = cd.ic_id;
						}
						System.out.println(Arrays.toString(card));

						try {
							String URLString = "http://128.199.235.83/icw/?q=icw/service/set_deck&user=624&pass=90408&deck="+Arrays.toString(card);
							URLString = URLString.replace(" ", "");
							URL dest = new URL(URLString);
							InputStream is = dest.openStream();
					//		is = new URL(url).openStream();
							Gson gs = new Gson();
							JsonObject job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
							System.out.println("STATUS: "+job.get("status").getAsInt());
							System.out.println("MSG: "+job.get("msg").getAsString());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});
				JPanel display = new JPanel();
				display.add(b);
				this.add(display);
				
				
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String [] args) {
		CardData.saveAllCardsToLocal();
		JFrame frame = new JFrame();
		frame.add(new SetDeck());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
	}
	
}