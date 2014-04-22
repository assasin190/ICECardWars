package main;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


public class SelectOpponent {
	ArrayList<Player> opponentList = new ArrayList<Player>();
	
	public static void main(String [] args) {
		
		try {
			saveAllOpponentsToLocal();
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void saveAllOpponentsToLocal() throws JsonSyntaxException, JsonIOException, IOException {
			
		URL opponentUrl = new URL("http://128.199.235.83/icw/?q=icw/service/opponent");
			Gson g = new Gson();
			HashMap<String, ArrayList> temp = g.fromJson(new InputStreamReader(opponentUrl.openStream()), HashMap.class );
			ArrayList<Map<String, String>> mapList = temp.get("data");
			//Not finished
		
		
	}
}
