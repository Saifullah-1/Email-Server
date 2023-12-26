package com.oop.backend.service;

public interface IServer {
    String signUp(String newUser);

    String login (String data);

    String filter (String section, String key, String value);

    String edit (String modify, String key, String replace);

    String search(String folder, String key);

    String sort(String folder, String method);

    String getData (String section);
}
