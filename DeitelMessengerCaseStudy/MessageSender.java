// Fig. 24.26: MessageSender.java
// Sends a message to the chat server in a separate Runnable.
package com.deitel.messenger.sockets.client;

import java.io.IOException;
import java.util.Formatter;
import java.net.Socket;

import static com.deitel.messenger.sockets.SocketMessengerConstants.*;

public class MessageSender implements Runnable 
{
   private Socket clientSocket; // Socket over which to send message
   private String messageToSend; // message to send

   public MessageSender( Socket socket, String userName, String message ) 
   {
      clientSocket = socket; // store Socket for client
      
      // build message to be sent
      messageToSend = userName + MESSAGE_SEPARATOR + message;
   } // end MessageSender constructor
   
   // send message and end
   public void run() 
   {
      try // send message and flush formatter
      {     
         Formatter output =
            new Formatter( clientSocket.getOutputStream() );
         output.format( "%s\n", messageToSend ); // send message
         output.flush(); // flush output
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end method run
} // end class MessageSender


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
