package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import com.google.gson.JsonObject;

import javax.swing.UIManager;

import misc.AudioPlayer;
import misc.Splash;
import misc.Splash2;
import misc.SplashPanel;
import misc.SplashPanel2;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2295628688056385946L;
	Battlefield bf;
	WTF wtf;
	SelectOpponent selectopponent;
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
	public static AudioPlayer bgMusic;
	Image i;

	public static void main(String[] args) {
		MainMenu main = new MainMenu(null);
		main.setVisible(true);
	}

	public MainMenu(Socket con) {

		initGUI();
	}

	private void initGUI() {

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBackground(Color.WHITE);
		playerList = new JTextArea();
		login = false;
		// Image i = ImageIO.read(new File("icw_mainmenu_wallpaper.jpg");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screen.width, screen.height - 30);
		try {
			i = ImageIO.read(new File("icw_mainmenu_wallpaper.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		contentPane = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(i, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setBackground(UIManager.getColor("Button.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel loginPanel = new JPanel();
		loginPanel.setOpaque(false);
		loginPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(loginPanel, BorderLayout.NORTH);
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));

		horizontalGlue = Box.createHorizontalGlue();
		loginPanel.add(horizontalGlue);

		welcome = new JLabel("");
		welcome.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 17));
		welcome.setForeground(Color.RED);
		welcome.setHorizontalAlignment(SwingConstants.RIGHT);
		welcome.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(welcome);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
		usernameField.setForeground(new Color(0, 191, 255));
		usernameField.setBackground(Color.WHITE);
		usernameField.setToolTipText("Username");
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendLogin();
				}
			}
		});

		usernameLabel = new JLabel(" Username ");
		usernameLabel.setForeground(new Color(0, 191, 255));
		loginPanel.add(usernameLabel);
		usernameField.setMaximumSize(new Dimension(5000, 2147483647));
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(usernameField);
		usernameField.setColumns(10);

		pw = new JPasswordField();
		pw.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
		pw.setForeground(new Color(0, 191, 255));
		pw.setBackground(Color.WHITE);
		pw.setToolTipText("Password");
		pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendLogin();
				}
			}
		});

		passwordLabel = new JLabel(" Password ");
		passwordLabel.setForeground(new Color(0, 191, 255));
		loginPanel.add(passwordLabel);
		pw.setMaximumSize(new Dimension(5000, 2147483647));
		pw.setHorizontalAlignment(SwingConstants.LEFT);
		pw.setAlignmentX(Component.RIGHT_ALIGNMENT);
		loginPanel.add(pw);
		pw.setColumns(10);

		loginButton = new JButton("Login");
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(Color.LIGHT_GRAY);
		loginButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
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

		// contentPane.add(logout, BorderLayout.NORTH);
		setContentPane(contentPane);

		ButtonPanel = new JPanel();
		ButtonPanel.setOpaque(false);
		contentPane.add(ButtonPanel, BorderLayout.CENTER);
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));

		verticalGlue_1 = Box.createVerticalGlue();
		ButtonPanel.add(verticalGlue_1);
		
		startButton = new JButton("New Game");
		startButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		startButton.setForeground(new Color(0, 191, 255));
		startButton.setBackground(new Color(0, 0, 0));
		
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Executors.newSingleThreadExecutor().execute(new Runnable(){

					@Override
					public void run() {
						Main.bgMusic.close();
						bgMusic = new AudioPlayer("DuelNew.wav");
						bgMusic.play();
						Splash2 frame = new Splash2();
						AudioPlayer bgMusic2 = new AudioPlayer("LoadingPage.wav");
						bgMusic2.playLoop();
						frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

						frame.setUndecorated(true);
						frame.setVisible(true);
						//frame.pack();
						frame.setLocationRelativeTo(null);

						// if(bf!=null){ //TODO: proper check (not done)
						// JOptionPane.showMessageDialog(null,
						// "An instance of ICB is already running!",
						// "",JOptionPane.DEFAULT_OPTION);
						// return;
						// }
						selectopponent = new SelectOpponent(user);
						selectopponent.setVisible(true);
						frame.dispose();
						bgMusic2.stop();
						// /bf = new Battlefield(new
						// Inw("{\"cv_uid\":\"517\",\"fb_id\":\"100000038984537\",\"firstname_en\":\"Assanee\",\"lastname_en\":\"Sukatham\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}")
						// ,new
						// Inw("{\"cv_uid\":\"663\",\"fb_id\":\"100003681922761\",\"firstname_en\":\"Ultra\",\"lastname_en\":\"7\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}"));
						// bf.setVisible(true);
					}
					
				});
				
			}
		});
		
		startButton.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent arg0) {
				bgMusic = new AudioPlayer("beep.wav");
				bgMusic.play();

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				

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
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.setEnabled(false);
		ButtonPanel.add(startButton);

		continueButton = new JButton("Continue The Game");
		continueButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		continueButton.setForeground(new Color(0, 191, 255));
		continueButton.setBackground(new Color(0, 0, 0));
		continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		continueButton.setEnabled(false);
		continueButton.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent arg0) {
				bgMusic = new AudioPlayer("beep.wav");
				bgMusic.play();

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
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
		ButtonPanel.add((Box.createRigidArea(new Dimension(0, 50))));
		ButtonPanel.add(continueButton);

		arrangeDeck = new JButton("Arrange Decks");
		arrangeDeck.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		arrangeDeck.setForeground(new Color(0, 191, 255));
		arrangeDeck.setBackground(Color.BLACK);
		arrangeDeck.setEnabled(false);
		arrangeDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.bgMusic.close();
				bgMusic = new AudioPlayer("Arrange the deck New.wav");
				bgMusic.play();

			}
		});
		arrangeDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
		arrangeDeck.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent arg0) {
				bgMusic = new AudioPlayer("beep.wav");
				bgMusic.play();

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
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
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		ButtonPanel.add(arrangeDeck);

		quit = new JButton("Quit");
		quit.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		quit.setForeground(new Color(0, 191, 255));
		quit.setBackground(new Color(0, 0, 0));
		quit.setAlignmentX(Component.CENTER_ALIGNMENT);
		quit.addMouseListener(new MouseListener() {

			public void mouseEntered(MouseEvent arg0) {
				bgMusic = new AudioPlayer("beep.wav");
				bgMusic.play();

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
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
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showOptionDialog(contentPane,
						"Are you sure you want to quit?", "",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (n == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}
		});
		ButtonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		ButtonPanel.add(quit);

		verticalGlue = Box.createVerticalGlue();
		ButtonPanel.add(verticalGlue);

		ChatPanel = new JPanel();
		ChatPanel.setVisible(false);
		// contentPane.add(ChatPanel, BorderLayout.EAST);
		ChatPanel.setLayout(new BorderLayout(0, 0));

		lblNewLabel = new JLabel("Chat Room");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ChatPanel.add(lblNewLabel, BorderLayout.NORTH);

		playerList.setBackground(Color.WHITE);

		ChatInputPanel = new JPanel();
		ChatPanel.add(ChatInputPanel, BorderLayout.SOUTH);
		ChatInputPanel
				.setLayout(new BoxLayout(ChatInputPanel, BoxLayout.X_AXIS));

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
		playerListPanel.setLayout(new BoxLayout(playerListPanel,
				BoxLayout.Y_AXIS));

		lblNewLabel_1 = new JLabel("Player List");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		playerListPanel.add(lblNewLabel_1);

		chatAreaScr = new JScrollPane(chatArea);
		playerListScr = new JScrollPane(playerList);
		playerListPanel.add(playerListScr);
		ChatPanel.add(chatAreaScr, BorderLayout.CENTER);
	}

	public void sendLogin() {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if (login) {
					logoutAction();
					return;
				}
				loginButton.setText("Logging in...");
				loginButton.setEnabled(false);
				loginButton.setIcon(new ImageIcon("aloader.gif"));
				String url = "http://128.199.235.83/icw/?q=icw/service/login&user="
						+ usernameField.getText() + "&pass=" + pw.getText();
				InputStream is;
				JsonObject job = null;
				while (true) {
					try {
						is = new URL(url).openStream();
						Gson gs = new Gson();
						job = gs.fromJson(new InputStreamReader(is),
								JsonObject.class);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						int a = JOptionPane.showConfirmDialog(null,
								"Could not connect to server\nTry again?", "",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.ERROR_MESSAGE);
						if (a == JOptionPane.YES_OPTION) // TRY LOGIN AGAIN
							continue;
						else { // CANCEL LOGIN
							loginButton.setEnabled(true);
							loginButton.setText("Login");
							loginButton.setIcon(null);
							return;
						}
					}
					break;
				}

				System.out.println(job.toString());
				// System.out.println("ENDU"+usernameField.getText()+":pass="+pw.getText());
				if (job.get("status").getAsInt() == 1) {
					welcome.setIcon(new ImageIcon("aloader.gif"));
					JsonObject j = job.getAsJsonObject("data");

					user = new Inw(j.get("firstname_en").getAsString(), j.get(
							"lastname_en").getAsString(), j.get("full_lp")
							.getAsInt(), j.get("full_mp").getAsInt(), j.get(
							"max_deck_size").getAsInt(), j.get("fb_id")
							.getAsString(), Integer.parseInt(usernameField
							.getText()));
					loginAction(true);
				} else
					loginAction(false);
				System.out.println("Login executor closing...");
			}

		});

	}

	public void loginAction(boolean b) {
		if (b) {
			usernameLabel.setVisible(false);
			passwordLabel.setVisible(false);
			usernameField.setVisible(false);
			pw.setVisible(false);
			loginButton.setText("Log Out");
			loginButton.setEnabled(true);
			loginButton.setIcon(null);
			welcome.setText("Welcome " + user.fname + " " + user.lname + "! ");
			welcome.setIcon(user.profile);
			//
			login = true;
			startButton.setEnabled(true);
			// TODO: case for continue button
			continueButton.setEnabled(true);
			arrangeDeck.setEnabled(true);
		} else {
			JOptionPane.showMessageDialog(this,
					"Incorrect username or password!", "",
					JOptionPane.DEFAULT_OPTION);
			loginFailedAttempt++;
			if (loginFailedAttempt == 3) {
				Executors.newSingleThreadExecutor().execute(new Runnable() {
					@Override
					public void run() {
						usernameField.setEnabled(false);
						pw.setEnabled(false);
						// loginButton.setEnabled(false);
						int temp = 60;
						while (temp > 0) {
							welcome.setText("You need to wait " + temp
									+ " seconds before trying again");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
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
			loginButton.setEnabled(true);
			loginButton.setText("Login");
			loginButton.setIcon(null);
		}
	}

	public void logoutAction() {
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

	public void sendMsg() {
		if (chatInput.getText().equals(""))
			return;
		try {
			out.writeObject(chatInput.getText());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem writing object",
					"Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		chatInput.setText("");
	}

	public static boolean isWideScreen() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenDimension.getWidth() / screenDimension.getHeight() > 1.4)
			return true;
		return false;
	}

	public static Dimension getScreenResolution() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	class EnterHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				sendMsg();
			}
		}
	}
}
