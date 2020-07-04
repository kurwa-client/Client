package net.kurwaclient.module.impl.movement;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.kurwaclient.events.client.EventPlayerUpdate;
import net.kurwaclient.module.base.Module;
import net.kurwaclient.module.base.ModuleCategory;
import net.kurwaclient.module.base.ModuleInfo;
import net.kurwaclient.module.value.impl.BooleanValue;
import net.kurwaclient.module.value.impl.ModeValue;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Sprint", desc = "Automatically sprint for you.", keybind = Keyboard.KEY_K, category = ModuleCategory.MOVEMENT, visable = true)
public class Sprint extends Module {
    private BooleanValue smart = new BooleanValue("Smart", true);

    @EventHandler
    private Listener<EventPlayerUpdate> playerUpdateListener = new Listener<>(e -> {
        if((smart.getObject()) /* smart is true */ && (mc.player.isSneaking() || !mc.player.onGround || mc.player.collidedVertically || mc.player.collidedHorizontally || !(mc.player.getFoodStats().getFoodLevel() > 6.0F) || mc.player.isOnLadder())) {
            mc.player.setSprinting(false);
        }

        if(mc.player.movementInput.moveForward >= 0.8F) {
            mc.player.setSprinting(true);
        }
    });
}
