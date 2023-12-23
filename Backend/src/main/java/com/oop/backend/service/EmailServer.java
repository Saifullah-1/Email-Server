package com.oop.backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServer {
    private Database database;
    private static EmailServer instance;
    private EmailServer() {
        database = Database.getInstance();
    }
    public static EmailServer getInstance() {
        if (instance == null)
            return new EmailServer();
        return instance;
    }

    public String addUser(String newUser) {
        return database.addUser(newUser);
    }

    public String deleteUser() {
        return database.deleteUser();
    }



}
