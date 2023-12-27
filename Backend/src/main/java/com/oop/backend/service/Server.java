package com.oop.backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oop.backend.module.Contact;
import com.oop.backend.module.Mail;
import com.oop.backend.module.User;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Server implements IServer {
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

        System.out.println(path);
        database.updateUserData(path.concat("/info.json"), user.convertToJson().toString());
        database.addUser(user);
        setCurrentUser(jsonObject.getString("email"));

//        Mail mail1 = new Mail();
//        mail1.setID(++mailID);
//        mail1.setReceiverName("Saifullah");
//        mail1.setSenderName("ahmed");
//        mail1.setSubject("Hello");
//        mail1.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
//        mail1.addTo(jsonObject.getString("email"));
//        mail1.setFrom("a@gmail.com");
//        mail1.setState("inbox");
//
//        user.addInbox(mail1);
//
//        Mail mail2 = new Mail();
//        mail2.setID(++mailID);
//        mail2.setSenderName("Mohamed");
//        mail2.setReceiverName("Saifullah");
//        mail2.setSubject("Hello");
//        mail2.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
//        mail2.addTo(jsonObject.getString("email"));
//        mail2.setFrom("a@gmail.com");
//        mail2.setState("inbox");
//
//        user.addInbox(mail2);
//
//        Mail mail3 = new Mail();
//        mail3.setID(++mailID);
//        mail3.setSenderName("kassam");
//        mail3.setReceiverName("Saifullah");
//        mail3.setSubject("Hello");
//        mail3.setBody("jkldhsflkhdlfhlkdh;kskalkadsfkdl;kfas;klfnlkdsackdls dscbsdkljvbdsajb skjcBskdjv");
//        mail3.addTo(jsonObject.getString("email"));
//        mail3.setFrom("k@gmail.com");
//        mail3.setState("inbox");
//
//        user.addInbox(mail3);


        this.onlineUser = user;
        return new Gson().toJson(database.getUsers());
    }

    public String login (String data) {
        JSONObject sentData = new JSONObject(data);
        String path = "./Users/".concat(sentData.getString("email"));
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

            JSONObject validUser = new JSONObject(jsonStr);
            String password = validUser.getString("password");
            if (!password.equals(sentData.getString("password"))) return "Invalid Password.";

            onlineUser= database.uploadUserData(validUser.getString("email"));
            List<Mail> inbox = onlineUser.getInbox();
            return gson.toJson(inbox);
        }
        catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getData (String section) {

        String path = onlineUser.getPath();
        System.out.println(path);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Object info = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});
            JSONObject jsonInfo = new JSONObject(info);
            if (section.equalsIgnoreCase("inbox")) return gson.toJson(onlineUser.getInbox());
            else if (section.equalsIgnoreCase("contacts")) return gson.toJson(onlineUser.getContacts());
            else if (section.equalsIgnoreCase("draft")) return gson.toJson(onlineUser.getDraft());
            else if (section.equalsIgnoreCase("sent")) return gson.toJson(onlineUser.getSent());
            else if (section.equalsIgnoreCase("trash")) return  gson.toJson(onlineUser.getTrash());
            else return jsonInfo.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String filter (String section, String subjectKey, String nameKey) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        List<Mail> result;

        if (subjectKey==null&&nameKey==null){
            return getData(section);
        }
        else if (nameKey==null){
            result = searchBySubject(section,subjectKey.toLowerCase());
        }
        else if (subjectKey==null){
            result = searchBySenderName(section,nameKey.toLowerCase());
        }
        else{
            List<Mail> sub = searchBySubject(section,subjectKey.toLowerCase());
            List<Mail> name = searchBySenderName(section,nameKey.toLowerCase());
            result = combineFilter(sub,name);
        }
        return gson.toJson(result);
    }

    public String edit (String modify, String key, String replace) {
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
//        allFound = combine(allFound, searchBySenderEmail(folder, key.toLowerCase()));
//        allFound = combine(allFound, searchByReceiverEmail(folder, key.toLowerCase()));

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

//    public List<Mail> searchBySenderEmail(String folder, String key) {
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
//            String sender = mail.getFrom().toLowerCase();
//            if (sender.contains(key)) {
//                found.add(mail);
//            }
//        }
//        return found;
//    }
//
//    public List<Mail> searchByReceiverEmail(String folder, String key) {
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
//            for (String sender : mail.getTo()) {
//                sender = sender.toLowerCase();
//                if (sender.contains(key)) {
//                    found.add(mail);
//                }
//            }
//        }
//        return found;
//    }

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
            for (String receiver : mail.getReceiverName()) {
                receiver = receiver.toLowerCase();
                if (receiver.contains(key)) {
                    found.add(mail);
                    break;
                }
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
            String name = contact.getFirstName().concat(" ").concat(contact.getLastName()).toLowerCase();
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

    public List<Mail> combineFilter(List<Mail> list1, List<Mail> list2) {
        List<Mail> newList = new ArrayList<>();
        for (Mail mail : list2) {
            if (list1.contains(mail)) {
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
            sorted = sortBySenderName(folder);
        else if (method.equalsIgnoreCase("receiver"))
            sorted = sortByReceiverName(folder);
//        else if (method.equalsIgnoreCase("date"))
//            sorted = sortByDate(folder);
        else if (method.equalsIgnoreCase("importance"))
            sorted = sortByImportance(folder);
        else if (method.equalsIgnoreCase("subject"))
            sorted = sortBySubject(folder);
        return gson.toJson(sorted);
    }

//    public List<Mail> sortByDate(String folder) {
//        List<Mail> emails = null;
//        if (folder.equalsIgnoreCase("inbox"))
//            emails = this.onlineUser.getInbox();
//        else if (folder.equalsIgnoreCase("draft"))
//            emails = this.onlineUser.getDraft();
//        else if (folder.equalsIgnoreCase("sent"))
//            emails = this.onlineUser.getSent();
//        else if (folder.equalsIgnoreCase("trash"))
//            emails = this.onlineUser.getTrash();
//
//        emails.sort(Comparator.comparing(Mail::getDate));
//
//        return emails;
//    }

    public List<Mail> sortBySenderName(String folder) {
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

    public List<Mail> sortByReceiverName(String folder) {
        List<Mail> emails = null;
        if (folder.equalsIgnoreCase("inbox"))
            emails = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            emails = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            emails = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            emails = this.onlineUser.getTrash();
        emails.sort(Comparator.comparing(Mail::getFirstReceiverName));

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

        emails.sort(Comparator.comparing(Mail::isFavourite).reversed());

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

    /*                                                       Mail Operations                                                       */
    public void sendEmail(String mail) {
        Gson gson = new Gson();
        Mail send = gson.fromJson(mail, Mail.class);
        send.setID(++mailID);
        send.setState("sent");
        String name = this.onlineUser.getFirstName().concat(" ").concat(this.onlineUser.getLastName());
        send.setSenderName(name);
        this.onlineUser.sendMail(send);

        for (String to : send.getTo()) {
            String path = "./Users/".concat(to);
            File file = new File(path);

            if (!file.exists())
                continue;

            User receiver = this.database.uploadUserData(to);
            String rec = receiver.getFirstName().concat(" ").concat(receiver.getLastName());
            send.addReceiverName(rec);

            String recPath = path.concat("/Inbox/Inbox.json");
            send.setState("inbox");
            receiver.addInbox(send);
            System.out.println(gson.toJson(receiver.getInbox()));
            this.database.updateUserData(recPath, gson.toJson(receiver.getInbox()));
        }

        String sentPath = "./Users/".concat(send.getFrom()).concat("/Sent/Sent.json");

        this.database.updateUserData(sentPath, gson.toJson(this.onlineUser.getSent()));

        //        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Object info = objectMapper.readValue(new File(path.concat("/info.json")), new TypeReference<Object>() {});
//
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.setPrettyPrinting();
//            gson = gsonBuilder.create();
//
//            String jsonStr = gson.toJson(info);
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            String rec = jsonObject.getString("firstName").concat(" ").concat(jsonObject.getString("lastName"));
//            send.setReceiverName(rec);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void starMail(String folder, long id) {
        List<Mail> list = null;
        if (folder.equalsIgnoreCase("inbox"))
            list = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            list = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            list = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            list = this.onlineUser.getTrash();
        for (Mail mail : list) {
            System.out.println(mail.getID());
            if (mail.getID() == id) {
                list.remove(mail);
                mail.setFavourite();
                list.add(mail);
                break;
            }
        }

        String path = this.onlineUser.getPath();
        if (folder.equalsIgnoreCase("inbox")) {
            this.onlineUser.setInbox(new ArrayList<>(list));
            path = path.concat("/Inbox/Inbox.json");
        } else if (folder.equalsIgnoreCase("draft")) {
            this.onlineUser.setDraft(new ArrayList<>(list));
            path = path.concat("/Draft/Draft.json");
        } else if (folder.equalsIgnoreCase("sent")) {
            this.onlineUser.setSent(new ArrayList<>(list));
            path = path.concat("/Sent/Sent.json");
        } else if (folder.equalsIgnoreCase("trash")) {
            this.onlineUser.setTrash(new ArrayList<>(list));
            path = path.concat("/Trash/Trash.json");
        }

        Gson gson = new Gson();
        System.out.println(path);
        this.database.updateUserData(path, gson.toJson(list));

        System.out.println(gson.toJson(list));
    }

    public void deleteMail(String folder, long id) {
        List<Mail> list = null;
        if (folder.equalsIgnoreCase("inbox"))
            list = this.onlineUser.getInbox();
        else if (folder.equalsIgnoreCase("draft"))
            list = this.onlineUser.getDraft();
        else if (folder.equalsIgnoreCase("sent"))
            list = this.onlineUser.getSent();
        else if (folder.equalsIgnoreCase("trash"))
            list = this.onlineUser.getTrash();

        for (Mail mail : list) {
            if (mail.getID() == id) {
                list.remove(mail);
                this.onlineUser.deleteMail(mail);
                break;
            }
        }

        String path = this.onlineUser.getPath();
        if (folder.equalsIgnoreCase("inbox")) {
            this.onlineUser.setInbox(new ArrayList<>(list));
            path = path.concat("/Inbox/Inbox.json");
        } else if (folder.equalsIgnoreCase("draft")) {
            this.onlineUser.setDraft(new ArrayList<>(list));
            path = path.concat("/Draft/Draft.json");
        } else if (folder.equalsIgnoreCase("sent")) {
            this.onlineUser.setSent(new ArrayList<>(list));
            path = path.concat("/Sent/Sent.json");
        } else if (folder.equalsIgnoreCase("trash")) {
            this.onlineUser.setTrash(new ArrayList<>(list));
            path = path.concat("/Trash/Trash.json");
        }

        Gson gson = new Gson();
        this.database.updateUserData(path, gson.toJson(list));

        System.out.println(gson.toJson(list));
    }

    public void draftMail(String mail) {
        Gson gson = new Gson();

        JSONObject draftObject = new JSONObject(mail);
        Mail drafted = gson.fromJson(mail, Mail.class);
        drafted.setID(++mailID);

        this.onlineUser.addDraft(drafted);

        String path = this.onlineUser.getPath().concat("/Draft/Draft.json");

        this.database.updateUserData(path, gson.toJson(this.onlineUser.getDraft()));

        System.out.println(gson.toJson(this.onlineUser.getDraft()));
    }
}
