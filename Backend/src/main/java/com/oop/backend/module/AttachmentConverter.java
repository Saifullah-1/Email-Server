package com.oop.backend.module;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class AttachmentConverter {
    private MultipartFile file;
    private List<MultipartFile> files;

    public AttachmentConverter(MultipartFile file) {
        this.file = file;
    }

    public AttachmentConverter(List<MultipartFile> files) {
        this.files = files;
    }

    public JSONObject convertToJsonObject() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", file.getOriginalFilename());
        jsonObject.put("content", file.getBytes());
        return jsonObject;
    }

    public JSONArray convertToJsonArray() throws IOException {
        JSONArray jsonArray = new JSONArray();
        if (files != null) {
            for (MultipartFile f : files) {
                this.file = f;
                JSONObject jsonObject = convertToJsonObject();
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray;
    }
}
