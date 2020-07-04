package net.kurwaclient.manager.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.manager.IKurwaManager;
import net.kurwaclient.module.value.Value;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueManager implements IKurwaManager {
    private HashMap<String, List<Value>> values;

    @Override
    public void load(Kurwa kurwa) {
        this.values = new HashMap<>();
    }

    public void registerObject(String name,  Object obj) {
        List<Value> _values = new ArrayList<>();
        for(final Field f : obj.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                final Object o = f.get(obj);

                if(o instanceof Value) {
                    _values.add((Value)obj);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        values.put(name, _values);
    }

    public List<Value> getAllValues(String name) {
        for(Map.Entry<String, List<Value>> entry : values.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public HashMap<String, List<Value>> getValues() {
        return values;
    }

    public Value get(String o, String n) {
        List<Value> found = getAllValues(o);
        if(found == null) return null;
        return found.stream().filter(val -> n.equalsIgnoreCase(val.getName())).findFirst().orElse(null);
    }
}
