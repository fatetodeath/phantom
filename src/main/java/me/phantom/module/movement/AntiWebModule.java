package me.phantom.module.movement;

import me.phantom.core.module.ClientPlayStyle;
import me.phantom.core.module.ModuleCategory;
import me.phantom.core.module.ModuleMetadata;
import me.phantom.event.world.BlockCollisionShapeEvent;
import me.phantom.module.base.BaseModule;
import me.phantom.util.GlyphTextUtil;

import net.minecraft.block.CobwebBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.shape.VoxelShapes;

@ModuleMetadata(
   x = -20.5,
   y = -60.0,
   z = 5.5
)
public class AntiWebModule extends BaseModule {
   public AntiWebModule() {
      super(ModuleCategory.wld, ClientPlayStyle.ats, GlyphTextUtil.encode("AntiWeb"));
      this.markClipboardHint();
   }

   public void olx(BlockCollisionShapeEvent var1) {
      if (var1.kp() instanceof CobwebBlock && MinecraftClient.getInstance().player.isOnGround() && MinecraftClient.getInstance().player.getVelocity().y <= 0.0) {
         var1.yvk(VoxelShapes.fullCube());
      }
   }
}
