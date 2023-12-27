package com.oop.backend.module;

public class Attachment {
    private long ID;
    private String fileName;
    private byte content;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte getContent() {
        return content;
    }

    public void setContent(byte content) {
        this.content = content;
    }
}
