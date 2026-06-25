package com.mycompany.loginprojectpoe2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Message class based on assignment requirements
 * 
 * @author Student Name
 * @version 2.0
 * @date 2026-05-20
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    public void setUp() {
        Message.resetCounter();
        message = new Message();
    }
    
    // ========== Test 1: Message should not be more than 250 Characters ==========
    
    @Test
    @Order(1)
    public void testMessageWithin250Chars_Success() {
        message.setMessageContent("This is a short message under 250 characters.");
        String result = message.validateMessageContent();
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    @Order(2)
    public void testMessageExceeds250Chars_Failure() {
        // Create a 260 character message
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("A");
        }
        message.setMessageContent(longMessage.toString());
        String result = message.validateMessageContent();
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }
    
    @Test
    @Order(3)
    public void testMessageExactly250Chars_Success() {
        StringBuilder exactMessage = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            exactMessage.append("B");
        }
        message.setMessageContent(exactMessage.toString());
        String result = message.validateMessageContent();
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    @Order(4)
    public void testMessageExceedsBy50Chars_Failure() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longMessage.append("C");
        }
        message.setMessageContent(longMessage.toString());
        String result = message.validateMessageContent();
        assertEquals("Message exceeds 250 characters by 50; please reduce the size.", result);
    }
    
    // ========== Test 2: Recipient number is correctly formatted ==========
    
    @Test
    @Order(5)
    public void testRecipientNumberCorrectlyFormatted_Success() {
        message.setRecipientNumber("+27831234567");
        String result = message.validateRecipientCell();
        assertEquals("Cell phone number successfully captured.", result);
    }
    
    @Test
    @Order(6)
    public void testRecipientNumberWithoutCountryCode_Failure() {
        message.setRecipientNumber("0831234567");
        String result = message.validateRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    
    @Test
    @Order(7)
    public void testRecipientNumberWithWrongCode_Failure() {
        message.setRecipientNumber("+44123456789");
        String result = message.validateRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    
    @Test
    @Order(8)
    public void testRecipientNumberTooShort_Failure() {
        message.setRecipientNumber("+27123");
        String result = message.validateRecipientCell();
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }
    
    // ========== Test 3: Message hash is correct ==========
    
    @Test
    @Order(9)
    public void testMessageHashForTestCase1() {
        // Test Case 1 from specification
        Message testMessage = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        testMessage.setMessageId("0012345678");
        testMessage.setMessageNumber(1);
        testMessage.generateMessageHash();
        
        // Expected: 00:1:HITONIGHT?
        assertEquals("00:1:HITONIGHT?", testMessage.getMessageHash());
    }
    
    @Test
    @Order(10)
    public void testMessageHashForMultipleMessagesInLoop() {
        // Test multiple message hashes in a loop
        String[] testMessages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hello Team, meeting at 2pm",
            "Quick update on the project",
            "Don't forget the deadline tomorrow"
        };
        
        String[] expectedHashes = {
            "00:1:HITONIGHT?",
            "00:2:HELLOTEAM",
            "00:3:QUICKPROJECT",
            "00:4:DON'TFORGETTOMORROW"
        };
        
        for (int i = 0; i < testMessages.length; i++) {
            Message msg = new Message("+27718693002", testMessages[i]);
            msg.setMessageId("0012345678");
            msg.setMessageNumber(i + 1);
            msg.generateMessageHash();
            assertEquals(expectedHashes[i], msg.getMessageHash(), 
                "Failed for message: " + testMessages[i]);
        }
    }
    
    @Test
    @Order(11)
    public void testMessageHashWithSingleWord() {
        Message testMessage = new Message("+27718693002", "Hello");
        testMessage.setMessageId("9876543210");
        testMessage.setMessageNumber(5);
        testMessage.generateMessageHash();
        
        assertEquals("98:5:HELLOHELLO", testMessage.getMessageHash());
    }
    
    @Test
    @Order(12)
    public void testMessageHashWithThreeWords() {
        Message testMessage = new Message("+27718693002", "The quick brown fox");
        testMessage.setMessageId("5544332211");
        testMessage.setMessageNumber(6);
        testMessage.generateMessageHash();
        
        assertEquals("55:6:THEFOX", testMessage.getMessageHash());
    }
    
    // ========== Test 4: Message ID is created ==========
    
    @Test
    @Order(13)
    public void testMessageIdIsCreated() {
        Message testMessage = new Message("+27831234567", "Test message content");
        assertNotNull(testMessage.getMessageId());
        assertEquals(10, testMessage.getMessageId().length());
        assertTrue(testMessage.getMessageId().matches("\\d{10}"));
    }
    
    @Test
    @Order(14)
    public void testMessageIdDisplayFormat() {
        Message testMessage = new Message("+27831234567", "Test message");
        String messageId = testMessage.getMessageId();
        String expectedPrefix = "Message ID generated: " + messageId;
        
        // Simulate the display
        String displayMessage = "Message ID generated: " + messageId;
        assertTrue(displayMessage.startsWith("Message ID generated: "));
        assertTrue(displayMessage.contains(messageId));
    }
    
    @Test
    @Order(15)
    public void testUniqueMessageIds() {
        Message msg1 = new Message("+27831234567", "First message");
        Message msg2 = new Message("+27831234567", "Second message");
        
        assertNotEquals(msg1.getMessageId(), msg2.getMessageId());
    }
    
    // ========== Test 5: Message Sent Options ==========
    
    @Test
    @Order(16)
    public void testSendMessageOption() {
        String result = message.processMessageAction(1);
        assertEquals("Message successfully sent.", result);
    }
    
    @Test
    @Order(17)
    public void testDisregardMessageOption() {
        String result = message.processMessageAction(3);
        assertEquals("Press 0 to delete the message.", result);
    }
    
    @Test
    @Order(18)
    public void testStoreMessageOption() {
        String result = message.processMessageAction(2);
        assertEquals("Message successfully stored.", result);
    }
    
    @Test
    @Order(19)
    public void testInvalidOption() {
        String result = message.processMessageAction(99);
        assertEquals("Invalid option selected.", result);
    }
    
    // ========== Additional Edge Case Tests ==========
    
    @Test
    @Order(20)
    public void testEmptyMessage() {
        message.setMessageContent("");
        String result = message.validateMessageContent();
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    @Order(21)
    public void testMessageHashWithPunctuation() {
        Message testMessage = new Message("+27718693002", "Hello!!! How are you?");
        testMessage.setMessageId("0012345678");
        testMessage.setMessageNumber(7);
        testMessage.generateMessageHash();
        
        assertEquals("00:7:HELLOYOU?", testMessage.getMessageHash());
    }
    
    @Test
    @Order(22)
    public void testMultipleMessagesSentCount() {
        Message.resetCounter();
        assertEquals(0, Message.returnTotalMessages());
        
        Message msg1 = new Message("+27831234567", "First");
        msg1.processMessageAction(1);
        Message.addMessage(msg1);
        assertEquals(1, Message.returnTotalMessages());
        
        Message msg2 = new Message("+27831234567", "Second");
        msg2.processMessageAction(1);
        Message.addMessage(msg2);
        assertEquals(2, Message.returnTotalMessages());
    }
}