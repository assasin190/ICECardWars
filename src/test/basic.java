package test;

public class basic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sa_param = "LP,4";
		String param_type = sa_param.substring(0,sa_param.indexOf(','));
		int param_value = Integer.parseInt(sa_param.substring(sa_param.indexOf(',')+1,sa_param.length()));
		System.out.println(param_type);
		System.out.println(param_value);
	}

}
