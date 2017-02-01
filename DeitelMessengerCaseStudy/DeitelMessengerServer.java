// Fig. 24.18: DeitelMessengerServer.java
// DeitelMessengerServer is a multithreaded, socket- and 
// packet-based chat server.
package com.deitel.messenger.sockets.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import com.deitel.messenger.MessageListener;
import static com.deitel.messenger.sockets.SocketMessengerConstants.*;

public class DeitelMessengerServer implements MessageListener 
{
   private ExecutorService serverExecutor; // executor for server
   
   // start chat server
   public void startServer() 
   {
      // create executor for server runnables
      serverExecutor = Executors.newCachedThreadPool();

      try // create server and manage new clients
      {
         // create ServerSocket for incoming connections
         ServerSocket serverSocket = 
            new ServerSocket( SERVER_PORT, 100 );
         
         System.out.printf( "%s%d%s", "Server listening on port ", 
            SERVER_PORT, " ..." );
         
         // listen for clients constantly
         while ( true ) 
         {
            // accept new client connection
            Socket clientSocket = serverSocket.accept();
            
            // create MessageReceiver for receiving messages from client
            serverExecutor.execute( 
               new MessageReceiver( this, clientSocket ) );
                        
            // print connection information
            System.out.println( "Connection received from: " +
               clientSocket.getInetAddress() );
         } // end while     
      } // end try
      catch ( IOException ioException )
      {
         ioException.printStackTrace();
      } // end catch
   } // end method startServer
   
   // when new message is received, broadcast message to clients
   public void messageReceived( String from, String message ) 
   {          
      // create String containing entire message
      String completeMessage = from + MESSAGE_SEPARATOR + message;
      
      // create and start MulticastSender to broadcast messages
      serverExecutor.execute( 
         new MulticastSender( completeMessage.getBytes() ) );
   } // end method messageReceived
} // end class DeitelMessengerServer


/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
