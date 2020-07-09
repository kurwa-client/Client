package net.kurwaclient.manager.impl;

import akka.io.Tcp;
import net.kurwaclient.Kurwa;
import net.kurwaclient.command.KurwaCommand;
import net.kurwaclient.command.impl.CommandBind;
import net.kurwaclient.command.impl.CommandToggle;
import net.kurwaclient.manager.IKurwaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements IKurwaManager {
    private List<KurwaCommand> commands;
    public static String prefix = ">";

    @Override
    public void load(Kurwa kurwa) {
        this.commands = new ArrayList<>();

        // have to add manually since we need to pass in Kurwa
        this.commands.add(new CommandToggle(kurwa));
        this.commands.add(new CommandBind(kurwa));
    }

    public List<KurwaCommand> getCommands() {
        return commands;
    }

    public void exec(String s) {
        if(!s.contains(prefix) || !s.startsWith(prefix)) {
            return;
        }

        boolean resolved = false;
        String r = s.trim().substring((prefix).length()).trim();
        String c = r.contains(" ") ? r.split(" ")[0] : r.trim();
        String[] args = r.contains(" ") ? r.substring(c.length()).split(" ") : new String[0];

        for(KurwaCommand command : commands) {
            if(command.getName().equalsIgnoreCase(c)) {
                command.fire(s, args);
                resolved = true;
                break;
            }
        }

        if(!resolved) {
            outputChatMessage("Could not find what you were looking for. Use >help");
        }
    }

    public static void outputChatMessage(String s) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
                "kurwa > " + s
        ));
    }
}
