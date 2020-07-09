package net.kurwaclient.module.value.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kurwaclient.module.value.Value;

public class BooleanValue extends Value<Boolean> {
    public BooleanValue(String name, boolean defaultValue) {
        super(name, defaultValue, null);
    }

    @Override
    public void addObject(JsonObject obj) {
        obj.addProperty(getName(), getObject());
    }

    @Override
    public void fromObject(JsonObject obj) {
        if(obj.has(getName())) {
            JsonElement element = obj.get(getName());

            if(element instanceof JsonPrimitive && ((JsonPrimitive)element).isBoolean()) {
                setObject(element.getAsBoolean());
            }else{
                throw new IllegalArgumentException("Boolean value " + getName() + " is not valid.");
            }
        }else{
            throw new IllegalArgumentException("Object does not have" + getName());
        }
    }
}
