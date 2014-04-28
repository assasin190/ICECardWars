package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import misc.Splash;
import misc.SplashPanel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public abstract class CardData {
	
	private static ArrayList<String> sa_code = new ArrayList<String>();
	private static ArrayList<String> spell_code = new ArrayList<String>();
	private static ArrayList<JsonObject> all_card = new ArrayList<JsonObject>();
	private static ArrayList<ImageIcon> all_image =new ArrayList<ImageIcon>();
	public static void main(String[] args){
		CardData.saveAllCardsToLocal();
	//	while(true){
	//		System.out.println(chance(0.15));
	//	}
	}
	public static String getSaCode(int ID){
		return sa_code.get(ID);
	}
	public static String getSpellCode(int ID){
		return spell_code.get(ID);
	}
	public static JsonObject getCardData(int ID){
		try{
			return all_card.get(ID);
		}catch(IndexOutOfBoundsException e){
			System.err.println("Card doesn't exist in local save");
		}
		return null;
	}

	public static boolean chance(double chance){
		return (Math.random()<chance);
	}
	
	public static ImageIcon getCardImage(int ID){
		try{
			return all_image.get(ID);
		}catch(IndexOutOfBoundsException e){
			System.err.println("Card doesn't exist in local save");
		}
		return null;
	}
	public static int getNumberOfCards(){
		return all_card.size()-1;
	}
	/*
	public static Object[] allCardData(){
		return all_card;
	}
	*/
	public static void saveAllCardsToLocal(){
		all_card.clear();
		all_image.clear();
		all_card.add(null);
		all_image.add(null);
		sa_code.add(null);
		spell_code.add(null);
		int count = 1;
		Gson gs;
		InputStream is;		
		while(true){
			String url ="http://128.199.235.83/icw/?q=icw/service/ic&ic_id="+count;
			SplashPanel.setProgress("RETRIEVING DATA: IC NO."+count+"        ");
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("problem with the connection (retrieving JSON map)... retrying");
				SplashPanel.setProgress("Could not connect to server, retrying...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}	
			if(job.get("data").toString().equals("false")){
				break;
			}
			JsonObject data = job.getAsJsonObject("data");
	//		System.out.println("DATA: "+data);
			ImageIcon b = null;
			try {
		//		System.out.println("http://128.199.235.83/icw/"+data.get("picture"))
				SplashPanel.setProgress("RETRIEVING DATA: IC NO."+count+" PICTURE");
				b = new ImageIcon(ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture").getAsString())));
			} catch (MalformedURLException e) {

			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("problem with the connection (retrieving picture)... retrying");
				SplashPanel.setProgress("Could not connect to server, retrying...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
			System.out.println(data);
		//	SplashPanel.setProgress("RETRIEVING DATA FROM WEB: "+data);
			all_image.add(b);
			all_card.add(data);
			count++;
		}
		
		//GET SPELL CODE
		while(true){
			String url ="http://128.199.235.83/icw/?q=icw/service/spell";
			SplashPanel.setProgress("RETRIEVING DATA: SPELL DATA");
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("problem with the connection (retrieving spell code)... retrying");
				SplashPanel.setProgress("Could not connect to server, retrying...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
			Type listType = new TypeToken<List<JsonObject>>(){}.getType();
			List<JsonObject> spell_code_json = new Gson().fromJson(job.get("data"), listType);
			for(JsonObject js:spell_code_json){
				spell_code.add(js.get("spell_desc").getAsString());
				
			}
			break;
		}
//		System.out.println(spell_code);
		//GET SA CODE
		while(true){
			String url ="http://128.199.235.83/icw/?q=icw/service/sa";
			SplashPanel.setProgress("RETRIEVING DATA: SA DATA");
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("problem with the connection (retrieving sa code)... retrying");
				SplashPanel.setProgress("Could not connect to server, retrying...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
			Type listType = new TypeToken<List<JsonObject>>(){}.getType();
			List<JsonObject> spell_code_json = new Gson().fromJson(job.get("data"), listType);
			for(JsonObject js:spell_code_json){
				sa_code.add(js.get("sa_desc").getAsString());
				
			}
			break;
		}
//		System.out.println(sa_code);
	}
}
