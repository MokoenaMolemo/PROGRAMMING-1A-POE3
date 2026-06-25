package com.mycompany.loginprojectpoe2;

import java.util.Scanner;
import java.util.List;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Login currentUser = null;

    public static void main(String[] args) {
        int choice = 0;
        
        Message.loadMessages();

        do {
            printMenu();
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("\nInvalid input. Please enter 1, 2, 3, 4, 5, or 6.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    showRegistrationWindow();
                    break;
                case 2:
                    showLoginWindow();
                    break;
                case 3:
                    if (currentUser != null && currentUser.isLoggedIn()) {
                        showMessagingMenu();
                    } else {
                        System.out.println("\n Please login first to access messaging features.\n");
                    }
                    break;
                case 4:
                    if (currentUser != null && currentUser.isLoggedIn()) {
                        showStoredMessagesMenu();
                    } else {
                        System.out.println("\n Please login first to access stored messages.\n");
                    }
                    break;
                case 5:
                    if (currentUser != null && currentUser.isLoggedIn()) {
                        viewJSONFile();
                    } else {
                        System.out.println("\n Please login first to view JSON data.\n");
                    }
                    break;
                case 6:
                    System.out.println("\nThank you for using the system. Goodbye!");
                    Message.storeMessage();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please select 1, 2, 3, 4, 5, or 6.\n");
            }

        } while (choice != 6);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n------------------------------------------");
        System.out.println("[       Registration and Login System       ]");
        System.out.println("----------------------------------------");
        System.out.println("[  1. Register                             ]");
        System.out.println("[  2. Login                                ]");
        System.out.println("[  3. QuickChat (Messaging)                ]");
        System.out.println("[  4. Stored Messages                      ]");
        System.out.println("[  5. View JSON File Messages              ]");
        System.out.println("[  6. Exit                                 ]");
        System.out.println("-----------------------------------");
    }

    private static void showRegistrationWindow() {
        System.out.println("\n--- REGISTRATION ---");
        System.out.println();

        System.out.print("First Name      : ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name       : ");
        String lastName = scanner.nextLine();

        String username = "";
        boolean validUsername = false;
        do {
            System.out.print("Username (must contain '_' and max 5 chars): ");
            username = scanner.nextLine();
            
            Login tempLogin = new Login();
            if (tempLogin.checkUserName(username)) {
                System.out.println(" Username successfully captured.");
                validUsername = true;
            } else {
                System.out.println(" Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        } while (!validUsername);

        String password = "";
        boolean validPassword = false;
        do {
            System.out.println("\nPassword Requirements:");
            System.out.println("  • At least 8 characters long");
            System.out.println("  • Contains a capital letter");
            System.out.println("  • Contains a number");
            System.out.println("  • Contains a special character (e.g., !@#$%^&*)");
            System.out.print("Password: ");
            password = scanner.nextLine();
            
            Login tempLogin = new Login();
            if (tempLogin.checkPasswordComplexity(password)) {
                System.out.println(" Password successfully captured.");
                validPassword = true;
            } else {
                System.out.println(" Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        } while (!validPassword);

        String cellPhone = "";
        boolean validCellPhone = false;
        do {
            System.out.print("\nCell Phone Number (format: +27 followed by 9 digits, e.g., +27831234567): ");
            cellPhone = scanner.nextLine();
            
            Login tempLogin = new Login();
            if (tempLogin.checkCellPhoneNumber(cellPhone)) {
                System.out.println(" Cell phone number successfully added.");
                validCellPhone = true;
            } else {
                System.out.println(" Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
            }
        } while (!validCellPhone);

        currentUser = new Login(firstName, lastName, username, password, cellPhone);
        
        System.out.println("\n Registration completed successfully!");
        System.out.println("Your account has been created.\n");
    }

    private static void showLoginWindow() {
        System.out.println("\n--- LOGIN ----");
        System.out.println();

        if (currentUser == null) {
            System.out.println("No account found. Please register first.\n");
            return;
        }

        String loginUsername = "";
        String loginPassword = "";
        boolean loginSuccess = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        do {
            if (attempts > 0) {
                System.out.println("\n️ Invalid credentials. Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            }
            
            System.out.print("Username : ");
            loginUsername = scanner.nextLine();

            System.out.print("Password : ");
            loginPassword = scanner.nextLine();

            if (currentUser.loginUser(loginUsername, loginPassword)) {
                loginSuccess = true;
                String status = currentUser.returnLoginStatus(loginUsername, loginPassword);
                System.out.println("\n " + status + "\n");
            } else {
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    System.out.println("\n Too many failed attempts. Please contact support.\n");
                    return;
                }
            }
        } while (!loginSuccess);
    }
    
    private static void showMessagingMenu() {
        System.out.println("\n--------------------------------------");
        System.out.println("[       Welcome to QuickChat!          ]");
        System.out.println("---------------------------------------");
        
        int choice = 0;
        do {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. View Messages");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Invalid input.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    sendMessages();
                    break;
                case 2:
                    System.out.println("\n Coming Soon. This feature is still in development.\n");
                    break;
                case 3:
                    System.out.println("\nReturning to main menu...\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } while (choice != 3);
    }
    
    private static void showStoredMessagesMenu() {
        System.out.println("\n---------------------------------------");
        System.out.println("[          STORED MESSAGES MENU              ]");
        System.out.println("-------------------------------------------");
        
        int choice = 0;
        do {
            System.out.println("\n--- Stored Messages Options ---");
            System.out.println("1. Display Sender and Recipient of Stored Messages");
            System.out.println("2. Display Longest Stored Message");
            System.out.println("3. Search for Message by ID");
            System.out.println("4. Search for Messages by Recipient");
            System.out.println("5. Delete Message by Hash");
            System.out.println("6. Display Stored Messages Report");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Invalid input.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    displaySenderRecipient();
                    break;
                case 2:
                    displayLongestStoredMessage();
                    break;
                case 3:
                    searchMessageById();
                    break;
                case 4:
                    searchMessagesByRecipient();
                    break;
                case 5:
                    deleteMessageByHash();
                    break;
                case 6:
                    displayStoredReport();
                    break;
                case 7:
                    System.out.println("\nReturning to main menu...\n");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        } while (choice != 7);
    }
    
    // ========== STORED MESSAGES MENU METHODS ==========
    
    private static void displaySenderRecipient() {
        System.out.println(Message.displayStoredMessagesSenderRecipient());
    }
    
    private static void displayLongestStoredMessage() {
        System.out.println("\n--- LONGEST STORED MESSAGE ---");
        System.out.println(Message.getLongestStoredMessage());
        System.out.println();
    }
    
    private static void searchMessageById() {
        System.out.print("\nEnter Message ID to search: ");
        String messageId = scanner.nextLine();
        System.out.println("\n--- SEARCH RESULT ---");
        System.out.println(Message.searchMessageById(messageId));
        System.out.println();
    }
    
    private static void searchMessagesByRecipient() {
        System.out.print("\nEnter Recipient Number to search: ");
        String recipient = scanner.nextLine();
        List<String> results = Message.searchMessagesByRecipient(recipient);
        
        System.out.println("\n--- SEARCH RESULTS ---");
        if (results.isEmpty()) {
            System.out.println("No messages found for recipient: " + recipient);
        } else {
            System.out.println("Found " + results.size() + " message(s):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
        System.out.println();
    }
    
    private static void deleteMessageByHash() {
        System.out.print("\nEnter Message Hash to delete: ");
        String hash = scanner.nextLine();
        System.out.println("\n--- DELETE RESULT ---");
        System.out.println(Message.deleteStoredMessageByHash(hash));
        System.out.println();
    }
    
    private static void displayStoredReport() {
        System.out.println(Message.generateStoredMessagesReport());
    }
    
    private static void viewJSONFile() {
        System.out.println(Message.readJSONIntoArray());
    }
    
    // ========== SEND MESSAGES METHOD ==========
    
    private static void sendMessages() {
        System.out.println("\n--- SEND MESSAGES ---");
        
        System.out.print("How many messages do you want to send? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numMessages + " ---");
            
            String recipientNumber = "";
            boolean validRecipient = false;
            do {
                System.out.print("Recipient Number (+27XXXXXXXXX): ");
                recipientNumber = scanner.nextLine();
                
                Message tempMsg = new Message(recipientNumber, "test");
                String validationResult = tempMsg.checkRecipientCell();
                if (validationResult.equals("Cell phone number successfully captured.")) {
                    System.out.println(" " + validationResult);
                    validRecipient = true;
                } else {
                    System.out.println(" " + validationResult);
                }
            } while (!validRecipient);
            
            String messageContent = "";
            boolean validContent = false;
            do {
                System.out.print("Message (max 250 characters): ");
                messageContent = scanner.nextLine();
                
                Message tempMsg = new Message(recipientNumber, messageContent);
                String validationResult = tempMsg.validateMessageContent();
                if (validationResult.equals("Message ready to send.")) {
                    System.out.println(" " + validationResult);
                    validContent = true;
                } else {
                    System.out.println(" " + validationResult);
                }
            } while (!validContent);
            
            Message message = new Message(recipientNumber, messageContent);
            System.out.println("\n Message ID generated: " + message.getMessageId());
            System.out.println(" Message Hash: " + message.getMessageHash());
            
            int action = 0;
            do {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1. Send Message");
                System.out.println("2. Store Message");
                System.out.println("3. Disregard Message");
                System.out.print("Enter your choice: ");
                
                if (scanner.hasNextInt()) {
                    action = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                    continue;
                }
                
                String result = message.sentMessage(action);
                System.out.println(" " + result);
                
                if (action == 1 || action == 2) {
                    Message.addMessage(message);
                }
            } while (action < 1 || action > 3);
        }
        
        System.out.println("\n" + Message.printMessages());
        System.out.println(" Total messages sent: " + Message.returnTotalMessages());
        System.out.println(" Messages have been saved to messages.json");
    }
}