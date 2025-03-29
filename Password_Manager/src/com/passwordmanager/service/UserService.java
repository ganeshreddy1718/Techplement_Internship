package com.passwordmanager.service;

import com.passwordmanager.data.DataStorage;
import com.passwordmanager.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final DataStorage dataStorage;
    private final SecurityService securityService;
    private final Map<String, Integer> failedAttempts = new HashMap<>();
    private final Map<String, LocalDateTime> lockoutTime = new HashMap<>();
    private static final int MAX_ATTEMPTS = 3;
    private static final int LOCKOUT_DURATION_MINUTES = 10;

    public UserService() {
        this.dataStorage = new DataStorage();
        this.securityService = new SecurityService();
    }

    // User Registration
    public boolean registerUser(String username, String password) {
        if (dataStorage.userExists(username)) {
            System.out.println("Username already exists!");
            return false;
        }
        if (!isValidPassword(password)) {
            System.out.println("Weak password! Must be 8+ chars, include uppercase, lowercase, digit, and special character.");
            return false;
        }

        try {
            String hashedPassword = securityService.hashPassword(password);
            User user = new User(username, hashedPassword);
            return dataStorage.saveUser(user);
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            return false;
        }
    }

    // User Login
    public boolean loginUser(String username, String password) {
        if (isLockedOut(username)) {
            System.out.println("Too many failed attempts! Account is locked. Try again later.");
            return false;
        }

        User user = dataStorage.getUser(username);
        if (user != null) {
            try {
                if (securityService.verifyPassword(password, user.getPassword())) {
                    System.out.println("Login successful!");
                    resetFailedAttempts(username);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Error during login: " + e.getMessage());
            }
        }

        incrementFailedAttempts(username);
        return false;
    }

    // Password Validation
    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }

    // Increment Failed Login Attempts
    private void incrementFailedAttempts(String username) {
        failedAttempts.put(username, failedAttempts.getOrDefault(username, 0) + 1);
        if (failedAttempts.get(username) >= MAX_ATTEMPTS) {
            lockoutTime.put(username, LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
            System.out.println("Account locked due to too many failed attempts. Try again later.");
        }
    }

    // Reset Failed Login Attempts
    private void resetFailedAttempts(String username) {
        failedAttempts.remove(username);
        lockoutTime.remove(username);
    }

    // Check Lockout Status
    private boolean isLockedOut(String username) {
        if (lockoutTime.containsKey(username)) {
            LocalDateTime unlockTime = lockoutTime.get(username);
            if (LocalDateTime.now().isBefore(unlockTime)) {
                return true;
            } else {
                resetFailedAttempts(username);
            }
        }
        return false;
    }
}
