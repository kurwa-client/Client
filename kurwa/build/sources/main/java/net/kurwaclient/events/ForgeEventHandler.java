package net.kurwaclient.events;

import net.kurwaclient.Kurwa;
import net.kurwaclient.events.impl.EventPlayerUpdate;
import net.kurwaclient.events.impl.render.EventRender2D;
import net.kurwaclient.manager.impl.CommandManager;
import net.kurwaclient.module.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ForgeEventHandler {
    private Kurwa kurwa;

    public ForgeEventHandler(Kurwa kurwa) {
        this.kurwa = kurwa;
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent e) {
        if(e.getEntity() == Minecraft.getMinecraft().player) {
            this.kurwa.moduleManager.getModules().stream().filter(Module::getState).forEach(m -> {
                Kurwa.EVENT_BUS.post(new EventPlayerUpdate());
            });
        }
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent e) {
        if(Keyboard.getEventKeyState()) {
            this.kurwa.moduleManager.getModules().stream().filter(m -> m.getKeybind() == Keyboard.getEventKey()).forEach(Module::toggle);
        }
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent e) {
        if(e.isCancelable() || e.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        Kurwa.EVENT_BUS.post(new EventRender2D(e.getResolution()));
    }

    @SubscribeEvent
    public void sendChat(ServerChatEvent e) {
        if(e.getMessage().startsWith(CommandManager.prefix)) {
            e.setCanceled(true);
            kurwa.commandManager.exec(e.getMessage());
        }
    }
}
