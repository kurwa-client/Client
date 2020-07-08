package net.kurwaclient.manager.impl;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import net.kurwaclient.Kurwa;
import net.kurwaclient.KurwaConstants;
import net.kurwaclient.manager.IKurwaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

/**
 * USE DIFFERENT DISCORD RPC LIBRARY
 * AND ALSO DO INJECTION SHIT I ADDED MIXIN TO GRADLE
 */
public class DiscordRPCManager implements IKurwaManager {
    private DiscordRPC rpc;

    @Override
    public void load(Kurwa kurwa) {
        this.rpc = DiscordRPC.INSTANCE;

        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> Kurwa.LOGGER.info("Discord RPC Started! Name: " + user.username + "#" + user.discriminator);
        rpc.Discord_Initialize(KurwaConstants.RPC_ID, handlers, true, "");

        new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                updatePresence();
                rpc.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                }catch(Exception e){}
            }
        }, "Kurwa-RPC-Callback-Handler").start();

    }

    public void updatePresence() {
        DiscordRichPresence presence = new DiscordRichPresence();

        if(Minecraft.getMinecraft().player != null) {
            ServerData data = Minecraft.getMinecraft().getCurrentServerData();
            boolean singleplayer = (data == null || data.isOnLAN());

            presence.details = "Fucking up shit on " + (singleplayer ? "singleplayer" : data.serverIP);
        }else{
            presence.details = "In Menu";
        }

        presence.state = "Version " + KurwaConstants.VERSION;
        rpc.Discord_UpdatePresence(presence);
    }

    public DiscordRPC getRPC() {
        return rpc;
    }
}
