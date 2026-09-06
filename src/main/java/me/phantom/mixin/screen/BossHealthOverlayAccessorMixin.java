package me.phantom.mixin.screen;

import me.phantom.util.mc.BossBarsAccessor;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BossHealthOverlay.class})
public class BossHealthOverlayAccessorMixin implements BossBarsAccessor {
   @Shadow
   @Final
   private Map<UUID, LerpingBossEvent> events;

   @Override
   public Map<UUID, LerpingBossEvent> getBossBars() {
      return this.events;
   }
}
