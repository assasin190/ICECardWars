package misc;

import java.awt.Panel;
import java.awt.datatransfer.DataFlavor;

import javax.swing.JPanel;

import main.Card;


public class PanelDataFlavor extends DataFlavor {

	public static final PanelDataFlavor SHARED_INSTANCE = new PanelDataFlavor();

	public PanelDataFlavor() {

		super(JPanel.class, null);

	}

}