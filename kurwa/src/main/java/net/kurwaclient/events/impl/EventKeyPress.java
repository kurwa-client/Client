package net.kurwaclient.events.impl;

public class EventKeyPress {
    private int key;

    public EventKeyPress(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
