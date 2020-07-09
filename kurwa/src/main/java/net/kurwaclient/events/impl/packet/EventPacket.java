package net.kurwaclient.events.impl.packet;

import me.zero.alpine.type.Cancellable;
import net.minecraft.network.Packet;

public class EventPacket extends Cancellable {
    private Packet packet;

    public EventPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
