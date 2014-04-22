package misc;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


import main.Card;

public class PanelTransferable implements Transferable {

    private DataFlavor[] flavors = new DataFlavor[]{PanelDataFlavor.SHARED_INSTANCE};
    private Card card;

    public PanelTransferable(Card panel) {
        this.card = panel;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return flavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {

        // Okay, for this example, this is over kill, but makes it easier
        // to add new flavor support by subclassing
        boolean supported = false;

        for (DataFlavor mine : getTransferDataFlavors()) {

            if (mine.equals(flavor)) {

                supported = true;
                break;

            }

        }

        return supported;

    }

    public Card getPanel() {

        return card;

    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

        Object data = null;
        if (isDataFlavorSupported(flavor)) {

            data = getPanel();

        } else {

            throw new UnsupportedFlavorException(flavor);

        }

        return data;

    }

}