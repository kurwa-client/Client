package net.kurwaclient.command.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.command.CommandInfo;
import net.kurwaclient.command.KurwaCommand;
import net.kurwaclient.manager.impl.CommandManager;
import net.kurwaclient.module.base.Module;
import org.lwjgl.input.Keyboard;
import scala.Int;

@CommandInfo(name = "bind", usage = ">bind <module> <key>")
public class CommandBind extends KurwaCommand {
    private Kurwa kurwa;

    public CommandBind(Kurwa kurwa) {
        this.kurwa = kurwa;
    }

    @Override
    public void fire(String s, String[] args) {
        if(args.length == 0) {
            CommandManager.outputChatMessage("No module given! Usage: " + getUsage());
            return;
        }
        Module module = this.kurwa.moduleManager.getModule(args[1]);
        if(module == null) {
            CommandManager.outputChatMessage("Could not find the module given.");
            return;
        }

        if(args[2] == null) {
            CommandManager.outputChatMessage("You must provide a key!");
            return;
        }
        int keybind = Keyboard.getKeyIndex(args[2]);

        module.setKeybind(keybind);
        kurwa.fileManager.save(kurwa);
        CommandManager.outputChatMessage("Changed bind for " + module.getName() + " to " + Keyboard.getKeyName(keybind));
    }
}
