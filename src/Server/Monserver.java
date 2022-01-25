package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Monserver extends   Thread{
	int nb=0 ;
	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(1234)	;	
			
			while(true) {
				Socket s = ss.accept();
				nb++;
				new Conversation(s,nb).start();
				
			}
		}catch(Exception e) {
			
		}
		
	}
	public static void main(String[] args) throws Exception {
		  new Monserver().start();
		
	}
	public class Conversation extends   Thread{
		private int nb ;
		private Socket s;
		public Conversation(Socket s , int nb) {
			// TODO Auto-generated constructor stub
			this.nb = nb;
			this.s=s;
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
					String s = br.readLine();
					if(s!=null){
						String rep="Size="+s.length();
						pw.println("Requet : " +  rep);
				}
					}
				
			} catch (IOException e) {
				
			}
			
		}
	}
	
	}


