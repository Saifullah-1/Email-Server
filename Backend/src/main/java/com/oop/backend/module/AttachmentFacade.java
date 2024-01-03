package com.oop.backend.module;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class AttachmentFacade {
    private AttachmentConverter attachmentConverter;

    public AttachmentFacade(MultipartFile file) {
        this.attachmentConverter = new AttachmentConverter(file);
    }

    public AttachmentFacade(List<MultipartFile> files) {
        this.attachmentConverter = new AttachmentConverter(files);
    }

    public JSONObject convertToJsonObject() throws IOException {
        return attachmentConverter.convertToJsonObject();
    }

    public JSONArray convertToJsonArray() throws IOException {
        return attachmentConverter.convertToJsonArray();
    }
}
