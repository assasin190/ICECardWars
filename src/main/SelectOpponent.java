package main;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent {
	static ArrayList<Inw> opponentList = new ArrayList<Inw>();
	JFrame frame;
	
	public static void main(String [] args) {
		
		try {
			saveAllOpponentsToLocal();
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		}
		System.out
		
	}
	
	public static void saveAllOpponentsToLocal() throws JsonSyntaxException, JsonIOException, IOException {
			
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
			Inw inw = new Inw(fname, lname, LP, MP, maxDeck, fb_id, image, user_ID, 0);
			opponentList.add(inw);
		
		}
			
	}
	
}
