package main;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Action extends JPanel{
	// ADD (IC-MONSTER TO LANE)
	// USE (IC-SPELL)
	// SURRENDER
	public Action(){
		this.setVisible(true);
		JScrollPane jj = new JScrollPane(this);
	
	}
	public static void main(String[] args) {
	
		
Action a = new Action();
	
		
	}
	public void paint(Graphics g){
		g.drawRect(10, 10, 10, 10);
	}
}
