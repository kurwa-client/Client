package net.kurwaclient.events.impl.render;

import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D {
    private ScaledResolution res;

    public EventRender2D(ScaledResolution res) {
        this.res = res;
    }

    public ScaledResolution getResolution() {
        return res;
    }
}
