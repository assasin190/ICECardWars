package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

import misc.Splash;
import misc.SplashPanel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class CardData {
	
	//MAKE TO MAP
	@SuppressWarnings("rawtypes")
	private static ArrayList<JsonObject> all_card = new ArrayList<JsonObject>();
	private static ArrayList<BufferedImage> all_image =new ArrayList<BufferedImage>();
	public static void main(String[] args){
		CardData.saveAllCardsToLocal();
	}
	/*
	public static Object[] getAllCard(){
		return all_card;
	}
	*/
	@SuppressWarnings("rawtypes")
	public static JsonObject getCardData(int ID){
		try{
			return all_card.get(ID);
		}catch(IndexOutOfBoundsException e){
			System.err.println("Card doesn't exist in local save");
		}
		return null;
	}
	 
	public static BufferedImage getCardImage(int ID){
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
		int count = 1;
		Gson gs;
		InputStream is;	
		while(true){
			String url ="http://128.199.235.83/icw/?q=icw/service/ic&ic_id="+count;
			JsonObject job = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				job = gs.fromJson(new InputStreamReader(is), JsonObject.class);
			} catch (MalformedURLException e) {e.printStackTrace();
			} catch (IOException e) {
				System.err.println("problem with the connection (retrieving JSON map)... retrying");
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
			BufferedImage b = null;
			try {
		//		System.out.println("http://128.199.235.83/icw/"+data.get("picture"));
				b = ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture").getAsString()));
			} catch (MalformedURLException e) {
				System.err.println("problem with the connection (retrieving picture)... retrying");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(data);
			Splash.setProgress("RETRIEVING DATA FROM WEB: "+data);
			all_image.add(b);
			all_card.add(data);
			count++;
		}
	//	System.out.println(all_image.toString());
	//	System.out.println(all_card.toString());
	}
}
