package net.kurwaclient.command.impl;

import net.kurwaclient.Kurwa;
import net.kurwaclient.command.CommandInfo;
import net.kurwaclient.command.KurwaCommand;
import net.kurwaclient.manager.impl.CommandManager;
import net.kurwaclient.module.base.Module;

import java.util.Arrays;

@CommandInfo(name = "toggle", usage = ">toggle <module>")
public class CommandToggle extends KurwaCommand {
    private Kurwa kurwa;

    public CommandToggle(Kurwa kurwa) {
        this.kurwa = kurwa;
    }

    @Override
    public void fire(String s, String[] args) {
        if(args.length == 0) {
            CommandManager.outputChatMessage("No module given!");
            return;
        }
        Module module = this.kurwa.moduleManager.getModule(args[1]);
        if(module == null) {
            CommandManager.outputChatMessage("Could not find the module given.");
            return;
        }

        module.toggle();
        CommandManager.outputChatMessage("Toggled " + module.getName() + " " + (module.getState() ? "on" : "off"));
    }
}
