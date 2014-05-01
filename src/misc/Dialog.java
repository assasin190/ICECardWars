package misc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class Dialog extends JDialog {
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Dialog dialog = new Dialog("OPPONENT PREPARE PHRASE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Dialog(String s) {
		lblNewLabel = new JLabel(s);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		initGUI();
	}
	private void initGUI() {
	
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 1024, 250);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		Timer timer = new Timer(1280, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                Dialog.this.setVisible(false);
                Dialog.this.dispose();
            }
        });
		timer.start();
		lblNewLabel.setForeground(new Color(0, 51, 102));
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 50));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
