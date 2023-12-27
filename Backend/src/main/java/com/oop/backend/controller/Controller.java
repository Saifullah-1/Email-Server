package com.oop.backend.controller;

import com.oop.backend.service.IServer;
import com.oop.backend.service.Proxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@CrossOrigin
@RestController
@RequestMapping("/mail")
public class Controller {
    private final IServer server;

    public Controller(Proxy server) {
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

    @PostMapping("/mailto")
    public void sendEmail(@RequestBody String mail) {
        server.sendEmail(mail);
    }

    @PostMapping("/star")
    public void starMsg(@RequestParam String folder, @RequestParam long id) {
        this.server.starMail(folder, id);
    }

    @PostMapping("/draft")
    public void draftMsg(@RequestBody String mail) {
        this.server.draftMail(mail);
    }

    @DeleteMapping("/delete")
    public void deleteMsg(@RequestParam String folder, @RequestParam long id) {
        this.server.deleteMail(folder, id);
    }

    @PostMapping ("/contactsF")
    public ResponseEntity<String> contacts() {
        return new ResponseEntity<>(server.getData("Contacts"), HttpStatus.OK);
    }

    @PostMapping ("/draftF")
    public ResponseEntity<String> draft() {
        return new ResponseEntity<>(server.getData("Draft"), HttpStatus.OK);
    }

    @PostMapping ("/sentF")
    public ResponseEntity<String> sent() {
        return new ResponseEntity<>(server.getData("Sent"), HttpStatus.OK);
    }

    @PostMapping ("/trashF")
    public ResponseEntity<String> trash() {
        return new ResponseEntity<>(server.getData("Trash"), HttpStatus.OK);
    }

    @PostMapping ("/inboxF")
    public ResponseEntity<String> inbox() {
        return new ResponseEntity<>(server.getData("Inbox"), HttpStatus.OK);
    }

    //    @GetMapping ("/favourite")
//    public ResponseEntity<String> favourite() {
//        return new ResponseEntity<>(server.favourite, HttpStatus.OK);
//    }

    @GetMapping ("/filter")
    public ResponseEntity<String> filter(@RequestParam String section, @RequestParam (required = false) String key, @RequestParam (required = false) String value) {
        return new ResponseEntity<>(server.filter(section, key, value), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String folder, @RequestBody String key) {
        if (this.server.search(folder, key) == null)
            return new ResponseEntity<>("[]", HttpStatus.OK);
        return new ResponseEntity<>(this.server.search(folder, key), HttpStatus.OK);
    }

    @GetMapping("/sort")
    public ResponseEntity<String> sort(@RequestParam String folder, @RequestParam String method) {
        if (this.server.sort(folder, method) == null)
            return new ResponseEntity<>("[]", HttpStatus.OK);
        return new ResponseEntity<>(this.server.sort(folder, method), HttpStatus.OK);
    }

//    @GetMapping ("/delete")
//    public ResponseEntity<String> delete(@RequestParam String user, @RequestParam  (required = false) String name) {
//        return new ResponseEntity<>(server.Delete(user,name), HttpStatus.OK);
//    }

    @PostMapping ("/deleteUser")
    public ResponseEntity<String> deleteUser() {
        server.DeleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping ("/editUser")
    public ResponseEntity<String> editUser(@RequestParam String field, @RequestParam  String replace) {
        return new ResponseEntity<>(server.editUser(field,replace), HttpStatus.OK);
    }

}
