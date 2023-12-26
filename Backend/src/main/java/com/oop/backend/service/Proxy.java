package com.oop.backend.service;

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

    @Override
    public String edit(String modify, String key, String replace) {
        return server.edit(modify, key, replace);
    }

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


}
