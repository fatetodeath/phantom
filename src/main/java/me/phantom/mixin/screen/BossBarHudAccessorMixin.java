package me.phantom.mixin.screen;

import me.phantom.util.mc.BossBarsAccessor;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({BossBarHud.class})
public class BossBarHudAccessorMixin implements BossBarsAccessor {
   @Shadow
   @Final
   private Map<UUID, ClientBossBar> bossBars;

   @Override
   public Map<UUID, ClientBossBar> getBossBars() {
      return this.bossBars;
   }
}
