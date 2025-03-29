package com.passwordmanager.data;

import com.passwordmanager.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final String FILE_PATH = "users.dat";
    private Map<String, User> users;

    public DataStorage() {
        this.users = loadUsers();
    }

    // Save user during registration
    public boolean saveUser(User user) {
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        users.put(user.getUsername(), user);
        saveToFile();
        return true;
    }

    // Get user for login
    public User getUser(String username) {
        return users.get(username);
    }

    // Check if user already exists
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    // Load users from file
    @SuppressWarnings(value = "unchecked")
    private Map<String, User> loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Save users to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
