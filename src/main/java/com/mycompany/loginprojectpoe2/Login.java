package com.mycompany.loginprojectpoe2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Login class for user registration and authentication
 * 
 * @author Student Name
 * @version 3.0
 * @date 2026-05-20
 */
public class Login {
    
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellNumber;
    private String firstName;
    private String lastName;
    private boolean isLoggedIn;
    
    public Login() {
        this.registeredUsername = "";
        this.registeredPassword = "";
        this.registeredCellNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.isLoggedIn = false;
    }
    
    public Login(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellNumber = cellNumber;
        this.isLoggedIn = false;
    }
    
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    public boolean checkCellPhoneNumber(String cellNumber) {
        String regex = "^\\+27[0-9]{9}$";
        return cellNumber != null && cellNumber.matches(regex);
    }
    
    public String registerUser(String username, String password, String cellNumber) {
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellNumber = checkCellPhoneNumber(cellNumber);
        
        if (!validUsername) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        } else if (!validPassword) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!validCellNumber) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        } else {
            this.registeredUsername = username;
            this.registeredPassword = password;
            this.registeredCellNumber = cellNumber;
            return "Cell phone number successfully added.";
        }
    }
    
    public boolean loginUser(String username, String password) {
        if (registeredUsername.isEmpty()) {
            return false;
        }
        boolean loginSuccess = this.registeredUsername.equals(username) && 
                               this.registeredPassword.equals(password);
        if (loginSuccess) {
            this.isLoggedIn = true;
        }
        return loginSuccess;
    }
    
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public void logout() {
        this.isLoggedIn = false;
    }
    
    // Getters and Setters
    public String getRegisteredUsername() { return registeredUsername; }
    public void setRegisteredUsername(String registeredUsername) { this.registeredUsername = registeredUsername; }
    public String getRegisteredPassword() { return registeredPassword; }
    public void setRegisteredPassword(String registeredPassword) { this.registeredPassword = registeredPassword; }
    public String getRegisteredCellNumber() { return registeredCellNumber; }
    public void setRegisteredCellNumber(String registeredCellNumber) { this.registeredCellNumber = registeredCellNumber; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}