package com.mycompany.loginprojectpoe2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Advanced Unit Tests for Part 3
 * Tests: Array population, longest message, search by ID, search by recipient,
 *        delete by hash, and report generation
 * 
 * @author Student Name
 * @version 3.0
 * @date 2026-05-20
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageAdvancedTest {
    
    @BeforeEach
    public void setUp() {
        Message.resetAll();
        createTestMessages();
        Message.populateArrays();
    }
    
    /**
     * Creates test messages as per specification
     */
    private void createTestMessages() {
        // Test Data Message 1 - Sent
        Message msg1 = new Message("+27834557896", "Did you get the cake?");
        msg1.setMessageId("1111111111");
        msg1.setMessageNumber(1);
        msg1.setMessageHash("11:1:DIDYOUGETTHECAKE?");
        msg1.setStatus("sent");
        Message.addMessage(msg1);
        
        // Test Data Message 2 - Stored
        Message msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        msg2.setMessageId("2222222222");
        msg2.setMessageNumber(2);
        msg2.setMessageHash("22:2:WHEREAREYOU? ON TIME.");
        msg2.setStatus("stored");
        Message.addMessage(msg2);
        
        // Test Data Message 3 - Disregarded
        Message msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        msg3.setMessageId("3333333333");
        msg3.setMessageNumber(3);
        msg3.setMessageHash("33:3:YOHOOOO, GATE.");
        msg3.setStatus("disregarded");
        Message.addMessage(msg3);
        
        // Test Data Message 4 - Sent
        Message msg4 = new Message("0838884567", "It is dinner time!");
        msg4.setMessageId("4444444444");
        msg4.setMessageNumber(4);
        msg4.setMessageHash("44:4:ITIS DINNERTIME!");
        msg4.setStatus("sent");
        Message.addMessage(msg4);
        
        // Test Data Message 5 - Stored
        Message msg5 = new Message("+27838884567", "Ok, I am leaving without you.");
        msg5.setMessageId("5555555555");
        msg5.setMessageNumber(5);
        msg5.setMessageHash("55:5:OK, YOU.");
        msg5.setStatus("stored");
        Message.addMessage(msg5);
    }
    
    // ========== TEST 1: Arrays Correctly Populated ==========
    
    @Test
    @Order(1)
    public void testArraysCorrectlyPopulated() {
        Message.populateArrays();
        
        assertEquals(2, Message.getSentMessages().size(), "Sent messages array should have 2 messages");
        assertEquals(1, Message.getDisregardedMessages().size(), "Disregarded messages array should have 1 message");
        assertEquals(2, Message.getStoredMessages().size(), "Stored messages array should have 2 messages");
        assertEquals(5, Message.getMessageHashes().size(), "Message hashes array should have 5 entries");
        assertEquals(5, Message.getMessageIds().size(), "Message IDs array should have 5 entries");
        
        // Verify content of sent messages array
        List<Message> sentMessages = Message.getSentMessages();
        boolean hasCakeMessage = false;
        boolean hasDinnerMessage = false;
        
        for (Message msg : sentMessages) {
            if (msg.getMessageContent().equals("Did you get the cake?")) {
                hasCakeMessage = true;
            }
            if (msg.getMessageContent().equals("It is dinner time!")) {
                hasDinnerMessage = true;
            }
        }
        
        assertTrue(hasCakeMessage, "Sent messages should contain 'Did you get the cake?'");
        assertTrue(hasDinnerMessage, "Sent messages should contain 'It is dinner time!'");
    }
    
    // ========== TEST 2: Display Details for the Longest Message ==========
    
    @Test
    @Order(2)
    public void testDisplayLongestMessage() {
        String longest = Message.getLongestMessage();
        String expected = "Longest message:\nWhere are you? You are late! I have asked you to be on time.";
        assertEquals(expected, longest, "Longest message should be the one about being late");
    }
    
    @Test
    @Order(3)
    public void testDisplayLongestStoredMessage() {
        String longest = Message.getLongestStoredMessage();
        String expected = "Longest stored message:\nWhere are you? You are late! I have asked you to be on time.";
        assertEquals(expected, longest, "Longest stored message should be the one about being late");
    }
    
    // ========== TEST 3: Search Array for Messages to Particular Recipient ==========
    
    @Test
    @Order(4)
    public void testSearchMessagesByRecipient_ParallelArrays() {
        List<String> results = Message.searchMessagesByRecipient("+27838884567");
        
        assertEquals(2, results.size(), "Should find 2 messages for this recipient");
        
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."),
                  "Should contain the late message");
        assertTrue(results.contains("Ok, I am leaving without you."),
                  "Should contain the leaving message");
    }
    
    @Test
    @Order(5)
    public void testSearchMessagesByRecipient_NoResults() {
        List<String> results = Message.searchMessagesByRecipient("+27999999999");
        assertEquals(0, results.size(), "Should find no messages for non-existent recipient");
    }
    
    @Test
    @Order(6)
    public void testSearchMessagesByRecipient_SingleResult() {
        List<String> results = Message.searchMessagesByRecipient("+27834557896");
        assertEquals(1, results.size(), "Should find 1 message for this recipient");
        assertEquals("Did you get the cake?", results.get(0), "Should contain the cake message");
    }
    
    // ========== TEST 4: Search for Message by Message ID ==========
    
    @Test
    @Order(7)
    public void testSearchByMessageId_Message4() {
        String result = Message.searchMessageById("4444444444");
        assertTrue(result.contains("It is dinner time!"), "Should find the dinner time message");
        assertTrue(result.contains("0838884567"), "Should find the recipient number");
    }
    
    @Test
    @Order(8)
    public void testSearchByMessageId_NotFound() {
        String result = Message.searchMessageById("9999999999");
        assertEquals("Message not found with ID: 9999999999", result, "Should return not found message");
    }
    
    // ========== TEST 5: Delete a Message Using Message Hash ==========
    
    @Test
    @Order(9)
    public void testDeleteMessageByHash() {
        int initialCount = Message.getStoredMessages().size();
        
        String result = Message.deleteStoredMessageByHash("22:2:WHEREAREYOU? ON TIME.");
        
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                     result, "Delete message should return success");
        
        assertEquals(initialCount - 1, Message.getStoredMessages().size(), "Stored messages count should decrease by 1");
    }
    
    @Test
    @Order(10)
    public void testDeleteMessageByHash_NotFound() {
        String result = Message.deleteStoredMessageByHash("99:9:NONEXISTENT");
        assertEquals("Stored message not found with hash: 99:9:NONEXISTENT", result, "Should return not found message");
    }
    
    // ========== TEST 6: Display Report ==========
    
    @Test
    @Order(11)
    public void testGenerateStoredMessagesReport() {
        String report = Message.generateStoredMessagesReport();
        
        assertTrue(report.contains("STORED MESSAGES REPORT"), "Report should have title");
        assertTrue(report.contains("Message Hash"), "Report should have Hash column");
        assertTrue(report.contains("Recipient"), "Report should have Recipient column");
        assertTrue(report.contains("+27838884567"), "Report should contain recipient number");
        assertTrue(report.contains("Ok, I am leaving without you."), "Report should contain stored message");
    }
    
    @Test
    @Order(12)
    public void testGenerateStoredMessagesReport_Empty() {
        Message.resetAll();
        String report = Message.generateStoredMessagesReport();
        assertEquals("No stored messages to display.", report, "Empty report should show appropriate message");
    }
    
    // ========== TEST 7: Display Sender and Recipient ==========
    
    @Test
    @Order(13)
    public void testDisplayStoredMessagesSenderRecipient() {
        String result = Message.displayStoredMessagesSenderRecipient();
        assertTrue(result.contains("STORED MESSAGES - SENDER & RECIPIENT"), "Should show header");
        assertTrue(result.contains("+27838884567"), "Should contain recipient");
        assertTrue(result.contains("Ok, I am leaving without you."), "Should contain message");
    }
    
    // ========== TEST 8: Read JSON File ==========
    
    @Test
    @Order(14)
    public void testReadJSONIntoArray() {
        String result = Message.readJSONIntoArray();
        assertNotNull(result);
        assertTrue(result.contains("MESSAGES FROM JSON FILE") || 
                  result.contains("No messages.json file found"));
    }
}