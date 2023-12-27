package com.oop.backend.service;

public interface IServer {
    String signUp(String newUser);

    String login (String data);

    String filter (String section, String key, String value);

    String editUser (String field, String replace);

    String search(String folder, String key);

    String sort(String folder, String method);

    String getData (String section);

    void DeleteUser();
    void sendEmail(String mail);

    void starMail(String folder, long id);

    void deleteMail(String folder, long id);

    void draftMail(String mail);


}
