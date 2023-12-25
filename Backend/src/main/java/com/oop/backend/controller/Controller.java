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

    @PostMapping ("/contacts")
    public ResponseEntity<String> contacts() {
        return new ResponseEntity<>(server.getData("Contacts"), HttpStatus.OK);
    }

    @PostMapping ("/draft")
    public ResponseEntity<String> draft() {
        return new ResponseEntity<>(server.getData("Draft"), HttpStatus.OK);
    }

    @PostMapping ("/sent")
    public ResponseEntity<String> sent() {
        return new ResponseEntity<>(server.getData("Sent"), HttpStatus.OK);
    }

    @PostMapping ("/trash")
    public ResponseEntity<String> trash() {
        return new ResponseEntity<>(server.getData("Trash"), HttpStatus.OK);
    }

    @PostMapping ("/inbox")
    public ResponseEntity<String> inbox() {
        return new ResponseEntity<>(server.getData("Inbox"), HttpStatus.OK);
    }

    //    @GetMapping ("/favourite")
//    public ResponseEntity<String> favourite() {
//        return new ResponseEntity<>(server.favourite, HttpStatus.OK);
//    }

    @PostMapping ("/edit")
    public ResponseEntity<String> edit(@RequestParam String modify, @RequestParam  (required = false) String key, @RequestParam  (required = false) String replace) {
        return new ResponseEntity<>(server.edit(modify,key,replace), HttpStatus.OK);
    }

    @GetMapping ("/filter")
    public ResponseEntity<String> filter(@RequestParam String section, @RequestParam String key, @RequestParam String value) {
        return new ResponseEntity<>(server.filter(section, key, value), HttpStatus.OK);
    }
}
