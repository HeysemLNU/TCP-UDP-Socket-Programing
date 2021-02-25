/*
  UDPEchoClient.java
  A simple echo client with no error handling
*/

package dv201.labb2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class UDPEchoClient {
    //public static final int BUFSIZE= 1024;
    public static final int MYPORT= 0;
    public static final String MSG= "The Messag is a Long and Big Message to be Read ";

    public static void main(String[] args) throws IOException {
	byte[] buf= new byte[Integer.valueOf(args[3])];
	if (args.length != 4) {
	    System.err.printf("usage: %s server_name port\n", args[1]);
	    System.exit(1);
	}
	if (Integer.valueOf(args[1])<0){
	    System.err.println("Port Number Cannot be Negative");
        System.exit(1);
    }
    if (Integer.valueOf(args[2])<0){
        System.err.println("Time Rate Cannot be Negative");
        System.exit(1);
    }
    if (Integer.valueOf(args[3])<0){
        System.err.println("Buffer size can not be Negative");
        System.exit(1);
    }
	
	/* Create socket */
	DatagramSocket socket= new DatagramSocket(null);
	
	/* Create local endpoint using bind() */
	SocketAddress localBindPoint= new InetSocketAddress(MYPORT);
	socket.bind(localBindPoint);
	
	/* Create remote endpoint */
	SocketAddress remoteBindPoint=
	    new InetSocketAddress(args[0],
				  Integer.valueOf(args[1]));
	
	/* Create datagram packet for sending message */
	DatagramPacket sendPacket=
	    new DatagramPacket(MSG.getBytes(),
			       MSG.length(),
			       remoteBindPoint);
	
	/* Create datagram packet for receiving echoed message */
	DatagramPacket receivePacket= new DatagramPacket(buf, buf.length);
	
	/* Send and receive message*/

            if (Integer.valueOf(args[2])==0){            // Condition if rate is 0

                try {
                    socket.send(sendPacket);
                    socket.receive(receivePacket);        // Loop Once
                    Thread.sleep((long) (1000-0.5));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                int timeDevider=Integer.valueOf(args[2]);
            int x=Integer.valueOf(args[2]);
            long processTime= (long) (0.5*timeDevider); // Take aproximate time on which the total amount process time it would take
            long startTime=System.currentTimeMillis();
            while (x!=0){
                try {
                    socket.send(sendPacket);
                    socket.receive(receivePacket);
                    Thread.sleep((1000-processTime)/timeDevider);  // We aproximate the amount of time the thread should sleep assuming each process time is equal
                    String receivedString=
                            new String(receivePacket.getData(),
                                    receivePacket.getOffset(),
                                    receivePacket.getLength());
                    if (receivedString.compareTo(MSG) == 0)
                        System.out.printf("%d bytes sent and received\n", receivePacket.getLength());
                    else
                        System.out.printf("Sent and received msg not equal!\n");


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                x--;
            }
            long endTime=System.currentTimeMillis()-startTime;
                System.out.println("Time it took in ms: "+endTime);
            }



	socket.close();
    }
}