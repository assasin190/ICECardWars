package misc;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JPanel;

import main.Card;
import main.CardHolder;


public class DragGestureHandler implements DragGestureListener, DragSourceListener {

    private Container parent;
    private JPanel child;

    public DragGestureHandler(JPanel child) {
        this.child = child;
    }

    public JPanel getCard() {
        return child;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

    public Container getParent() {
        return parent;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {

        // When the drag begins, we need to grab a reference to the
        // parent container so we can return it if the drop
        // is rejected
        Container parent = getCard().getParent();

        setParent(parent);

        // Remove the panel from the parent.  If we don't do this, it
        // can cause serialization issues.  We could over come this
        // by allowing the drop target to remove the component, but that's
        // an argument for another day
        parent.remove(getCard());
        for (int i = 0; i < parent.getComponentCount(); i++) {
        	if (parent.getComponent(i) == getCard()){
        		((CardHolder)parent).index = i;
        		break;
        	}
        }
        // Update the display
        parent.invalidate();
        parent.repaint();

        // Create our transferable wrapper
        Transferable transferable = new PanelTransferable(getCard());

        // Start the "drag" process...
        DragSource ds = dge.getDragSource();
        ds.startDrag(dge, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), transferable, this);

    }

    @Override
    public void dragEnter(DragSourceDragEvent dsde) {
    }

    @Override
    public void dragOver(DragSourceDragEvent dsde) {
    }

    @Override
    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    @Override
    public void dragExit(DragSourceEvent dse) {
    }

    @Override
    public void dragDropEnd(DragSourceDropEvent dsde) {

        // If the drop was not sucessful, we need to
        // return the component back to it's previous
        // parent
        if (!dsde.getDropSuccess()) {

            getParent().add(getCard(),((CardHolder)getParent()).index);
            ((CardHolder) getParent()).getCard().addListeners();
            getParent().invalidate();
            getParent().repaint();

        }
    }
}