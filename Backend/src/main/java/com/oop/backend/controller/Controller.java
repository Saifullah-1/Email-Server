package com.oop.backend.controller;

import com.oop.backend.service.Server;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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
}
