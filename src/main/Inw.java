package main;

import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Inw extends JPanel{
	
	String fname;
	String lname;
	int LP;
	int MP;
	int maxDeck;
	Image profile;
	
	public Inw(String fname,String lname,int LP,int MP,int maxDeck,Image profile){
		this.fname = fname;
		this.lname = lname;
		this.LP = LP;
		this.MP = MP;
		this.maxDeck = maxDeck;
		this.profile = profile;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel profile_l = new JLabel("");
		add(profile_l);
		
	}
	public String toString(){
		return "FIRSTNAME: "+fname+",LASTNAME: "+lname+",LP: "+LP+",MP: "+MP+",MAXDECK: "+maxDeck+",IMAGE: "+profile.toString(); 
	}
}
