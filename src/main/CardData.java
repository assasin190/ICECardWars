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

import com.google.gson.Gson;

public abstract class CardData {
	
	//MAKE TO MAP
	@SuppressWarnings("rawtypes")
	private static ArrayList<Map> all_card = new ArrayList<Map>();
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
	public static Map getCardData(int ID){
		try{
			return all_card.get(ID);
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Card doesn't exist in local save");
		}
		return null;
	}
	public static BufferedImage getCardImage(int ID){
		try{
			return all_image.get(ID);
		}catch(ArrayIndexOutOfBoundsException e){
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
	@SuppressWarnings({ "rawtypes" })
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
			Object o = null;
			try {
				is = new URL(url).openStream();
				gs = new Gson();
				o = gs.fromJson(new InputStreamReader(is), Object.class);
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
			Map temp = (Map)o;
			if(temp.get("data").equals(false)){
				break;
			}
			Map data = (Map)temp.get("data");
			BufferedImage b = null;
			try {
				b = ImageIO.read(new URL("http://128.199.235.83/icw/"+data.get("picture")));
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
	//		System.out.println(b);
			System.out.println("RETRIEVING DATA FROM WEB: "+data);
			all_image.add(b);
			all_card.add(data);
			count++;
		}
	//	System.out.println(all_image.toString());
	//	System.out.println(all_card.toString());
	}
}
