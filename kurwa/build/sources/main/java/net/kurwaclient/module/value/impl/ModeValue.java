package net.kurwaclient.module.value.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kurwaclient.Kurwa;
import net.kurwaclient.module.value.Value;

import java.util.function.Predicate;

public class ModeValue extends Value<Integer> {
    private String[] modes;

    public ModeValue(String name, String defaultVal, String... modes) {
        this(name, defaultVal, null, modes);
    }

    public ModeValue(String name, String defaultValue, Predicate<Integer> validator, String... modes) {
        super(name, 0 ,validator);
        this.modes = modes;
        setObject(defaultValue);
    }

    @Override
    public void addObject(JsonObject obj) {
        obj.addProperty(getName(), getObject());
    }

    @Override
    public void fromObject(JsonObject obj) {
        if(obj.has(getName())) {
            JsonElement element = obj.get(getName());

            if(element instanceof JsonNull) {
                Kurwa.LOGGER.severe("Number value " + getName() + " json data returned null, not good!");
                return;
            }

            if(element instanceof JsonPrimitive && ((JsonPrimitive) element).isNumber()) {
                setObject(element.getAsInt());
            }else{
                throw new IllegalArgumentException("Entry " + getName() + " is not valid.");
            }
        }else{
            throw new IllegalArgumentException("Object does not have " + getName());
        }
    }

    private void setObject(String s) {
        int o = -1;
        for(int i = 0; i < modes.length; i++) {
            String mode = modes[i];
            if(mode.equalsIgnoreCase(s)) o = i;
        }
        if(o == -1) {
            throw new IllegalArgumentException("Value " + o + " wasn't found.");
        }
        setObject(o);
    }


    @Override
    public boolean setObject(Integer obj) {
        if(obj < 0 || modes.length <= obj) {
            throw new IllegalArgumentException(obj + " is not valid (max: " + (modes.length - 1 ) + ")");
        }
        return super.setObject(obj);
    }

    public String[] getModes() {
        return modes;
    }
}
