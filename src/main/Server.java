package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import misc.LoginObject;

public class Server {
	static ArrayList<ObjectOutputStream> clientList;
	static ArrayList<Integer> clientID;
	static ArrayList<ClientRunnable> clientData;
	static ArrayList<String> clientName;
	static final int POOL_SIZE = 10;
	static Executor execs = Executors.newFixedThreadPool(POOL_SIZE);
	static int numClients = 0;
	static Object inputLine;
	public static void main(String [] args) throws IOException{
		clientList = new ArrayList<ObjectOutputStream>();
		clientID = new ArrayList<Integer>();
		clientData = new ArrayList<ClientRunnable>();
		clientName = new ArrayList<String>();
		ServerSocket soc = new ServerSocket(12345);
		while(true){
			System.out.println("Waiting for a Client ...");
			Socket con = soc.accept();
		//	System.out.println(clientList.toString());
			numClients++;
			Runnable r = new ClientRunnable(numClients,con);
			execs.execute(r);
		}
	}
}

class ClientRunnable implements Runnable{
	boolean isLoggedIn;
	String username;
	int id;
	Socket con;
	final int NUM_REPEAT = 5;
	public ClientRunnable(int id,Socket con){
		username = "Guest";
		isLoggedIn = false;
		this.id = id;
		this.con = con;
		System.out.println("Client #"+id+" is connected.");
		Server.clientID.add(id);
		Server.clientData.add(this);
	}
	
	public String echo(String msg){
		return "Client #"+id+": "+msg;
	}
	public void broadcastPlayerList(){
		String[] s = new String[Server.clientName.size()];
		int i = 0;
		for(String t:Server.clientName){
			s[i] = t;
			i++;
		}
    	for(ObjectOutputStream p:Server.clientList){
    		try {
				p.writeObject(s);
			} catch (IOException e) {System.err.println("Server has problem writing to client");e.printStackTrace();	}
    	}
	}
	public void broadcastObj(Object o){
    	for(ObjectOutputStream p:Server.clientList){
    		try {
				p.writeObject(o);
			} catch (IOException e) {
				System.err.println("Server has problem writing to client");
				e.printStackTrace();
			}
    	}
	}
	public void broadcast(String msg){
    	for(ObjectOutputStream p:Server.clientList){
    		try {
				p.writeObject(msg);
			} catch (IOException e) {
				System.err.println("Server has problem writing to client");
				e.printStackTrace();
			}
    	}
	}
	@Override
	public void run() {
		boolean stillOn = true;
		while(stillOn){
			try {
	            ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
	            Server.clientList.add(out);
	            broadcast("SERVER: Client #"+id+" is connected.");
	            ObjectInputStream in = new ObjectInputStream(con.getInputStream());
	            try {
					while ((Server.inputLine = in.readObject()) != null) {
						if(Server.inputLine instanceof String){
					        System.out.println("Client #"+id+" says:"+Server.inputLine);
					        
					        if(Server.inputLine.equals("\\clientid")){
					        	out.writeObject("\\clientid"+id);
					        }else{
					        	broadcast(username+": "+Server.inputLine);
					        }
						}
						else if(Server.inputLine instanceof LoginObject){
							LoginObject o = (LoginObject)Server.inputLine;
							if(o.isLogin()){
								// REQUEST LOGIN
								if(Arrays.equals(new char[]{'1','2','3','4','5','6'},o.getPassword())){
									out.writeObject(true);
									isLoggedIn = true;
									username = o.getUsername();
									broadcast("SERVER: "+username+" has logged in");
									Server.clientName.add(username);
									broadcastPlayerList();
								}
								else out.writeObject(false);
							}else{
								// REQUEST LOGOUT
								isLoggedIn = false;
								broadcast("SERVER: "+username+" has logged out");
								Server.clientName.remove(username);
								username = "Guest";
								out.writeObject(true);
							
								broadcastPlayerList();
							}

						}
						
						
						//TODO: deal with non-string input
					    
					    
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println("Client#"+id+" has left.");
	            out.close();
	            in.close();
	            con.close();
	        } catch (IOException e) {
	   //         System.err.println("Problem with Communication Server");
	            stillOn = false;
	        }
        }
		System.out.println("Client #"+id+" has disconnected.");
	//	System.out.println(id+" INT: "+Server.clientID.toString());
		for(int i=0;true;i++){
			if(Server.clientID.get(i) == id){
				Server.clientList.remove(i);
				Server.clientID.remove(i);
				Server.clientData.remove(i);
				broadcast("Client #"+id+" has left.");
				return;
			}
		}
		
	}	
}
