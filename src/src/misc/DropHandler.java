package misc;

import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JComponent;

import main.Battlefield;
import main.Card;
import main.CardHolder;
import main.Inw;
import main.Main;

public class DropHandler implements DropTargetListener {

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		System.out.println("DROPHANDLER: dragEnter");
		// Determine if can actual process the contents comming in.
		// You could try and inspect the transferable as well, but 
		// There is an issue on the MacOS under some circumstances
		// where it does not actually bundle the data until you accept the
		// drop.
		if (dtde.isDataFlavorSupported(PanelDataFlavor.SHARED_INSTANCE)) {

			dtde.acceptDrag(DnDConstants.ACTION_MOVE);

		} else {

			dtde.rejectDrag();

		}

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		System.out.println("DROPHANDLER: dragOver");
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		System.out.println("DROPHANDLER: dropActionChanged");
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		System.out.println("DROPHANDLER: dropExit");
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		System.out.println("DROPHANDLER: drop");
		System.out.println("original: "+Main.orig);
		boolean success = false;
		// Basically, we want to unwrap the present...
		if (dtde.isDataFlavorSupported(PanelDataFlavor.SHARED_INSTANCE)) {
			Transferable transferable = dtde.getTransferable();
			try {
				Object data = transferable.getTransferData(PanelDataFlavor.SHARED_INSTANCE);
				if (data instanceof Card) {
					Card card = (Card) data;
					Container original = Main.orig;
					//		Main.ch = null;		TODO:
					DropTargetContext dtc = dtde.getDropTargetContext();
					//		System.out.println("sth: "+Main.getSelectedCard().getParent());
					Component destination = dtc.getComponent();
					if (destination instanceof JComponent) {

						System.out.println("dest: "+destination+" orig: "+original);
						if(destination instanceof CardHolder && original instanceof CardHolder){	//This, by default, should be true
							System.out.println("DEST/ORIG is instanceof CardHolder");
							CardHolder c_destination = (CardHolder)destination;
							CardHolder c_original = (CardHolder)original;
					//		if(true){
							if(dropCondition(c_original, c_destination, card)){
								//TODO: CHECK CARD TYPE AND SPELL CODE
								/*
								if(original instanceof CardHolder){			// Cardholer to cardholder
									c_original.removeCard();
								}else if(original instanceof Container){	// container to cardholder
									original.remove(card);
								}
								 */
								c_destination.addCard(card);
								c_destination.getCard().addListeners();
								success = true;
								dtde.acceptDrop(DnDConstants.ACTION_MOVE);
								c_destination.revalidate();
								c_destination.repaint();
								card.invalidate();
								card.repaint();
							}else{		//destination cardholder is not empty
								success = false;
								dtde.rejectDrop();
							}
							//				((JComponent)destination).add(card);				
						}else{
							success = false;
							dtde.rejectDrop();
						}

					} else {
						success = false;
						dtde.rejectDrop();
					}
				} else {
					success = false;
					dtde.rejectDrop();
				}

			} catch (Exception exp) {
				success = false;
				dtde.rejectDrop();
				exp.printStackTrace();
			}
		} else {
			success = false;
			dtde.rejectDrop();
		}
		System.out.println("DROP: "+success);
		dtde.dropComplete(success);
	}
	private boolean dropCondition(CardHolder o,CardHolder d, Card c){
		//CHECK DIFFERENT DROP CONDITION
		if(o==d){
			System.out.println("Drop rejected: Source is the same as destination");
			return false;
		}
		
		if((d.type==CardHolder.PLAYER||d.type==CardHolder.OPPONENT)&&!d.isEmpty())return false;
		int ot = o.type;	
		int dt = d.type;
		System.out.println(ot + "|" + dt);
		System.out.println(CardHolder.DECK+"|"+CardHolder.DECK);
		String s = ot + "|" + dt;
		switch(s){
		case CardHolder.DECK+"|"+CardHolder.DECK:
			System.out.println("Drop accepted: DECK to DECK");
		return true;
		case CardHolder.PLAYER_HAND+"|"+CardHolder.PLAYER:
			// check if [YOUR TURN, CARD = MONSTER, ENOUGH MP TO SUMMON, DESTINATION IS EMPTY]
			boolean b = Main.Turn&&
			c.getType()==1&&
			Battlefield.player.useMP(c.getMc())&&
			d.isEmpty();

		System.out.println("HAND TO PLAYER: "+b);
		return b;
		default: return false;
		}
	}
}