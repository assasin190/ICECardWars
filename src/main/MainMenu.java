package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;


public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2295628688056385946L;
	Battlefield bf;
	private Inw user;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField pw;
	private JButton loginButton;
	private JButton signupButton;
	private JLabel welcome;
	boolean login;
	private Component horizontalGlue;
	private JPanel ButtonPanel;
	private JButton startButton;
	private JButton continueButton;
	private JButton arrangeDeck;
	private JButton quit;
	Socket con;
	int loginFailedAttempt = 0;
	private JScrollPane chatAreaScr;
	private JTextArea chatArea;
	private JPanel ChatPanel;
	private JLabel lblNewLabel;
	private JTextField chatInput;
	private JPanel ChatInputPanel;
	private JButton sendButton;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JScrollPane playerListScr;
	private JPanel playerListPanel;
	private JLabel lblNewLabel_1;
	private JTextArea playerList;
	private Component rigidArea;
	private Component verticalGlue;
	private Component verticalGlue_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		/*
		Splash frame = new Splash();
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainMenu main = new MainMenu(null);
		main.setUndecorated(true);
	//	main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		main.setVisible(true);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.dispose();
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}

	/**
	 * Create the frame.
	 */
	public MainMenu(Socket con) {
		
		/*
		this.con = con;
		try {
			out = new ObjectOutputStream(con.getOutputStream());
		//	con.get
	        in = new ObjectInputStream(con.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//	chatArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		*/
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(Color.WHITE);
		
		playerList = new JTextArea();
		
/*

		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run(){
				Object o;
				try {
					try {
						while ((o = in.readObject()) != null) {
							if(o instanceof String){
								chatArea.append(o+"\n");
							}
							else if(o instanceof String[]){
								playerList.setText("");
								for(String s : (String[])o){
									playerList.append(s+"\n");
								}
								
							}
							else {
								if(!login)loginAction((boolean)o);
								else if(login)logoutAction();
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (SocketException e) {
		//			System.err.println("Client #"+id+" reports connection reset");
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		});
		
		*/
		login = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0,screen.width,screen.height - 30);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(loginPanel, BorderLayout.NORTH);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
		
		horizontalGlue = Box.createHorizontalGlue();
		loginPanel.add(horizontalGlue);
		
		welcome = new JLabel("");
		welcome.setHorizontalAlignment(SwingConstants.RIGHT);
		welcome.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(welcome);
		
		usernameField = new JTextField();
		usernameField.setToolTipText("Username");
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					sendLogin();
				}
			}
		});
		
		usernameLabel = new JLabel(" Username ");
		loginPanel.add(usernameLabel);
		usernameField.setMaximumSize(new Dimension(5000, 2147483647));
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(usernameField);
		usernameField.setColumns(10);
		
		pw = new JPasswordField();
		pw.setToolTipText("Password");
		pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					sendLogin();
				}
			}
		});
		
		passwordLabel = new JLabel(" Password ");
		loginPanel.add(passwordLabel);
		pw.setMaximumSize(new Dimension(5000, 2147483647));
		pw.setHorizontalAlignment(SwingConstants.LEFT);
		pw.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(pw);
		pw.setColumns(10);
		
		loginButton = new JButton("Login");
		loginButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					sendLogin();
				}
			}
		});
		loginButton.setHorizontalAlignment(SwingConstants.LEFT);
		loginButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendLogin();
			}
		});
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(10, 20));
		rigidArea.setMinimumSize(new Dimension(10, 20));
		loginPanel.add(rigidArea);
		loginPanel.add(loginButton);
		
		signupButton = new JButton("");
		signupButton.setVisible(false);
		signupButton.setHorizontalAlignment(SwingConstants.LEFT);
		signupButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(signupButton);
		

