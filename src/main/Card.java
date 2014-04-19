package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.commons.lang3.SerializationUtils;

import com.google.gson.Gson;

public class Card extends JPanel{
	int ic_id;
	String title;
	int type;
	int mc;
	//for monster
	int atk;
	int lp;
	int lck;
	double car;
	int sa_code;
	int sa_mc;
	String sa_param;
	int sa_value;
	int rr;
	//for spell
	int spell_code;
	String spell_param;
	BufferedImage picture;
	String desc;
	
	private JLabel titleLabel;
	private JLabel rrLabel;
	private JLabel descLabel;
	private JLabel lpLabel;
	private JLabel atkLabel;
	private JLabel carLabel;
	private JLabel car_l;
	private JLabel lckLabel;
	private JLabel lck_l;
	private JLabel lp_l;
	private JLabel atk_l;
	private JLabel mcLabel;
	private JLabel mc_l;
	private JLabel pictureIcon;
	private JPanel panel;
	
	/*
//	{"status":1,"data":
	{"ic_id":"1","title":"Slime","type":"1","mc":"1","atk":"1","lp":"3","lck":"0","car":"0","sa_code":"2","sa_mc":"1","sa_param":"ATK,1","rr":"1","spell_code":"0","spell_param":"-","picture":"sites\/all\/modules\/icw\/pict\/card\/slime.jpg"}}



	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					//			Object[] o = CardData.allCardData();
					JFrame frame = new JFrame();

					frame.setSize(700, 700);
					frame.setVisible(true);
					frame.setLayout(new GridLayout(10,10));
					for(int i = 1;i<CardData.getNumberOfCards();i++){
						frame.add(new Card(i));
					}
					Card c = new Card(6);
					Card c1 = new Card(6);
					c.atk = 100000;
					System.out.println(c);
					System.out.println(c1);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
<<<<<<< HEAD
	public Card(int ID) {
	
		String url = "http://128.199.235.83/icw/?q=icw/service/ic&ic_id="+ID;
		InputStream is;	
		Map m = null;
=======
	public Card(double test){
>>>>>>> 8a2d55dc93f40eca0e0099c24888a768c3d38195
		try {
			picture = ImageIO.read(new File("null.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		titleLabel.setText("test card: "+test);
		initGUI();
	}

	public Card(int ID) {
		@SuppressWarnings("rawtypes")
		Map m2 = CardData.getCardData(ID);
		car = Double.parseDouble((String) m2.get("car"));
		lp = Integer.parseInt((String) m2.get("lp"));
		spell_param = (String) m2.get("spell_param");
		type = Integer.parseInt((String) m2.get("type"));
		sa_param = (String) m2.get("sa_param");
		sa_code = Integer.parseInt((String) m2.get("sa_code"));
<<<<<<< HEAD
		try {
			picture = ImageIO.read(new URL("http://128.199.235.83/icw/"+m2.get("picture")));
			picture = ImageIO.read(new URL("http://128.199.235.83/icw/"));
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
=======
		picture = CardData.getCardImage(ID);
>>>>>>> 8a2d55dc93f40eca0e0099c24888a768c3d38195
		lck = Integer.parseInt((String) m2.get("lck"));
		title = (String) m2.get("title");
		atk = Integer.parseInt((String) m2.get("atk"));
		mc = Integer.parseInt((String) m2.get("mc"));
		rr = Integer.parseInt((String) m2.get("rr"));
		ic_id = Integer.parseInt((String) m2.get("ic_id"));
		sa_mc = Integer.parseInt((String) m2.get("sa_mc"));
		spell_code = Integer.parseInt((String) m2.get("spell_code"));
		desc = null;
		initGUI();
	}
	private void initGUI() {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK, 1));
		setLayout(null);
		if(picture==null)
			try {
				picture = ImageIO.read(new File("null.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		pictureIcon = new JLabel(new ImageIcon(picture));
		pictureIcon.setBounds(12, 63, 176, 58);
		add(pictureIcon);
		
		lck_l = new JLabel("LCK:");
		lck_l.setBounds(12, 192, 56, 16);
		add(lck_l);
		
		lckLabel = new JLabel(""+lck);
		lckLabel.setBounds(80, 192, 56, 16);
		add(lckLabel);
		
		car_l = new JLabel("CAR:");
		car_l.setBounds(12, 221, 56, 16);
		add(car_l);
		
		carLabel = new JLabel(""+car);
		carLabel.setBounds(80, 221, 56, 16);
		add(carLabel);
		
		descLabel = new JLabel("SA desc"+desc);
		descLabel.setBounds(12, 221, 176, 66);
		add(descLabel);
		
		titleLabel = new JLabel(""+title);
		titleLabel.setBounds(12, 13, 56, 16);
		add(titleLabel);
		
		mc_l = new JLabel("MC:");
		mc_l.setBounds(99, 13, 31, 16);
		add(mc_l);
		
		mcLabel = new JLabel(""+mc);
		mcLabel.setBounds(132, 13, 56, 16);
		add(mcLabel);
		
		rrLabel = new JLabel(rr(rr));
		rrLabel.setBounds(12, 34, 90, 16);
		add(rrLabel);
		
		atk_l = new JLabel("ATK:");
		atk_l.setBounds(12, 134, 56, 16);
		add(atk_l);
		
		atkLabel = new JLabel(""+atk);
		atkLabel.setBounds(80, 134, 56, 16);
		add(atkLabel);
		
		lp_l = new JLabel("LP:");
		lp_l.setBounds(12, 163, 56, 16);
		add(lp_l);
		
		lpLabel = new JLabel(""+lp);
		lpLabel.setBounds(80, 163, 56, 16);
		add(lpLabel);
		
		addListeners();
	}
	public void addListeners(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBorder(new LineBorder(Color.MAGENTA, 5));
				Main.setSelectedCard(Card.this);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				if(Main.getSelectedCard()==Card.this)return;
				setBorder(new LineBorder(Color.GREEN, 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(Main.getSelectedCard()==Card.this)return;
				setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
	}
	public void deselect(){
		setBorder(new LineBorder(Color.BLACK, 1));
	}
	public String rr(int rr){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<rr;i++){
			sb.append("\u2605");
		}
		return sb.toString();
	}
	@Override
	public String toString(){
		return "ic_id="+ic_id+", title="+title+ ", type="+type+ ", mc="+mc+ ", atk="+atk+ ", lp="+lp+ ", lck="+lck+ ", car="+car+ ", sa_code="+sa_code+ ", sa_mc="+sa_mc+ ", sa_param="+sa_param+ ", rr="+rr+ ", spell_code="+spell_code+ ", spell_param="+spell_param+ ", picture="+picture.toString(); 
	}
}
