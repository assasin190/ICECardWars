package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		int[] a = {1,2,3,4,5,6,7,8,9};
		List<Integer> l = Arrays.asList(a);
		Collections.shuffle(Arrays.asList(l));
		a = l.toArray();
		System.out.println(Arrays.toString(a));
		
	}

}
