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

	public final static int PLAYER = 0;				//player lane (LIMITED TO ONE CARD)!
	public final static int OPPONENT = 1;			//opponent lane (LIMITED TO ONE CARD)!
	public final static int DECK = 2;				//deck, unlimited, Cards is freely transferable between deck
	public final static int DUMPSTER = 3;			//unlimited capacity
	public final static int PLAYER_HAND = 4;		//unlimited capacity
	public final static int OPPONENT_HAND = 5;		//unlimited capacity card may probably display as hidden
	public final static int DISPLAY = 6;			//single unchangeable display card, use setCard() with this type
	DropHandler dropHandler;
	DropTarget dropTarget;
	protected BufferedImage screenshot;
	public int type;
	private CardHolder opposingCH;

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CardHolder(CardHolder.DECK,false));
					frame.setSize(700, 700);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Creates a CardHolder with specified type and add DnD support
	 * @param Type - the type of the CardHolder, look at static variable in CardHolder class for detail
	 * @param customGUI - if true, the JPanel will skip the initGUI method
	 * 
	 */
	public CardHolder(int Type,boolean customGUI) {
		type = Type;
		dropHandler = new DropHandler();
		dropTarget = new DropTarget(this, DnDConstants.ACTION_MOVE, dropHandler, true);
		if(!customGUI)initGUI();
	}
	private void initGUI() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
	}

	public boolean isEmpty(){
		return this.getComponentCount()==0;

	}
	public void addCard(Card c){
		if(!isEmpty()&&(type==0||type==1))System.err.println("Attempt to add card into nonempty Lane");
		this.add(c);
	}
	public void removeCard(){
		if(isEmpty()&&(type==0||type==1))System.err.println("Attempt to remove card from empty Lane");
		this.remove(0);
	}
	public Card getCard(){
		return (Card)this.getComponent(0);
	}
	/**Set the reference to CardHolder belonging to the opposing side and on same lane with this CardHolder
	 * @param c 
	 */
	public void setOpposingCH(CardHolder c){
		opposingCH = c;
	}
	public CardHolder getOpposingCardHolder(){
		return opposingCH;
	}


	
}
