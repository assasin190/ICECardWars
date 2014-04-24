package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import misc.DragGestureHandler;

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
	ImageIcon picture;
	String desc;
	Inw caster;
	private JLabel titleLabel;
	private JLabel rrLabel;
	private JLabel descLabel;
	private JLabel car_l;
	private JLabel lck_l;
	private JLabel lp_l;
	private JLabel atk_l;
	private JLabel mc_l;
	private DragGestureHandler dragGestureHandler;
	private DragGestureRecognizer dgr;
	public static void main(String[] args){

		EventQueue.invokeLater(new Runnable() {		//TEST GETTING DECK
			public void run() {
				try {
					CardData.saveAllCardsToLocal();
					JFrame frame = new JFrame();
					frame.setSize(700, 700);
					frame.setVisible(true);
					frame.getContentPane().setLayout(new GridLayout(4,5));
					Gson gs;
					InputStream is;	
					String url ="http://128.199.235.83/icw/?q=icw/service/get_deck&user=595";	//INTERT YOUR ID HERE
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
				//	System.out.println(deck.toString());
					for(int a:deck){
						frame.getContentPane().add(new Card(a));
					}


					frame.getContentPane().add(new Card(1,true));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	@Override
	public void addNotify() {
		System.out.println("CARD: addNotify");
		super.addNotify();
		if (dgr == null) {
			dragGestureHandler = new DragGestureHandler(this);
			dgr = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(
					this,
					DnDConstants.ACTION_MOVE,
					dragGestureHandler);
		}
	}

	@Override
	public void removeNotify() {
		System.out.println("CARD: removeNotify");
		if (dgr != null) {
			dgr.removeDragGestureListener(dragGestureHandler);
			dragGestureHandler = null;
		}
		dgr = null;
		super.removeNotify();
	}
	public Card(int ID,boolean notused){	//TEMPORARY CONSTRUCTOR THAT ACCESS THE WEB DIRECTLY FOR CARD'S INFO
		JsonObject data = null;
		ImageIcon b = null;
		while(true){
			String url ="http://128.199.235.83/icw/?q=icw/service/ic&ic_id="+ID;
			JsonObject job = null;
			try {
				InputStream is = new URL(url).openStream();
				Gson gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("problem with the connection (retrieving JSON map)... retrying");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}	
			if(job.get("data").toString().equals("false")){
				break;
			}
			data = job.getAsJsonObject("data");
			b = null;
			try {
				b = new ImageIcon(ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture").getAsString())));
			} catch (MalformedURLException e) {
				System.err.println("problem with the connection (retrieving picture)... retrying");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(data);
			break;
		}
		car = data.get("car").getAsDouble();
		lp = data.get("lp").getAsInt();
		spell_param = data.get("spell_param").getAsString();
		sa_param = data.get("sa_param").getAsString();
		type = data.get("type").getAsInt();
		sa_code = data.get("sa_code").getAsInt();
		picture = b;
		lck = data.get("lck").getAsInt();
		title = data.get("title").getAsString();
		atk = data.get("atk").getAsInt();
		mc = data.get("mc").getAsInt();
		rr = data.get("rr").getAsInt();
		ic_id = data.get("ic_id").getAsInt();
		sa_mc = data.get("sa_mc").getAsInt();
		spell_code = data.get("spell_code").getAsInt();
		desc = "null";
	}
	/**
	 * @wbp.parser.constructor
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
		//	System.out.println("DESC:"+desc);
		System.out.println("DESC:"+desc);
		initGUI(); 

	}
	private void initGUI() {
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
<<<<<<< HEAD
		 */

		g.drawImage(picture.getImage(), getWidth()/32, getHeight()/7, getWidth()-getWidth()/32, getHeight()/2, null);
		g.drawImage(picture.getImage(), getWidth()/32, getHeight()/7, getWidth()-getWidth()/32, getHeight()/2, null);
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
	public BufferedImage createImage(JPanel panel) {
		int w = panel.getWidth();
		int h = panel.getHeight();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		panel.paint(g);
		return bi;
	}
	public int getType(){
		return type;
	}
	public int getMc(){
		return mc;
	}
	public Inw getCaster(){
		return caster;
	}
}
