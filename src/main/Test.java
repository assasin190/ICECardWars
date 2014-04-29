package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import misc.DropHandler;

public class Test {
	static JFrame frame;
	static CardHolder cardholder1;
	static CardHolder cardholder2;
	static JScrollPane scrollPane1;
	static JButton btnNewButton;
	
	public static void main(String [] args) throws MalformedURLException, IOException {
		
		frame = new JFrame();
		cardholder1 = new CardHolder(CardHolder.DECK, false);
		cardholder1.setBackground(Color.LIGHT_GRAY);
		DropHandler dropHandler = new DropHandler();
		
		scrollPane1 = new JScrollPane(cardholder1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		DropTarget dropTarget = new DropTarget(cardholder1, DnDConstants.ACTION_MOVE, dropHandler, true);
		cardholder1.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));

		cardholder2 = new CardHolder(CardHolder.DECK,false);
		cardholder2.setBackground(Color.YELLOW);
		cardholder2.setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(1, 2));
		temp.add(scrollPane1);
		temp.add(cardholder2);
		btnNewButton = new JButton("add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card c = new Card((int)(Math.random() * ((60) + 1)),true);
				cardholder1.add(c);
				cardholder1.revalidate();
			}
		});
		frame.setLayout(new BorderLayout());
		frame.add(btnNewButton, BorderLayout.PAGE_START);
		frame.add(temp, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		System.out.println(frame.getSize());
	}
	
	
}