//		contentPane.add(logout, BorderLayout.NORTH);
		setContentPane(contentPane);
		
		ButtonPanel = new JPanel();
		contentPane.add(ButtonPanel, BorderLayout.CENTER);
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));
		
		verticalGlue_1 = Box.createVerticalGlue();
		ButtonPanel.add(verticalGlue_1);
		
		startButton = new JButton("Commence a new ICB");
	//	startButton.setIcon(null);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(bf!=null){
					JOptionPane.showMessageDialog(null, "An instance of ICB is already running!", "",JOptionPane.DEFAULT_OPTION);
					return;
				}
				bf = new Battlefield();
				bf.setVisible(true);
			}
		});
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setEnabled(false);
		ButtonPanel.add(startButton);
		
		continueButton = new JButton("Continue the current ICB");
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.setEnabled(false);
		ButtonPanel.add(continueButton);
		
		arrangeDeck = new JButton("Arrange Decks");
		arrangeDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
		arrangeDeck.setEnabled(false);
		ButtonPanel.add(arrangeDeck);
		
		quit = new JButton("Quit");
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showOptionDialog(contentPane, "Are you sure you want to quit?" ,
	    				"", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
	    		if(n==JOptionPane.YES_OPTION){
	    			System.exit(0);
	    		}
				
			}
		});
		ButtonPanel.add(quit);
		
		verticalGlue = Box.createVerticalGlue();
		ButtonPanel.add(verticalGlue);
		
		ChatPanel = new JPanel();
		ChatPanel.setVisible(false);
	//	contentPane.add(ChatPanel, BorderLayout.EAST);
		ChatPanel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("Chat Room");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ChatPanel.add(lblNewLabel, BorderLayout.NORTH);
		
		playerList.setBackground(Color.WHITE);

		
		ChatInputPanel = new JPanel();
		ChatPanel.add(ChatInputPanel, BorderLayout.SOUTH);
		ChatInputPanel.setLayout(new BoxLayout(ChatInputPanel, BoxLayout.X_AXIS));
		
		chatInput = new JTextField();
		ChatInputPanel.add(chatInput);
		chatInput.addKeyListener(new EnterHandler());
		chatInput.setBackground(Color.WHITE);
		


		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg();
			}
		});
		sendButton.addKeyListener(new EnterHandler());
		ChatInputPanel.add(sendButton);
		
		playerListPanel = new JPanel();
		ChatPanel.add(playerListPanel, BorderLayout.EAST);
		playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
		
		lblNewLabel_1 = new JLabel("Player List");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		playerListPanel.add(lblNewLabel_1);


		chatAreaScr = new JScrollPane(chatArea);
		playerListScr = new JScrollPane(playerList);
		playerListPanel.add(playerListScr);
		ChatPanel.add(chatAreaScr, BorderLayout.CENTER);
	}
	public void sendLogin(){
		if(login){
			logoutAction();
			return;
		}
		JsonParserFactory factory=JsonParserFactory.getInstance();
		JSONParser parser=factory.newJsonParser();
		String url = "http://128.199.235.83/icw/?q=icw/service/login&user="
		+usernameField.getText()+"&pass="+pw.getText();
		InputStream is;
		Map m = null;
	//	m.get("status");
		try {
			is = new URL(url).openStream();
			Gson gs = new Gson();
			m = (Map) gs.fromJson(new InputStreamReader(is), Object.class);
		} catch (MalformedURLException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}	
		
		//
	//	Integer.parseInt(arg0) m.get("full_lp");
	//	System.out.println(m.get("status"));
	//	System.out.println(((Map) m.get("data")).get("firstname_en"));
		System.out.println(m.toString());
		if(m.get("status").equals("1.0")){
		
			System.out.println(m.get("firstname_en"));
			user = new Inw((String)((Map) m.get("data")).get("firstname_en"),(String) ((Map) m.get("data")).get("lastname_en"),Integer.parseInt((String) ((Map) m.get("data")).get("full_lp"))
					,Integer.parseInt((String) ((Map) m.get("data")).get("full_mp")),Integer.parseInt((String) ((Map) m.get("data")).get("max_deck_size")),null);
			
	//		System.out.println(user.toString());
			loginAction(true);
		}else loginAction(false);
	}
	public void loginAction(boolean b){
		if(b){
			usernameLabel.setVisible(false);
			passwordLabel.setVisible(false);
			//			sendButton.setEnabled(true);
			//			chatInput.setEnabled(true);
			usernameField.setVisible(false);
			pw.setVisible(false);
	//		signupButton.setVisible(false);
			loginButton.setText("Log Out");
			

	//		System.out.println(user.fname + " " + user.lname);
			welcome.setText("Welcome "+user.fname+" "+user.lname+"! ");
			try {
				welcome.setIcon(new ImageIcon(ImageIO.read(new URL("https://graph.facebook.com/100003770583869/picture"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			login = true;
			startButton.setEnabled(true);
			//TODO: case for continue button
			continueButton.setEnabled(true);
			arrangeDeck.setEnabled(true);
			//		chatArea.append("You are now logged in!\n");

		}
		else{
			JOptionPane.showMessageDialog(null, "Incorrect username or password!", "",JOptionPane.DEFAULT_OPTION);
			loginFailedAttempt++;
			if(loginFailedAttempt==3){
				Executors.newSingleThreadExecutor().execute(new Runnable(){
					@Override
					public void run() {
						usernameField.setEnabled(false);
						pw.setEnabled(false);
						loginButton.setEnabled(false);
						int temp = 60;
						while(temp>0){
							welcome.setText("You need to wait "+temp+" seconds before trying again");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							temp--;
						}
						usernameField.setEnabled(true);
						pw.setEnabled(true);
						loginButton.setEnabled(true);
						welcome.setText("");
						loginFailedAttempt = 0;
					}			
				});
			}
		}
	}
	public void logoutAction(){
		usernameLabel.setVisible(true);
		passwordLabel.setVisible(true);
	//	sendButton.setEnabled(false);
	//	chatInput.setEnabled(false);
		usernameField.setVisible(true);
		pw.setVisible(true);
	//	signupButton.setVisible(true);
		loginButton.setText("Log In");
		welcome.setText("");
		usernameField.setText("");
		pw.setText("");
		startButton.setEnabled(false);
		//TODO: case for continue button
		continueButton.setEnabled(false);
		arrangeDeck.setEnabled(false);
//		chatArea.append("You have logged out\n");
		login = false;
		welcome.setIcon(null);
		user = null;
	}
	
	public void sendMsg(){
		if(chatInput.getText().equals(""))return;
		try {
			out.writeObject(chatInput.getText());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem writing object", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	//	out.println(chatInput.getText());
		chatInput.setText("");
	}
	class EnterHandler extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				sendMsg();
			}	
		}
	}
}

