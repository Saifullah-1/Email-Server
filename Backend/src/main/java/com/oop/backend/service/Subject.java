package com.oop.backend.service;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}