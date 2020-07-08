package net.kurwaclient.module.impl.visual;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.kurwaclient.Kurwa;
import net.kurwaclient.events.client.render.EventRender2D;
import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.base.ModuleCategory;
import net.kurwaclient.module.base.ModuleInfo;

@ModuleInfo(name="HUD", desc="Heads up display.", category = ModuleCategory.VISUAL, keybind = 0, visable = true)
public class HUD extends Module {
    @EventHandler
    public Listener<EventRender2D> render2DListener = new Listener<>(e -> {

    });
}
