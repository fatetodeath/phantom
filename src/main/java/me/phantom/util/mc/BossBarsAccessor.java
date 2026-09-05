package me.phantom.util.mc;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.hud.ClientBossBar;

public interface BossBarsAccessor {
   Map<UUID, ClientBossBar> getBossBars();
}
