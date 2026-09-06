package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;
import me.phantom.util.mc.JumpCooldownAccessor;

import net.minecraft.client.Minecraft;
@ModuleMetadata(
   x = 21.5,
   y = -60.0,
   z = -4.5
)
public class NoJumpDelayModule extends BaseModule {
   public NoJumpDelayModule() {
      super(ModuleCategory.wld, ClientPlayStyle.bq, GlyphTextUtil.encode("NoJumpDelay"));
   }

   public void tjy(TickEvent var1) {
      ((JumpCooldownAccessor) Minecraft.getInstance().player).setJumpingCooldown(0);
   }
}
