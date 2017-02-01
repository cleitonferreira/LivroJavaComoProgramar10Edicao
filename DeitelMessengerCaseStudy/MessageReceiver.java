// Fig. 24.22: MessageReceiver.java
// MessageReceiver is a Runnable that listens for messages from a 
// particular client and delivers messages to a MessageListener.
package com.deitel.messenger.sockets.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

import com.deitel.messenger.MessageListener;
import static com.deitel.messenger.sockets.SocketMessengerConstants.*;

public class MessageReceiver implements Runnable
{
   private BufferedReader input; // input stream
   private MessageListener messageListener; // message listener
   private boolean keepListening = true; // when false, ends runnable
   
   // MessageReceiver constructor
   public MessageReceiver( MessageListener listener, Socket clientSocket ) 
   {
      // set listener to which new messages should be sent
      messageListener = listener;
      
      try 
      {
         // set timeout for reading from client
         clientSocket.setSoTimeout( 5000 ); // five seconds
         
         // create BufferedReader for reading incoming messages
         input = new BufferedReader( new InputStreamReader( 
            clientSocket.getInputStream() ) );
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } // end catch
   } // end MessageReceiver constructor
   
   // listen for new messages and deliver them to MessageListener
   public void run() 
   {    
      String message; // String for incoming messages
      
      // listen for messages until stopped
      while ( keepListening ) 
      {   
         try 
         {            
            message = input.readLine(); // read message from client
         } // end try
         catch ( SocketTimeoutException socketTimeoutException ) 
         {
            continue; // continue to next iteration to keep listening
         } // end catch
         catch ( IOException ioException ) 
         {
            ioException.printStackTrace();            
            break;
         } // end catch

         // ensure non-null message
         if ( message != null ) 
         {
            // tokenize message to retrieve user name and message body
            StringTokenizer tokenizer = new StringTokenizer( 
               message, MESSAGE_SEPARATOR );

            // ignore messages that do not contain a user
            // name and message body
            if ( tokenizer.countTokens() == 2 ) 
            {
               // send message to MessageListener
               messageListener.messageReceived( 
                  tokenizer.nextToken(), // user name
                  tokenizer.nextToken() ); // message body
            } // end if
            else
            {
               // if disconnect message received, stop listening
               if ( message.equalsIgnoreCase(
                  MESSAGE_SEPARATOR + DISCONNECT_STRING ) ) 
                  stopListening();
            } // end else
         } // end if
      } // end while  
      
      try
      {         
         input.close(); // close BufferedReader (also closes Socket)
      } // end try
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();     
      } // end catch 
   } // end method run
   
   // stop listening for incoming messages
   public void stopListening() 
   {
      keepListening = false;
   } // end method stopListening
} // end class MessageReceiver


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
