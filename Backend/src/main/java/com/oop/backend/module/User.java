package com.oop.backend.module;

import org.json.JSONObject;
import java.util.List;

public class User {
    private long id;
    private String name;
    private List<String> email;
    private String password;
    private List<Mail> inbox;
    private List<Mail> sent;
    private List<Mail> draft;

    public User(long id, String name, List<String> email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJson() {
        return new JSONObject().put("id", this.id).put("name", this.name).put("email", this.email).put("password", this.password);
    }
}
