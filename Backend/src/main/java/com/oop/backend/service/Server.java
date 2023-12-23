package com.oop.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class Server {
    private long ID = 0;
    private Database database;

    public Server() {
        this.database = Database.getInstance();
    }
//    private EmailValidation emailValidation;

//    public String logIn() {
//      // check first if the user is found or not
//    }

    public String signUp(String newUser) {
        // check first if the email is found or not
        JSONObject jsonObject = new JSONObject(newUser);
        String path = "./Users/".concat(jsonObject.getString("email"));
        Gson gson = new Gson();
        User user = gson.fromJson(newUser, User.class);
        user.setID(++ID);
        user.setPath(path);
//        this.database.addUser(user);

        File newUserFile = new File(path); // user folder
        Boolean created1 = newUserFile.mkdir();
        System.out.println(created1);

        File inbox = new File(path.concat("/Inbox")); // inbox folder
        Boolean created2 = newUserFile.mkdir();
        System.out.println(created2);

        File sent = new File(path.concat("/Sent")); // sent folder
        Boolean created3 = newUserFile.mkdir();
        System.out.println(created3);

        File trash = new File(path.concat("/Trash")); // trash folder
        Boolean created4 = newUserFile.mkdir();
        System.out.println(created4);

        File draft = new File(path.concat("/Draft")); // trash folder
        Boolean created5 = newUserFile.mkdir();
        System.out.println(created5);

        File contacts = new File(path.concat("/Contacts")); // contacts folder
        Boolean created6 = newUserFile.mkdir();
        System.out.println(created6);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();

        database.updateUserData(path.concat("/info.json"), user, "new");

        return jsonObject.toString();
    }


}
