package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class Inw extends JPanel{
	
	String fname;
	String lname;
	int LP;
	int MP;
	int maxDeck;
	String fb_id;
	ImageIcon profile;
	int user_ID;
	private int user_pw;
	public Inw(String fname,String lname,int LP,int MP,int maxDeck,String fb_id,ImageIcon profile,int user_ID,int user_PW){
		this.fname = fname;
		this.lname = lname;
		this.LP = LP;
		this.MP = MP;
		this.maxDeck = maxDeck;
		this.fb_id = fb_id;
		this.profile = profile;
		this.user_ID = user_ID;
		this.user_pw = user_PW;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
	}
	public String toString(){
		return "FIRSTNAME: "+fname+",LASTNAME: "+lname+",LP: "+LP+",MP: "+MP+",MAXDECK: "+",IMAGE: "+profile.toString(); 
	}
	public int getID(){
		return user_ID;
	}
	public int getPW(){
		return user_pw;
	}
}
