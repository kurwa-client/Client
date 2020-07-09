package net.kurwaclient.events.impl.packet.impl;

import net.kurwaclient.events.impl.packet.EventPacket;
import net.minecraft.network.Packet;

public class EventPacketSend extends EventPacket {
    public EventPacketSend(Packet packet) {
        super(packet);
    }
}
