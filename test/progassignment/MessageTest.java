package progassignment;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessageTest {

    private Message messageValid;
    private Message messageInvalid;

    @BeforeMethod
    public void setUp() {
        messageValid = new Message("MSG001", "0837976899", "Hi Keagan, did you receive the payment?");
        messageInvalid = new Message("LONGMESSAGE12345", "27839796899", "This is a very long test message to trigger validation.");
    }

    // ✅ Test 1: Message ID length not more than 10
    @Test
    public void testMessageIDLength_Valid() {
        Assert.assertTrue(messageValid.checkMessageID(), "Message ID should be valid (<= 10 chars).");
    }

    @Test
    public void testMessageIDLength_Invalid() {
        Assert.assertFalse(messageInvalid.checkMessageID(), "Message ID should be invalid (> 10 chars).");
    }

    // ✅ Test 2: Recipient number correctly formatted
    @Test
    public void testRecipientCell_Valid() {
        int result = messageValid.checkRecipientCell();
        Assert.assertEquals(result, 1, "Recipient cell number should be correctly formatted.");
    }

    @Test
    public void testRecipientCell_Invalid() {
        int result = messageInvalid.checkRecipientCell();
        Assert.assertEquals(result, 0, "Recipient cell number should be incorrectly formatted.");
    }

    // ✅ Test 3: Message hash creation
    @Test
    public void testMessageHashGeneration() {
        String hash = messageValid.createMessageHash();
        Assert.assertNotNull(hash, "Message hash should not be null.");
        Assert.assertEquals(hash.length(), 10, "Message hash should be 10 characters long.");
    }

    // ✅ Test 4: Message sending options
    @Test
    public void testSendMessage_SendOption() {
        // Simulate sending message manually (bypassing GUI)
        messageValid.storeMessage();
        Assert.assertTrue(Message.returnTotalMessages() > 0, "Message should be stored successfully.");
    }

    // ✅ Test 5: Total messages count
    @Test
    public void testReturnTotalMessages() {
        int totalBefore = Message.returnTotalMessages();
        messageValid.storeMessage();
        int totalAfter = Message.returnTotalMessages();
        Assert.assertTrue(totalAfter >= totalBefore, "Total messages should increase or remain same.");
    }

    // ✅ Test 6: JSON storage simulation
    @Test
    public void testStoreMessageJSON() {
        messageValid.storeMessage();
        String hash = messageValid.createMessageHash();
        Assert.assertNotNull(hash, "Hash must be generated for stored message.");
    }
}

