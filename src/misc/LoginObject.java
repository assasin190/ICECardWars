package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class LoginObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 493524791682643916L;
	private boolean isLogin;
	private String username;
	private char[] password;
	public static void main(String[] args){
		//http://128.199.235.83/icw/?q=icw/service/login&user=595&pass=78378
		JsonParserFactory factory=JsonParserFactory.getInstance();
		JSONParser parser=factory.newJsonParser();
		String url = "http://128.199.235.83/icw/?q=icw/service/login&user=595&pass=78378";
		InputStream is;
		Map m = null;
		try {
			is = new URL(url).openStream();
			m = parser.parseJson(is, "UTF-8");
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(m.toString());
		System.out.println(m.get("status"));
	}
	public LoginObject(boolean isLogin, String username,char[] password){
		this.isLogin = isLogin;
		this.username = username;
		this.password = password;
	}
	public String getUsername(){
		return username;
	}
	public char[] getPassword(){
		return password;
	}
	public boolean isLogin(){
		return isLogin;
	}
}
