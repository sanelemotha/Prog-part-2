package progassignment;

import javax.swing.*;

public class recentmessages {
    
    public void displayMenu() {
        while (true) {
            String[] options = {
                "Display sender and recipient of all sent messages",
                "Display longest sent message", 
                "Search for message by ID",
                "Search messages by recipient",
                "Delete message by hash",
                "Display full message report",
                "Exit"
            };
            
            int choice = JOptionPane.showOptionDialog(null,
                "Recent Messages Menu\nChoose an option:",
                "Message Management System",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
            
            switch (choice) {
                case 0:
                    displaySendersAndRecipients();
                    break;
                case 1:
                    displayLongestMessage();
                    break;
                case 2:
                    searchMessageByID();
                    break;
                case 3:
                    searchMessagesByRecipient();
                    break;
                case 4:
                    deleteMessageByHash();
                    break;
                case 5:
                    displayFullReport();
                    break;
                case 6:
                default:
                    JOptionPane.showMessageDialog(null, "Returning to main menu.");
                    return;
            }
        }
    }
    
    private void displaySendersAndRecipients() {
        if (Message.sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("All Sent Messages - Senders and Recipients:\n\n");
        for (int i = 0; i < Message.sentMessages.size(); i++) {
            sb.append("Message ").append(i + 1).append(":\n");
            sb.append("  Message ID: ").append(Message.messageIDs.get(i)).append("\n");
            sb.append("  Recipient: ").append(Message.recipients.get(i)).append("\n");
            sb.append("  Message: ").append(Message.sentMessages.get(i)).append("\n\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString());
    }
    
    private void displayLongestMessage() {
        if (Message.sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        
        String longestMessage = "";
        for (String message : Message.sentMessages) {
            if (message.length() > longestMessage.length()) {
                longestMessage = message;
            }
        }
        
        JOptionPane.showMessageDialog(null, 
            """
            Longest Sent Message:
            
            Length: """ + longestMessage.length() + " characters\n" +
            "Message: " + longestMessage);
    }
    
    private void searchMessageByID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (searchID == null || searchID.trim().isEmpty()) {
            return;
        }
        
        int index = Message.messageIDs.indexOf(searchID);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Message ID '" + searchID + "' not found.");
            return;
        }
        
        String message = Message.sentMessages.get(index);
        String hash = Message.messageHashes.get(index);
        String recipient = Message.recipients.get(index);
        
        JOptionPane.showMessageDialog(null,
            """
            Message Found:
            
            Message ID: """ + searchID + "\n" +
            "Recipient: " + recipient + "\n" +
            "Message Hash: " + hash + "\n" +
            "Message: " + message);
    }
    
    private void searchMessagesByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number to search:");
        if (recipient == null || recipient.trim().isEmpty()) {
            return;
        }
        
        StringBuilder sb = new StringBuilder("Messages sent to " + recipient + ":\n\n");
        boolean found = false;
        
        for (int i = 0; i < Message.recipients.size(); i++) {
            if (Message.recipients.get(i).equals(recipient)) {
                found = true;
                sb.append("Message ID: ").append(Message.messageIDs.get(i)).append("\n");
                sb.append("Message: ").append(Message.sentMessages.get(i)).append("\n");
                sb.append("Hash: ").append(Message.messageHashes.get(i)).append("\n\n");
            }
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient: " + recipient);
        } else {
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
    
    private void deleteMessageByHash() {
        String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) {
            return;
        }
        
        int index = Message.messageHashes.indexOf(hashToDelete);
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Message with hash '" + hashToDelete + "' not found.");
            return;
        }
        
        // Store the details before deletion for confirmation
        String messageID = Message.messageIDs.get(index);
        String messageText = Message.sentMessages.get(index);
        String recipient = Message.recipients.get(index);
        
        // Remove from all arrays
        Message.sentMessages.remove(index);
        Message.messageIDs.remove(index);
        Message.messageHashes.remove(index);
        Message.recipients.remove(index);
        
        JOptionPane.showMessageDialog(null,
            """
            Message successfully deleted!
            
            Message ID: """ + messageID + "\n" +
            "Recipient: " + recipient + "\n" +
            "Message Hash: " + hashToDelete + "\n" +
            "Message: " + messageText);
    }
    
    private void displayFullReport() {
        if (Message.sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("FULL MESSAGE REPORT\n\n");
        sb.append("Total Sent Messages: ").append(Message.sentMessages.size()).append("\n");
        sb.append("Total Disregarded Messages: ").append(Message.disregardedMessages.size()).append("\n");
        sb.append("Total Stored Messages: ").append(Message.storedMessages.size()).append("\n\n");
        
        sb.append("=== SENT MESSAGES ===\n\n");
        for (int i = 0; i < Message.sentMessages.size(); i++) {
            sb.append("Message ").append(i + 1).append(":\n");
            sb.append("  ID: ").append(Message.messageIDs.get(i)).append("\n");
            sb.append("  Recipient: ").append(Message.recipients.get(i)).append("\n");
            sb.append("  Hash: ").append(Message.messageHashes.get(i)).append("\n");
            sb.append("  Content: ").append(Message.sentMessages.get(i)).append("\n\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}