package dv201.labb2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.io.IOException;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        final int port = 9696;                       // Assighn the buffer and decide on the port to communicate with
        final int buffSize = 1000;
        final int msgSize = 10;
        if (buffSize <= 0) {
            System.err.println("Buff Size Error");
            System.exit(1);
        }
        if (buffSize < 0) {                                // Handle Errors incase of Inapropriate input
            System.err.println("Buff Port Number Error");
            System.exit(1);
        }
        if (msgSize < 0) {
            System.err.println("Buff Port Number Error");
            System.exit(1);
        }

        ServerSocket newSocket = new ServerSocket();
        SocketAddress sa = new InetSocketAddress(port);                     // Create the Socket
        newSocket.bind(sa);

        while (true) {
            Socket sock = newSocket.accept();
            TCPThread newThread = new TCPThread(sock, buffSize, msgSize);        // It will create new threads for each Conection Requests
            newThread.start();
        }

    }

    public static class TCPThread extends Thread {
        Socket clientSock;
        int buffSize;
        int msgSize;

        public TCPThread(Socket clientSock, int buffSize, int msgSize) {
            this.clientSock = clientSock;
            this.buffSize = buffSize;
            this.msgSize = msgSize;

        }

        @Override
        public void run() {

            InputStream inStream = null;
            OutputStream outStream = null;


            try {
                outStream = clientSock.getOutputStream();
                inStream = clientSock.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // While True it will recieve and Send back Data
            // Before I didnt have errors when I recived Information once and sent it back
            // After adding the while loop does recive and send back how ever it gives a lot of errors

            while (true) {
                String msgRecieved = "";
                int AmountRead = 0;
                byte[] buff = new byte[buffSize];
                do {

                    try {
                        AmountRead += inStream.read(buff, 0, buff.length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    msgRecieved += new String(buff, 0, buff.length);


                } while (buffSize <= msgSize);
                try {
                    if (AmountRead != -1) {
                        outStream.write(msgRecieved.getBytes(), 0, AmountRead);

                    } else {
                        clientSock.close();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Echo request from: " + clientSock.getInetAddress().getCanonicalHostName() + " Using Port: " + clientSock.getPort());
            }

        }


    }
}
