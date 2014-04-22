package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import main.Card;
import main.CardHolder;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Test_CardHolder extends JFrame {

	private JPanel contentPane;
	private CardHolder ch1;
	private CardHolder ch2;
	private JButton test;
	private JLabel st1;
	private JLabel st2;
	private JButton b1;
	private JButton b2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test_CardHolder frame = new Test_CardHolder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		ch1 = new CardHolder();

		contentPane.add(ch1);
		
				
				st1 = new JLabel("New label");
				contentPane.add(st1);
		
		ch2 = new CardHolder();

		
		b1 = new JButton("update 1");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("settext");
				if(ch1.c==null)st1.setText("null");
				else st1.setText(ch1.c.toString());
				
			}
		});
		contentPane.add(b1);
		contentPane.add(ch2);
		ch2.addCard(new Card(1,true));
		
		st2 = new JLabel("New label");
		contentPane.add(st2);
		
		b2 = new JButton("update 2");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("settext");
				if(ch2.c==null)st2.setText("null");
				else st2.setText(ch2.c.toString());
				
			}
		});
		contentPane.add(b2);
	}

}
