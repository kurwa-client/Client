package net.kurwaclient.module.impl.visual;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.kurwaclient.Kurwa;
import net.kurwaclient.KurwaConstants;
import net.kurwaclient.events.impl.render.EventRender2D;
import net.kurwaclient.font.GlyphPageFontRenderer;
import net.kurwaclient.manager.impl.FontManager;
import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.base.ModuleCategory;
import net.kurwaclient.module.base.ModuleInfo;
import net.kurwaclient.module.value.impl.BooleanValue;

@ModuleInfo(name="HUD", desc="Heads up display.", category = ModuleCategory.VISUAL, keybind = 0, visable = true)
public class HUD extends Module {
    private GlyphPageFontRenderer fr = FontManager.HUD;

    public HUD() {
        Kurwa.LOGGER.info("hi from constructor");
    }

    @EventHandler
    public Listener<EventRender2D> render2DListener = new Listener<>(e -> {
        fr.drawString("Kurwa" + (Kurwa.isPlus ? "+":"") + " " + KurwaConstants.VERSION, 5, 5, 0xFFFFFF, true);
    });
}
