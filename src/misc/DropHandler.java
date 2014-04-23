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

import main.Card;
import main.CardHolder;

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
		boolean success = false;

		// Basically, we want to unwrap the present...
		if (dtde.isDataFlavorSupported(PanelDataFlavor.SHARED_INSTANCE)) {
			Transferable transferable = dtde.getTransferable();
			try {
				Object data = transferable.getTransferData(PanelDataFlavor.SHARED_INSTANCE);
				if (data instanceof Card) {
					Card card = (Card) data;
					DropTargetContext dtc = dtde.getDropTargetContext();
					Component destination = dtc.getComponent();
					if (destination instanceof JComponent) {
						Container original = card.getParent();
						if(destination instanceof CardHolder){
							if(((CardHolder) destination).isEmpty()){
								//TODO: CHECK CARD TYPE AND SPELL CODE
								if(original instanceof CardHolder){			// Cardholer to cardholder
									((CardHolder) original).removeCard();
								}else if(original instanceof Container){	// container to cardholder
									original.remove(card);
								}
								System.out.println("dtc: addCard");
								CardHolder cc = ((CardHolder) destination);
								cc.addCard(card);
								success = true;
								dtde.acceptDrop(DnDConstants.ACTION_MOVE);
								card.invalidate();
								card.repaint();
							}
							else{		//destination cardholder is not empty
								success = false;
								System.out.println("DROP REJECTED");
								dtde.rejectDrop();
							}
		//				((JComponent)destination).add(card);				
						}else if(destination instanceof Container){
							if(original instanceof CardHolder){			// Cardholer to container
								((CardHolder) original).removeCard();
							}else if(original instanceof Container){	// container to container
								original.remove(card);
							}
							((JComponent)destination).add(card);
							success = true;
							dtde.acceptDrop(DnDConstants.ACTION_MOVE);
							card.invalidate();
							card.repaint();
						}else{
							success = false;
							System.out.println("DROP REJECTED");
							dtde.rejectDrop();
						}

					} else {
						success = false;
						System.out.println("DROP REJECTED");
						dtde.rejectDrop();
					}
				} else {
					success = false;
					System.out.println("DROP REJECTED");
					dtde.rejectDrop();
				}

			} catch (Exception exp) {
				success = false;
				System.out.println("DROP REJECTED");
				dtde.rejectDrop();
				exp.printStackTrace();
			}
		} else {
			success = false;
			System.out.println("DROP REJECTED");
			dtde.rejectDrop();
		}
		dtde.dropComplete(success);
	}
}