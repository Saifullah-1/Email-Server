package com.oop.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.Mail;
import com.oop.backend.module.User;
import org.json.JSONObject;

import java.util.*;

public class Database {
    private long lastID = 0;
    List<User> users = new ArrayList<>();
    private static Database instance;
    private Database() {}
    public static Database getInstance() {
        if (instance == null)
            return new Database();
        return instance;
    }

    List<Mail> mails = new ArrayList<>();

    public String addUser(String newUser) {
        // Handle user if found or not
        lastID++;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        User user = gson.fromJson(newUser, User.class);
        user.setId(lastID);
        this.users.add(user);
        return gson.toJson(this.users);
    }

    public String deleteUser() {
        User deletedUser = users.remove(users.size() - 1);
        System.out.println(new JSONObject(deletedUser).toString());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this.users);
    }
}
