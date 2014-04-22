package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import misc.DropHandler;

import java.awt.BorderLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class CardHolder extends JPanel{

	public static int PLAYER = 0;
	public static int OPPONENT = 1;
	public static int PLAYER_DECK = 2;
	public static int DUMPSTER = 3;
	DropHandler dropHandler;
	DropTarget dropTarget;
	Card c;

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
		initGUI();
	}
	private void initGUI() {
		dropHandler = new DropHandler();
		dropTarget = new DropTarget(this, DnDConstants.ACTION_MOVE, dropHandler, true);
	//	setTransferHandler(null);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
	}
	
	public CardHolder(int type) {
		setLayout(new BorderLayout(0, 0));
	}
	
	public void addCard(Card c){
		this.add(c);
		this.c = c;
	}
}
