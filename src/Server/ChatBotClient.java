package Server;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChatBotClient extends Application{
	PrintWriter pw;
	 public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
	// TODO Auto-generated method stub
    primaryStage.setTitle("Hello World");
    BorderPane borderpane= new BorderPane();
    Label labelHost = new Label("Host");
    TextField text1 = new TextField("localhost");
    Label labelPort = new Label("Port");
    TextField text2 = new TextField("1234");
    Button btnC = new Button("Connecter");
    ObservableList<String> listmodel = FXCollections.observableArrayList();
    ListView<String> listView = new ListView<String>(listmodel);
    HBox hbox= new HBox();
    	hbox.setSpacing(10);
    	hbox.setPadding(new Insets(10));
    	hbox.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
    	
    	
    	hbox.getChildren().addAll(labelHost,text1,labelPort,text2,btnC);
    	borderpane.setTop(hbox);
    	
    	VBox hbox2 = new VBox();hbox2.setSpacing(10);hbox2.setPadding(new Insets(10));
    	hbox2.getChildren().add(listView);
    	
    	
    	borderpane.setCenter(hbox2);
    	Label send = new Label("Message");
    	TextField text3 = new TextField();text3.setPrefSize(300, 40);
    	Button btnS = new Button("Send");
    	 
    	HBox hbox3 = new HBox(); hbox3.setSpacing(10);hbox3.setPadding(new Insets(10));
    	hbox3.getChildren().addAll(send,text3,btnS);
    	borderpane.setBottom(hbox3);
    	Scene scene = new Scene(borderpane,800,600);
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	btnC.setOnAction((evt)->{
    		String host = text1.getText();
    		String port = text2.getText();
    		try {
				Socket socket = new Socket(host,Integer.parseInt(port));
				
				InputStream is = socket.getInputStream();
				InputStreamReader  isr = new InputStreamReader(is);
				BufferedReader bf = new BufferedReader(isr);
				
				 pw = new PrintWriter(socket.getOutputStream(),true);
				
				new Thread(()->{
					try {
						while(true) {
							String reponse = bf.readLine();
							Platform.runLater(()->{
								listmodel.add(reponse);
							});
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}).start(); 
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    		
    		
    	});
    	btnS.setOnAction((evt)->{
    		String ms= text3.getText();
    		pw.println(ms);
    	});
	}

}
