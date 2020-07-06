package net.kurwaclient.events.client;

public class EventKeyPress {
    private int key;

    public EventKeyPress(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
