package com.oop.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;

    private List<User> users = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void updateUserData(String path, String data) {
        try(FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(data);
            fileWriter.flush();
            return;
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User uploadUserData(String email) {
        Gson gson = new Gson();

        JSONObject userObject = new JSONObject();
        String path = "./Users/".concat(email);

        try { //  info file
            ObjectMapper objectMapper = new ObjectMapper();
            Object info = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});

            String jsonStr = gson.toJson(info);
            userObject = new JSONObject(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        list.add("/Inbox/Inbox.json");
        list.add("/Sent/Sent.json");
        list.add("/Draft/Draft.json");
        list.add("/Trash/Trash.json");
        list.add("/Contacts/Contacts.json");

        for (String s : list) {
            try { //  inbox file
                if (!new File(path.concat(s)).exists()) {
                    if (s.endsWith("Inbox.json"))
                        userObject.put("inbox", new ArrayList<>());
                    else if (s.endsWith("Sent.json"))
                        userObject.put("sent", new ArrayList<>());
                    else if (s.endsWith("Draft.json"))
                        userObject.put("draft", new ArrayList<>());
                    else if (s.endsWith("Trash.json"))
                        userObject.put("trash", new ArrayList<>());
                    else if (s.endsWith("Contacts.json"))
                        userObject.put("contacts", new ArrayList<>());
                    continue;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                List<Object> mails = objectMapper.readValue(new File(path.concat(s)), new TypeReference<List<Object>>() {});

                JSONArray jsonArr = new JSONArray(mails);
                if (s.endsWith("Inbox.json"))
                    userObject.put("inbox", jsonArr);
                else if (s.endsWith("Sent.json"))
                    userObject.put("sent", jsonArr);
                else if (s.endsWith("Draft.json"))
                    userObject.put("draft", jsonArr);
                else if (s.endsWith("Trash.json"))
                    userObject.put("trash", jsonArr);
                else if (s.endsWith("Contacts.json"))
                    userObject.put("contacts", jsonArr);

                System.out.println(userObject.toString());
                System.out.println(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return gson.fromJson(userObject.toString(), User.class);
    }
}
