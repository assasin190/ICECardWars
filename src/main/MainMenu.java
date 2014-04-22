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
import com.google.gson.JsonObject;


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
	
	public MainMenu(Socket con) {
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(Color.WHITE);
		
		playerList = new JTextArea();
		
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
		Executors.newSingleThreadExecutor().execute(new Runnable(){
			@Override
			public void run() {
				if(login){
					logoutAction();
					return;
				}
				loginButton.setEnabled(false);
				loginButton.setIcon(new ImageIcon("aloader.gif"));
				loginButton.setText("Logging in...");
				String url = "http://128.199.235.83/icw/?q=icw/service/login&user="
				+usernameField.getText()+"&pass="+pw.getText();
				InputStream is;
				JsonObject job = null;
				while(true){
					try {
						is = new URL(url).openStream();
						Gson gs = new Gson();
						job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
					} catch (MalformedURLException e) {e.printStackTrace();
					} catch (IOException e) {
						int a = JOptionPane.showConfirmDialog(null, "Could not connect to server\nTry again?", "",JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
						if(a==JOptionPane.YES_OPTION)	//TRY LOGIN AGAIN
							continue;
						else{	//CANCEL LOGIN
							loginButton.setEnabled(true);
							loginButton.setText("Login");
							loginButton.setIcon(null);
							return;
						}
					}	
					break;
				}
				loginButton.setEnabled(true);
				loginButton.setText("Login");
				loginButton.setIcon(null);
				System.out.println(job.toString());
				if(job.get("status").getAsInt()==1){
					JsonObject j = job.getAsJsonObject("data");
					user = new Inw(j.get("firstname_en").getAsString(),j.get("lastname_en").getAsString(),j.get("full_lp").getAsInt()
							,j.get("full_mp").getAsInt(),j.get("max_deck_size").getAsInt(), j.get("fb_id").getAsString(),null);
					loginAction(true);
				}else loginAction(false);
				System.out.println("Login executor closing...");
			}

		});
		
	}
	public void loginAction(boolean b){
		if(b){
			usernameLabel.setVisible(false);
			passwordLabel.setVisible(false);
			usernameField.setVisible(false);
			pw.setVisible(false);
			loginButton.setText("Log Out");
			welcome.setText("Welcome "+user.fname+" "+user.lname+"! ");
			try {
				welcome.setIcon(new ImageIcon(ImageIO.read(new URL("https://graph.facebook.com/"+user.fb_id+"/picture"))));
			} catch (IOException e) {
				e.printStackTrace();
			}
			login = true;
			startButton.setEnabled(true);
			//TODO: case for continue button
			continueButton.setEnabled(true);
			arrangeDeck.setEnabled(true);
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
				//		loginButton.setEnabled(false);
						int temp = 60;
						while(temp>0){
							welcome.setText("You need to wait "+temp+" seconds before trying again");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {e.printStackTrace();
							}
							temp--;
							loginButton.setEnabled(false);
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
		usernameField.setVisible(true);
		pw.setVisible(true);
		loginButton.setText("Log In");
		welcome.setText("");
		usernameField.setText("");
		pw.setText("");
		startButton.setEnabled(false);
		continueButton.setEnabled(false);
		arrangeDeck.setEnabled(false);
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

