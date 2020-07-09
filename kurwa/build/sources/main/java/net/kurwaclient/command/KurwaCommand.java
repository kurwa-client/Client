package net.kurwaclient.command;

import net.kurwaclient.Kurwa;

public abstract class KurwaCommand {
    private CommandInfo info = getClass().getAnnotation(CommandInfo.class);

    public KurwaCommand() {
        if(!getClass().isAnnotationPresent(CommandInfo.class)) {
            Kurwa.LOGGER.severe("@CommandInfo not present in class " + getClass().getName());
        }
    }

    private String name = info.name(), usage = info.usage();

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public abstract void fire(String s, String[] args);
}
