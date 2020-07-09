package net.kurwaclient.injection.impl;

import io.netty.channel.ChannelHandlerContext;
import net.kurwaclient.Kurwa;
import net.kurwaclient.events.impl.packet.EventPacket;
import net.kurwaclient.events.impl.packet.impl.EventPacketReceive;
import net.kurwaclient.events.impl.packet.impl.EventPacketSend;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void channelRead(CallbackInfo info, ChannelHandlerContext context, Packet<?> packet) {
        EventPacketReceive eventPacketReceive = new EventPacketReceive(packet);
        Kurwa.EVENT_BUS.post(eventPacketReceive);
        if(eventPacketReceive.isCancelled()) info.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(CallbackInfo info, Packet<?> packet) {
        EventPacketSend eventPacketSend = new EventPacketSend(packet);
        Kurwa.EVENT_BUS.post(eventPacketSend);
        if(eventPacketSend.isCancelled()) info.cancel();
    }
}
