package com.oop.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.User;
import com.oop.backend.service.Server;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/mail")
public class Controller {
    private final Server server;
    public Controller(Server server) {
        this.server = server;
        File users = new File("./Users");
        Boolean start = users.mkdir();
        System.out.println(start);
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody String data) {
        return new ResponseEntity<>(server.signUp(data), HttpStatus.OK);
    }

    @PostMapping ("/login")
    public ResponseEntity<String> login(@RequestBody String data) {
        return new ResponseEntity<>(server.login(data), HttpStatus.OK);
    }
}
