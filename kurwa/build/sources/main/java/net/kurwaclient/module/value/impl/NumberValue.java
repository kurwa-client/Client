package net.kurwaclient.module.value.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kurwaclient.Kurwa;
import net.kurwaclient.module.value.Value;

import java.util.function.Predicate;

public class NumberValue<T extends Number> extends Value<T> {
   private T min, max;

   public NumberValue(String name, T defaultValue,  T min,  T max,  Predicate<T> validator) {
       super(name, defaultValue, validator == null ? val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue() : validator.and(val -> val.doubleValue() >= min.doubleValue() && val.doubleValue() <= max.doubleValue()));
       this.min = min;
       this.max = max;
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

            if(element instanceof JsonPrimitive && ((JsonPrimitive)element).isNumber()) {
                if(getObject() instanceof Integer) {
                    setObject((T)Integer.valueOf(obj.get(getName()).getAsNumber().intValue()));
                }else if(getObject() instanceof Long) {
                    setObject((T)Long.valueOf(obj.get(getName()).getAsNumber().longValue()));
                }else if(getObject() instanceof Float) {
                    setObject((T)Float.valueOf(obj.get(getName()).getAsNumber().floatValue()));
                }else if(getObject() instanceof Double) {
                    setObject((T)Double.valueOf(obj.get(getName()).getAsNumber().doubleValue()));
                }
            }else{
                throw new IllegalArgumentException("Number value " + getName() + " returned null.");
            }
        }else{
            throw new IllegalArgumentException("Object does not have " + getName());
        }
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
