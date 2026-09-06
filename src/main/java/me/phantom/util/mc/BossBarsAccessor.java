package me.phantom.util.mc;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.components.LerpingBossEvent;

public interface BossBarsAccessor {
   Map<UUID, LerpingBossEvent> getBossBars();
}
