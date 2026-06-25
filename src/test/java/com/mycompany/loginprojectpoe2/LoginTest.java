package com.mycompany.loginprojectpoe2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Login class
 * 
 * @author Student Name
 * @version 3.0
 * @date 2026-05-20
 */
public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        login = new Login("John", "Doe", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
    }
    
    // ========== assertEquals Tests ==========
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        String result = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome John, Doe it is great to see you again.", result);
    }
    
    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login tempLogin = new Login();
        String result = tempLogin.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }
    
    @Test
    public void testPasswordMeetsComplexity() {
        Login tempLogin = new Login();
        String result = tempLogin.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Cell phone number successfully added.", result);
    }
    
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login tempLogin = new Login();
        String result = tempLogin.registerUser("kyl_1", "password", "+27838968976");
        assertEquals("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.", result);
    }
    
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login tempLogin = new Login();
        String result = tempLogin.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Cell phone number successfully added.", result);
    }
    
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login tempLogin = new Login();
        String result = tempLogin.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.", result);
    }
    
    // ========== assertTrue/assertFalse Tests ==========
    
    @Test
    public void testLoginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    
    @Test
    public void testLoginFailed() {
        assertFalse(login.loginUser("wrong_user", "wrongPassword1!"));
    }
    
    @Test
    public void testCheckUserNameTrue() {
        Login tempLogin = new Login();
        assertTrue(tempLogin.checkUserName("kyl_1"));
    }
    
    @Test
    public void testCheckUserNameFalse() {
        Login tempLogin = new Login();
        assertFalse(tempLogin.checkUserName("kyle!!!!!!!"));
    }
    
    @Test
    public void testCheckPasswordComplexityTrue() {
        Login tempLogin = new Login();
        assertTrue(tempLogin.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testCheckPasswordComplexityFalse() {
        Login tempLogin = new Login();
        assertFalse(tempLogin.checkPasswordComplexity("password"));
    }
    
    @Test
    public void testCheckCellPhoneNumberTrue() {
        Login tempLogin = new Login();
        assertTrue(tempLogin.checkCellPhoneNumber("+27838968976"));
    }
    
    @Test
    public void testCheckCellPhoneNumberFalse() {
        Login tempLogin = new Login();
        assertFalse(tempLogin.checkCellPhoneNumber("08966553"));
    }
}