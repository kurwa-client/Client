package net.kurwaclient.module.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ModuleInfo {
    String name();
    String desc();
    int keybind();
    ModuleCategory category();

    boolean visable(); //visable on arraylist
}
