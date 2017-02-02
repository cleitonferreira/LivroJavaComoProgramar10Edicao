// Fig. 29.x: AddressBook.java
// A simple address book
package com.deitel.jhtp.jpa;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddressBook extends JFrame
{
   // create an EntityManagerFactory for the persistence unit
   private final EntityManagerFactory entityManagerFactory = 
      Persistence.createEntityManagerFactory("AddressBookPU");

   // create an EntityManager for interacting with the persistence unit
   private final EntityManager entityManager = 
      entityManagerFactory.createEntityManager();
   
   private List<Addresses> results;
   
   private int numberOfEntries = 0;
   private int currentEntryIndex;

   private final JButton browseButton = new JButton();
   private final JLabel emailLabel = new JLabel();
   private final JTextField emailTextField = new JTextField(10);
   private final JLabel firstNameLabel = new JLabel();
   private final JTextField firstNameTextField = new JTextField(10);
   private final JLabel idLabel = new JLabel();
   private final JTextField idTextField = new JTextField(10);
   private final JTextField indexTextField = new JTextField(2);
   private final JLabel lastNameLabel = new JLabel();
   private final JTextField lastNameTextField = new JTextField(10);
   private final JTextField maxTextField = new JTextField(2);
   private final JButton nextButton = new JButton();
   private final JLabel ofLabel = new JLabel();
   private final JLabel phoneLabel = new JLabel();
   private final JTextField phoneTextField = new JTextField(10);
   private final JButton previousButton = new JButton();
   private final JButton queryButton = new JButton();
   private final JLabel queryLabel = new JLabel();
   private final JPanel queryPanel = new JPanel();
   private final JPanel navigatePanel = new JPanel();
   private final JPanel displayPanel = new JPanel();
   private final JTextField queryTextField = new JTextField(10);
   private final JButton insertButton = new JButton();
   
   // constructor
   public AddressBook()
   {
      super("Address Book"); 
      
      setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
      setSize(400, 355);
      setResizable(false);

      navigatePanel.setLayout(
         new BoxLayout(navigatePanel, BoxLayout.X_AXIS));

      previousButton.setText("Previous");
      previousButton.setEnabled(false);
      previousButton.addActionListener(
         this::previousButtonActionPerformed); 

      navigatePanel.add(previousButton);
      navigatePanel.add(Box.createHorizontalStrut(10));

      indexTextField.setHorizontalAlignment(JTextField.CENTER);
      indexTextField.addActionListener(
         this::indexTextFieldActionPerformed);

      navigatePanel.add(indexTextField);
      navigatePanel.add(Box.createHorizontalStrut(10));

      ofLabel.setText("of");
      navigatePanel.add(ofLabel);
      navigatePanel.add(Box.createHorizontalStrut(10));

      maxTextField.setHorizontalAlignment(JTextField.CENTER);
      maxTextField.setEditable(false);
      navigatePanel.add(maxTextField);
      navigatePanel.add(Box.createHorizontalStrut(10));

      nextButton.setText("Next");
      nextButton.setEnabled(false);
      nextButton.addActionListener(this::nextButtonActionPerformed); 
      
      navigatePanel.add(nextButton);
      add(navigatePanel);

      displayPanel.setLayout(new GridLayout(5, 2, 4, 4));

      idLabel.setText("Address ID:");
      displayPanel.add(idLabel);

      idTextField.setEditable(false);
      displayPanel.add(idTextField);

      firstNameLabel.setText("First Name:");
      displayPanel.add(firstNameLabel);
      displayPanel.add(firstNameTextField);

      lastNameLabel.setText("Last Name:");
      displayPanel.add(lastNameLabel);
      displayPanel.add(lastNameTextField);

      emailLabel.setText("Email:");
      displayPanel.add(emailLabel);
      displayPanel.add(emailTextField);

      phoneLabel.setText("Phone Number:");
      displayPanel.add(phoneLabel);
      displayPanel.add(phoneTextField);
      add(displayPanel);

      queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.X_AXIS));
      queryPanel.setBorder(BorderFactory.createTitledBorder(
         "Find an entry by last name"));
      queryLabel.setText("Last Name:");
      queryPanel.add(Box.createHorizontalStrut(5));
      queryPanel.add(queryLabel);
      queryPanel.add(Box.createHorizontalStrut(10));
      queryPanel.add(queryTextField);
      queryPanel.add(Box.createHorizontalStrut(10));

      queryButton.setText("Find");
      queryButton.addActionListener(this::queryButtonActionPerformed);

      queryPanel.add(queryButton);
      queryPanel.add(Box.createHorizontalStrut(5));
      add(queryPanel);

      browseButton.setText("Browse All Entries");
      browseButton.addActionListener(this::browseButtonActionPerformed);
      add(browseButton);

      insertButton.setText("Insert New Entry");
      insertButton.addActionListener(this::insertButtonActionPerformed);
	   add(insertButton);

      addWindowListener(
         new WindowAdapter() 
         {  
            @Override
            public void windowClosing(WindowEvent evt)
            {
               System.exit(0);
            } 
         } 
      ); // end call to addWindowListener
	
      setVisible(true);
   }
   
   // handles call when previousButton is clicked
   private void previousButtonActionPerformed(ActionEvent e)
   {
      --currentEntryIndex;
      
      if (currentEntryIndex < 0)
         currentEntryIndex = numberOfEntries - 1;
      
      indexTextField.setText("" + (currentEntryIndex + 1));
      indexTextFieldActionPerformed(e);  
   } 

   // handles call when nextButton is clicked
   private void nextButtonActionPerformed(ActionEvent e) 
   {
      ++currentEntryIndex;
      
      if (currentEntryIndex >= numberOfEntries)
         currentEntryIndex = 0;
      
      indexTextField.setText("" + (currentEntryIndex + 1));
      indexTextFieldActionPerformed(e);
   }

   // handles call when queryButton is clicked
   private void queryButtonActionPerformed(ActionEvent e)
   {
      // query that returns all contacts
      TypedQuery<Addresses> findByLastname = 
         entityManager.createNamedQuery(
            "Addresses.findByLastname", Addresses.class);

      // configure parameter for query
      findByLastname.setParameter("lastname", queryTextField.getText());
      results = findByLastname.getResultList(); // get all addresses
      numberOfEntries = results.size();
      
      if (numberOfEntries != 0)
      {
         currentEntryIndex = 0;
         displayRecord();
         nextButton.setEnabled(true);
         previousButton.setEnabled(true);
      } 
      else
         browseButtonActionPerformed(e);
   } 

   // displays record at currentEntryIndex in results
   private void displayRecord() 
   {
      Addresses currentEntry = results.get(currentEntryIndex);
      idTextField.setText("" + currentEntry.getAddressid());
      firstNameTextField.setText(currentEntry.getFirstname());
      lastNameTextField.setText(currentEntry.getLastname());
      emailTextField.setText(currentEntry.getEmail());
      phoneTextField.setText(currentEntry.getPhonenumber());
      maxTextField.setText("" + numberOfEntries);
      indexTextField.setText("" + (currentEntryIndex + 1));
   }
   
   // handles call when a new value is entered in indexTextField
   private void indexTextFieldActionPerformed(ActionEvent e)
   {
      currentEntryIndex = 
         (Integer.parseInt(indexTextField.getText()) - 1);
      
      if (numberOfEntries != 0 && currentEntryIndex < numberOfEntries)
      {
         displayRecord();
      } 
   }

   // handles call when browseButton is clicked
   private void browseButtonActionPerformed(ActionEvent e)
   {
      // query that returns all contacts
      TypedQuery<Addresses> findAllAddresses = 
         entityManager.createNamedQuery(
            "Addresses.findAll", Addresses.class);

      results = findAllAddresses.getResultList(); // get all addresses
      numberOfEntries = results.size();

      if (numberOfEntries != 0)
      {
         currentEntryIndex = 0;
         displayRecord();
         nextButton.setEnabled(true);
         previousButton.setEnabled(true);
      } 
   } 

   // handles call when insertButton is clicked
   private void insertButtonActionPerformed(ActionEvent evt) 
   {
      Addresses address = new Addresses();
      address.setFirstname(firstNameTextField.getText());
      address.setLastname(lastNameTextField.getText());
      address.setPhonenumber(phoneTextField.getText());
      address.setEmail(emailTextField.getText());
      
      // get an EntityTransaction to manage insert operation
      EntityTransaction transaction = entityManager.getTransaction();
      
      try
      {
         transaction.begin(); // start transaction
         entityManager.persist(address); // store new entry
         transaction.commit(); // commit changes to the database
         JOptionPane.showMessageDialog(this, "Person added!",
            "Person added", JOptionPane.PLAIN_MESSAGE);
      }
      catch (Exception e) // if transaction failed
      {
         transaction.rollback(); // undo database operations 
         JOptionPane.showMessageDialog(this, "Person not added!",
            e.getMessage(), JOptionPane.PLAIN_MESSAGE);
      }
   }
   
   // main method
   public static void main(String args[])
   {
      new AddressBook();
   } 
} // end class AddressBookDisplay


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
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

 