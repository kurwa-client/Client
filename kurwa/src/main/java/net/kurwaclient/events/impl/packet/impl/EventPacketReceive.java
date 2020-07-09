package net.kurwaclient.events.impl.packet.impl;

import net.kurwaclient.events.impl.packet.EventPacket;
import net.minecraft.network.Packet;

public class EventPacketReceive extends EventPacket {
    public EventPacketReceive(Packet packet) {
        super(packet);
    }
}
