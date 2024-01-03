package com.oop.backend.service;

import com.oop.backend.module.Mail;

import java.util.List;

public interface Observer {
    void update(List<Mail> inbox);
}