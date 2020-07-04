package net.kurwaclient.module.impl.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.kurwaclient.events.client.EventPlayerUpdate;
import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.base.ModuleCategory;
import net.kurwaclient.module.base.ModuleInfo;
import net.kurwaclient.module.value.impl.ModeValue;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Sprint", desc = "Automatically sprint for you.", keybind = Keyboard.KEY_K, category = ModuleCategory.MOVEMENT, visable = true)
public class Sprint extends Module {
    @EventHandler
    private Listener<EventPlayerUpdate> playerUpdateListener = new Listener<>(e -> {
        mc.player.setSprinting(true);
    });
}
