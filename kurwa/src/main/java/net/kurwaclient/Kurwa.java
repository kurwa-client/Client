package net.kurwaclient;

import net.kurwaclient.events.FMLEvents;
import net.kurwaclient.manager.impl.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.logging.Logger;

/**
 * use alpine n make event shit
 */
@Mod(modid="kurwa")
public class Kurwa {
    public static final Logger LOGGER = Logger.getLogger("Kurwa");

    public final ModuleManager moduleManager = new ModuleManager();
    private final FMLEvents fmlEvents = new FMLEvents();

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        LOGGER.info("Initialising Kurwa " + KurwaConstants.VERSION);

        FMLCommonHandler.instance().bus().register(fmlEvents);
        MinecraftForge.EVENT_BUS.register(fmlEvents);
        moduleManager.load(this);
    }
}
