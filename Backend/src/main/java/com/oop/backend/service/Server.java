package com.oop.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class Server {
    private long ID = 0;
    private Database database;
    private EmailValidator emailValidator;

    public Server() {
        this.database = Database.getInstance();
        this.emailValidator = EmailValidator.getInstance();
    }


//    public String logIn() {
//      // check first if the user is found or not
//    }

    public String signUp(String newUser) {
        JSONObject jsonObject = new JSONObject(newUser);
        // check first if the email is found or not

        if (!emailValidator.isValidEmail(jsonObject.getString("email")))
            return "Enter a valid email";


        String path = "./Users/".concat(jsonObject.getString("email"));
        System.out.println(path);
        Gson gson = new Gson();
        User user = gson.fromJson(newUser, User.class);
        user.setID(++ID);
        user.setPath(path);
//        this.database.addUser(user);

        File newUserFile = new File(path); // user folder
        Boolean created1 = newUserFile.mkdir();
        System.out.println(created1);

        File inbox = new File(path.concat("/Inbox")); // inbox folder
        Boolean created2 = inbox.mkdir();
        System.out.println(created2);

        File sent = new File(path.concat("/Sent")); // sent folder
        Boolean created3 = sent.mkdir();
        System.out.println(created3);

        File trash = new File(path.concat("/Trash")); // trash folder
        Boolean created4 = trash.mkdir();
        System.out.println(created4);

        File draft = new File(path.concat("/Draft")); // trash folder
        Boolean created5 = draft.mkdir();
        System.out.println(created5);

        File contacts = new File(path.concat("/Contacts")); // contacts folder
        Boolean created6 = contacts.mkdir();
        System.out.println(created6);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();

        database.updateUserData(path.concat("/info.json"), user, "new");

        return new Gson().toJson(database.getUsers());
    }


}
