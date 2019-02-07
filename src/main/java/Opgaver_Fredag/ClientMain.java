/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Opgaver_Fredag;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Bruger
 */
public class ClientMain {
    

    public static void main( String[] args ) throws Exception {
        picoClient03();
    }

    private static void picoClient01() throws Exception {
        String hostName = "dilbert.com";
        int portNumber = 80;
        String message = "GET /strip/2018-08-10 HTTP/1.0\r\n\r\n";
        try {
            System.out.println( "Connect to: " + hostName + ":" + portNumber );
            Socket mySocket = new Socket( hostName, portNumber );
            sendMessagePrintResult( mySocket, message );
        } catch ( Exception ex ) {
            System.out.println( "Uuups: " + ex.getLocalizedMessage() );
        }
    }

    private static void picoClient02() throws Exception {
        String hostName = "whois.iana.org";
        int portNumber = 43;
        String message = "dr.dk\r\n";
        try {
            System.out.println( "Connect to: " + hostName + ":" + portNumber );
            Socket mySocket = new Socket( hostName, portNumber );
            sendMessagePrintResult( mySocket, message );
            // Send message to server
            // print response
        } catch ( Exception ex ) {
            System.out.println( "Uuups: " + ex.getLocalizedMessage() );
        }
    }
    
    private static void picoClient03() throws Exception {
        String hostName = "localhost";
        int portNumber = 8080;
        try {
            System.out.println( "Connect to: " + hostName + ":" + portNumber );
            Socket mySocket = new Socket( hostName, portNumber );
            
            ExecutorService clientJack = Executors.newFixedThreadPool( 8);
            System.out.println( "Main starts" );
            for (int count = 0; count < 25; count++) {
                Runnable task = new ClientTask(count);
                clientJack.submit(task);
            }
            System.out.println("Main is done");
            clientJack.shutdown();
        } catch ( Exception ex ) {
            System.out.println( "Uuups: " + ex.getLocalizedMessage() );
        }
    }

    private static void sendMessagePrintResult( Socket mySocket, String message ) throws IOException {
        PrintWriter out = new PrintWriter( mySocket.getOutputStream(), true );
        BufferedReader in = new BufferedReader(
                new InputStreamReader( mySocket.getInputStream() ) );
        // Send message to server
        out.println( message );
        // print response
        System.out.println( "------" );
        String line;
        while ( ( line = in.readLine() ) != null ) {
            System.out.println( line );
        }
    }
}
    
    class ClientTask implements Runnable {
        
        private int count = 0;
        
        ClientTask (int cnt ) {
            count = cnt;
        }
        
        
        @Override
        public void run() {
            try {
                Socket mySocket = new Socket("localhost", 8080);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream()));
                
                System.out.println("ClientTask sent: Task: " + count);
                out.write("Task: " + count);
                out.newLine();
                out.flush();
                
                Thread.sleep(200);
                
            } catch (UnknownHostException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
        }
    }


