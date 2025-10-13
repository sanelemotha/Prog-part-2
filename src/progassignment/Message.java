/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progassignment;

/**
 *
 * @author RC_Student_Lab
 */
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;



public class Message {

    private String messageID;
    private String recipientCell;
    private String messageText;
    private boolean sent;

    // Static list to store all messages
    private static ArrayList<Message> messageList = new ArrayList<>();

    // Constructor
    public Message(String messageID, String recipientCell, String messageText) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.sent = false;
    }

    // Ensures the message ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Ensures the recipient number is valid (max 10 chars, starts with 0)
    public int checkRecipientCell() {
        if (recipientCell != null && recipientCell.length() <= 10 && recipientCell.startsWith("0")) {
            return 1; // valid
        } else {
            return 0; // invalid
        }
    }

    // Creates and returns a message hash
    public String createMessageHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String toHash = messageID + ":" + recipientCell + ":" + messageText;
            byte[] hashBytes = digest.digest(toHash.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes).substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            return "HASH_ERROR";
        }
    }

    // Allows user to choose action for the message
    public String sendMessage() {
        String[] options = {"Send Message", "Store Message", "Disregard Message"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option for this message:",
                "Message Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0:
                sent = true;
                return "Message successfully sent.";
            case 1:
                storeMessage();
                return "Message successfully stored.";
            case 2:
                return "Message disregarded.";
            default:
                return "No option selected.";
        }
    }

    // Prints all sent messages
    public static String printMessages() {
        StringBuilder sb = new StringBuilder("Messages:\n");
        for (Message msg : messageList) {
            sb.append("ID: ").append(msg.messageID)
              .append(", To: ").append(msg.recipientCell)
              .append(", Sent: ").append(msg.sent)
              .append(", Text: ").append(msg.messageText).append("\n");
        }
        return sb.toString();
    }

    // Returns total number of messages sent
    public static int returnTotalMessages() {
        return messageList.size();
    }

    // Stores messages in a JSON array (simulation)
   // Stores messages in a JSON array (simulation)
// Stores messages using simple string concatenation
public void storeMessage() {
    messageList.add(this);
    
    StringBuilder jsonBuilder = new StringBuilder();
    jsonBuilder.append("[\n");
    
    for (int i = 0; i < messageList.size(); i++) {
        Message msg = messageList.get(i);
        jsonBuilder.append("  {\n");
        jsonBuilder.append("    \"messageID\": \"").append(msg.messageID).append("\",\n");
        jsonBuilder.append("    \"recipientCell\": \"").append(msg.recipientCell).append("\",\n");
        jsonBuilder.append("    \"messageText\": \"").append(escapeJsonString(msg.messageText)).append("\",\n");
        jsonBuilder.append("    \"sent\": ").append(msg.sent).append(",\n");
        jsonBuilder.append("    \"hash\": \"").append(msg.createMessageHash()).append("\"\n");
        jsonBuilder.append("  }");
        
        if (i < messageList.size() - 1) {
            jsonBuilder.append(",");
        }
        jsonBuilder.append("\n");
    }
    
    jsonBuilder.append("]");
    
    JOptionPane.showMessageDialog(null, "Messages stored in JSON:\n" + jsonBuilder.toString());
}

// Helper method to escape special characters in JSON strings
private String escapeJsonString(String text) {
    if (text == null) return "";
    return text.replace("\\", "\\\\")
               .replace("\"", "\\\"")
               .replace("\n", "\\n")
               .replace("\r", "\\r")
               .replace("\t", "\\t");
}
}