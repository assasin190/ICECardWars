package test;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import main.Card;
import main.CardData;
import main.CardHolder;
import misc.DropHandler;

import java.awt.Color;

import javax.swing.BoxLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class Test_CardHolder extends JFrame {

	private JPanel contentPane;
	private CardHolder ch1;
	private CardHolder ch2;
	private JLabel st1;
	private JLabel st2;
	private JButton b1;
	private JButton b2;
	private CardHolder ch3;
	private JLabel st3;
	private JPanel panel;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//			CardData.saveAllCardsToLocal();
					Test_CardHolder frame = new Test_CardHolder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Hello");
				
				
			}
		
		});
	}

	/**
	 * Create the frame.
	 */
	public Test_CardHolder() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 539, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));

		ch1 = new CardHolder(CardHolder.DECK,false);
		ch1.setBackground(new Color(255, 255, 153));
		DropHandler dropHandler = new DropHandler();

		scrollPane = new JScrollPane(ch1);
		contentPane.add(scrollPane);
		DropTarget dropTarget = new DropTarget(ch1, DnDConstants.ACTION_MOVE, dropHandler, true);
		ch1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


		st1 = new JLabel("New label");
		contentPane.add(st1);

		ch2 = new CardHolder(CardHolder.DECK,false);
		ch2.setBackground(Color.YELLOW);
		panel = new JPanel();
		contentPane.add(panel);


		b1 = new JButton("update 1");
		panel.add(b1);

		btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card c = new Card((int)(Math.random() * ((60) + 1)),true);
				c.descArea.setText("555555555555555555555555555555555555534634634634634634655555555555555555555555555555555534634634634634634655555555555555555555555555555555553463463463463463465555555555555555555555555555555555555555555555555555555555555");
				ch1.add(c);
				ch1.revalidate();
			}
		});
		panel.add(btnNewButton);

		btnNewButton_1 = new JButton("red effect");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((Card)ch1.getComponent(0)).effectRed();;
			}
		});
		panel.add(btnNewButton_1);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("settext");
				//		if(ch1.isEmpty())st1.setText("null");
				st1.setText(ch1.getComponentCount()+":"+Arrays.toString(ch1.getComponents()));

			}
		});
		contentPane.add(ch2);
		ch2.setLayout(new GridLayout(1, 0, 0, 0));
		ch2.addCard(new Card(1,true));

		st2 = new JLabel("New label");
		contentPane.add(st2);

		b2 = new JButton("update 2");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("settext");
				//	if(ch2.isEmpty())st2.setText("null");
				st2.setText(ch2.getComponentCount()+":"+Arrays.toString(ch2.getComponents()));

			}
		});
		contentPane.add(b2);

		ch3 = new CardHolder(CardHolder.DECK,false);
		ch3.setBackground(Color.MAGENTA);
		contentPane.add(ch3);
		ch3.setLayout(new BorderLayout(0, 0));

		st3 = new JLabel("update 3");
		contentPane.add(st3);

		JButton b3 = new JButton("update 3");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("settext");
				//		if(ch3.isEmpty())st3.setText("null");
				st3.setText(ch3.getComponentCount()+":"+Arrays.toString(ch3.getComponents()));

			}
		});
		Card c = new Card(23,true);
		//c.desc = "555555555555555555555555555555555555555555555555555555555555555555555555";
		c.descArea.setText("555555555555555555555555555555555555534634634634634634655555555555555555555555555555555534634634634634634655555555555555555555555555555555553463463463463463465555555555555555555555555555555555555555555555555555555555555");
		ch3.addCard(c);
		contentPane.add(b3);
	}

}
