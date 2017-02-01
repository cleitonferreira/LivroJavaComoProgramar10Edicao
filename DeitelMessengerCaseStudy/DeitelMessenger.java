// Fig. 24.29: DeitelMessenger.java
// DeitelMessenger is a chat application that uses a ClientGUI
// and SocketMessageManager to communicate with DeitelMessengerServer.
package com.deitel.messenger.sockets.client;

import com.deitel.messenger.MessageManager;
import com.deitel.messenger.ClientGUI;

public class DeitelMessenger
{   
   public static void main( String args[] ) 
   {
      MessageManager messageManager; // declare MessageManager
      
      if ( args.length == 0 )
         // connect to localhost
         messageManager = new SocketMessageManager( "localhost" );
      else
         // connect using command-line arg
         messageManager = new SocketMessageManager( args[ 0 ] );  
      
      // create GUI for SocketMessageManager
      ClientGUI clientGUI = new ClientGUI( messageManager );
      clientGUI.setSize( 300, 400 ); // set window size
      clientGUI.setResizable( false ); // disable resizing
      clientGUI.setVisible( true ); // show window
   } // end main
} // end class DeitelMessenger


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
