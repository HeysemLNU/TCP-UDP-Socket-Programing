package dv201.labb2;
import java.io.*;
import java.net.*;

public class TCPClient  {
   public static final String msg="The Messag is a Long and Big Message to be Read";
    public static void main(String[] args) throws IOException, InterruptedException {
        if (Integer.valueOf(args[1])<0){
            System.err.println("Port Number Error");              // Checking the proper Format
            System.exit(1);
        }
        if (Integer.valueOf(args[2])<=0){
            System.err.println("Buffer Size Error");
            System.exit(1);
        }
        if (Integer.valueOf(args[3])<0){
            System.err.println("Transfer Rate Error");
            System.exit(1);
        }
        byte [] buff=new byte[Integer.valueOf(args[2])];        // Creating the Buffer for the messages to come in
        Socket hostSock=new Socket(args[0],Integer.valueOf(args[1]));
        OutputStream outStream=hostSock.getOutputStream();           // Creating the socket and the Streams
        InputStream inputStream=hostSock.getInputStream();

        int transferRate=Integer.valueOf(args[3]);       // Get the transfer rate

        if (transferRate==0){
            long startT=System.currentTimeMillis();
            outStream.write(msg.getBytes(),0,msg.length());
            inputStream.read(buff);                                   // If the Transfer rate is 0 Iterate Once (Send And Recieve)
            long endt=System.currentTimeMillis()-startT;
            Thread.sleep(1000-endt);

        }
        else {

            long startT=System.currentTimeMillis();                    // If the Transfer rate is more then 0 Iterate that many times
            while (transferRate!=0){
                outStream.write(msg.getBytes(),0,msg.length());
                inputStream.read(buff);
                transferRate--;
            }
            long endt=System.currentTimeMillis()-startT;
            Thread.sleep(1000-endt);
            System.out.println(endt);
        }
        System.out.println(msg.getBytes().length);
        hostSock.close();                    // Close the connection
    }
}
