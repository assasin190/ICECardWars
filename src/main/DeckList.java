package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DeckList extends JPanel {

	ArrayList<Card> deck = new ArrayList<Card>();
	ArrayList<JPanel> list = new ArrayList<JPanel>();
	Card hold;
	JScrollPane scroller = new JScrollPane(this);
	int full = 0;
	int count;
	JPanel mydeck, left = new JPanel(), right = new JPanel();
	Dimension screenSize = getToolkit().getScreenSize();
	boolean check = true;
	boolean holding = false;
	Image drag;
	JButton go = new JButton("go");

	public DeckList(int userid) {
		count = 0;
		// this.setSize(this.getWidth() / 3, this.getHeight());
		String url = "http://128.199.235.83/icw/?q=icw/service/all_ic_of&user="
				+ userid;
		JsonObject job = null;

		InputStream is;
		final Dimension screenSize = getToolkit().getScreenSize();
		//System.out.println(screenSize.getHeight());
		CardData.saveAllCardsToLocal();
	

		try {
			is = new URL(url).openStream();
			Gson gs = new Gson();
			job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			String usercard = job.get("data").toString(); // [1,4,6,6,6,7,7,8,8,10,10,10,11,13,13,14,14,15,16,17,17,17,17,18,18,18,19,19,19,20,27,29,32,33,39,41,46,52,53,54]
			int count = 1;
			
			for (int i = 0; i < 39; i++) {

				deck.add(new Card(Integer.parseInt(usercard.substring(count,
						usercard.indexOf(",", count)))));

			
				count = usercard.indexOf(",", count) + 1;
			}
			deck.add(new Card(Integer.parseInt(usercard.substring(count,
					count + 2))));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setVisible(true);

		int track = 0;
		
		right.setLayout(new GridLayout(40, 7));
		left.setVisible(true);
		left.setLayout(new GridLayout(20,7));
		
		for (int i = 0; i < 40; i++) {
			ImageIcon ee = deck.get(track).picture;
			final Image picz3 = ee.getImage().getScaledInstance(20, 20,
					java.awt.Image.SCALE_SMOOTH);

			final JPanel temp = new JPanel();

			temp.setLayout(new GridLayout(1, 6));
			JLabel title = new JLabel("" + deck.get(track).title);
			temp.add(new JLabel(new ImageIcon(picz3)));
			temp.add(title);

			JLabel atk = new JLabel("ATK: " + deck.get(track).atk);

			temp.add(atk);

			JLabel lp = new JLabel("LP: " + deck.get(track).lp);

			temp.add(lp);

			JLabel lck = new JLabel("LCK: " + deck.get(track).lck);

			temp.add(lck);

			JLabel car = new JLabel("CAR: " + deck.get(track).car);
			

			temp.add(car);
			temp.setOpaque(false);
			if(left.getComponentCount()!=20) go.setEnabled(false);
			else go.setEnabled(true);
			go.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			left.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
					check = false;
				
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
				

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					holding = true;

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(holding){
						full++;
					}
					

				}

			});
			temp.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					check = true;
					
					

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					temp.setVisible(false);
					

				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
					if (check) {
						temp.setVisible(true);
						right.add(temp);
						list.remove(temp);
						if(left.getComponentCount()!=20) go.setEnabled(false);
					

					} else {
						temp.setVisible(true);
						list.add(temp);
						left.add(temp);
						//temp.removeAll();
						
						
						
					}
					if(left.getComponentCount()!=20) go.setEnabled(false);
					else go.setEnabled(true);
					go.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
					});
					left.repaint();
					right.repaint();

				}

			});
			right.add(temp);

			track++;
			count++;
		}
		
		
		

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		ImageIcon sea = new ImageIcon("sea.jpg");
		Image seaz = sea.getImage();
		g.drawImage(seaz, 0, 0, this.getWidth(), this.getHeight(), null);
		

	}



	public static void main(String[] args) throws MalformedURLException,
			IOException {

		DeckList a = new DeckList(574);
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel ss = new JPanel();
		JPanel st = new JPanel();
		
		ss.setLayout(new BorderLayout());
		ss.add(a.right, BorderLayout.EAST);
		ss.setOpaque(false);
		ss.add(a.left,BorderLayout.CENTER);
		ss.add(a.go,BorderLayout.SOUTH);
		
		test.add(ss);

		test.setVisible(true);

	}

}
