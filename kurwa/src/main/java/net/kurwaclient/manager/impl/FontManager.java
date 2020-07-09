package net.kurwaclient.manager.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.font.GlyphPageFontRenderer;
import net.kurwaclient.manager.IKurwaManager;

public class FontManager implements IKurwaManager {
    public static GlyphPageFontRenderer HUD;

    @Override
    public void load(Kurwa kurwa) {
        HUD = GlyphPageFontRenderer.create("Consolas", 15, false, false, false);
    }
}
