package com.oop.backend.service;

import com.oop.backend.module.Mail;

import java.util.ArrayList;
import java.util.List;

public class EmailFolder implements Subject {
    private List<Observer> observers;
    private List<Mail> mails;

    public EmailFolder() {
        this.observers = new ArrayList<>();
        this.mails = new ArrayList<>();
    }

    public void addMail(Mail mail) {
        mails.add(mail);
        notifyObservers();
    }

    public List<Mail> getMails() {
        return mails;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(mails);
        }
    }
}
