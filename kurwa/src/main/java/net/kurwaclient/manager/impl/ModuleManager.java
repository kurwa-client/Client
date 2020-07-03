package net.kurwaclient.manager.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.manager.IKurwaManager;
import net.kurwaclient.module.base.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: load from filesystem when actually done lol
 */
public class ModuleManager implements IKurwaManager {
    private List<Module> modules = new ArrayList<>();

    @Override
    public void load(Kurwa kurwa) {
        //load modules
    }

    public Module getModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
