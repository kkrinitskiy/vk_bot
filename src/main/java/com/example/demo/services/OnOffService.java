package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class OnOffService {
    private boolean working = false;

    public void turnOn() {
        working = true;
    }

    public void turnOff() {
        working = false;
    }

    public boolean getState() {
        return working;
    }
}
