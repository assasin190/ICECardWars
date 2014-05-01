package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import misc.AudioPlayer;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent extends JPanel {
	static ArrayList<Inw> opponentList;
	static MyPanel current;
	static MyPanel previous;
	static JPanel display;
	static String screenResolutionString;
	static ArrayList<BufferedImage> displayPicture;
	static JPanel opponentPanel;
	static JButton select;
	static JButton back;
	static JButton random;
	static WTF wtf;
	private static AudioPlayer bgMusic;
	static Inw player;
	public SelectOpponent(Inw player){
		this.player = player;
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
		
		determineResolution();
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		/*
		switch(screenResolutionString) {
			case "1024x768":  so.createGUI1024x768();
							  break;
			case "1366x768":  so.createGUI1366x768();
							  break;
			case "1920x1080": so.createGUI1920x1080();
							  break;
		}
		*/
		if(screenResolutionString == "1024x768") createGUI1024x768();
		else if(screenResolutionString == "1366x768") createGUI1366x768();
		else createGUI1920x1080();
		test.add(this);
		test.setVisible(true);
		
		
	}
	
	
	public void createGUI1024x768(){
		opponentPanel = new JPanel();
		opponentPanel.setLayout(new GridLayout(8, 8));
		opponentPanel.setBackground(Color.BLACK);
		//southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 288));
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < opponentList.size(); i++) {
			MyPanel pic = new MyPanel(opponentList.get(i));
			if(i == 0) {
				this.current = pic;
				pic.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
			}
			if(i == opponentList.size() - 1) this.previous = pic;
			pic.addMouseListener(pic);
			pic.addMouseMotionListener(pic);
			pic.setPreferredSize(new Dimension(90, 90));
			opponentPanel.add(pic);
			
		}
		this.add(opponentPanel,BorderLayout.LINE_START);

		display = new JPanel();
		setupDisplay();
		this.add(display, BorderLayout.CENTER); 
		
	}
	
	public void createGUI1366x768(){
		opponentPanel = new JPanel();
		opponentPanel.setLayout(new GridLayout(7, 9));
		opponentPanel.setBackground(Color.BLACK);
		//southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 288));
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < opponentList.size(); i++) {
			MyPanel pic = new MyPanel(opponentList.get(i));
			if(i == 0) {
				this.current = pic;
				pic.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
			}
			if(i == opponentList.size() - 1) this.previous = pic;
			pic.addMouseListener(pic);
			pic.addMouseMotionListener(pic);
			pic.setPreferredSize(new Dimension(100, 100));
			opponentPanel.add(pic);
			
		}
		this.add(opponentPanel,BorderLayout.LINE_START);

		display = new JPanel();
		setupDisplay();
		this.add(display, BorderLayout.CENTER); 
		
	}
	
	public void createGUI1920x1080(){
		opponentPanel = new JPanel();
		opponentPanel.setLayout(new GridLayout(7, 9));
		opponentPanel.setBackground(Color.BLACK);
		//southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 288));
		this.setLayout(new BorderLayout());
		
		for(int i = 0; i < opponentList.size(); i++) {
			MyPanel pic = new MyPanel(opponentList.get(i));
			if(i == 0) {
				this.current = pic;
				pic.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
			}
			if(i == opponentList.size() - 1) this.previous = pic;
			pic.addMouseListener(pic);
			pic.addMouseMotionListener(pic);
			pic.setPreferredSize(new Dimension(140, 140));
			opponentPanel.add(pic);
			
		}
		this.add(opponentPanel,BorderLayout.LINE_START);

		display = new JPanel();
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
			URL fb_url = null;
			/*
			switch(screenResolutionString) {
			case "1024x768":  fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture?width=140&height=140");
							  break;
			case "1366x768":  fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture?width=100&height=100");
							  break;
			case "1920x1080": fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture?width=140&height=140");
							  break;
			}
			*/
		//	fb_url = new URL("https://graph.facebook.com/" + mapList.get(i).get("fb_id") + "/picture?width=200&height=200");
			HashMap<String, String> temp2 = new Gson().fromJson(new InputStreamReader(new URL("https://graph.facebook.com/" + mapList.get(i).get("fb_id")).openStream()), HashMap.class);
			String fbname = temp2.get("name");
		//	BufferedImage image = ImageIO.read(fb_url);
			//String fname, String lname, int LP, int MP, int maxDeck,
		//	String fb_id, int user_ID) {
			Inw inw = new Inw(fname, lname, LP, MP, maxDeck, fb_id, user_ID);
		//TODO: change to new constructor
			opponentList.add(inw);
			System.out.println(i + ": " + fname);
		
		}
	}
	
	public static void setupDisplay(){
		if(screenResolutionString == "1024x768") setupDisplay1024x768();
		else if(screenResolutionString == "1366x768") setupDisplay1366x768();
		else setupDisplay1920x1080();
		/*
		switch(screenResolutionString) {
		case "1024x768":  setupDisplay1024x768();
						  break;
		case "1366x768":  setupDisplay1366x768();
						  break;
		case "1920x1080": setupDisplay1920x1080();
						  break;
		
		}
		*/
	}
	
	public static void setupDisplay1024x768(){
		display.removeAll();
		display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
		display.add(Box.createRigidArea(new Dimension(0, 75)));
		JLabel pic = new JLabel();
		pic.setIcon(current.inw.profile);
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(pic);
		JLabel fbname = new JLabel(current.inw.fbname);
		fbname.setFont(new Font("Serif", Font.PLAIN, 22));
		fbname.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(Box.createRigidArea(new Dimension(0, 30)));
		display.add(fbname);
		select = new JButton("Select");
		select.setFont(new Font("Serif", Font.PLAIN, 16));
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		back = new JButton("Back");
		back.setFont(new Font("Serif", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
			//	String s = "{\"cv_uid\":\"+\",\"fb_id\":\"" + current.inw.fb_id + "\",\"firstname_en\":\"" + current.inw.fname + "\",\"lastname_en\":\"" + current.inw.lname + "\",\"full_lp\":\"40\",\"full_mp\":\"" + current.inw.LP_full + "\",\"max_deck_size\":\"" + current.inw.maxDeck + "\"}";
				String [] args = {s};
		//		Battlefield bf = new Battlefield();
				Battlefield.main(args);
				System.out.println(s);
				*/
				Inw op = current.inw;
				Battlefield bf = new Battlefield(player,op);
			}
			
		});
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
		temp.add(select);
		temp.add(Box.createRigidArea(new Dimension(40, 0)));
		temp.add(back);
		display.add(Box.createRigidArea(new Dimension(0, 30)));
		display.add(temp);
		random = new JButton("Random");
		random.setFont(new Font("Serif", Font.PLAIN, 20));
		random.setAlignmentX(Component.CENTER_ALIGNMENT);
		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new MyThread();
				t.start();
			}
			
		});
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		display.add(random);
		display.repaint();
	}
	
	public static void setupDisplay1366x768(){
		display.removeAll();
		display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		JLabel pic = new JLabel();
		pic.setIcon(current.inw.profile);
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(pic);
		JLabel fbname = new JLabel(current.inw.fbname);
		fbname.setFont(new Font("Serif", Font.PLAIN, 36));
		fbname.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(Box.createRigidArea(new Dimension(0, 35)));
		display.add(fbname);
		select = new JButton("Select");
		select.setFont(new Font("Serif", Font.PLAIN, 16));
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				String s = "{\"cv_uid\":\"+\",\"fb_id\":\"" + current.inw.fb_id + "\",\"firstname_en\":\"" + current.inw.fname + "\",\"lastname_en\":\"" + current.inw.lname + "\",\"full_lp\":\"40\",\"full_mp\":\"" + current.inw.LP_full + "\",\"max_deck_size\":\"" + current.inw.maxDeck + "\"}";
				String [] args = {s};
				Battlefield bf = new Battlefield();
				System.out.println(s);
				*/
				Inw op = current.inw;
				Battlefield bf = new Battlefield(player,op);
			}
			
		});
		back = new JButton("Back");
		back.setFont(new Font("Serif", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
		temp.add(select);
		temp.add(Box.createRigidArea(new Dimension(25, 0)));
		temp.add(back);
		display.add(Box.createRigidArea(new Dimension(0, 35)));
		display.add(temp);
		random = new JButton("Random");
		random.setFont(new Font("Serif", Font.PLAIN, 20));
		random.setAlignmentX(Component.CENTER_ALIGNMENT);
		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MyThread().start();
			}
			
		});
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		display.add(random);
		display.repaint();
	}
	
	public static void setupDisplay1920x1080(){
		display.removeAll();
		display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));
		display.add(Box.createRigidArea(new Dimension(0, 75)));
		JLabel pic = new JLabel();
		pic.setIcon(current.inw.profile);
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(pic);
		JLabel fbname = new JLabel(current.inw.fbname);
		fbname.setFont(new Font("Serif", Font.PLAIN, 50));
		fbname.setAlignmentX(Component.CENTER_ALIGNMENT);
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		display.add(fbname);
		select = new JButton("Select");
		select.setFont(new Font("Serif", Font.PLAIN, 20));
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				String s = "{\"cv_uid\":\"+\",\"fb_id\":\"" + current.inw.fb_id + "\",\"firstname_en\":\"" + current.inw.fname + "\",\"lastname_en\":\"" + SelectOpponent.current.inw.lname + "\",\"full_lp\":\"40\",\"full_mp\":\"" + SelectOpponent.current.inw.LP_full + "\",\"max_deck_size\":\"" + SelectOpponent.current.inw.maxDeck + "\"}";
				String [] args = {s};
				Battlefield.main(args);
				System.out.print(s);
				//System.out.println("fuck you");*/
				Inw op = current.inw;
				Battlefield bf = new Battlefield(player,op);
				
			}
			
		});
		back = new JButton("Back");
		back.setFont(new Font("Serif", Font.PLAIN, 20));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		JPanel temp = new JPanel();
		temp.setLayout(new BoxLayout(temp, BoxLayout.LINE_AXIS));
		temp.add(select);
		temp.add(Box.createRigidArea(new Dimension(40, 0)));
		temp.add(back);
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		display.add(temp);
		random = new JButton("Random");
		random.setFont(new Font("Serif", Font.PLAIN, 20));
		random.setAlignmentX(Component.CENTER_ALIGNMENT);
		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread t = new MyThread();
				t.start();
			}
			
		});
		display.add(Box.createRigidArea(new Dimension(0, 50)));
		display.add(random);
		display.repaint();
	}
	
	public static void determineResolution() {
		Dimension temp = Toolkit.getDefaultToolkit().getScreenSize();
		if(temp.getWidth() < 1366) screenResolutionString = "1024x768";
		else if(temp.getWidth() < 1920) screenResolutionString = "1366x768";
		else screenResolutionString = "1920x1080";
		
	}
	
	class MyPanel extends JPanel implements MouseInputListener{
		Inw inw;
		
		public MyPanel(Inw inw) {
			super();
			this.inw = inw;
			this.setOpaque(false);
		}
		
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(inw.profile.getImage(), 5, 5, this.getWidth() - 10, this.getHeight() - 10, null);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if(this == current) return;
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 10));
			bgMusic = new AudioPlayer("beep.wav");
			bgMusic.play();
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if(this == current) return;
			this.setBorder(BorderFactory.createEmptyBorder());
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			previous = current;
			current = (MyPanel) arg0.getSource();
			previous.setBorder(BorderFactory.createEmptyBorder());
			current.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
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
	
	static class MyThread extends Thread {
		
		public MyThread() {
			super();
		}
		
		public void run() {
			
			int count = 0;
			int delay = 100;
			while(true) {
				try {
					int r = (int) Math.round(Math.random()*59);
					previous = current;
					current = (MyPanel) opponentPanel.getComponent(r);
					previous.setBorder(BorderFactory.createEmptyBorder());
					current.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
					setupDisplay();
					select.setEnabled(false);
					back.setEnabled(false);
					random.setEnabled(false);
					display.validate();
					bgMusic = new AudioPlayer("beep.wav");
					bgMusic.play();
					sleep(delay);
					count++;
					if(count == 20) delay = 250;
					if(count == 28) delay = 500;
					if(count == 32) delay = 1000;
					if(count == 34) {
						current.setBorder(BorderFactory.createLineBorder(Color.GREEN, 10));
						break;
					}
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			select.setEnabled(true);
			back.setEnabled(true);
			random.setEnabled(true);
		}
	}
	
}	

