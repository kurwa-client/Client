package net.kurwaclient.module.value.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kurwaclient.Kurwa;
import net.kurwaclient.module.value.Value;

import java.util.function.Predicate;

public class StringValue extends Value<String> {
    public StringValue(String name, String defaultValue) {
        super(name, defaultValue, null);
    }

    public StringValue(String name, String defaultValue, Predicate<String> validator) {
        super(name, defaultValue, validator);
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
                Kurwa.LOGGER.severe("String value " + getName() + " json data returned null, not good!");
                return;
            }

            if(element instanceof JsonPrimitive && ((JsonPrimitive)element).isString()) {
                setObject(element.getAsString());
            }else{
                throw new IllegalArgumentException("String value " + getName() +"  returned null.");
            }
        }else{
            throw new IllegalArgumentException("Object does not have " + getName());
        }
    }
}
