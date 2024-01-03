package com.oop.backend.service;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class Proxy implements IServer {
    private IServer server = new Server();

    @Override
    public String signUp(String newUser) {
        return server.signUp(newUser);
    }

    @Override
    public String login(String data) {
        return server.login(data);
    }

    @Override
    public String filter(String section, String key, String value) {
        return server.filter(section, key, value);
    }

//    @Override
//    public String edit(String modify, String key, String replace) {
//        return server.edit(modify, key, replace);
//    }

    @Override
    public String search(String folder, String key) {
        return server.search(folder, key);
    }

    @Override
    public String sort(String folder, String method) {
        return server.sort(folder, method);
    }

    @Override
    public String getData(String section) {
        return server.getData(section);
    }

    @Override
    public String editUser(String field, String replace) {
        return server.editUser(field,replace);
    }

    @Override
    public String createContact(String info) {
        return server.createContact(info);
    }

    @Override
    public String deleteContact(long id) {
        return server.deleteContact(id);
    }
    @Override
    public void DeleteUser() {
        server.DeleteUser();
    }
    @Override
    public void sendEmail(String mail, JSONArray attachments) {
        server.sendEmail(mail, attachments);
    }

    @Override
    public void starMail(String folder, long id) {
        server.starMail(folder, id);
    }

    @Override
    public void unstarMail(String folder, long id) {
        server.unstarMail(folder, id);
    }

    @Override
    public String deleteMail(String folder, long id) {
        return server.deleteMail(folder, id);
    }

    @Override
    public void draftMail(String mail, JSONArray attachments) {
        server.draftMail(mail, attachments);
    }
}
