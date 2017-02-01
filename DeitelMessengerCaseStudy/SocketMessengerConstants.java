// Fig. 24.20: SocketMessengerConstants.java
// SocketMessengerConstants defines constants for the port numbers
// and multicast address in DeitelMessenger
package com.deitel.messenger.sockets;

public interface SocketMessengerConstants 
{   
   // address for multicast datagrams
   public static final String MULTICAST_ADDRESS = "239.0.0.1";
   
   // port for listening for multicast datagrams
   public static final int MULTICAST_LISTENING_PORT = 5555;
   
   // port for sending multicast datagrams
   public static final int MULTICAST_SENDING_PORT = 5554;
   
   // port for Socket connections to DeitelMessengerServer
   public static final int SERVER_PORT = 12345;   
   
   // String that indicates disconnect
   public static final String DISCONNECT_STRING = "DISCONNECT";

   // String that separates the user name from the message body
   public static final String MESSAGE_SEPARATOR = ">>>";

   // message size (in bytes)
   public static final int MESSAGE_SIZE = 512;
} // end interface SocketMessengerConstants


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
 