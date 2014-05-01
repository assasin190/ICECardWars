package main;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import misc.DragGestureHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.Rectangle;

public class Card extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5704617275462345564L;
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
	private String sa_param;
	String param_type = "";
	double param_value = 0;
	int rr;
	//for spell
	int spell_code;
	private String spell_param;
	ImageIcon picture;
	ImageIcon background;
	public String desc;
	Inw caster;
	boolean Protected = false;		//invulnerable to normal attack
	boolean sacrifice = false;		//sacrifice
	boolean directInw = false;		//attacks Inw directly
	boolean SAactivated = false;		//the monster already uses the SA for its turn
	private JLabel titleLabel;
	private JLabel rrLabel;
	//	private JLabel descLabel;
	private JLabel car_l;
	private JLabel lck_l;
	private JLabel lp_l;
	private JLabel atk_l;
	private JLabel mc_l;
	private DragGestureHandler dragGestureHandler;
	private DragGestureRecognizer dgr;
	private JPanel pic_panel;
	private JScrollPane scrollPane;
	public JTextArea descArea;
	private JLabel pic_label;
	private JPanel statPanel;
	private JLabel mcLabel;
	public static Image spell;
	public static Image attack;
	public static void main(String[] args){

		EventQueue.invokeLater(new Runnable() {		//TEST GETTING DECK
			public void run() {

				Card garf = new Card(3,true);
				Card sli = new Card(1,true);
				System.out.println(garf.param_value);
				sli.apply(garf);
				System.out.println(sli.toString());
				/*
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


		//			frame.getContentPane().add(new Card(1,true));
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				 */

			}
		});

	}
	@Override
	public void addNotify() {
		//	System.out.println("CARD: addNotify");
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
		//	System.out.println("CARD: removeNotify");
		if (dgr != null) {
			dgr.removeDragGestureListener(dragGestureHandler);
			dragGestureHandler = null;
		}
		dgr = null;
		super.removeNotify();
	}
	/**Creates a clone of card c (right now only used for display)
	 * @param c
	 */
	public Card(Card c){
		ic_id = c.ic_id;
		title = c.title;
		type = c.type;
		mc = c.mc;
		atk = c.atk;
		lp = c.lp;
		lck = c.lck;
		car = c.car;
		sa_code  = c.sa_code;
		sa_mc = c.sa_mc;
		sa_param = c.sa_param;
		param_type = c.param_type;
		param_value = c.param_value;
		rr = c.rr;
		spell_code = c.spell_code;
		spell_param = c.spell_param;
		picture = c.picture;
		desc = c.desc;
		repaint();
		initGUI();
		addListeners();
		//	caster = c.caster;
		//	Protected = c.Protected;
		//	sacrifice = c.sacrifice;
		//	directInw = c.directInw;
		//	SAactivated = c.SAactivated;
	}
	/**TEMPORARY CONSTRUCTOR THAT ACCESS THE WEB DIRECTLY FOR CARD'S INFO
	 * @param ID
	 * @param notused
	 * @param caster
	 */
	public Card(int ID,boolean notused,Inw caster){
		this(ID,notused);
		this.caster = caster;
	}
	/**TEMPORARY CONSTRUCTOR THAT ACCESS THE WEB DIRECTLY FOR CARD'S INFO
	 * @param ID
	 * @param notused
	 */
	public Card(int ID,boolean notused){	
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
				Image img = ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture").getAsString())).getScaledInstance(80, 80, Image.SCALE_DEFAULT);
				//b = new ImageIcon(ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture").getAsString())));
				b = new ImageIcon(img);
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
		if(type==1){
			if(!sa_param.equals("")){
				if(sa_param.contains(",")){
					param_type = sa_param.substring(0,sa_param.indexOf(','));
					param_value =Double.parseDouble(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
				}else
					param_value =Double.parseDouble(sa_param);
			}
		}else if(type==2){
			if(!spell_param.equals("")){
				if(spell_param.contains(",")){
					param_type = spell_param.substring(0,spell_param.indexOf(','));
					param_value =Double.parseDouble(spell_param.substring(spell_param.indexOf(',')+1,spell_param.length()));
				}else
					param_value =Double.parseDouble(spell_param);
			}
		}
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
		initGUI();
		addListeners();
	}
	/**
	 * @wbp.parser.constructor
	 */
	public Card(int ID) {
		setBounds(new Rectangle(0, 0, 123, 180));
		setSize(new Dimension(123, 180));
		//Original card size 221x324


		JsonObject m2 = CardData.getCardData(ID);
		car = m2.get("car").getAsDouble();
		lp = m2.get("lp").getAsInt();
		spell_param = m2.get("spell_param").getAsString();
		sa_param = m2.get("sa_param").getAsString();
		type = m2.get("type").getAsInt();
		System.out.println("TYPE: "+type);
		if(type==1){
			if(!sa_param.equals("")){
				if(sa_param.contains(",")){
					param_type = sa_param.substring(0,sa_param.indexOf(','));
					param_value =Double.parseDouble(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
				}else
					param_value =Double.parseDouble(sa_param);
			}
		}else if(type==2){
			if(!spell_param.equals("")){
				if(spell_param.contains(",")){
					param_type = spell_param.substring(0,spell_param.indexOf(','));
					param_value =Double.parseDouble(spell_param.substring(spell_param.indexOf(',')+1,spell_param.length()));
				}else
					param_value =Double.parseDouble(spell_param);
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
		System.out.println("TYPE: "+type);
		System.out.println("t1: "+CardData.getSpellCode(spell_code).replace("{1}", param_value+""));
		System.out.println("t2: "+CardData.getSpellCode(spell_code).replace("{1}", param_type).replace("{2}", param_value+""));
		if(type==1){	// DESCRIPTION FOR MONSTER
			if(param_type.equals("")) desc = CardData.getSaCode(sa_code).replace("{1}", param_value+"");
			else desc = CardData.getSaCode(sa_code).replace("{1}", param_type).replace("{2}", param_value+"");
			desc = "("+sa_mc+") "+desc;
		}else if(type==2){	//DESCRIPTION FOR SPELL
			System.out.println("SPELL TYPE!");
			if(param_type.equals("")) CardData.getSpellCode(spell_code).replace("{1}", param_value+"");
			else desc = CardData.getSpellCode(spell_code).replace("{1}", param_type).replace("{2}", param_value+"");
			desc = "("+mc+") "+desc;
		}
		//	System.out.println("DESC:"+desc);
		System.out.println("DESC:"+desc);
		initGUI();
		addListeners();
	}
	/**In real battles Card need a reference to their caster aswell
	 * @param ID
	 * @param caster
	 */
	/*
	public Card(int ID,Inw caster) {
		this.caster = caster;
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
					param_value =Double.parseDouble(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
				}else
					param_value =Double.parseDouble(sa_param);
			}
		}else if(type==2){
			if(!spell_param.equals("")){
				if(spell_param.contains(",")){
					param_type = spell_param.substring(0,spell_param.indexOf(','));
					param_value =Double.parseDouble(spell_param.substring(spell_param.indexOf(',')+1,spell_param.length()));
				}else
					param_value =Double.parseDouble(spell_param);
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
		//System.out.println();
		if(type==1){	// DESCRIPTION FOR MONSTER
			if(param_type.equals("")) desc = CardData.getSaCode(sa_code).replace("{1}", param_value+"");
			else desc = CardData.getSaCode(sa_code).replace("{1}", param_type).replace("{2}", param_value+"");
			desc = "("+sa_mc+") "+desc;
		}else if(type==2){	//DESCRIPTION FOR SPELL
			System.out.println(spell_code);
			if(param_type.equals("")) CardData.getSpellCode(spell_code).replace("{1}", param_value+"");
			else desc = CardData.getSpellCode(spell_code).replace("{1}", param_type).replace("{2}", param_value+"");
			desc = "("+mc+") "+desc;
		}
		//	System.out.println("DESC:"+desc);
		//	System.out.println("DESC:"+desc);
		initGUI();
		addListeners();
	}
	*/
	public void addListeners(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//			setBorder(new LineBorder(Color.MAGENTA, 5));
				//			System.out.println("selectedCardChanged");
				Main.setSelectedCard(Card.this);
				Main.updateDisplay();
				//		if(Battlefield.selectedCard!=null)Battlefield.setDisplayCard(Card.this);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				//			if(Main.getSelectedCard()==Card.this)return;
				//			setBorder(new LineBorder(Color.GREEN, 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				//			if(Main.getSelectedCard()==Card.this)return;
				//			setBorder(new LineBorder(Color.BLACK, 1));
			}
		});
	}
	public void initGUI(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {10, 0, 0, 55};
		gridBagLayout.columnWidths = new int[] {20, 20, 20, 20};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		titleLabel = new JLabel(title);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_titleLabel.anchor = GridBagConstraints.NORTH;
		gbc_titleLabel.insets = new Insets(2, 2, 5, 5);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 0;
		this.add(titleLabel, gbc_titleLabel);

		descArea = new JTextArea(desc);
		descArea.setRows(2);
		descArea.setWrapStyleWord(true);
		descArea.setFont(new Font("Calibri", Font.PLAIN, 11));
		descArea.setLineWrap(true);
		descArea.setMaximumSize(new Dimension(221, 2147483647));
		descArea.setEditable(false);
		try {
			if(isMonster()){
				Image img = ImageIO.read(new File("card_monster_bg.jpg")).getScaledInstance(123, 180, Image.SCALE_DEFAULT);
				background = new ImageIcon(img);
			}else background = new ImageIcon(ImageIO.read(new File("card_spell_bg.jpg")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mcLabel = new JLabel("MC");
		mcLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_mcLabel = new GridBagConstraints();
		gbc_mcLabel.insets = new Insets(0, 2, 5, 2);
		gbc_mcLabel.gridx = 3;
		gbc_mcLabel.gridy = 0;
		add(mcLabel, gbc_mcLabel);

		statPanel = new JPanel();
		GridBagConstraints gbc_statPanel = new GridBagConstraints();
		gbc_statPanel.weighty = 0.1;
		gbc_statPanel.gridwidth = 4;
		gbc_statPanel.insets = new Insets(2, 0, 5, 2);
		gbc_statPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_statPanel.gridx = 0;
		gbc_statPanel.gridy = 3;
		add(statPanel, gbc_statPanel);
		lp_l = new JLabel();
		lp_l.setFont(new Font("Tahoma", Font.PLAIN, 10));
		statPanel.add(lp_l);
		lp_l.setHorizontalAlignment(SwingConstants.CENTER);
		atk_l = new JLabel();
		atk_l.setFont(new Font("Tahoma", Font.PLAIN, 10));
		statPanel.add(atk_l);
		atk_l.setHorizontalAlignment(SwingConstants.CENTER);
		lck_l = new JLabel();
		lck_l.setFont(new Font("Tahoma", Font.PLAIN, 10));
		statPanel.add(lck_l);
		lck_l.setHorizontalAlignment(SwingConstants.CENTER);
		//descLabel = new JLabel();
		car_l = new JLabel();
		car_l.setFont(new Font("Tahoma", Font.PLAIN, 10));
		statPanel.add(car_l);
		car_l.setHorizontalAlignment(SwingConstants.CENTER);
		rrLabel = new JLabel(rr(rr));
		rrLabel.setHorizontalAlignment(SwingConstants.LEFT);
		rrLabel.setForeground(Color.WHITE);
		GridBagConstraints gbc_rrLabel = new GridBagConstraints();
		gbc_rrLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_rrLabel.anchor = GridBagConstraints.NORTH;
		gbc_rrLabel.insets = new Insets(2, 2, 5, 5);
		gbc_rrLabel.gridx = 0;
		gbc_rrLabel.gridy = 1;
		this.add(rrLabel, gbc_rrLabel);
		mc_l = new JLabel(mc+"");
		mc_l.setForeground(Color.WHITE);
		mc_l.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_mc_l = new GridBagConstraints();
		gbc_mc_l.insets = new Insets(2, 2, 5, 2);
		gbc_mc_l.gridx = 3;
		gbc_mc_l.gridy = 1;
		this.add(mc_l, gbc_mc_l);

		scrollPane = new JScrollPane(descArea);
		scrollPane.setPreferredSize(new Dimension(150, 34));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 0.5;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(2, 2, 5, 2);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		//add(scrollPane, gbc_scrollPane);

		pic_panel = new JPanel();
		pic_panel.setOpaque(false);
		GridBagConstraints gbc_pic_panel = new GridBagConstraints();
		gbc_pic_panel.weighty = 0.4;
		gbc_pic_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pic_panel.gridwidth = 4;
		gbc_pic_panel.insets = new Insets(2, 2, 5, 2);
		gbc_pic_panel.gridx = 0;
		gbc_pic_panel.gridy = 2;
		add(pic_panel, gbc_pic_panel);
		pic_panel.setLayout(new BorderLayout(0, 0));

		pic_label = new JLabel(picture);
		pic_panel.add(pic_label);
		this.setPreferredSize(new Dimension(164, 240));
		updateGUI();
	}
	/*
	public void deselect(){
		setBorder(new LineBorder(Color.BLACK, 1));
	}
	 */
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
	public void updateGUI(){
		lp_l.setText("LP: "+lp);
		atk_l.setText("ATK: "+atk);
		lck_l.setText("LCK: "+lck);
		car_l.setText("CAR: "+car);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isMonster())g.setColor(Color.GREEN);
		else g.setColor(Color.BLUE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
		/*
		setBackground(Color.WHITE);
		setLayout(null);

		setBorder(new LineBorder(Color.BLACK, 1));
		if(picture==null)
			try {
				picture = ImageIO.read(new File("null.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		pictureIcon = new JLabel(new ImageIcon(picture));
		pictureIcon.setBounds(12, 63, 176, 58);
		add(pictureIcon);


		g.drawImage(picture.getImage(), getWidth()/32, getHeight()/7, getWidth()-getWidth()/32, getHeight()/2, null);
		g.drawImage(picture.getImage(), getWidth()/32, getHeight()/7, getWidth()-getWidth()/32, getHeight()/2, null);
		if(lck_l != null) remove(lck_l);
		lck_l = new JLabel("LCK: " + lck);
		lck_l.setBounds(getWidth()/32, 24*getHeight()/32, (int)lck_l.getPreferredSize().getWidth(), 10);
		add(lck_l);


		if(lckLabel != null) remove(lck_l);
		lckLabel = new JLabel(""+lck);
		lckLabel.setBounds(80, 192, 56, 16);
		add(lckLabel);



		if(car_l != null) remove(car_l);
		car_l = new JLabel("CAR: " + car);
		car_l.setBounds(getWidth()/32, 26*getHeight()/32, (int)car_l.getPreferredSize().getWidth(), 10);
		add(car_l);


		if(carLabel != null) remove(carLabel);
		carLabel = new JLabel(""+car);
		carLabel.setBounds(80, 221, 56, 16);
		add(carLabel);



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


		mcLabel = new JLabel(""+mc);
		mcLabel.setBounds(132, 13, 56, 16);
		add(mcLabel);



		if(rrLabel != null) remove(rrLabel);
		rrLabel = new JLabel(rr(rr));
		rrLabel.setBounds(getWidth()/32, 4*getHeight()/32, (int)rrLabel.getPreferredSize().getWidth(), 10);
		add(rrLabel);


		if(atk_l != null) remove(atk_l);
		atk_l = new JLabel("ATK: " + atk);
		atk_l.setBounds(getWidth()/32, 20*getHeight()/32, (int)atk_l.getPreferredSize().getWidth(), 10);
		add(atk_l);

		if(atkLabel != null) remove(atkLabel);
		atkLabel = new JLabel(""+atk);
		atkLabel.setBounds(80, 134, 56, 16);
		add(atkLabel); 

		if(lp_l != null) remove(lp_l);
		lp_l = new JLabel("LP: " + lp);
		lp_l.setBounds(getWidth()/32, 22*getHeight()/32, (int)lp_l.getPreferredSize().getWidth(), 10);
		add(lp_l);

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
	/**
	 * @return true if card = IC-monster, false if card = IC-spell
	 */
	public boolean isMonster(){
		return type==1;
	}


	/**
	 * @param param - sa_param / spell_param
	 * @param value	- value, IF THE SPELL DECREASE THE PARAMETER, MAKE THE VALUE NEGATIVE!
	 */
	public void param(String param,double value){
		switch(param){
		case "ATK":
			atk = (int) Math.max(0, atk + value);
			break;
		case "LP":
			lp = (int) Math.max(0, lp + value);
			break;
		case "LCK":
			lck = (int) Math.max(0, lck + value);
			break;
		}
	}
	/**Apply the SA or Spell belonging to card C to this card
	 * This method does not calculate which card will get the apply. That process is done in battlefield processNotify(...) method
	 * @param c the monster or spell card to apply
	 * @return true if the monster is dead after receiving the SA/Spell
	 */
	public boolean apply(Card c){
		//return false;
		//TODO: not done!

		if(c.isMonster()){
			Battlefield.notify.append(c.title+" used special ability on "+this.title+"\n");
			switch(c.sa_code){
			case 1:	// increase self
				param(c.param_type,c.param_value);
				effectColor(Color.GREEN);
				break;
			case 2:	// decreasing opposing
				param(c.param_type,-c.param_value);
				effectColor(Color.MAGENTA);
				break;
			case 3:	// increase team
				param(c.param_type,c.param_value);
				effectColor(Color.GREEN);
				break;
			case 4:	// decrease opponent
				param(c.param_type,-c.param_value);
				effectColor(Color.MAGENTA);
				break;
			case 5:	// increase team , sacrifice
				param(c.param_type,c.param_value);
				sacrifice = true;
				effectColor(Color.CYAN);
				break;
			case 6:
				car = param_value;
				effectColor(Color.GREEN);
				break;
			case 7:
				directInw = true;
				effectColor(Color.BLUE);
				break;
			}	
		}else{
			Battlefield.notify.append(c.title+" spell used on "+this.title+"\n");
			switch(c.spell_code){
			case 1: //increase
				param(c.param_type,c.param_value);
				effectColor(Color.GREEN);
				break;
			case 2:	//decrease
				param(c.param_type,-c.param_value);
				effectColor(Color.MAGENTA);
				break;
			case 3:	//re shuffle deck
				System.err.println("This should already be done in Battlefield!");
				break;
			case 4:
				Protected = true;
				effectColor(Color.GRAY);
				break;
			case 5:	//Heal Inw
				System.err.println("This should already be done in Battlefield!");
				break;
			case 6:	// return can IC
				System.err.println("This should already be done in Battlefield!");
				break;
			case 7:	// return all IC with star greater than ...
				System.err.println("This should already be done in Battlefield!");
				break;
			}
		}
		updateGUI();
		repaint();
		return lp<=0;
	}

	/**Attack this card with the specified damage
	 * @param DMG - the damage dealt (calculate ATK + LUK beforehand)
	 * @param CAR - if this attack is CAR, protected status doesn't help
	 * @return true if the monster is dead after receiving the attack
	 */
	public boolean attack(int DMG,boolean CAR){
		if(Protected&&!CAR){
			System.out.println(this.title+" is protected!");
			Battlefield.notify.append(this.title+" is protected!\n");
			return false;
		}
		Battlefield.notify.append(this.title+" received "+DMG+" damages\n");
		//	System.out.println(this.title+" received "+DMG+" damage");
		this.lp -= DMG;
		updateGUI();
		effectRed();
		return lp<=0;
	}
	public int generateNetAtk(){
		return (int) Math.max(0, atk-lck + (int)(Math.random() * ((lck*2) + 1)));
	}
	public void effectRed(){
		//	Executors.newSingleThreadExecutor().execute
		//	SwingUtilities.invokeLater
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				int alpha = 0;
				Graphics g = Card.this.getGraphics();
				if(g==null)return;
				alpha = 255;
				while(alpha > 0){
					g.setColor(new Color(255,0,0,alpha));
					g.fillRect(0, 0, getWidth(), getHeight());
					try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
					alpha -= 26;
					//			repaint();
				}
				//			repaint();
			}
		});
	}

	public void effectGreen(){
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				int alpha = 0;
				Graphics g = Card.this.getGraphics();
				if(g==null)return;
				alpha = 255;
				while(alpha > 0){
					g.setColor(new Color(0,255,0,alpha));
					g.fillRect(0, 0, getWidth(), getHeight());
					try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
					alpha -= 26;
					//			repaint();
				}
				//			repaint();
			}
		});
	}
	public void effectColor(final Color c){
		//	Executors.newSingleThreadExecutor().execute
		//	SwingUtilities.invokeLater
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				int alpha = 0;
				Graphics gr = Card.this.getGraphics();
				if(gr==null)return;
				alpha = 255;
				while(alpha > 0){
					gr.setColor(new Color(r,g,b,alpha));
					gr.fillRect(0, 0, getWidth(), getHeight());
					try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
					alpha -= 26;
					//			repaint();
				}
				//			repaint();
			}
		});
	}
	public void effectSpell(){
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				float alpha = 0.0f;
				Graphics g = Card.this.getGraphics();
				if(g==null)return;
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.GREEN);
				while(alpha <=1.0f){
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, alpha));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawImage(spell, 0, 0, Card.this.getWidth(), Card.this.getHeight(), null);
					alpha += 0.02f;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//			repaint();
				}
				//		repaint();
			}
		});
	}
	public void effectAttack(){
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				float alpha = 0.0f;
				Graphics g = Card.this.getGraphics();
				if(g==null)return;
				Graphics2D g2d = (Graphics2D) g;
				g2d.setColor(Color.GREEN);
				while(alpha <=1.0f){
					g2d.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_OVER, alpha));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawImage(attack, 0, 0, Card.this.getWidth(), Card.this.getHeight(), null);
					alpha += 0.02f;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//			repaint();
				}
				//		repaint();
			}
		});
	}
}
