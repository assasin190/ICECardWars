package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class basic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		while(true){
			System.out.println((int)Math.round((Math.random()*3)));
		}
		System.out.println("{\"cv_uid\":\"516\",\"fb_id\":\"557757076\",\"firstname_en\":\"Disakorn\",\"lastname_en\":\"Suebsanguan Galassi\",\"full_lp\":\"40\",\"full_mp\":\"5\",\"max_deck_size\":\"20\"}");
		String sa_param = "LP,4";
		String param_type = sa_param.substring(0,sa_param.indexOf(','));
		int param_value = Integer.parseInt(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
		System.out.println(param_type);
		System.out.println(param_value);
		*/
		LinkedList<Integer> l = randomIndexArray(6);
		System.out.println(l);
	}
	public static LinkedList<Integer> randomIndexArray(int length){
		LinkedList<Integer> l = new LinkedList<Integer>();
		for(int i = 0;i<length;i++){
			l.add(i);
		}
		Collections.shuffle(l,new Random(System.currentTimeMillis()));
		return l;
	}
}
