package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

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
	String param_type = "";
	int param_value = 0;
	int rr;
	//for spell
	int spell_code;
	String spell_param;
	BufferedImage picture;
	String desc;
	
	private JLabel titleLabel;
	private JLabel rrLabel;
	private JLabel descLabel;
	private JLabel car_l;
	private JLabel lck_l;
	private JLabel lp_l;
	private JLabel atk_l;
	private JLabel mc_l;
	public static void main(String[] args){

		EventQueue.invokeLater(new Runnable() {		//TEST GETTING DECK
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					JFrame frame = new JFrame();
					frame.setSize(700, 700);
					frame.setVisible(true);
					frame.setLayout(new GridLayout(4,5));
					Gson gs;
					InputStream is;	
					String url ="http://128.199.235.83/icw/?q=icw/service/get_deck&user=603";	//INTERT YOUR ID HERE
					JsonObject job = null;
					try {
						is = new URL(url).openStream();
						gs = new Gson();
						job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
						System.out.println(job);
					} catch (MalformedURLException e) {e.printStackTrace();
					} catch (IOException e) {}
					Type listType = new TypeToken<List<Integer>>() {}.getType();
					List<Integer> deck = new Gson().fromJson(job.get("data"), listType);
					System.out.println(deck.toString());
					for(int a:deck){
						frame.add(new Card(a));
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	/*
	public Card(double test){
		try {
			picture = ImageIO.read(new File("null.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		titleLabel.setText("test card: "+test);
		initGUI();
	}
	*/
	public Card(int ID) {
		
		JsonObject m2 = CardData.getCardData(ID);
		car = m2.get("car").getAsDouble();
		lp = m2.get("lp").getAsInt();
		spell_param = m2.get("spell_param").getAsString();
		sa_param = m2.get("sa_param").getAsString();
		type = m2.get("type").getAsInt();
		if(type==1){
			if(!sa_param.equals("")){
				if(sa_param.contains(",")){
					param_type = sa_param.substring(0,sa_param.indexOf(','));
					param_value = Integer.parseInt(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
				}else
					param_value = Integer.parseInt(sa_param);
			}
		}else if(type==2){
			if(!spell_param.equals("")){
				if(spell_param.contains(",")){
					param_type = spell_param.substring(0,spell_param.indexOf(','));
					param_value = Integer.parseInt(spell_param.substring(spell_param.indexOf(',')+1,spell_param.length()));
				}else
					param_value = Integer.parseInt(spell_param);
			}
		}
		sa_code = m2.get("sa_code").getAsInt();
		picture = CardData.getCardImage(ID);
		lck = m2.get("lck").getAsInt();
		title = m2.get("title").getAsString();
		atk = m2.get("atk").getAsInt();
		mc = m2.get("mc").getAsInt();
		rr = m2.get("rr").getAsInt();
		ic_id = m2.get("ic_id").getAsInt();
		sa_mc = m2.get("sa_mc").getAsInt();
		spell_code = m2.get("spell_code").getAsInt();
		desc = null;
		if(type==1){	// DESCRIPTION FOR MONSTER
			desc = CardData.getSaCode(sa_code).replace("{1}", param_type).replace("{2}", param_value+"");
		}else if(type==2){	//DESCRIPTION FOR SPELL
			desc = CardData.getSpellCode(spell_code).replace("{1}", param_type).replace("{2}", param_value+"");
		}
		System.out.println("DESC:"+desc);
		initGUI(); 
		
		
		/*
		setSize(221, 324);
		setBackground(Color.WHITE);
		setLayout(null);
		Gson gs;
		InputStream is;	
		String url ="http://128.199.235.83/icw/?q=icw/service/ic&ic_id=1";
		Object o = null;
		try {
			is = new URL(url).openStream();
			gs = new Gson();
			o = gs.fromJson(new InputStreamReader(is), Object.class);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		Map temp = (Map)o;
		Map m2 = (Map)temp.get("data");
		BufferedImage b = null;
		try {
			b = ImageIO.read(new URL("http://128.199.235.83/icw/"+m2.get("picture")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		car = Double.parseDouble((String) m2.get("car"));
		lp = Integer.parseInt((String) m2.get("lp"));
		spell_param = (String) m2.get("spell_param");
		type = Integer.parseInt((String) m2.get("type"));
		sa_param = (String) m2.get("sa_param");
		sa_code = Integer.parseInt((String) m2.get("sa_code"));
		picture = b;
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
		setVisible(true);
		*/
		
	}
	private void initGUI() {
		addListeners();
	//	repaint();
		/*
		setBackground(Color.WHITE);
		//setBorder(new LineBorder(Color.BLACK, 1));
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

		lck_l = new JLabel("LCK: ");
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
		titleLabel.setLocation(this.getWidth()/32, getHeight()/32);
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
		*/
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		setLayout(null);
		/*
		setBorder(new LineBorder(Color.BLACK, 1));
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
		*/
		
		g.drawImage(picture, getWidth()/32, getHeight()/7, getWidth()-getWidth()/32, getHeight()/2, null);
	
		if(lck_l != null) remove(lck_l);
		lck_l = new JLabel("LCK: " + lck);
		lck_l.setBounds(getWidth()/32, 24*getHeight()/32, (int)lck_l.getPreferredSize().getWidth(), 10);
		add(lck_l);
		
		/*
		if(lckLabel != null) remove(lck_l);
		lckLabel = new JLabel(""+lck);
		lckLabel.setBounds(80, 192, 56, 16);
		add(lckLabel);
		*/
		

		if(car_l != null) remove(car_l);
		car_l = new JLabel("CAR: " + car);
		car_l.setBounds(getWidth()/32, 26*getHeight()/32, (int)car_l.getPreferredSize().getWidth(), 10);
		add(car_l);
		
		/*
		if(carLabel != null) remove(carLabel);
		carLabel = new JLabel(""+car);
		carLabel.setBounds(80, 221, 56, 16);
		add(carLabel);
		*/
		

		if(descLabel != null) remove(descLabel);
		descLabel = new JLabel("SA desc: "+desc);
		descLabel.setBounds(getWidth()/32, 28*getHeight()/32, (int)descLabel.getPreferredSize().getWidth(), 10);
		add(descLabel);
		
		if(titleLabel != null) remove(titleLabel);
		titleLabel = new JLabel(""+title);
		titleLabel.setLocation(getWidth()/32, getHeight()/32);
		titleLabel.setBounds(getWidth()/32, getHeight()/32, getWidth()/32 + (int)titleLabel.getPreferredSize().getWidth(), getHeight()/32 + (int)titleLabel.getPreferredSize().getHeight());
		add(titleLabel);

		
		if(mc_l != null) remove(mc_l);
		mc_l = new JLabel("MC: "+mc);
		mc_l.setBounds(getWidth() - getWidth()/32 - (int)mc_l.getPreferredSize().getWidth(), getHeight()/32, getWidth() - getWidth()/32 , getHeight()/32 + (int)mc_l.getPreferredSize().getHeight());
		add(mc_l);

		/*
		mcLabel = new JLabel(""+mc);
		mcLabel.setBounds(132, 13, 56, 16);
		add(mcLabel);
		*/
		
		
		if(rrLabel != null) remove(rrLabel);
		rrLabel = new JLabel(rr(rr));
		rrLabel.setBounds(getWidth()/32, 4*getHeight()/32, (int)rrLabel.getPreferredSize().getWidth(), 10);
		add(rrLabel);
		
		
		if(atk_l != null) remove(atk_l);
		atk_l = new JLabel("ATK: " + atk);
		atk_l.setBounds(getWidth()/32, 20*getHeight()/32, (int)atk_l.getPreferredSize().getWidth(), 10);
		add(atk_l);
		
		/*
		if(atkLabel != null) remove(atkLabel);
		atkLabel = new JLabel(""+atk);
		atkLabel.setBounds(80, 134, 56, 16);
		add(atkLabel);
		*/
		
		if(lp_l != null) remove(lp_l);
		lp_l = new JLabel("LP: " + lp);
		lp_l.setBounds(getWidth()/32, 22*getHeight()/32, (int)lp_l.getPreferredSize().getWidth(), 10);
		add(lp_l);
		
		/*
		if(lpLabel != null) remove(lpLabel);
		lpLabel = new JLabel(""+lp);
		lpLabel.setBounds(80, 163, 56, 16);
		add(lpLabel);
		*/

	}
}
