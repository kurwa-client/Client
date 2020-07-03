package net.kurwaclient.module.base;

public enum ModuleCategory {
    COMBAT("Combat", 0x00),
    MOVEMENT("Movement", 0x00),
    VISUAL("Visual", 0x00),
    MISC("Misc", 0x00);

    private String name;
    int color;

    ModuleCategory(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}
