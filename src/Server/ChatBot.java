
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatBot extends   Thread{
	int nb=0 ;
	private List<Conversation> clients= new  ArrayList<Conversation>();
	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(1234)	;	
			
			while(true) {
				Socket s = ss.accept();
				nb++;
				Conversation cv = new Conversation(s,nb);
				clients.add(cv);
				cv.start();
				
			}
		}catch(Exception e) {
			
		}
		
	}
	public static void main(String[] args) throws Exception {
		  new ChatBot().start();
		
	}
	public class Conversation extends   Thread{
		private int nb ;
		public Socket s;
		public Conversation(Socket s , int nb) {
			// TODO Auto-generated constructor stub
			this.nb = nb;
			this.s=s;
		}
		void broadcastMessage(String s,int recepteur) {
			if(recepteur==-1) {
			try {
				for(Conversation client:clients) {
					if(client.s !=this.s) {
					PrintWriter pw=new PrintWriter(client.s.getOutputStream() ,true);	
					pw.println(s);}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else {
				try {
					for(Conversation client:clients) {
						if(client.nb ==recepteur) {
						PrintWriter pw=new PrintWriter(client.s.getOutputStream() ,true);	
						pw.println(s);}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				InputStream is = s.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader (isr);
				/**********************************/
				PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
				/**********************************/
				String ipserver = s.getRemoteSocketAddress().toString();
				System.out.println("Connexion du client numéro :"+nb+", IP="+ipserver);
				
				
				pw.println("Bienvenue, vous êtes le client numéro "+nb);
				while(true) {
					
					String r;
					try {
						r = br.readLine();
						if(r.contains("=>")) {
							String[] par= r.split("=>");
							String message = par[1];
							int recepteur = Integer.parseInt(par[0]);
							broadcastMessage(message,recepteur);
							
						}else
							broadcastMessage(r,-1);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						pw.println("Bad Format");
					}
					
					}
				
			} catch (IOException e) {
				
			}
			
		}
	}
	
	}


