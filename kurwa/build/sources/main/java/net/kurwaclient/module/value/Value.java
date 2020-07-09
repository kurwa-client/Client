package net.kurwaclient.module.value;

import com.google.gson.JsonObject;

import java.util.function.Predicate;

public abstract class Value<T> {
    private String name;
    private T object, defaultValue;
    private Predicate<T> validator;

    public Value(String name, T defaultValue, Predicate<T> validator) {
        this.name = name;
        this.object = defaultValue;
        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    public String getName() {
        return name;
    }

    public T getObject() {
        return object;
    }

    public boolean setObject(T obj) {
        if(validator != null && !validator.test(obj)) return false;
        this.object = object;
        return true;
    }

    public void setValidator(Predicate<T> validator) {
        this.validator = validator;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public abstract void addObject(JsonObject obj);
    public abstract void fromObject(JsonObject obj);
}
