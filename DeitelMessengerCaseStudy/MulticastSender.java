// Fig. 24.23: MulticastSender.java
// MulticastSender broadcasts a chat message using a multicast datagram.
package com.deitel.messenger.sockets.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static com.deitel.messenger.sockets.SocketMessengerConstants.*;

public class MulticastSender implements Runnable
{   
   private byte[] messageBytes; // message data
   
   public MulticastSender( byte[] bytes ) 
   { 
      messageBytes = bytes; // create the message
   } // end MulticastSender constructor

   // deliver message to MULTICAST_ADDRESS over DatagramSocket
   public void run() 
   {
      try // deliver message
      {         
         // create DatagramSocket for sending message
         DatagramSocket socket = 
            new DatagramSocket( MULTICAST_SENDING_PORT );
         
         // use InetAddress reserved for multicast group
         InetAddress group = InetAddress.getByName( MULTICAST_ADDRESS );
         
         // create DatagramPacket containing message
         DatagramPacket packet = new DatagramPacket( messageBytes, 
            messageBytes.length, group, MULTICAST_LISTENING_PORT );
         
         socket.send( packet ); // send packet to multicast group
         socket.close(); // close socket
      } // end try
      catch ( IOException ioException ) 
      { 
         ioException.printStackTrace();
      } // end catch
   } // end method run
} // end class MulticastSender


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
