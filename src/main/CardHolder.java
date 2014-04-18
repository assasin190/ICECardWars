package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class CardHolder extends JPanel {

	public static int PLAYER = 0;
	public static int OPPONENT = 1;
	public static int PLAYER_DECK = 2;
	public static int DUMPSTER = 3;
	private Card c;
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CardHolder());
					frame.setSize(700, 700);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public CardHolder() {
		setLayout(new BorderLayout(0, 0));
	}
	
	public CardHolder(int type) {
		setLayout(new BorderLayout(0, 0));
	}
}
