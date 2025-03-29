package com.passwordmanager.test;

import com.passwordmanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @BeforeEach
    void resetDataFile() {
        File file = new File("users.dat");
        try (FileWriter writer = new FileWriter(file, false)){
            writer.write("");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void testRegisterUserSuccess() {
        boolean result = userService.registerUser("testUser", "Strong@123");
        assertTrue(result, "User should be registered successfully");
    }

    @Test
    void testRegisterUserDuplicate() {
        userService.registerUser("duplicateUser", "Strong@123");
        boolean result = userService.registerUser("duplicateUser", "NewPass@123");
        assertFalse(result, "Duplicate registration should fail");
    }

    @Test
    void testRegisterUserWeakPassword() {
        boolean result = userService.registerUser("weakUser", "weakpass");
        assertFalse(result, "Weak password should not be accepted");
    }

    @Test
    void testLoginUserSuccess() {
        userService.registerUser("loginUser", "Strong@123");
        boolean result = userService.loginUser("loginUser", "Strong@123");
        assertTrue(result, "Login should succeed with correct credentials");
    }

    @Test
    void testLoginUserWrongPassword() {
        userService.registerUser("wrongPassUser", "Strong@123");
        boolean result = userService.loginUser("wrongPassUser", "WrongPassword");
        assertFalse(result, "Login should fail with wrong password");
    }

    @Test
    void testLoginUserLockout() {
        userService.registerUser("lockoutUser", "Strong@123");

        // Simulating 3 failed attempts to trigger lockout
        for (int i = 0; i < 3; i++) {
            userService.loginUser("lockoutUser", "WrongPassword");
        }

        // Expecting the account to be locked now
        boolean result = userService.loginUser("lockoutUser", "Strong@123");
        assertFalse(result, "Login should fail due to account lockout");
    }
}
