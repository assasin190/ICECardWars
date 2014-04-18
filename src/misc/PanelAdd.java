package misc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import main.Card;
import main.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.LinkedList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;

public class PanelAdd extends JFrame {
	private JPanel contentPane;
	LinkedList<Card> l = new LinkedList<Card>();
	private JPanel onePanel;
	private JPanel twoPanel;
	private JPanel[] grid1 = new JPanel[4];
	private JPanel[] grid2 = new JPanel[4];
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelAdd frame = new PanelAdd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PanelAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton addButton = new JButton("Add");
		panel_1.add(addButton, BorderLayout.NORTH);
		
		JButton removeButton = new JButton("Remove");
		panel_1.add(removeButton, BorderLayout.SOUTH);
		
		onePanel = new JPanel();
		panel_1.add(onePanel, BorderLayout.CENTER);
		onePanel.setLayout(new GridLayout(4, 0, 0, 0));
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onePanel.remove(Main.getSelectedCard());
				onePanel.paintAll(onePanel.getGraphics());
			}
		});
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(onePanel.getComponentCount()==4)return;
				onePanel.add(new Card(Math.random()));
				onePanel.paintAll(onePanel.getGraphics());
			}

			
		});
		
		JPanel transferPanel = new JPanel();
		contentPane.add(transferPanel);
		transferPanel.setLayout(new BoxLayout(transferPanel, BoxLayout.Y_AXIS));
		
		JButton oneTwo = new JButton("---->");
		oneTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	if(Arrays.binarySearch(onePanel.getComponents(),Main.getSelectedCard())>=0&&twoPanel.getComponentCount()!=4){
					onePanel.remove(Main.getSelectedCard());
					onePanel.paintAll(onePanel.getGraphics());
					twoPanel.add(Main.getSelectedCard());
					twoPanel.paintAll(twoPanel.getGraphics());
			//	}
			}
		});
		transferPanel.add(oneTwo);
		
		JButton TwoOne = new JButton("<----");
		TwoOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		//		if(Arrays.binarySearch(twoPanel.getComponents(),Main.getSelectedCard())>=0&&onePanel.getComponentCount()!=4){
					twoPanel.remove(Main.getSelectedCard());
					onePanel.add(Main.getSelectedCard());
					onePanel.paintAll(onePanel.getGraphics());
					twoPanel.paintAll(twoPanel.getGraphics());
		//		}
			}
		});
		transferPanel.add(TwoOne);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JButton addButton2 = new JButton("Add");
		addButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(twoPanel.getComponentCount()==4)return;
				twoPanel.add(new Card(Math.random()));
				twoPanel.paintAll(twoPanel.getGraphics());
			}
		});
		panel_2.add(addButton2, BorderLayout.NORTH);
		
		JButton removeButton2 = new JButton("Remove");
		removeButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				twoPanel.remove(Main.getSelectedCard());
				twoPanel.paintAll(twoPanel.getGraphics());
			}
		});
		panel_2.add(removeButton2, BorderLayout.SOUTH);
		
		twoPanel = new JPanel();
		panel_2.add(twoPanel);
		twoPanel.setLayout(new GridLayout(4, 0, 0, 0));
		for(int i = 0;i<grid2.length;i++){
	//		twoPanel.add(grid2[i]);
		}
	}

}
