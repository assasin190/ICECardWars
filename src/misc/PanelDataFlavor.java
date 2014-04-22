package misc;

import java.awt.datatransfer.DataFlavor;


import main.Card;

public class PanelDataFlavor extends DataFlavor {

	public static final PanelDataFlavor SHARED_INSTANCE = new PanelDataFlavor();

	public PanelDataFlavor() {

		super(Card.class, null);

	}

}