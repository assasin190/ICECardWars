package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
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
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent extends JPanel {
	static ArrayList<Inw> opponentList;
	static MyPanel current;
	static MyPanel previous;
	static JPanel display;
	
	
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
		SelectOpponent so = new SelectOpponent();

		so.createGUI1920x1080();
	//	a.adddesc();
		test.add(so);
		test.setVisible(true);
		
		
	}
	public void createGUI1920x1080(){
		JPanel opponentPanel = new JPanel();
		opponentPanel.setLayout(new GridLayout(7, 9));
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
			if(i == 0) this.current = pic;
			if(i == opponentList.size() - 1) this.previous = pic;
			pic.addMouseListener(pic);
			pic.addMouseMotionListener(pic);
			pic.setPreferredSize(new Dimension(140, 140));
			opponentPanel.add(pic);
			
		}
		this.add(opponentPanel,BorderLayout.LINE_START);

		display = new JPanel();
		display.setBackground(Color.BLACK);
		setupDisplay();
		this.add(display, BorderLayout.CENTER); 
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
			URL fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture?width=140&height=140");
			BufferedImage image = ImageIO.read(fb_url);
			Inw inw = new Inw(fname, lname, LP, MP, maxDeck, fb_id, user_ID, image);
			opponentList.add(inw);
			System.out.println(i);
		
		}
	}
	
	public static void setupDisplay(){
		display.removeAll();
		display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
		display.add(Box.createRigidArea(new Dimension(0, 75)));
		JLabel pic = new JLabel();
		try {
			pic.setIcon(new ImageIcon(ImageIO.read(new URL("https://graph.facebook.com/"+ current.inw.fb_id +"/picture?width=300&height=300"))));
			pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.add(pic);
		try {
			HashMap<String, String> temp = new Gson().fromJson(new InputStreamReader(new URL("https://graph.facebook.com/" + current.inw.fb_id).openStream()), HashMap.class);
			JLabel fbname = new JLabel(temp.get("name"));
			fbname.setFont(new Font("Serif", Font.PLAIN, 50));
			fbname.setAlignmentX(Component.CENTER_ALIGNMENT);
			display.add(Box.createRigidArea(new Dimension(0, 50)));
			display.add(fbname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.repaint();
		JTextPane textPane = new JTextPane();
	}
	
	class MyPanel extends JPanel implements MouseInputListener{
		Inw inw;
		
		public MyPanel(Inw inw) {
			super();
			this.inw = inw;
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(inw.image, 5, 5, this.getWidth() - 10, this.getHeight() - 10, null);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if(this == current) return;
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if(this == current) return;
			this.setBorder(BorderFactory.createEmptyBorder());
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			previous = current;
			current = this;
			previous.setBorder(BorderFactory.createEmptyBorder());
			this.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
			setupDisplay();
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
}
