package net.kurwaclient.module.base;

import net.kurwaclient.Kurwa;

/**
 * TODO: Values
 */
public class Module {
    private ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);

    public Module() {
        if(!getClass().isAnnotationPresent(ModuleInfo.class)) {
            Kurwa.LOGGER.severe("Class " + getClass().getCanonicalName() + " threw an error. ModuleInfo is not present in the module!");
        }
    }

    private String name = info.name(), desc = info.desc();
    private int keybind = info.keybind();
    private ModuleCategory category = info.category();
    private boolean visable = info.visable();

    private boolean state;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isVisable() {
        return visable;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        if(state) {
            this.state = true;
            this.onEnable();
            //todo: register with event manager
        }else{
            this.state = false;
            this.onDisable();
            //todo: register with event manager
        }
    }

    public void onEnable(){}
    public void onDisable(){}
}
