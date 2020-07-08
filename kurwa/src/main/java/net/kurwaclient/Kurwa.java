package net.kurwaclient;

import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.kurwaclient.events.FMLEvents;
import net.kurwaclient.manager.impl.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;

import java.util.logging.Logger;

@Mod(modid="kurwa")
public class Kurwa {
    public static final Logger LOGGER = Logger.getLogger("Kurwa");
    public static final EventBus EVENT_BUS = new EventManager();

    public final ModuleManager moduleManager = new ModuleManager();
    public final ValueManager valueManager = new ValueManager();
    public final FileManager fileManager = new FileManager();
    public final CommandManager commandManager = new CommandManager();
    public final DiscordRPCManager rpcManager = new DiscordRPCManager();

    private final FMLEvents fmlEvents = new FMLEvents(this);

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        Display.setTitle((KurwaConstants.CHANGE_TITLE ? "Kurwa " + KurwaConstants.VERSION : "Minecraft 1.12.2"));
        LOGGER.info("Initialising Kurwa " + KurwaConstants.VERSION);

        FMLCommonHandler.instance().bus().register(fmlEvents);
        MinecraftForge.EVENT_BUS.register(fmlEvents);

        valueManager.load(this);
        moduleManager.load(this);
        fileManager.load(this);
        commandManager.load(this);
        rpcManager.load(this);

        Runtime.getRuntime().addShutdownHook(new Thread(this::end));
    }

    public void end() {
        fileManager.save(this);
        rpcManager.getRPC().Discord_Shutdown();
    }
}
