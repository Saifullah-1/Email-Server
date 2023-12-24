package com.oop.backend.module;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private long ID;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String password;
    private List<Mail> inbox = new ArrayList<>();
    private List<Mail> sent = new ArrayList<>();
    private List<Mail> draft = new ArrayList<>();
    private List<Mail> trash = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private String path;

//    private User() {}
    public User(long ID, String firstName, String lastName, Date birthDate, String password, String path) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.password = password;
        this.path = path;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void sendMail(Mail sent) {
        this.sent.add(sent);
    }

    public long getID() {
        return ID;
    }

    public List<Mail> getInbox() {
        return inbox;
    }

    public void setInbox(List<Mail> inbox) {
        this.inbox = inbox;
    }

    public List<Mail> getSent() {
        return sent;
    }

    public void setSent(List<Mail> sent) {
        this.sent = sent;
    }

    public List<Mail> getDraft() {
        return draft;
    }

    public void setDraft(List<Mail> draft) {
        this.draft = draft;
    }

    public List<Mail> getTrash() {
        return trash;
    }

    public void setTrash(List<Mail> trash) {
        this.trash = trash;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public JSONObject convertToJson() {
        return (new JSONObject()).put("ID", this.ID).put("firstName", this.firstName).put("lastName", this.lastName).put("Date of Birth", this.birthDate).put("email", this.email).put("password", this.password);
    }

    public void addInbox(Mail newInbox) {
        if (this.inbox == null) {
            this.inbox = new ArrayList<>();
        }
        this.inbox.add(newInbox);
    }

    public void addDraft(long id) {
        for(Mail drafted : this.inbox) {
            if (drafted.getID() == id) {
                this.inbox.remove(drafted);
                this.draft.add(drafted);
                break;
            }
        }
    }

    public void deleteMail(long id) {
        for(Mail deleted : this.inbox) {
            if (deleted.getID() == id) {
                this.inbox.remove(deleted);
                this.trash.add(deleted);
                break;
            }
        }
    }

    public void addContact(Contact newContact) {
        this.contacts.add(newContact);
    }

    public void deleteContact(long id) {
        for(Contact deleted : this.contacts) {
            if (deleted.getID() == id) {
                this.contacts.remove(deleted);
                break;
            }
        }
    }

    public void editContact(long id, String newData) {
        int i = 0;
        Gson gson = new Gson();
        for(Contact edited : this.contacts) {
            if (edited.getID() == id) {
                Contact newContact = gson.fromJson(newData, Contact.class);
                this.contacts.set(i, newContact);
                break;
            }
            i++;
        }
    }
}