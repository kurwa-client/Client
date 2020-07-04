package net.kurwaclient.events.client;

public class KeyPressEvent {
    private int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
