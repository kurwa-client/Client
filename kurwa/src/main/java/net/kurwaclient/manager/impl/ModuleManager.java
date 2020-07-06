package net.kurwaclient.manager.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.manager.IKurwaManager;
import net.kurwaclient.module.base.Module;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: load from filesystem when actually done lol
 */
public class ModuleManager implements IKurwaManager {
    private List<Module> modules = new ArrayList<>();

    @Override
    public void load(Kurwa kurwa) {
        for(Class<? extends Module> clazz : new Reflections().getSubTypesOf(Module.class)) {
            try {
                Module m = clazz.newInstance();
                modules.add(m);
                kurwa.valueManager.registerObject(m.getName(), m);

                Kurwa.LOGGER.info("Loaded Module " + m.getName() + " from category " + m.getCategory());
            }catch(Exception e) {
                Kurwa.LOGGER.severe("Failure loading module " + clazz.getCanonicalName() + ".");
            }
        }
        if(!getModule("HUD").getState()) {
            getModule("HUD").setState(true);
        }

        Kurwa.LOGGER.info(String.valueOf(getModule("HUD").getState()));
    }

    public Module getModule(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public List<Module> getModules() {
        return modules;
    }
}
