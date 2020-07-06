package net.kurwaclient.module.impl.visual;

import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.base.ModuleCategory;
import net.kurwaclient.module.base.ModuleInfo;

@ModuleInfo(name="HUD", desc="Heads up display.", category = ModuleCategory.VISUAL, keybind = 0, visable = true)
public class HUD extends Module {
    public HUD() {
        this.setState(true);
    }
}
