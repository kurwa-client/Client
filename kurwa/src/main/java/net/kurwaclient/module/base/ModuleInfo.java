package net.kurwaclient.module.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    String desc();
    int keybind();
    ModuleCategory category();

    boolean visable(); //visable on arraylist
}
