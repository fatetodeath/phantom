package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.input.KeyStateEvent;
import me.phantom.event.TickEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Box;
@ModuleMetadata(
   x = -4.5,
   y = -60.0,
   z = -18.5
)
public class ObstacleSpeedModule extends BaseModule {
   public int ticksRemaining;

   public ObstacleSpeedModule() {
      super(ModuleCategory.wld, ClientPlayStyle.zfw, GlyphTextUtil.encode("ObstacleSpeed"));
      this.markClipboardHint();
   }

   public void tjy(TickEvent var1) {
      ClientPlayerEntity var2 = MinecraftClient.getInstance().player;
      if (this.ticksRemaining > 0) {
         this.ticksRemaining--;
      }

      if (var2.isOnGround() && !(var2.forwardSpeed <= 0.0F) && !var2.isSneaking()) {
         Box var3 = var2.getBoundingBox();
         if (MinecraftClient.getInstance()
            .world
            .getBlockCollisions(var2, new Box(var3.minX, var3.maxY, var3.minZ, var3.maxX, var3.maxY + 0.7, var3.maxZ))
            .iterator()
            .hasNext()) {
            var2.jump();
            this.ticksRemaining = 5;
         }
      }
   }

   @Override
   public boolean ma() {
      return this.ticksRemaining > 0;
   }

   public void sau(KeyStateEvent var1) {
      if (var1.by().getName().equals(GlyphTextUtil.plain("key.sprint")) && this.ticksRemaining > 0) {
         var1.ywz(true);
      }
   }
}
