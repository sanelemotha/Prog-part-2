package progassignment;

import javax.swing.*;
import java.util.*;

public class sendingmessages {

    // Generate 10-digit unique ID
    private static String generateUniqueId() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10)); // Generates a digit 0-9
        }
        return id.toString();
    }

    public void sendMessage() {
        int messageCounter = 0;
        
        // Get number of messages
        String numInput = JOptionPane.showInputDialog(null, "How many messages do you want to send?");
        if (numInput == null) return; // User cancelled
        
        int numOfMes = Integer.parseInt(numInput);

        // Get the recipient number using phone number validation
        String recipient;
        while (true) {
            recipient = JOptionPane.showInputDialog(null, "Enter recipient's phone number (+27...)");
            if (recipient == null) {
                JOptionPane.showMessageDialog(null, "Operation cancelled by user.");
                return;
            }
            
            // Check if the recipient starts with +27 and is 13 characters long
            if (recipient.matches("\\+27\\d{9}")) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect phone number format. Example: +27821234567",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (int i = 0; i < numOfMes; i++) {
            messageCounter++;
            JOptionPane.showMessageDialog(null, "This is message " + messageCounter);

            // Message input and validation
            String message;
            while (true) {
                message = JOptionPane.showInputDialog(null, "Enter your message (max 250 characters):");
                if (message == null) {
                    // User cancelled, skip this message
                    JOptionPane.showMessageDialog(null, "Message cancelled. Moving to next message.");
                    break;
                }
                
                if (message.length() <= 250 && !message.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Message content captured!");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid message of less than 250 characters.");
                }
            }
            
            // Skip if user cancelled message input
            if (message == null) {
                continue;
            }

            // Generate Unique ID
            String messageId = generateUniqueId();

            // Send options - ADDED "Store Message" OPTION
            String[] options = {"Send Message", "Store Message", "Disregard Message"};
            int choice = JOptionPane.showOptionDialog(null,
                    """
                    Choose an option for your message:
                    
                    Message: """ + message + "\n" +
                    "Message ID: " + messageId + "\n" +
                    "Recipient: " + recipient,
                    "Message Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0: // Send Message
                    JOptionPane.showMessageDialog(null, 
                        """
                        === MESSAGE SENT ===
                        
                        Message ID: """ + messageId + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + message + "\n\n" +
                        "Number of messages sent: " + messageCounter);

                    // Generate hash & store in arrays using the new method
                    Message temp = new Message(messageId, recipient, message);
                    String hash = temp.createMessageHash();
                    Message.addSentMessage(messageId, recipient, message, hash);
                    break;

                case 1: // Store Message - NEW OPTION
                    // Create Message object and call storeMessage() which handles JSON
                    Message storeTemp = new Message(messageId, recipient, message);
                    storeTemp.storeMessage(); // This will show the JSON dialog
                    
                    // Also add to stored messages array
                    String storeHash = storeTemp.createMessageHash();
                    Message.addStoredMessage(messageId, recipient, message, storeHash);
                    
                    JOptionPane.showMessageDialog(null, 
                        """
                        === MESSAGE STORED ===
                        
                        Message ID: """ + messageId + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + message + "\n\n" +
                        "Message has been stored successfully!");
                    break;

                case 2: // Disregard Message
                    JOptionPane.showMessageDialog(null, "Message " + messageCounter + " has been disregarded.");
                    Message.addDisregardedMessage(messageId, message);
                    break;
                    
                default: // User closed dialog
                    JOptionPane.showMessageDialog(null, "Operation cancelled for message " + messageCounter);
                    break;
            }
        }
        
        // Show final summary
        JOptionPane.showMessageDialog(null, 
            """
            === MESSAGING COMPLETE ===
            
            Total messages processed: """ + messageCounter + "\n" +
            "Recipient: " + recipient + "\n\n" +
            "Thank you for using the messaging system!");
    }
}