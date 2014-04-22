package test;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Card;
import main.CardHolder;

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
		setBounds(100, 100, 539, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));

		ch1 = new CardHolder();

		contentPane.add(ch1);


		st1 = new JLabel("New label");
		contentPane.add(st1);

		ch2 = new CardHolder();

		panel = new JPanel();
		contentPane.add(panel);


		b1 = new JButton("update 1");
		panel.add(b1);

		btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ch1.add(new JLabel("hue"));
			}
		});
		panel.add(btnNewButton);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("settext");
		//		if(ch1.isEmpty())st1.setText("null");
				st1.setText(ch1.getComponentCount()+":"+Arrays.toString(ch1.getComponents()));

			}
		});
		contentPane.add(ch2);
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

		ch3 = new CardHolder();
		contentPane.add(ch3);

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

		ch3.addCard(new Card(23,true));
		contentPane.add(b3);
	}

}
