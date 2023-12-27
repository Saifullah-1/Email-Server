package com.oop.backend.module;

import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mail {
    private long ID;
//    private Date date;
    private String senderName;
    private List<String> receiverName = new ArrayList<>();
    private String from;
    private List<String> to = new ArrayList<>();
    private String subject;
    private String body;
    private boolean favourite = false;
    private String state; // Inbox or Sent
    private List<Attachment> attachments = new ArrayList<>();

//    public Date getDate() {
//        return date;
//    }

//    public void setDate(Date date) {
//        this.date = date;
//    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void addTo(String to) {
        if (to == null)
            this.to = new ArrayList<>();
        this.to.add(to);
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attachment) {
        if (attachments == null)
            attachments = new ArrayList<>();

        this.attachments.add(attachment);
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public List<String> getReceiverName() {
        return receiverName;
    }

    public String getFirstReceiverName() {
        return receiverName.get(0);
    }

    public void setReceiverName(List<String> receiverName) {
        this.receiverName = receiverName;
    }

    public void addReceiverName(String receiver) {
        if (receiverName == null)
            receiverName = new ArrayList<>();
        receiverName.add(receiver);
    }

    public String getFirstAttachmentFileName() {
        return this.attachments.get(0).getFileName();
    }

    public JSONObject convertToJson() {
        Gson gson = new Gson();
        return new JSONObject().put("ID", this.ID).put("from", this.from).put("to", gson.toJson(this.to)).put("Subject", this.subject).put("body", this.body).put("favourite", this.favourite).put("state", this.state).put("attachments", gson.toJson(attachments));
    }
}
