package com.oop.backend.controller;

import com.oop.backend.module.AttachmentConverter;
import com.oop.backend.service.IServer;
import com.oop.backend.service.Proxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void sendEmail(@RequestBody String mail, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        AttachmentConverter attachmentConverter = new AttachmentConverter(files);
        server.sendEmail(mail, attachmentConverter.convertToJsonArray());
    }

    @PostMapping("/star")
    public void starMsg(@RequestParam String folder, @RequestParam long id) {
        this.server.starMail(folder, id);
    }

    @PostMapping("/unstar")
    public void unstarMsg(@RequestParam String folder, @RequestParam long id) {
        this.server.unstarMail(folder, id);
    }

    @PostMapping("/draft")
    public void draftMsg(@RequestBody String mail, @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {
        AttachmentConverter attachmentConverter = new AttachmentConverter(files);
        this.server.draftMail(mail, attachmentConverter.convertToJsonArray());
    }

    @DeleteMapping("/delete")
    public void deleteMsg(@RequestParam String folder, @RequestParam long id) {
        this.server.deleteMail(folder, id);
    }

    @GetMapping ("/contactsF")
    public ResponseEntity<String> contacts() {
        return new ResponseEntity<>(server.getData("Contacts"), HttpStatus.OK);
    }

    @GetMapping ("/draftF")
    public ResponseEntity<String> draft() {
        return new ResponseEntity<>(server.getData("Draft"), HttpStatus.OK);
    }

    @GetMapping ("/sentF")
    public ResponseEntity<String> sent() {
        return new ResponseEntity<>(server.getData("Sent"), HttpStatus.OK);
    }

    @GetMapping ("/trashF")
    public ResponseEntity<String> trash() {
        return new ResponseEntity<>(server.getData("Trash"), HttpStatus.OK);
    }

    @GetMapping ("/inboxF")
    public ResponseEntity<String> inbox() {
        return new ResponseEntity<>(server.getData("Inbox"), HttpStatus.OK);
    }

    @GetMapping ("/filter")
    public ResponseEntity<String> filter(@RequestParam String section, @RequestParam (required = false) String key, @RequestParam (required = false) String value) {
        return new ResponseEntity<>(server.filter(section, key, value), HttpStatus.OK);
    }

    @PostMapping("/search")
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

    @DeleteMapping ("/deleteUser")
    public ResponseEntity<String> deleteUser() {
        server.DeleteUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping ("/editUser")
    public ResponseEntity<String> editUser(@RequestParam String field, @RequestParam  String replace) {
        return new ResponseEntity<>(server.editUser(field,replace), HttpStatus.OK);
    }

    @PostMapping ("/createContact")
    public ResponseEntity<String> createContact(@RequestBody String info) {
        return new ResponseEntity<>(server.createContact(info), HttpStatus.OK);
    }

    @DeleteMapping ("/deleteContact")
    public ResponseEntity<String> deleteContact(@RequestParam long id) {
        return new ResponseEntity<>(server.deleteContact(id), HttpStatus.OK);
    }
}
