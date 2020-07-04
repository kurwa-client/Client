package net.kurwaclient.module.base;

import net.kurwaclient.Kurwa;
import net.minecraft.client.Minecraft;

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

    protected Minecraft mc = Minecraft.getMinecraft();

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

    public ModuleCategory getCategory() {
        return category;
    }

    public void setState(boolean state) {
        if(state) {
            this.state = true;
            this.onEnable();
            Kurwa.EVENT_BUS.subscribe(this);
        }else{
            this.state = false;
            this.onDisable();
            Kurwa.EVENT_BUS.unsubscribe(this);

        }
    }

    public void toggle() {
        setState(!this.getState());
    }

    public void onEnable(){}
    public void onDisable(){}
}
