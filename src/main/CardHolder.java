package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import misc.DropHandler;

public class CardHolder extends JPanel{

	public static int PLAYER = 0;
	public static int OPPONENT = 1;
	public static int PLAYER_DECK = 2;
	public static int DUMPSTER = 3;
	DropHandler dropHandler;
	DropTarget dropTarget;
	protected BufferedImage screenshot;

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
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
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
	public boolean isEmpty(){
		return this.getComponentCount()==0;

	}
	public void addCard(Card c){
		if(!isEmpty())System.err.println("Attempt to add card into nonempty CardHolder");
		this.add(c);
	}
	public void removeCard(){
		if(isEmpty())System.err.println("Attempt to remove card from empty CardHolder");
		this.remove(0);
	}
	public Card getCard(){
		return (Card)this.getComponent(0);
	}
}
