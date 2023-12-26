package com.oop.backend.module;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mail {
    private long ID;
    private Date date;
    private String senderName;
    private String receiverName;
    private String from;
    private String to;
    private String subject;
    private String body;
    private boolean favourite = false;
    private String state; // Inbox or Sent
    private List<String> attachments = new ArrayList<>();

//    public LocalDateTime getDateTime() { return date;}
//
//    public void setDateTime() {
//        date = LocalDateTime.now();
//    }
//        DateTimeFormatter myFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        String formattedDate = date.format(myFormatObj);


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite() {
        this.favourite = true;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addAttachment(String newAttach) {
        this.attachments.add(newAttach);
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getsenderName() {
        return senderName;
    }

    public void setsenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public JSONObject convertToJson() {
        if (this.state.equalsIgnoreCase("inbox"))
            return new JSONObject().put("ID", this.ID).put("From", this.from).put("Subject", this.subject).put("Body", this.body).put("favourite", this.favourite).put("Type", this.state);
        else // "sent"
            return new JSONObject().put("ID", this.ID).put("To", this.to).put("Subject", this.subject).put("Body", this.body).put("favourite", this.favourite).put("Type", this.state);
    }
}
