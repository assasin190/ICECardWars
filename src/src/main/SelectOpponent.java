package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent extends JPanel {
	static ArrayList<Inw> opponentList;
	static Inw chosenOpponent;
	MyPanel showPic;
	
	
	public SelectOpponent(){
		opponentList = new ArrayList<Inw>();
		try {
			saveAllOpponentsToLocal();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String [] args) {
		
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setExtendedState(JFrame.MAXIMIZED_BOTH);
		SelectOpponent a = new SelectOpponent();

		a.createGUI();
	//	a.adddesc();
		test.add(a);
		test.setVisible(true);
		
		
	}
	public void createGUI(){
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(4,15));
		//southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 288));
		this.setLayout(new BorderLayout());
		/*
		for(int i = 0;i< opponentList.size();i++){
			JLabel lb = new JLabel(opponentList.get(i).profile);
			lb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pic.add(lb);
			
			
		}
		*/
		for(int i = 0; i < opponentList.size(); i++) {
			MyPanel pic = new MyPanel(opponentList.get(i));
			pic.setPreferredSize(new Dimension(72, 128));
			southPanel.add(pic);
			
		}
		this.add(southPanel,BorderLayout.PAGE_END);
	}
	
	
	public void saveAllOpponentsToLocal() throws JsonSyntaxException, JsonIOException, IOException {
			
		opponentList.clear();
		URL opponentUrl = new URL("http://128.199.235.83/icw/?q=icw/service/opponent");
		Gson g = new Gson();
		HashMap<String, ArrayList<Map<String, String>>> temp = g.fromJson(new InputStreamReader(opponentUrl.openStream()), HashMap.class );			
		ArrayList<Map<String, String>> mapList = temp.get("data");
			
		for(int i = 0; i < mapList.size(); i++) {
			int user_ID = Integer.parseInt(mapList.get(i).get("cv_uid"));
			String fb_id = mapList.get(i).get("fb_id");
			String fname = mapList.get(i).get("firstname_en");
			String lname = mapList.get(i).get("lastname_en");
			int LP = Integer.parseInt(mapList.get(i).get("full_lp"));
			int MP = Integer.parseInt(mapList.get(i).get("full_mp"));
			int maxDeck = Integer.parseInt(mapList.get(i).get("max_deck_size"));
			URL fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture?width=128&height=128");
			BufferedImage image = ImageIO.read(fb_url);
			Inw inw = new Inw(fname, lname, LP, MP, maxDeck, fb_id, user_ID, image);
			opponentList.add(inw);
		
		}		
	}
	
	class MyPanel extends JPanel{
		Inw inw;
		
		public MyPanel(Inw inw) {
			super();
			this.inw = inw;
		}
		
		public void paint(Graphics g) {
			
			/*
			int width = inw.image.getWidth();
			int height = inw.image.getHeight();
			if (width > height) {
				BufferedImage subImage = inw.image.getSubimage((width-height)/2, 0, height, height);
				inw.image = subImage;
			}
			*/
			
			g.drawImage(inw.image, 0, 0, this.getWidth(), this.getHeight(), null);
			System.out.println(this.getWidth() + ", " + this.getHeight());
		}
	}
	
}