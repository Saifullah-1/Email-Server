package com.oop.backend.controller;

import com.oop.backend.service.EmailServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class Controller {
    private EmailServer server;
    private Controller(EmailServer server) {
        this.server = server;
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> hello(@PathVariable String name) {
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
    @PutMapping("/add")
    public ResponseEntity<String> add(@RequestBody String newUser) {
        System.out.println(new ResponseEntity<>(this.server.addUser(newUser), HttpStatus.OK).getBody());
        return new ResponseEntity<>(this.server.addUser(newUser), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public String delete() {
        return this.server.deleteUser();
    }
}
