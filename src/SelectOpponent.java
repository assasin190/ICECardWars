import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonParser;


public class SelectOpponent {
	ArrayList<Player> opponentList = new ArrayList<Player>();
	
	public static void main(String [] args) {
		
	}
	
	public static void saveAllOpponentsToLocal() {
		try {
			URL opponentUrl = new URL("http://128.199.235.83/icw/?q=icw/service/opponent");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		
	}
}
