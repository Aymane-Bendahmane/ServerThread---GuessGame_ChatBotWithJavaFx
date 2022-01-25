	package Server;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.ServerSocket;
	import java.net.Socket;
import java.util.Random;

	public class GuessGames  extends   Thread{
		int nb=0 ;
		boolean wins=false;
		public final int o= new Random().nextInt(100);
		@Override
		public void run() {
			try {
				ServerSocket ss = new ServerSocket(1234);
				while(true) {
					Socket s = ss.accept();
					nb++;
					new Conversation(s,nb).start();
					
				}
			}catch(Exception e) {
				
			}
			
		}
		public static void main(String[] args) throws Exception {
//			Random r = new  Random();
//			o = r.nextInt(1000);
			System.out.println("Here ");
			  new GuessGames().start();
			
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
					PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
					/**********************************/
					String ipserver = s.getRemoteSocketAddress().toString();
					System.out.println("Connexion2 du client numéro ::"+nb+", IP="+ipserver);
					pw.println("Bienvenue, Player n: "+nb+" Guess the number");
					while(true) {
						if(wins==false) {
							int  s=0;
							try {
								s = Integer.parseInt(br.readLine()); }
							catch(Exception e) {
								pw.println("Bad value");
							}
						
						if(s==o) {
							wins=true;
							pw.println("Player  : " +nb+ "is the winner");
							break;
						}else if (s<o){
							pw.println("  : The number is higher  " );
						}else
						{
							pw.println("  : The number is lower  " );
						}
						}else {
							
							pw.println("End of the game  " );
							break;
						}
						}
					
				} catch (IOException e) {
					
				}
				
			}
		}
		
		



}
