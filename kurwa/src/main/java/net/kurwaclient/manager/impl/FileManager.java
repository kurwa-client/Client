package net.kurwaclient.manager.impl;

import com.google.common.io.Files;
import com.google.gson.*;
import net.kurwaclient.Kurwa;
import net.kurwaclient.manager.IKurwaManager;
import net.kurwaclient.manager.IKurwaSaveable;
import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.value.Value;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager implements IKurwaManager, IKurwaSaveable {
    private final File mainDirectory = new File(Minecraft.getMinecraft().mcDataDir, "Kurwa");
    private final File saveFile = new File(mainDirectory, "kurwa.json");
    private final File backupDirectory = new File(mainDirectory, "backups");

    @Override
    public void load(Kurwa kurwa) {
        if(!saveFile.exists()) {
            return;
        }

        Kurwa.LOGGER.info("Loading settings from kurwa.json...");
        List<String> bkReasons = new ArrayList<>();

        try {
            JsonObject object = (JsonObject) new JsonParser().parse(new InputStreamReader(new FileInputStream(saveFile)));

            { //modules
                JsonElement modulesElement = object.get("modules");
                if(modulesElement instanceof JsonNull) {
                    Kurwa.LOGGER.severe("Module element in client config returned null, not good!");
                    return;
                }

                if(modulesElement instanceof JsonObject) {
                    JsonObject modules = (JsonObject) modulesElement;

                    for(Map.Entry<String, JsonElement> entry : modules.entrySet()) {
                        Module module = kurwa.moduleManager.getModule(entry.getKey());
                        if(module == null) {
                            bkReasons.add("Module " + entry.getKey() + " does not exist.");
                            continue;
                        }

                        if(entry.getValue() instanceof JsonObject) {
                            JsonObject moduleObject = (JsonObject) entry.getValue();
                            JsonElement state = moduleObject.get("state");

                            if(state instanceof JsonPrimitive && ((JsonPrimitive)state).isBoolean()) {
                                module.setState(state.getAsBoolean());
                            }else{
                                bkReasons.add(entry.getKey() + " state isn't valid!");
                            }

                            JsonElement keybind = moduleObject.get("keybind");

                            if(keybind instanceof JsonPrimitive && ((JsonPrimitive)keybind).isNumber()) {
                                module.setKeybind(keybind.getAsInt());
                            }else{
                                bkReasons.add(entry.getKey() + "'s keybind is not valid!");
                            }
                        }else{
                            bkReasons.add("Module object " + entry.getKey() + "isn't valid.");
                        }
                    }
                }else{
                    bkReasons.add("'modules' object is not valid.");
                }
            }

            { //values
                JsonElement valuesElement = object.get("values");

                if(valuesElement instanceof JsonNull) {
                    Kurwa.LOGGER.severe("Values element in client config returned null, not good!");
                    return;
                }

                if(valuesElement instanceof JsonObject) {
                    for(Map.Entry<String, JsonElement> entry : ((JsonObject)valuesElement).entrySet()) {
                        List<Value> values = kurwa.valueManager.getAllValues(entry.getKey());

                        if(values == null) {
                            bkReasons.add("Value owner " + entry.getKey() + " does not exist.");
                            continue;
                        }

                        if(!entry.getValue().isJsonObject()) {
                            bkReasons.add("value " + entry.getKey() + " is not valid.");
                            continue;
                        }

                        JsonObject valueObject = (JsonObject) entry.getValue();

                        for(Value value : values) {
                            try {
                                value.fromObject(valueObject);
                            }catch(Exception e) {
                                bkReasons.add("Error while applying 'values/" + entry.getKey() + "' " + e.toString());
                            }
                        }
                    }
                }else{
                    bkReasons.add("'values' object is not valid.");
                }
            }

            if(bkReasons.size() > 0) executeBackup(bkReasons);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void executeBackup(List<String> reasons) {
        Kurwa.LOGGER.info("Creating backup " + reasons);

        try {
            backupDirectory.mkdirs();

            File out = new File(backupDirectory, "kurwa-backup_" + System.currentTimeMillis() + ".zip");
            out.createNewFile();

            StringBuffer reason = new StringBuffer();

            reasons.forEach(r -> reason.append("- ").append(r).append("\n"));
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(out));
            outputStream.putNextEntry(new ZipEntry("kurwa.json"));
            Files.copy(saveFile, outputStream);
            outputStream.closeEntry();

            outputStream.putNextEntry(new ZipEntry("reasons.txt"));
            outputStream.write(reason.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.closeEntry();

            outputStream.close();
        }catch(Exception e) {
            Kurwa.LOGGER.severe("Failed to backup.");
            e.printStackTrace();
        }
    }

    @Override
    public void save(Kurwa kurwa) {
        mainDirectory.mkdirs();
        try {
            if(!saveFile.exists() && !saveFile.createNewFile()) {
                throw new IOException("Failed to create " + saveFile.getAbsolutePath());
            }

            Files.write(toJsonObject(kurwa).toString().getBytes(StandardCharsets.UTF_8), saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject toJsonObject(Kurwa kurwa) {
        Kurwa.LOGGER.info("Saving settings!");

        JsonObject obj = new JsonObject();
        { //modules
            JsonObject modules = new JsonObject();

            kurwa.moduleManager.getModules().stream().forEach(m -> {
                JsonObject moduleObject = new JsonObject();

                moduleObject.addProperty("state", m.getState());
                moduleObject.addProperty("keybind", m.getKeybind());

                modules.add(m.getName(), moduleObject);
            });

            obj.add("modules", modules);
        }
        { //values
            JsonObject values = new JsonObject();

            for(Map.Entry<String, List<Value>> entry : kurwa.valueManager.getValues().entrySet()) {
                JsonObject valueObject = new JsonObject();

                for(Value v : entry.getValue()) {
                    v.addObject(valueObject);
                }

                values.add(entry.getKey(), valueObject);
            }

            obj.add("values", values);
        }
        return obj;
    }
}
