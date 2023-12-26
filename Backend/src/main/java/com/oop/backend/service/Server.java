package com.oop.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.Contact;
import com.oop.backend.module.Mail;
import com.oop.backend.module.User;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.springframework.util.StringUtils.capitalize;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class Server {
    private long ID = 0;
    private long mailID = 0;
    private static String currentUser;
    private User onlineUser;
    private Database database;

    public Server() {
        this.database = Database.getInstance();
    }

    public void setCurrentUser(String email){
        currentUser = email;
    }

    public String getCurrentUser(){
        return currentUser;
    }

    public String signUp(String newUser) {
        JSONObject jsonObject = new JSONObject(newUser);
        String path = "./Users/".concat(jsonObject.getString("email"));
        File find = new File(path);

        System.out.println(find.exists());
        if (find.exists()){ // check first if the email is found or not
            return "Email already exists.";
        }

        if (!EmailValidator.isValidEmail(jsonObject.getString("email")))
            return "Enter a valid email.";

//        System.out.println(path);
        System.out.println(jsonObject.toString());
        Gson gson = new Gson();
        User user = gson.fromJson(jsonObject.toString(), User.class);
        user.setID(++ID);
        user.setPath(path);

        File newUserFile = new File(path); // user folder
        Boolean created1 = newUserFile.mkdir();
        System.out.println(created1);

        File inbox = new File(path.concat("/Inbox")); // inbox folder
        Boolean created2 = inbox.mkdir();
        System.out.println(created2);

        File sent = new File(path.concat("/Sent")); // sent folder
        Boolean created3 = sent.mkdir();
        System.out.println(created3);

        File trash = new File(path.concat("/Trash")); // trash folder
        Boolean created4 = trash.mkdir();
        System.out.println(created4);

        File draft = new File(path.concat("/Draft")); // trash folder
        Boolean created5 = draft.mkdir();
        System.out.println(created5);

        File contacts = new File(path.concat("/Contacts")); // contacts folder
        Boolean created6 = contacts.mkdir();
        System.out.println(created6);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();

        database.updateUserData(path.concat("/info.json"), user, "new");
        setCurrentUser(jsonObject.getString("email"));

        Mail mail1 = new Mail();
        mail1.setID(++mailID);
        mail1.setReceiverName("Saifullah");
        mail1.setSenderName("ahmed");
        mail1.setSubject("Hello");
        mail1.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
        mail1.setTo(jsonObject.getString("email"));
        mail1.setFrom("a@gmail.com");
        mail1.setState("inbox");

        user.addInbox(mail1);

        Mail mail2 = new Mail();
        mail2.setID(++mailID);
        mail2.setSenderName("Mohamed");
        mail2.setReceiverName("Saifullah");
        mail2.setSubject("Hello");
        mail2.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
        mail2.setTo(jsonObject.getString("email"));
        mail2.setFrom("a@gmail.com");
        mail2.setState("inbox");

        user.addInbox(mail2);

        Mail mail3 = new Mail();
        mail3.setID(++mailID);
        mail3.setSenderName("kassam");
        mail3.setReceiverName("Saifullah");
        mail3.setSubject("Hello");
        mail3.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
        mail3.setTo(jsonObject.getString("email"));
        mail3.setFrom("k@gmail.com");
        mail3.setState("inbox");

        user.addInbox(mail3);


        this.onlineUser = user;
        return new Gson().toJson(database.getUsers());
    }

    public String login (String data){
        JSONObject dataObj = new JSONObject(data);
        String path = "./Users/".concat(dataObj.getString("email"));
        System.out.println(path);
        File user = new File(path);
        if (!user.exists()){
            return "There is no Such an Email.";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object info = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            String jsonStr = gson.toJson(info);
            System.out.println(jsonStr);

            JSONObject jsonObject = new JSONObject(jsonStr);
            String password = jsonObject.getString("password");
            if (!password.equals(dataObj.getString("password"))) return "Invalid Password.";

            setCurrentUser(jsonObject.getString("email"));

            File inboxMessages = new File(path.concat("/Inbox"));
            String[] messages = inboxMessages.list();
            JSONArray inbox = new JSONArray();

            for (String s : messages){
                objectMapper = new ObjectMapper();
                Object message = objectMapper.readValue(new File(path.concat("/Inbox/"+s)), new TypeReference<Object>() {});

                gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                gson = gsonBuilder.create();

                jsonStr = gson.toJson(message);
                JSONObject recieved = new JSONObject(jsonStr);
                inbox.put(recieved);
            };
            return inbox.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getData (String section){
        String userEmail = getCurrentUser();
        String path = "./Users/".concat(userEmail);
        System.out.println(path);

        try {
            File Folder = new File(path.concat("/"+section));
            String[] items = Folder.list();
            JSONArray objects = new JSONArray();

            for (String s : items){
                ObjectMapper objectMapper = new ObjectMapper();
                Object message = objectMapper.readValue(new File(path.concat("/"+section+"/"+s)), new TypeReference<Object>() {});

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();

                String jsonStr = gson.toJson(message);
                JSONObject recieved = new JSONObject(jsonStr);
                objects.put(recieved);
            };
            return objects.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String filter (String section, String key, String value){
        String userEmail = getCurrentUser();
        section = section.toLowerCase();
        section = capitalize(section);
        key = key.toLowerCase();
        value = value.toLowerCase();
        String path = "./Users/".concat(userEmail+"/"+section);
        System.out.println(path);

        try {
            File Folder = new File(path);
            String[] items = Folder.list();
            JSONArray objects = new JSONArray();

            for (String s : items){
                ObjectMapper objectMapper = new ObjectMapper();
                Object message = objectMapper.readValue(new File(path.concat("/"+s)), new TypeReference<Object>() {});

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();

                String jsonStr = gson.toJson(message);
                JSONObject mail = new JSONObject(jsonStr);

                if (key.equals("subject")){
                    String check = mail.getString(key).toLowerCase();
                    System.out.println("File : "+path.concat("/"+s)+",, The Subject = "+check+" ,,, The Value = "+value);
                    if (check.contains(value)){
                        objects.put(mail);
                    }
                }
                else if (key.equals("sender")){
                    String check = mail.getString("senderName").toLowerCase();
                    if (check.contains(value)){
                        objects.put(mail);
                    }
                }
            }
            return objects.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String edit (String modify, String key, String replace){
        String userEmail = getCurrentUser();
        String path = "./Users/".concat(userEmail);
        System.out.println(path);
        File Folder = new File(path);
        if (modify.equals("delete")){
            Folder.delete();
            setCurrentUser(null);
            return null;
            // Returns to the login page
        }
        else{
            System.out.println(path);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Object user = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();

                String jsonStr = gson.toJson(user);
                JSONObject userToEdit = new JSONObject(jsonStr);

                userToEdit.put(key,replace);
                return userToEdit.toString();
            }
            catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
    }


    /*                                          SEARCH                                          */
    public String search(String folder, String key) { // To be sorted
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        if (folder.equalsIgnoreCase("contact"))
            return gson.toJson(searchContact(key.toLowerCase()));

        List<Mail> allFound = new ArrayList<>();
        allFound = searchBySubject(folder, key.toLowerCase());
        allFound = combine(allFound, searchByBody(folder, key.toLowerCase()));
        allFound = combine(allFound, searchBySenderName(folder, key.toLowerCase()));
        allFound = combine(allFound, searchByReceiverName(folder, key.toLowerCase()));


        return gson.toJson(allFound);
    }

    public List<Mail> searchBySenderName(String folder, String key) {
        List<Mail> folderMails = null;
        List<Mail> found = new ArrayList<>();
        if (folder.equalsIgnoreCase("inbox"))
            folderMails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            folderMails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            folderMails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            folderMails = this.onlineUser.getTrash();

        for (Mail mail : folderMails) {
            String sender = mail.getSenderName().toLowerCase();
            if (sender.contains(key)) {
                found.add(mail);
            }
        }
        return found;
    }

    public List<Mail> searchBySubject(String folder, String key) {
        List<Mail> folderMails = null;
        List<Mail> found = new ArrayList<>();
        if (folder.equalsIgnoreCase("inbox"))
            folderMails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            folderMails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            folderMails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            folderMails = this.onlineUser.getTrash();

        for (Mail mail : folderMails) {
            String subject = mail.getSubject().toLowerCase();
            if (subject.contains(key)) {
                found.add(mail);
            }
        }

        return found;
    }

    public List<Mail> searchByReceiverName(String folder, String key) {
        List<Mail> folderMails = null;
        List<Mail> found = new ArrayList<>();
        if (folder.equalsIgnoreCase("inbox"))
            folderMails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            folderMails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            folderMails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            folderMails = this.onlineUser.getTrash();

        for (Mail mail : folderMails) {
            String receiver = mail.getReceiverName().toLowerCase();
            if (receiver.contains(key)) {
                found.add(mail);
            }
        }

        return found;
    }

    public List<Mail> searchByBody(String folder, String key) {
        List<Mail> folderMails = null;
        List<Mail> found = new ArrayList<>();
        if (folder.equalsIgnoreCase("inbox"))
            folderMails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            folderMails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            folderMails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            folderMails = this.onlineUser.getTrash();

        for (Mail mail : folderMails) {
            String body = mail.getBody().toLowerCase();
            if (body.contains(key)) {
                found.add(mail);
            }
        }

        return found;
    }

    public List<Contact> searchContact(String key) {
        List<Contact> found = new ArrayList<>();
        for (Contact contact : this.onlineUser.getContacts()) {
            String name = contact.getFirstName().concat("").concat(contact.getLastName()).toLowerCase();
            if (name.equalsIgnoreCase(key)) {
                found.add(contact);
            }
        }
        return found;
    }

//    public List<Mail> searchByAttachment(String folder, String key) {
//        List<Mail> folderMails = null;
//        List<Mail> found = new ArrayList<>();
//        if (folder.equalsIgnoreCase("inbox"))
//            folderMails = this.onlineUser.getInbox();
//        else if (folder.equalsIgnoreCase("draft"))
//            folderMails = this.onlineUser.getDraft();
//        else if (folder.equalsIgnoreCase("sent"))
//            folderMails = this.onlineUser.getSent();
//        else if (folder.equalsIgnoreCase("trash"))
//            folderMails = this.onlineUser.getTrash();
//
//        for (Mail mail : folderMails) {
//            String receiver = mail.getReceiverName().toLowerCase();
//            if (receiver.contains(key)) {
//                found.add(mail);
//            }
//        }
//
//        return found;
//    }

    public List<Mail> combine(List<Mail> list1, List<Mail> list2) {
        List<Mail> newList = list1;
        for (Mail mail : list2) {
            if (!newList.contains(mail)) {
                newList.add(mail);
            }
        }

        return newList;
    }

    /*                                          SORT                                          */
    public String sort(String folder, String method) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        List<Mail> sorted = null;
        if (method.equalsIgnoreCase("sender"))
            sorted = sortBySender(folder);
        else if (method.equalsIgnoreCase("receiver"))
            sorted = sortByReceiver(folder);
        else if (method.equalsIgnoreCase("date"))
            sorted = sortByDate(folder);
        else if (method.equalsIgnoreCase("importance"))
            sorted = sortByImportance(folder);
        else if (method.equalsIgnoreCase("subject"))
            sorted = sortBySubject(folder);
        return gson.toJson(sorted);
    }

    public List<Mail> sortByDate(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();

        emails.sort(Comparator.comparing(Mail::getDate));

        return emails;
    }

    public List<Mail> sortBySender(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();

        emails.sort(Comparator.comparing(Mail::getSenderName));

        return emails;
    }

    public List<Mail> sortByReceiver(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();

        emails.sort(Comparator.comparing(Mail::getReceiverName));

        return emails;
    }

    public List<Mail> sortByImportance(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();

        emails.sort(Comparator.comparing(Mail::isFavourite));

        return emails;
    }

    public List<Mail> sortBySubject(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();

        emails.sort(Comparator.comparing(Mail::getSubject));

        return emails;
    }

}
