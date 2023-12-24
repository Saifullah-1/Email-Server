package com.oop.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.Mail;
import com.oop.backend.module.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class Server {
    private long ID = 0;
    private long mailID = 0;
    private Database database;

    public Server() {
        this.database = Database.getInstance();
    }


    public String signUp(String newUser) {
        JSONObject jsonObject = new JSONObject(newUser);
        // check first if the email is found or not

        if (!EmailValidator.isValidEmail(jsonObject.getString("email")))
            return "Enter a valid email";


        String path = "./Users/".concat(jsonObject.getString("email"));
        System.out.println(path);
        Gson gson = new Gson();
        User user = gson.fromJson(newUser, User.class);
        user.setID(++ID);
        user.setPath(path);

//        Mail mail = new Mail();
//        mail.setBody("Hello");
//        mail.setSubject("Hello");
//        mail.setID(++mailID);
//        user.addInbox(mail);


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
//        database.updateUserData(path.concat("/Inbox/inbox.json"), user, "edit");

        return new Gson().toJson(database.getUsers());
    }

    public String login (String data){
        JSONObject dataObj = new JSONObject(data);
        String path = "./Users/".concat(dataObj.getString("email"));
        System.out.println(path);
        File user = new File(path);
        if (!user.exists()){
            return "There is no Such an Email.";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object info = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            String jsonStr = gson.toJson(info);
            System.out.println(jsonStr);

            JSONObject jsonObject = new JSONObject(jsonStr);
            String password = jsonObject.getString("password");
            if (!password.equals(dataObj.getString("password"))) return "Invalid Password.";

            File inboxMessages = new File(path.concat("/Inbox"));
            String[] messages = inboxMessages.list();
            JSONArray inbox = new JSONArray();

            for (String s : messages){
                objectMapper = new ObjectMapper();
                Object message = objectMapper.readValue(new File(path.concat("/Inbox/"+s)), new TypeReference<Object>() {});

                gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                gson = gsonBuilder.create();

                jsonStr = gson.toJson(message);
                JSONObject recieved = new JSONObject(jsonStr);
                inbox.put(recieved);
            };
            return inbox.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }


}
