// Fig. 24.28: ClientGUI.java
// ClientGUI provides a user interface for sending and receiving
// messages to and from the DeitelMessengerServer.
package com.deitel.messenger;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

public class ClientGUI extends JFrame 
{   
   private JMenu serverMenu; // for connecting/disconnecting server
   private JTextArea messageArea; // displays messages
   private JTextArea inputArea; // inputs messages
   private JButton connectButton; // button for connecting
   private JMenuItem connectMenuItem; // menu item for connecting
   private JButton disconnectButton; // button for disconnecting
   private JMenuItem disconnectMenuItem; // menu item for disconnecting
   private JButton sendButton; // sends messages
   private JLabel statusBar; // label for connection status
   private String userName; // userName to add to outgoing messages
   private MessageManager messageManager; // communicates with server
   private MessageListener messageListener; // receives incoming messages
 
   // ClientGUI constructor
   public ClientGUI( MessageManager manager ) 
   {       
      super( "Deitel Messenger" );
      
      messageManager = manager; // set the MessageManager
      
      // create MyMessageListener for receiving messages
      messageListener = new MyMessageListener(); 
      
      serverMenu = new JMenu ( "Server" ); // create Server JMenu
      serverMenu.setMnemonic( 'S' ); // set mnemonic for server menu
      JMenuBar menuBar = new JMenuBar(); // create JMenuBar
      menuBar.add( serverMenu ); // add server menu to menu bar
      setJMenuBar( menuBar ); // add JMenuBar to application
      
      // create ImageIcon for connect buttons
      Icon connectIcon = new ImageIcon( 
         getClass().getResource( "images/Connect.gif" ) );
      
      // create connectButton and connectMenuItem
      connectButton = new JButton( "Connect", connectIcon );
      connectMenuItem = new JMenuItem( "Connect", connectIcon );  
      connectMenuItem.setMnemonic( 'C' );
      
      // create ConnectListener for connect buttons
      ActionListener connectListener = new ConnectListener();
      connectButton.addActionListener( connectListener );
      connectMenuItem.addActionListener( connectListener ); 
      
      // create ImageIcon for disconnect buttons
      Icon disconnectIcon = new ImageIcon( 
         getClass().getResource( "images/Disconnect.gif" ) );
      
      // create disconnectButton and disconnectMenuItem
      disconnectButton = new JButton( "Disconnect", disconnectIcon );
      disconnectMenuItem = new JMenuItem( "Disconnect", disconnectIcon );      
      disconnectMenuItem.setMnemonic( 'D' );
      
      // disable disconnect button and menu item
      disconnectButton.setEnabled( false );
      disconnectMenuItem.setEnabled( false );
      
      // create DisconnectListener for disconnect buttons
      ActionListener disconnectListener = new DisconnectListener();
      disconnectButton.addActionListener( disconnectListener );
      disconnectMenuItem.addActionListener( disconnectListener );
      
      // add connect and disconnect JMenuItems to fileMenu
      serverMenu.add( connectMenuItem );
      serverMenu.add( disconnectMenuItem );           
  
      // add connect and disconnect JButtons to buttonPanel
      JPanel buttonPanel = new JPanel();
      buttonPanel.add( connectButton );
      buttonPanel.add( disconnectButton );
     
      messageArea = new JTextArea(); // displays messages
      messageArea.setEditable( false ); // disable editing
      messageArea.setWrapStyleWord( true ); // set wrap style to word
      messageArea.setLineWrap( true ); // enable line wrapping
      
      // put messageArea in JScrollPane to enable scrolling
      JPanel messagePanel = new JPanel();
      messagePanel.setLayout( new BorderLayout( 10, 10 ) );
      messagePanel.add( new JScrollPane( messageArea ), 
         BorderLayout.CENTER );
      
      inputArea = new JTextArea( 4, 20 ); // for entering new messages
      inputArea.setWrapStyleWord( true ); // set wrap style to word
      inputArea.setLineWrap( true ); // enable line wrapping
      inputArea.setEditable( false ); // disable editing
      
      // create Icon for sendButton
      Icon sendIcon = new ImageIcon( 
         getClass().getResource( "images/Send.gif" ) );
      
      sendButton = new JButton( "Send", sendIcon ); // create send button
      sendButton.setEnabled( false ); // disable send button
      sendButton.addActionListener(
         new ActionListener() 
         {
            // send new message when user activates sendButton
            public void actionPerformed( ActionEvent event )
            {
               messageManager.sendMessage( userName, 
                  inputArea.getText() ); // send message
               inputArea.setText( "" ); // clear inputArea
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener
      
      Box box = new Box( BoxLayout.X_AXIS ); // create new box for layout
      box.add( new JScrollPane( inputArea ) ); // add input area to box
      box.add( sendButton ); // add send button to box
      messagePanel.add( box, BorderLayout.SOUTH ); // add box to panel
      
      // create JLabel for statusBar with a recessed border
      statusBar = new JLabel( "Not Connected" );
      statusBar.setBorder( new BevelBorder( BevelBorder.LOWERED ) );

      add( buttonPanel, BorderLayout.NORTH ); // add button panel
      add( messagePanel, BorderLayout.CENTER ); // add message panel
      add( statusBar, BorderLayout.SOUTH ); // add status bar
      
      // add WindowListener to disconnect when user quits
      addWindowListener ( 
         new WindowAdapter () 
         {
            // disconnect from server and exit application
            public void windowClosing ( WindowEvent event ) 
            {
               messageManager.disconnect( messageListener );
               System.exit( 0 );
            } // end method windowClosing
         } // end anonymous inner class
      ); // end call to addWindowListener
   } // end ClientGUI constructor
   
   // ConnectListener listens for user requests to connect to server
   private class ConnectListener implements ActionListener 
   {
      // connect to server and enable/disable GUI components
      public void actionPerformed( ActionEvent event )
      {
         // connect to server and route messages to messageListener
         messageManager.connect( messageListener ); 

         // prompt for userName
         userName = JOptionPane.showInputDialog( 
            ClientGUI.this, "Enter user name:" );
         
         messageArea.setText( "" ); // clear messageArea
         connectButton.setEnabled( false ); // disable connect
         connectMenuItem.setEnabled( false ); // disable connect
         disconnectButton.setEnabled( true ); // enable disconnect
         disconnectMenuItem.setEnabled( true ); // enable disconnect
         sendButton.setEnabled( true ); // enable send button
         inputArea.setEditable( true ); // enable editing for input area
         inputArea.requestFocus(); // set focus to input area
         statusBar.setText( "Connected: " + userName ); // set text
      } // end method actionPerformed      
   } // end ConnectListener inner class
   
   // DisconnectListener listens for user requests to disconnect
   // from DeitelMessengerServer
   private class DisconnectListener implements ActionListener 
   {
      // disconnect from server and enable/disable GUI components
      public void actionPerformed( ActionEvent event )
      {
         // disconnect from server and stop routing messages
         messageManager.disconnect( messageListener );
         sendButton.setEnabled( false ); // disable send button
         disconnectButton.setEnabled( false ); // disable disconnect
         disconnectMenuItem.setEnabled( false ); // disable disconnect
         inputArea.setEditable( false ); // disable editing
         connectButton.setEnabled( true ); // enable connect
         connectMenuItem.setEnabled( true ); // enable connect
         statusBar.setText( "Not Connected" ); // set status bar text
      } // end method actionPerformed      
   } // end DisconnectListener inner class
   
   // MyMessageListener listens for new messages from MessageManager and 
   // displays messages in messageArea using MessageDisplayer.
   private class MyMessageListener implements MessageListener 
   {
      // when received, display new messages in messageArea
      public void messageReceived( String from, String message ) 
      {
         // append message using MessageDisplayer
         SwingUtilities.invokeLater( 
            new MessageDisplayer( from, message ) );
      } // end method messageReceived
   } // end MyMessageListener inner class
   
   // Displays new message by appending message to JTextArea.  Should
   // be executed only in Event thread; modifies live Swing component
   private class MessageDisplayer implements Runnable
   {
      private String fromUser; // user from which message came
      private String messageBody; // body of message
      
      // MessageDisplayer constructor
      public MessageDisplayer( String from, String body )
      {
         fromUser = from; // store originating user
         messageBody = body; // store message body
      } // end MessageDisplayer constructor
      
      // display new message in messageArea
      public void run() 
      {
         // append new message
         messageArea.append( "\n" + fromUser + "> " + messageBody );   
      } // end method run      
   } // end MessageDisplayer inner class
} // end class ClientGUI


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