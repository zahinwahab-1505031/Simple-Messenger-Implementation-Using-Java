/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
package random;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
class ReceiverThread implements Runnable {
	DatagramSocket socketReceiver;
	String name;
	String via;
	InetAddress IPAddress;
	
	Thread t;
	Scanner in = new Scanner( System.in );
	
	public ReceiverThread( DatagramSocket socketReceiver, String name, String via, InetAddress IPAddress ) {
		this.socketReceiver = socketReceiver;
		this.name = name;
		this.via = via;
		this.IPAddress = IPAddress;
		
		t = new Thread( this );
		t.start();
	}
	
	public void run() {
		
                
            
			try {
		            while(true){
                                receive();
                            }
			} catch (Exception e) {
			}
		
	}
	
	public void receive() throws Exception {
		DatagramPacket packR = new DatagramPacket( new byte[1024], 1024 );
                socketReceiver.receive(packR);
                int length = packR.getLength();
		String response = new String( packR.getData());
              String[] strArray = new String [4];
                
		String[] lines = response.split("\n");
		strArray[0] = lines[0].split(":")[1];
		strArray[1] = lines[1].split(":")[1];
		strArray[2] = lines[2].split(":")[1];
		//strArray[3]
                int var1 = lines[3].indexOf("Body: ");
                strArray[3] = lines[3].substring(var1+6);
//		int counter1 = 0;
//		for (String s : lines) {
//			String[] tokens = s.split(":");
//			strArray[counter1++] = tokens[1].substring(1, tokens[1].length());
//		}


                String response1= strArray[2]  + " says: " + strArray[3]  ;
		System.out.println( response1.trim() );
               
                 Platform.runLater(new Runnable(){

                @Override
                public void run() {
                   Text text;
                    System.out.println(response1 +" From Server");
                     if(Client.textFlow.getChildren().size()==0){
                   
              text = new Text(response1.toString()+"\n");
              text.setFill(Color.BLUE);
              text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            } else {
                text = new Text( response1.toString()+"\n");
                 text.setFill(Color.BLUE);
                 text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            }
           
            if(response1.contains(":)")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/network-and-security-icons-by-artistsvalley/png/16x16/Regular/Friend%20Smiley.png");
             
                text.setText( response1.replace(":)"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
           else if(response1.contains(":D")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/toolbar-icons/fugue-32px-additional-icons-by-yusuke-kamiyamane/png/32x32/smiley-lol.png");
             
                text.setText( response1.replace(":D"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
            else if(response1.contains(":O")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Surprised.png");
             
                text.setText( response1.replace(":O"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
             else if(response1.contains("B)")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Cool.png");
             
                text.setText( response1.replace("B)"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
               else if(response1.contains(":(")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Sad.png");
             
                text.setText( response1.replace(":("," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
               else if(response1.contains(":P")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/circular-icons-by-pro-theme-design/png/16x16/smiley_tounge.png");
             
                text.setText( response1.replace(":P"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
               else if(response1.contains(":|")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Confused.png");
             
                text.setText( response1.replace(":|"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
           else {
               
               Client.textFlow.getChildren().add(text);
            }
//                    Text a = new Text(response1.toString()+"\n");
//                    Client.textFlow.getChildren().add(a);
                }

            });
   
	}
}

class SenderThread implements Runnable {
	DatagramSocket socketSender;
	String name;
	String via;
	InetAddress IPAddress;
	int destinationPort;
	Thread t;
	Scanner in = new Scanner( System.in );
	
	public SenderThread( DatagramSocket socketSender, String name, String via, InetAddress IPAddress, int destinationPort ) throws UnknownHostException {
		this.socketSender = socketSender;
		this.name = name;
		this.via = via;
		this.IPAddress = IPAddress;
		this.destinationPort = destinationPort;
		t = new Thread( this );
		t.start();
	}
	
	public void run() {
		
			try {
                            while(true){
                                send();
                            }
				
			} catch (Exception e) {
			}
		
	}
	
	public void send() throws Exception {
            Client. textField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                Client.button.fire();
            }
        });
               Client.button.setOnAction(event->{
                 Text text;
                  String msgToBeSent= Client.textField.getText();
              StringTokenizer st = new StringTokenizer( msgToBeSent, "$" );
                String clientName = st.nextToken();
		String msg = st.nextToken(); 
            if(Client.textFlow.getChildren().size()==0){
                text = new Text(msg+"\n");
                text.setFill(Color.RED);
                text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            } 
            else {
           
              text = new Text(msg+"\n");
              text.setFill(Color.RED);
              text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            }
          
            if(msg.contains(":)")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/network-and-security-icons-by-artistsvalley/png/16x16/Regular/Friend%20Smiley.png");
                // Remove :) from text
                text.setText(text.getText().replace(":)"," "));
                Client.textFlow.getChildren().addAll(text, imageView);
            }            else if(msg.contains(":D")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/toolbar-icons/fugue-32px-additional-icons-by-yusuke-kamiyamane/png/32x32/smiley-lol.png");
             
                text.setText( msg.replace(":D"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
            else if(msg.contains(":O")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Surprised.png");
             
                text.setText( msg.replace(":O"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
             else if(msg.contains("B)")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Cool.png");
             
                text.setText( msg.replace("B)"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
             else if(msg.contains(":(")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Sad.png");
             
                text.setText( msg.replace(":("," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
              else if(msg.contains(":P")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/circular-icons-by-pro-theme-design/png/16x16/smiley_tounge.png");
             
                text.setText( msg.replace(":P"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
               else if(msg.contains(":|")) {
                ImageView imageView = new ImageView("http://files.softicons.com/download/web-icons/august-icon-set-by-austintheheller/png/32x32/Smiley%20Confused.png");
             
                text.setText( msg.replace(":|"," "));
                Client.textFlow.getChildren().addAll( text, imageView);
            }
             else {
               Client.textFlow.getChildren().add(text);
            }
            Client.textField.clear();
            Client.textField.requestFocus();  
     
               // Client.messages.appendText(msg+'\n');
		String fullMsg = "Via: " +via+'\n'+ "To: "+ clientName +'\n'+"From: "+ name +'\n'+ "Body: "+ msg;
		byte[] data = fullMsg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket( data, data.length, IPAddress, destinationPort );
                    try {
                        socketSender.send( sendPacket );
                    } catch (IOException ex) {
                        Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
            
            
            ); 
             
	}
}


class Client2  {
	String identity;
	String name;
	int clientPort;
	String serverIP;
	String via;
	Thread t;
	
	DatagramSocket socketReceiver;
	DatagramSocket socketSender;
	
	DatagramPacket sendingPack;
	InetAddress IPAddress;
	
	public Client2( String name, int clientPort, String serverIP, String via ) throws SocketException, UnknownHostException {
		this.name = name;
		this.clientPort = clientPort;
		this.serverIP = serverIP;
		this.via = via;
		this.identity = "Via: " + via +'\n'+ "To: " + via +'\n'+ "From: " + name +'\n'+ "Port: " + clientPort;
		     try {         
			IPAddress = InetAddress.getByName( serverIP );
			
			socketSender = new DatagramSocket();
			socketReceiver = new DatagramSocket( clientPort );
			byte[] data = identity.getBytes();
			sendingPack = new DatagramPacket( data, data.length, IPAddress, 5050 );
			socketSender.send( sendingPack );
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void run() {
		try {
			SenderThread senderThread = new SenderThread( socketSender, name, via, IPAddress, 5050);
			ReceiverThread receiverThread = new ReceiverThread( socketReceiver, name, via, IPAddress );
			senderThread.t.join();
			receiverThread.t.join();
		} catch (Exception e) {
			
		}
	}
	

}

public class Client extends Application implements Runnable {
    
	static TextArea messages = new TextArea();
        static TextField textField = new TextField();
        public static  String [] arguments ;
        public   static TextFlow textFlow = new TextFlow();
        static Text text;
        static Button button;
        Thread t;
        String name ;//should be passed from command line argument
	int clientPort ;//should be passed from command line argument
	String serverIP ;//should be passed from command line argument
	String via;
	Client2 client2;
	public Client() throws SocketException, UnknownHostException{
            this.name=arguments[0];
            this.clientPort=Integer.parseInt(arguments[1]);
            this.serverIP=arguments[2];
            this.via=arguments[3];
            client2 = new Client2( name, clientPort, serverIP, via );
            t = new Thread(this);
            
           
        }
	
        private Parent createContent(){
        textFlow.setPadding(new Insets(10));
        textFlow.setLineSpacing(10);
        
        button = new Button("Send");
        button.setPrefWidth(70);
        VBox container = new VBox();
        container.getChildren().addAll(textFlow, new HBox(textField, button));
        VBox.setVgrow(textFlow, Priority.ALWAYS);
        
        textField.prefWidthProperty().bind(container.widthProperty().subtract(button.prefWidthProperty()));

         return container;
           
    }
      
    @Override
    public void start(Stage primaryStage) {
         
          
          primaryStage.setScene(new Scene(createContent()));
          
          primaryStage.show();
          primaryStage.setTitle(name);
          t.start();
          
          
    }
    public void run(){
       
            client2.run();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
         arguments = args;
        
        launch(args);
    }
    
}

