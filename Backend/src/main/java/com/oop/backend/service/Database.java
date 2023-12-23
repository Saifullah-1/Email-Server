package com.oop.backend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;
    private List<User> users = new ArrayList<>(); //will be priority queue

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

    public void updateUserData(String path, User user, String state) {
        if (state.equals("new"))
            this.users.add(user);
        else {
            for (User edited : this.users) {
                if (edited.getID() == user.getID()) {
                    this.users.remove(edited);
                    this.users.add(user);
                    break;
                }
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        try(FileWriter fileWriter = new FileWriter(path)) {
            if (path.endsWith("inbox.json"))
                fileWriter.write(gson.toJson(user.getInbox()));
            else if (path.endsWith("contacts.json"))
                fileWriter.write(gson.toJson(user.getContacts()));
            else if (path.endsWith("sent.json"))
                fileWriter.write(gson.toJson(user.getSent()));
            else if (path.endsWith("draft.json"))
                fileWriter.write(gson.toJson(user.getDraft()));
            else if (path.endsWith("trash.json"))
                fileWriter.write(gson.toJson(user.getTrash()));
            else if (path.endsWith("info.json"))
                fileWriter.write(gson.toJson(user.convertToJson().toString()));
            fileWriter.flush();
            return;
        }catch (IOException e) {
            e.printStackTrace();
        }


    }
}
