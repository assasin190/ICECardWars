package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DeckList extends JPanel {

	ArrayList<Card> deck = new ArrayList<Card>();
	JScrollPane scroller = new JScrollPane(this);
	 int x = this.getWidth();
	int y = this.getHeight();
	int count;

	public DeckList(int userid) {
count = 0;
		// this.setSize(this.getWidth() / 3, this.getHeight());
		String url = "http://128.199.235.83/icw/?q=icw/service/all_ic_of&user="
				+ userid;
		JsonObject job = null;

		InputStream is;
		CardData.saveAllCardsToLocal();

		try {
			is = new URL(url).openStream();
			Gson gs = new Gson();
			job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			String usercard = job.get("data").toString(); // [1,4,6,6,6,7,7,8,8,10,10,10,11,13,13,14,14,15,16,17,17,17,17,18,18,18,19,19,19,20,27,29,32,33,39,41,46,52,53,54]
			int count = 1;
			int x = 1;
			for (int i = 0; i < 39; i++) {

				deck.add(new Card(Integer.parseInt(usercard.substring(count,
						usercard.indexOf(",", count)))));
				
				x++;
				count = usercard.indexOf(",", count) + 1;
			}
			deck.add(new Card(Integer.parseInt(usercard.substring(count,
					count + 2))));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setVisible(true);
		int startx = 0;
		int starty = 0;
		int track = 0;
		this.setLayout(new GridLayout(40, 7));
		System.out.print(this.getWidth()/4);
		for (int i = 0; i < 40; i++) {
			ImageIcon a = deck.get(track).picture;
			Image picz = a.getImage().getScaledInstance(20, 20,
					java.awt.Image.SCALE_SMOOTH);

			final JPanel temp = new JPanel();

			temp.setLayout(new GridLayout(1, 6));
			JLabel title = new JLabel("" + deck.get(track).title);
			temp.add(new JLabel(new ImageIcon(picz)));
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
			temp.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
				temp.setVisible(false);
					
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				
			});
			this.add(temp);
			/*
			 * JLabel pic = new JLabel(picz); pic.setSize(10, 10); JLabel star =
			 * new JLabel("cost: "+deck.get(track).mc); pic.setBounds(startx,
			 * starty, star.getPreferredSize().width,
			 * star.getPreferredSize().height);
			 * 
			 * add(pic); JLabel title = new JLabel(""+deck.get(track).title);
			 * title.setBounds((int)startx +x/7, starty, x/7, y/40); add(title);
			 * 
			 * 
			 * star.setBounds((int)startx +(x/7)*2, starty, x/7, y/40);
			 * add(star);
			 * 
			 * JLabel atk = new JLabel("ATK: "+deck.get(track).atk);
			 * atk.setBounds((int)startx +(x/7)*3, starty, x/7, y/40); add(atk);
			 * 
			 * 
			 * JLabel lp = new JLabel("LP: "+deck.get(track).lp);
			 * lp.setBounds((int)startx +(x/7)*4, starty, x/7, y/40); add(lp);
			 * 
			 * JLabel lck = new JLabel("LCK: "+deck.get(track).lck);
			 * lck.setBounds((int)startx +(x/7)*5, starty, x/7, y/40); add(lck);
			 * 
			 * JLabel car = new JLabel("CAR: "+deck.get(track).car);
			 * car.setBounds((int)startx +(x/7)*6, starty, x/7, y/40); add(car);
			 * starty = starty + y/40;
			 */
			track++;
			count++;
		}

	}

	@Override
	protected void paintComponent(Graphics g) {

		ImageIcon sea = new ImageIcon("sea.jpg");
		Image seaz = sea.getImage();
		g.drawImage(seaz, 0, 0, this.getWidth(), this.getHeight(), null);

		/*
		 * for(int i = 10;i>0;i--){ for(int j = 4;j<0;j--){ BufferedImage pic =
		 * deck.get(track).picture; int picsize = pic.getHeight();
		 * g.drawImage(pic, startx , starty +10,x/4, y/10-80, null) ; JLabel
		 * lck_l = new JLabel("LCK: " + deck.get(track).lck);
		 * lck_l.setBounds(startx,
		 * starty,(int)lck_l.getPreferredSize().getWidth(), 10); add(lck_l);
		 * 
		 * JLabel car_l = new JLabel("CAR: " + deck.get(track).car);
		 * car_l.setBounds(startx, starty + picsize +10 ,
		 * (int)car_l.getPreferredSize().getWidth(), 10); add(car_l);
		 * 
		 * JLabel descLabel = new JLabel("SA desc: "+deck.get(track).desc);
		 * descLabel.setBounds(startx, starty + picsize +20,
		 * (int)descLabel.getPreferredSize().getWidth(), 10); add(descLabel);
		 * 
		 * JLabel titleLabel = new JLabel(""+deck.get(track).title);
		 * 
		 * titleLabel.setBounds(startx, starty + picsize + 30,
		 * (int)titleLabel.getPreferredSize().getWidth(), 10); add(titleLabel);
		 * 
		 * JLabel mc_l = new JLabel("MC: "+deck.get(track).mc);
		 * titleLabel.setBounds(startx, starty + picsize + 40,
		 * (int)titleLabel.getPreferredSize().getWidth(), 10); add(mc_l);
		 * 
		 * JLabel rrLabel = new JLabel(deck.get(track).rr(deck.get(track).rr));
		 * titleLabel.setBounds(startx, starty + picsize + 50,
		 * (int)titleLabel.getPreferredSize().getWidth(), 10); add(rrLabel);
		 * 
		 * JLabel atk_l = new JLabel("ATK: " + deck.get(track).atk);
		 * titleLabel.setBounds(startx, starty + picsize + 60,
		 * (int)titleLabel.getPreferredSize().getWidth(), 10); add(atk_l);
		 * 
		 * JLabel lp_l = new JLabel("LP: " + deck.get(track).lp);
		 * titleLabel.setBounds(startx, starty + picsize + 70,
		 * (int)titleLabel.getPreferredSize().getWidth(), 10); add(lp_l);
		 * 
		 * startx+=x/4; track++; } startx =0; starty = starty+y/10; }
		 */

	}

	public static void main(String[] args) throws MalformedURLException,
			IOException {
		DeckList a = new DeckList(574);
		JFrame test = new JFrame();
		JPanel ss = new JPanel();
		ss.setLayout(new BorderLayout());
		ss.add(a,BorderLayout.EAST);
		test.add(ss);
		
		test.setVisible(true);
	

	}

}
