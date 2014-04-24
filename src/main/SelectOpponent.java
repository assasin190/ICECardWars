package main;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent extends JPanel {
	static ArrayList<Inw> opponentList;
	
	
	public SelectOpponent(){
		opponentList = new ArrayList<Inw>();
		
	}
	public static void main(String [] args) {
		
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setExtendedState(JFrame.MAXIMIZED_BOTH);
		SelectOpponent a = new SelectOpponent();
		try {
			a.saveAllOpponentsToLocal();
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
<<<<<<< HEAD
		a.SelOpt();
	//	a.adddesc();
		test.add(a);
		test.setVisible(true);
		
		
	}
	public void SelOpt(){
		JPanel pic = new JPanel();
		pic.setLayout(new GridLayout(4,15));
		this.setLayout(new BorderLayout());
		try {
			saveAllOpponentsToLocal();
			for(int i = 0;i< opponentList.size();i++){
				
				
				pic.add(new JLabel(opponentList.get(i).profile));
				
			
					
					
				
			}
			this.add(pic,BorderLayout.SOUTH);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
=======
>>>>>>> 0a00e18cf2f2ed2cd13f66433a730611f0f4174e
		
	}
	
	public void adddesc(){
		JPanel des = new JPanel();
		
		for(int i = 0;i< opponentList.size();i++){
			
			JLabel fname = new JLabel(opponentList.get(i).fname);
			JLabel lname = new JLabel(opponentList.get(i).lname);
			JLabel lp = new JLabel(opponentList.get(i).LP + "");
	//		JLabel mp = new JLabel(opponentList.get(i).M P + "");
		
						
			
		}
		
	}
	
	public void saveAllOpponentsToLocal() throws JsonSyntaxException, JsonIOException, IOException {
			
		opponentList.clear();
		URL opponentUrl = new URL("http://128.199.235.83/icw/?q=icw/service/opponent");
		Gson g = new Gson();
		HashMap<String, ArrayList<Map<String, String>>> temp = g.fromJson(new InputStreamReader(opponentUrl.openStream()), HashMap.class );			ArrayList<Map<String, String>> mapList = temp.get("data");
			
		for(int i = 0; i < mapList.size(); i++) {
			int user_ID = Integer.parseInt(mapList.get(i).get("cv_uid"));
			String fb_id = mapList.get(i).get("fb_id");
			String fname = mapList.get(i).get("firstname_en");
			String lname = mapList.get(i).get("lastname_en");
			int LP = Integer.parseInt(mapList.get(i).get("full_lp"));
			int MP = Integer.parseInt(mapList.get(i).get("full_mp"));
			int maxDeck = Integer.parseInt(mapList.get(i).get("max_deck_size"));
			URL fb_url = new URL("https://graph.facebook.com/"+mapList.get(i).get("fb_id")+"/picture");
			ImageIcon image = new ImageIcon(ImageIO.read(fb_url));
			Inw inw = new Inw(fname, lname, LP, MP, maxDeck, fb_id, user_ID);
			opponentList.add(inw);
		
		}
		
			
	}
	
}
