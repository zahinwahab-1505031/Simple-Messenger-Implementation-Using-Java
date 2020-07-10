import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;

/**
 * Created by Zahin on 11/18/2016.
 */
import java.net.InetAddress;

public class Server implements Runnable {

    String via;
    DatagramSocket socketReceiver;
    DatagramSocket socketSender;
    DatagramPacket receivingPack;
    DatagramPacket sendingPack;
    String[] strArray;
    Hashtable< String, User> table;
    Thread t;

    public Server(String via) {
        this.via = via;
        table = new Hashtable();
        try {
            socketReceiver = new DatagramSocket(5050);
            socketSender = new DatagramSocket();
        } catch (SocketException e) {

        }
        t = new Thread(this);
        t.start();

    }

    public void run() {
        while (true) {
            try {
                listenReceiveandSend();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public int listenReceiveandSend() {
        try {

            receivingPack = new DatagramPacket(new byte[1024], 1024);
            strArray = new String[4];

            socketReceiver.receive(receivingPack);
            byte[] receivedData = receivingPack.getData();
            String str1 = new String(receivedData, 0, receivingPack.getLength());
            String msgToBeSent = new String(receivedData, 0, receivingPack.getLength());
            InetAddress IPAddress = receivingPack.getAddress();
            

            String[] lines = str1.split("\n");
            strArray[0] = lines[0].split(":")[1];
            strArray[1] = lines[1].split(":")[1];
            strArray[2] = lines[2].split(":")[1];
            strArray[3] = lines[3].split(":")[1];

            int counter1 = 0;
            for (String s : lines) {
                String[] tokens = s.split(":");
                strArray[counter1++] = tokens[1].substring(1, tokens[1].length());
            }
            
            if (strArray[0].equals(via) == false) {
                System.out.println("Warning: Server name mismatch. Message dropped.");
            } else if (strArray[0].equals(via) == true && strArray[1].equals(via) == true) {
                int portR = Integer.parseInt(strArray[3]);
                User user = new User(portR, IPAddress);
                table.put(strArray[2], user);
                System.out.println("new user added");

            } else if (strArray[0].equals(via) == true && !table.containsKey(strArray[1])) {

                System.out.println("Warning: Unknown recipient. Message dropped.");//add condition for port

            } else if (strArray[0].equals(via) == true && strArray[1].equals(via) == false && table.containsKey(strArray[1])) {

                byte[] sendingData = msgToBeSent.getBytes();
                User recipient = table.get(strArray[1]);
                sendingPack = new DatagramPacket(sendingData, sendingData.length, recipient.IPAddress, recipient.port);
                System.out.println("sending data packet to acknowledged user");
                socketSender.send(sendingPack);

            }

        } catch (SocketException e) {
            System.out.println(e);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            new Server(args[0]);
        } else {
            new Server("cse108");
        }
    }
}
